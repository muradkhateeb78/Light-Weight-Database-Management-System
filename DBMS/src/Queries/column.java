package Queries;

import java.io.Serializable;

public class column implements Serializable{
	public String columnName;
	public String columnType;
	public column(String columnName,String columbType){
		this.columnName=columnName;
		this.columnType=columbType;
	}
}
