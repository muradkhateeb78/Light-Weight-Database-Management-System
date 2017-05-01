package Queries;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalogue implements Serializable{
	
	/*
	 * This class holds the list of all the databases inside the database class.
	 */
	
	public ArrayList<database> databases;
	public Catalogue(){
		databases=new ArrayList<>();
	}
}
