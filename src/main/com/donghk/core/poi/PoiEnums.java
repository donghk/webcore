package com.donghk.core.poi;

/**
 * 
 * @author: dong.haikang@fescoadecco.com
 * @date: 2015年7月15日
 * @Description poi枚举
 */
public class PoiEnums {
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description cell数据类型
	 */
	public enum CellDataType {

		AUTO("auto"),
		VARCHAR("varchar"),
		NUMBER("number"),
		DATE("date");
		
		String value;  
		
		private CellDataType( String value ) {  
			this.value = value;  
	    }  
		
		public String getValue() {
			return value;
		}
		
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description cell规则
	 */
	public enum CellRule {
		
		REQUIRED("required"),
		EQUALSTO("equalsTo");
		
		String value;  
		
		private CellRule( String value ) {  
			this.value = value;  
	    }

		public String getValue() {
			return value;
		}
		
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 是否为常量
	 */
	public enum CellIsStatic {
		
		TRUE(true),
		FALSE(false);
		
		boolean value;  
		
		private CellIsStatic( boolean value ) {  
			this.value = value;  
	    }  
		
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 是否为固定列
	 */
	public enum CellIsFixed {
		
		TRUE(true),
		FALSE(false);
		
		boolean value;  
		
		private CellIsFixed( boolean value ) {  
			this.value = value;  
	    }  
		
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 是否为数据字典
	 */
	public enum CellIsBaseData {
		
		TRUE(true),
		FALSE(false);
		
		boolean value;  
		
		private CellIsBaseData( boolean value ) {  
			this.value = value;  
	    }  
		
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月16日
	 * @Description 是否在导出错误信息时显示
	 */
	public enum CellIsErrorDisplay {
		
		TRUE(true),
		FALSE(false);
		
		boolean value;  
		
		private CellIsErrorDisplay( boolean value ) {  
			this.value = value;  
	    }  
		
	}
	

}
