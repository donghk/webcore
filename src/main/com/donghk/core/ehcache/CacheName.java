package com.donghk.core.ehcache;

public enum CacheName {
	DATADICTCACHE("DataDictCache"),
	MENUCACHE("MenuCache");
	
	String name;  
	
	private CacheName( String name ) {  
		this.name = name;  
    }  
}
