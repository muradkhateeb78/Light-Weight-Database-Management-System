package Queries;

import java.io.Serializable;

public class column implements Serializable{
	
	/*
	 * This class holds the column names and their data types in it.
	 */
	
	public String columnName;
	public String columnType;
	public column(String columnName,String columbType){
		this.columnName=columnName;
		this.columnType=columbType;
	}
}
