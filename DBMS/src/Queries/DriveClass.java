package Queries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import Btree.Btree;

public class DriveClass {
	public static final String workingFolder="F:\\Namal_Database_Managment_System";
	public static Catalogue DBMS;
	public static database currentDatabase=null;
	public static ArrayList<BtreeTable> currentTables=new ArrayList<>();
	public DriveClass(){
		try {
			File f=new File(workingFolder+"\\metadata.dat");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			DBMS=(Catalogue)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveDataInHard(){
		try {
			write_Catalogue();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new BTree2().updateRoots();
	}
	public void write_Catalogue() throws FileNotFoundException, IOException{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(workingFolder+"\\metadata.dat")));
		oos.writeObject(DBMS);
		oos.close();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
//		DriveClass driver=new DriveClass();
//		System.out.println("AA GAYA HAY"+DBMS.databases.size());
		System.out.println("Welcome to Lite Database Management System!");
		Scanner input = new Scanner(System.in);
		System.out.println("Start entering queries below.");
		System.out.print("LDBMS?-> ");
		String query = input.nextLine();
		query = query.replaceAll("[ ]+"," ");
		while(!query.equals("exit;")){
			if(query.charAt(0) == ' '){
				query = query.substring(1, query.length());
			}
			if(!query.matches("[ ]*")){
				QueryValidator qv = new QueryValidator(query.toLowerCase());
				if(qv.isQueryValid()){
					System.out.println("Valid Query!");
					QueryParser qp = new QueryParser(query.replaceAll(";", "").toLowerCase());
					qp.executeQuery();
				}else{
					System.out.println("Invalid query!");
					System.out.println(qv.queryError());
				}
			}
			System.out.print("LDBMS?-> ");
			query = input.nextLine();
			query = query.replaceAll("[ ]+"," ");	
		}
		input.close();
		System.out.println("Exiting LDBMS!");
		//driver.saveDataInHard();
	}
}