package LDBMSADMINGUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import Queries.BTree2;
import Queries.BtreeTable;
import Queries.Catalogue;
import Queries.InsertQueryTokens;
import Queries.QueryParser;
import Queries.QueryValidator;
import Queries.database;

public class DriveClass implements Runnable {

	/*
	 * This should be the name of your starting folder. All of your databases and tables
	 * folders and all the files will be stored over here.
	 */

	public static final String workingFolder="F:\\Namal_Database_Managment_System";
	public static Catalogue DBMS;
	public static database currentDatabase=null;
	public static ArrayList<BtreeTable> currentTables=new ArrayList<>();

	public static void performExitingTasks(){
		saveDataInHard();
	}

	private static void saveDataInHard(){
		try {
			write_Catalogue();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new BTree2().updateRoots();
	}

	private static void write_Catalogue() throws FileNotFoundException, IOException{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(workingFolder+"\\metadata.dat")));
		oos.writeObject(DBMS);
		oos.close();
	}
	private void initializeDBMS(){
		try {
			File f=new File(workingFolder+"\\metadata.dat");
			if(!f.exists()){
				f.createNewFile();
				DBMS=new Catalogue();
			}
			else{
				ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
				DBMS=(Catalogue)ois.readObject();
				ois.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		new DBMSGUI(new DBMSModel());
		initializeDBMS();
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new DriveClass());  
	}
}