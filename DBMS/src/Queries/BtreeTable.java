package Queries;
import Btree.Btree;
public class BtreeTable {
	public String tableName;
	public Btree btree;
	public BtreeTable(String tableName,Btree btree){
		this.tableName=tableName;
		this.btree=btree;
	}

}
