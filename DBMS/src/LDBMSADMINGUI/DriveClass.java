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
import Queries.QueryParser;
import Queries.QueryValidator;
import Queries.database;

public class DriveClass implements Runnable {
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
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			DBMS=(Catalogue)ois.readObject();
			ois.close();
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
    	System.err.println("Called");
        new DBMSGUI(new DBMSModel());
        initializeDBMS();
        System.out.println(DriveClass.currentDatabase);
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DriveClass());
    }
}