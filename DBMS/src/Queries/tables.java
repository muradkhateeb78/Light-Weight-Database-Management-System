package Queries;

import java.io.Serializable;

public class tables implements Serializable{
	
	/*
	 * This class contains the information of the table. What names is. What primary key is. What are the columns
	 * and the names of them and their data type.
	 */
	
	public String tableName;
	public int primarayKeyIndex;
	public column columns[];
	public tables(String tableName,int noOfColumns,int primaryKeyIndex){
		this.tableName=tableName;
		columns=new column[noOfColumns];
		this.primarayKeyIndex=primaryKeyIndex;
	}
}
