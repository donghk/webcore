package com.donghk.core.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.ArrayUtils;

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class JsonToBean {

	public static <T> List<T> json2List(String jsonStr, Class<T> beanClass) {
		return json2List(jsonStr, beanClass, new String[] {
				"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
	}

	public static <T> List<T> json2List(String jsonStr, Class<T> beanClass,
			String[] dateFormats) {
		JSONArray array = JSONArray.fromObject(jsonStr);
		Object[] objects = array.toArray();
		List<T> list = new ArrayList<T>();
		for (Object object : objects) {
			T bean = json2Bean(object, beanClass, dateFormats);
			list.add(bean);
		}
		return list;
	}

	public static <T> T json2Bean(Object obj, Class<T> beanClass) {
		return json2Bean(obj, beanClass, new String[] { "yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd" });
	}

	public static <T> T json2Bean(Object obj, Class<T> beanClass,
			String[] dateFormats) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
		T bean = (T) JSONObject.toBean(jsonObject, beanClass);
		Field[] fields = beanClass.getDeclaredFields();
		for (Class clazz = beanClass; clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			fields = (Field[]) ArrayUtils.addAll(fields,
					clazz.getDeclaredFields());
		}
		for (Field field : fields) {
			if (field.getType() == Date.class) {
				if (jsonObject.get(field.getName()) != null) {
					try {
						Method method = beanClass.getMethod("set"
								+ toFirstLetterUpperCase(field.getName()),
								new Class[] { Date.class });
						method.invoke(
								bean,
								str2Date((String) jsonObject.get(field
										.getName()), dateFormats));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (field.getType() == Timestamp.class) {
				if (jsonObject.get(field.getName()) != null) {
					try {
						Method method = beanClass.getMethod("set"
								+ toFirstLetterUpperCase(field.getName()),
								new Class[] { Timestamp.class });
						method.invoke(bean, str2Timestamp((String) jsonObject
								.get(field.getName())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bean;
	}

	private static Date str2Date(String str, String[] dateFormats) {
		for (String formatStr : dateFormats) {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			try {
				return sdf.parse(str);
			} catch (ParseException e) {
				continue;
			}
		}
		return null;
	}

	private static Timestamp str2Timestamp(String str) {
		return Timestamp.valueOf(str);
	}

	private static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}

	public static Object beanCopy(Object sourceBean, Object targetBean) {
		Class sourceClazz = sourceBean.getClass();
		Class targetClazz = targetBean.getClass();
		Field[] fields = sourceClazz.getDeclaredFields();
		for (Class clazz = sourceClazz; clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			fields = (Field[]) ArrayUtils.addAll(fields,
					clazz.getDeclaredFields());
		}

		for (int i = 0; i < fields.length; i++) {
			try {
				Method sourceMethod = sourceClazz.getMethod("get"
						+ toFirstLetterUpperCase(fields[i].getName()));
				Object obj = sourceMethod.invoke(sourceBean);
				Method targetMethod = sourceClazz.getMethod("set"
						+ toFirstLetterUpperCase(fields[i].getName()));
				targetMethod.invoke(targetBean, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return targetBean;
	}

	public static Map parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}
}
