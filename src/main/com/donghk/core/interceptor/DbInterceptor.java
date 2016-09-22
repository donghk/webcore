package com.donghk.core.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.donghk.core.dialect.Dialect;
import com.donghk.core.dialect.MySql5Dialect;
import com.donghk.core.dialect.OracleDialect;
import com.donghk.core.permission.PermissionSQLUtils;
import com.donghk.core.util.EmptyUtil;
import com.donghk.core.util.LoginUtils;
import com.donghk.entity.BaseEntity;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DbInterceptor implements Interceptor {

	private final static Logger log = LoggerFactory.getLogger(DbInterceptor.class);

	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	private static final ThreadLocal<Integer> RESULT_COUNT_TL = new ThreadLocal<Integer>();

	public static void setResultCount(int count) {
		RESULT_COUNT_TL.set(count);
	}

	public static int getResultCount() {
		return RESULT_COUNT_TL.get();
	}

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();

		// log.info("SQL:" + boundSql.getSql());

		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

		SqlCommandType type = mappedStatement.getSqlCommandType();

		// 在insert和update时把操作人的信息输入
		if (!SqlCommandType.SELECT.equals(type)) {
			Date sysDate = new Date();
			if (SqlCommandType.UPDATE.equals(type)) {
				if (boundSql.getParameterObject() instanceof BaseEntity) {
					BaseEntity entity = (BaseEntity) boundSql.getParameterObject();
					if (LoginUtils.isLogin()) {
						entity.setUpdateUserId(LoginUtils.getLoginUserId());
						entity.setUpdateUserName(LoginUtils.getLoginUserRealName());
					}
					entity.setUpdateDate(sysDate);
				} else if (boundSql.getParameterObject() instanceof Map) {
					Map map = (Map) boundSql.getParameterObject();
					if (LoginUtils.isLogin()) {
						map.put("updateUserId", LoginUtils.getLoginUserId());
						map.put("updateUserName", LoginUtils.getLoginUserRealName());
					}
					map.put("updateDate", sysDate);
				}
			}
			if (SqlCommandType.INSERT.equals(type)) {
				if (boundSql.getParameterObject() instanceof BaseEntity) {
					BaseEntity entity = (BaseEntity) boundSql.getParameterObject();
					if (LoginUtils.isLogin()) {
						entity.setCreateUserId(LoginUtils.getLoginUserId());
						entity.setCreateUserName(LoginUtils.getLoginUserRealName());
					}
					entity.setCreateDate(sysDate);
				} else if (boundSql.getParameterObject() instanceof Map) {
					Map map = (Map) boundSql.getParameterObject();
					if (LoginUtils.isLogin()) {
						map.put("createUserId", LoginUtils.getLoginUserId());
						map.put("createUserName", LoginUtils.getLoginUserRealName());
					}
					map.put("createDate", sysDate);
				}
			}
			return invocation.proceed();
		} else {
			// 对查询SQL进行权限控制
			PermissionSQLUtils.addPermission(metaStatementHandler);
		}
		log.debug(boundSql.getSql().replace('\n', ' ')); // 打印查询SQL

		Object obj = boundSql.getParameterObject();
		if (!(obj instanceof Map) || EmptyUtil.isEmpty(obj)) {
			return invocation.proceed();
		}

		RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");

		Map<String, Object> parms = (Map<String, Object>) obj;

		Integer offset = null;
		Integer limit = null;

		if (parms.containsKey("offset") && parms.containsKey("limit")) {
			offset = (Integer) parms.get("offset");
			limit = (Integer) parms.get("limit");
		} else {
			log.debug(boundSql.getSql().toString().replace('\n', ' '));
			return invocation.proceed();
		}

		if ((rowBounds == null || rowBounds == RowBounds.DEFAULT) && (offset == null || limit == null)) {
			return invocation.proceed();
		}

		Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");

		Dialect.Type databaseType = null;
		try {
			databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
		} catch (Exception e) {
			// ignore
		}
		if (databaseType == null) {
			throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : " + configuration.getVariables().getProperty("dialect"));
		}

		Dialect dialect = null;
		switch (databaseType) {
		case MYSQL:
			dialect = new MySql5Dialect();
			break;
		case ORACLE:
			dialect = new OracleDialect();
			break;
		}

		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		String countSql = this.generateCountSQL(originalSql);

		Connection conn = configuration.getEnvironment().getDataSource().getConnection();
		log.debug(countSql.toString().replace('\n', ' ')); // 打印查询总记录数的SQL
		PreparedStatement countStmt = conn.prepareStatement(countSql);
		setParameters(countStmt, configuration, boundSql, parms);
		ResultSet rs = countStmt.executeQuery();
		int totpage = 0;
		if (rs.next()) {
			totpage = rs.getInt(1);
		}
		rs.close();
		countStmt.close();
		conn.close();

		setResultCount(totpage);

		metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, offset, limit));
		//log.info(boundSql.getSql().replace('\n', ' ')); // 打印查询SQL
		metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		if (log.isDebugEnabled()) {
			log.debug("进入分页拦截器：生成的SQL为: " + boundSql.getSql());
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

	private void setParameters(PreparedStatement ps, Configuration configuration, BoundSql boundSql, Object parameterObject) throws SQLException {
		// ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			// Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement ");
					}
					// 删除字符串的前后空格问题
					if (value != null && value instanceof String) {
						value = ((String) value).trim();
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	/*
	 * 将原始SQL转换为count(*)，除了union，其他的SQL直接将select后面的内容替换为count(*) 特殊情况不支持：
	 * 在from前的引号间出现 ( ) 且正反括号的数量不相等，会造成执行出错。 例如select a, (select a from t where
	 * a = ')') from t, (select a from t)会执行出错。
	 */
	private String generateCountSQL(String originalSql) {
		StringBuffer countSql = new StringBuffer();
		String lowerSql = originalSql.toLowerCase();

		// 判断order的语句是否能去除
		int orderIdx = lowerSql.lastIndexOf("order");
		if (orderIdx != -1 && lowerSql.lastIndexOf(')') < orderIdx && lowerSql.lastIndexOf("limit") < orderIdx) {
			originalSql = originalSql.substring(0, orderIdx);
			lowerSql = lowerSql.substring(0, orderIdx);
		}

		// 如果是destinct则不能直接将select后的内容改为count(*)
		if (lowerSql.indexOf("distinct") != -1) {
			countSql.append("select count(*) from (").append(originalSql).append(") t");
			return countSql.toString();
		}

		int unionIdx = lowerSql.indexOf("union");
		String unionSub = lowerSql;
		while (unionIdx != -1) {// 循环查找每一个union
			unionSub = unionSub.substring(unionIdx + 5);
			// 判断union后面的()数量是否相等，如果相等说明union在外层，不能直接将select后的内容改为count(*)
			if (StringUtils.countMatches(unionSub, "(") == StringUtils.countMatches(unionSub, ")")) {
				countSql.append("select count(*) from (").append(originalSql).append(") t");
				return countSql.toString();
			} else {
				unionIdx = unionSub.indexOf("union");
			}
		}

		int groupByIdx = lowerSql.indexOf("group by");
		String groupBySub = lowerSql;
		while (groupByIdx != -1) {// 循环查找每一个group by
			groupBySub = groupBySub.substring(groupByIdx + 8);
			// 判断group by后面的()数量是否相等，如果相等说明group by在外层，不能直接将select后的内容改为count(*)
			if (StringUtils.countMatches(groupBySub, "(") == StringUtils.countMatches(groupBySub, ")")) {
				countSql.append("select count(*) from (").append(originalSql).append(") t");
				return countSql.toString();
			} else {
				groupByIdx = groupBySub.indexOf("union");
			}
		}

		// 将select后面的内容替换为count(*)
		int fromIdx = lowerSql.indexOf("from");
		String sub = lowerSql.substring(0, fromIdx);
		while (StringUtils.countMatches(sub, "(") != StringUtils.countMatches(sub, ")")) {
			fromIdx = lowerSql.indexOf("from", fromIdx + 4);
			if (fromIdx != -1) {
				sub = lowerSql.substring(0, fromIdx);
			} else {
				break;
			}
		}
		if (fromIdx != -1) {
			countSql.append("select count(*) ");
			countSql.append(originalSql.substring(fromIdx));
		} else {
			countSql.append("select count(*) from (").append(originalSql).append(") t");
		}

		return countSql.toString();
	}

}
