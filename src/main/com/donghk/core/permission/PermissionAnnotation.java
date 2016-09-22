package com.donghk.core.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月12日
 * @Description 注解式权限验证
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface PermissionAnnotation {
	
	//权限类型
	PermissionEnum permissionType();
	
	//需要传递的表ID
	String tableID();

}
