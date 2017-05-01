package Queries;
import Btree.Btree;
public class BtreeTable {
	
	/*
	 * This class is used to identify the table and hold the data.
	 */
	
	public String tableName;
	public Btree btree;
	public BtreeTable(String tableName,Btree btree){
		this.tableName=tableName;
		this.btree=btree;
	}
}