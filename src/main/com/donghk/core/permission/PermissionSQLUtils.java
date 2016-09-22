package com.donghk.core.permission;

import java.lang.reflect.Method;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.StringUtils;

import com.donghk.core.security.BaseUserDetails;
import com.donghk.core.util.LoginUtils;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月12日
 * @Description 拼接数据权限语句
 */
public class PermissionSQLUtils {
	
	public static String PERMISSION_SQL_1 = "SELECT resource_data.* FROM (";
	public static String PERMISSION_SQL_2 = ") resource_data, ( SELECT DISTINCT c.customer_id FROM (SELECT a.customer_id FROM PB_CUSTOMER_SERVICE A, PB_CUSTOMER PC, ( SELECT  /*+ INDEX (AU_USER_SCOPE  USER_SCOPE_MANAGED_USER_INDEX0) */ managed_user_id FROM  AU_USER_SCOPE WHERE MANAGER_USER_ID = '";
	public static String PERMISSION_SQL_3 = "' AND is_del = '0' ) b WHERE a.user_id = b.managed_user_id AND a.EFFECTIVE_DATE <= sysdate AND (a.INVALID_DATE IS NULL OR a.INVALID_DATE >= sysdate) AND A.CUSTOMER_ID = PC.ID AND PC.IS_DEL = '0' AND PC.IS_VALID = '1' AND PC.IS_ZHONGLIANG = '100') c";
	public static String PERMISSION_SQL_4 = ") resource_data_power WHERE resource_data.";
	public static String PERMISSION_SQL_5 = " = resource_data_power.customer_id";
	
	
	public static String PERMISSION_SEND_SQL_1 = "SELECT resource_data.* FROM  (  ";
	public static String PERMISSION_SEND_SQL_2 = " ) resource_data, ( SELECT distinct c.send_id FROM (SELECT a.send_id FROM PB_CUSTOMER_SERVICE a, ( SELECT MANAGED_USER_ID FROM  AU_USER_SCOPE WHERE MANAGER_USER_ID = '";
	public static String PERMISSION_SEND_SQL_3 = "' AND IS_DEL = '0' ) b WHERE a.USER_ID = b.MANAGED_USER_ID AND a.EFFECTIVE_DATE <= SYSDATE AND (a.INVALID_DATE IS NULL OR a.INVALID_DATE >= SYSDATE)) c ";
	public static String PERMISSION_SEND_SQL_4 = ") resource_data_power WHERE resource_data.";
	public static String PERMISSION_SEND_SQL_5 = " = resource_data_power.send_id";

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月12日
	 * @Description 添加SQL权限
	 * @param metaStatementHandler
	 * @throws ClassNotFoundException
	 */
	public static void addPermission(MetaObject metaStatementHandler) throws ClassNotFoundException{
		//如果没有登录，则不添加权限SQL
		if(!LoginUtils.isLogin()){
			return;
		}
		//获取登录用户的登录名
		BaseUserDetails user = LoginUtils.getCurrentUser();
		if(!StringUtils.isEmpty(user.getUsername())){
			//如果是ROLE_SUPER_ADMIN和ROLE_OS，则不添加权限SQL
			if (user.getUsername().equalsIgnoreCase("admin")) {
				return;
			}
		}
		
		MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");
		String mappedStatementId = mappedStatement.getId();
		int indexClassName = mappedStatementId.lastIndexOf(".");
		//利用反射获得方法的annotation，方法有PermissionAnnotation则进行SQL的权限控制
		Class<?> mapperClass = Class.forName(mappedStatementId.substring(0, indexClassName));
		Method[] methods = mapperClass.getMethods();
		for(Method m : methods){
			if(m.getName().equals(mappedStatementId.substring(indexClassName + 1))){
				PermissionAnnotation permissionAnnotation = m.getAnnotation(PermissionAnnotation.class);
				if(permissionAnnotation != null) {
					String sql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
					sql = sql.replaceAll("[\\n\\t]", " ").replaceAll(" +", " ");
					//int index = sql.toUpperCase().indexOf("SELECT") + 7;
					if (permissionAnnotation.permissionType().equals(PermissionEnum.customer)) {
						StringBuffer stringBufferSql = new StringBuffer();
						stringBufferSql.append(PermissionSQLUtils.PERMISSION_SQL_1)
								.append(sql)
								.append(PermissionSQLUtils.PERMISSION_SQL_2)
								.append(LoginUtils.getLoginUserId())
								.append(PermissionSQLUtils.PERMISSION_SQL_3)
								.append(PermissionSQLUtils.PERMISSION_SQL_4)
								.append(permissionAnnotation.tableID())
								.append(PermissionSQLUtils.PERMISSION_SQL_5);
						metaStatementHandler.setValue("delegate.boundSql.sql", stringBufferSql.toString());
						break;						
					}
					else if(permissionAnnotation.permissionType().equals(PermissionEnum.test)) {
						StringBuffer stringBufferSql = new StringBuffer();
						stringBufferSql.append(PermissionSQLUtils.PERMISSION_SEND_SQL_1)
								.append(sql)
								.append(PermissionSQLUtils.PERMISSION_SEND_SQL_2)
								.append(LoginUtils.getLoginUserId())
								.append(PermissionSQLUtils.PERMISSION_SEND_SQL_3)
								.append(PermissionSQLUtils.PERMISSION_SEND_SQL_4)
								.append(permissionAnnotation.tableID())
								.append(PermissionSQLUtils.PERMISSION_SEND_SQL_5);
						metaStatementHandler.setValue("delegate.boundSql.sql", stringBufferSql.toString());
						break;	
					}
				}
			}
		}
	}
}
