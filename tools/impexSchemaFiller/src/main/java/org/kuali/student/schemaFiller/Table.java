package org.kuali.student.schemaFiller;

import java.util.HashMap;

public class Table {
	private String dbTableName;
	private String javaTableName;
	private HashMap<String,String> fields = new HashMap<String,String>();
	private HashMap<String,String> fieldDescription = new HashMap<String,String>();
	private String dbFieldName;
	private String javaFieldName;
	private String tableDescription;
	private Table metaInfo;
	
	
	
	public String getdbTableName(){
		return dbTableName;
	}
	
	public void  setdbTableName(String dbTableName){
		this.dbTableName= dbTableName;
	}
	
	public String getjavaTableName(){
		return javaTableName;
	}
	
	public void  setjavaTableName(String javaTableName){
		this.javaTableName= javaTableName;
	}
	
	public String getdbFieldName(){
		return dbFieldName;
	}
	
	public void  setdbFieldName(String dbFieldName){
		this.dbFieldName= dbFieldName;
	}
	
	public String getjavaFieldName(){
		return javaFieldName;
	}
	
	public void  setjavaFieldName(String javaFieldName){
		this.javaFieldName= javaFieldName;
	}
	
	public String gettableDescription(){
		return tableDescription;
	}
	
	public void  settableDescription(String tableDescription){
		this.tableDescription= tableDescription;
	}
	
	public HashMap<String,String> getfieldDescription(){
		return fieldDescription;
	}
	
	public void  setfieldDescription(HashMap<String,String> fieldDescription){
		this.fieldDescription= fieldDescription;
	}
	
	public HashMap getfields(){
		return fields;
	}
	
	public void setfields(HashMap fields){
		this.fields=fields;
	}
	
	public Table getMetaInfo(){
		return metaInfo;
	}
	
	public void setMetaInfo(Table metaInfo){
		this.metaInfo=metaInfo;
	}
	
}
