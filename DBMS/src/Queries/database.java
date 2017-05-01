package Queries;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class database implements Serializable{
	
	/*
	 * This class holds the database tables in it for any particular database. It is a serialized class.
	 */
	
	public String databaseName;
	public ArrayList<tables> tables;
	public database(String databaseName){
		this.databaseName=databaseName;
		tables=new ArrayList<>();
	}
}
