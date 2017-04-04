package Queries;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class database implements Serializable{
	public String databaseName;
	public ArrayList<tables> tables;
	public database(String databaseName){
		this.databaseName=databaseName;
		tables=new ArrayList<>();
	}
}
