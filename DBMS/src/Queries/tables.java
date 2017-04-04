package Queries;

import java.io.Serializable;

public class tables implements Serializable{
	public String tableName;
	public int primarayKeyIndex;
	public column columns[];
	public tables(String tableName,int noOfColumns,int primaryKeyIndex){
		this.tableName=tableName;
		columns=new column[noOfColumns];
		this.primarayKeyIndex=primaryKeyIndex;
	}
}
