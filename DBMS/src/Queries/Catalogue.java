package Queries;
import java.io.Serializable;
import java.util.ArrayList;

public class Catalogue implements Serializable{
	public ArrayList<database> databases;
	public Catalogue(){
		databases=new ArrayList<>();
	}
}
