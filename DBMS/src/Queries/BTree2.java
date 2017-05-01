package Queries;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import Btree.Btree;
import LDBMSADMINGUI.DBMSGUI;
import LDBMSADMINGUI.DBMSNode;
import LDBMSADMINGUI.DriveClass;
import LDBMSADMINGUI.JTreeAndPane;

public class BTree2 {

	public BTree2(){}

	public void runQuery(Object tokens){
		if(tokens.getClass().getName().equals("Queries.SelectQueryTokens")){
			SelectQueryTokens sqt = (SelectQueryTokens) tokens;
			for(String s : sqt.getAttributeList()){
			}
			for(String s : sqt.getRelationList()){
			}
			for(Qualifiers s : sqt.getQualifierList()){
			}
			for(String s : sqt.getAndOr()){
			}
			selectQuery(sqt);
		}else if(tokens.getClass().getName().equals("Queries.InsertQueryTokens")){
			InsertQueryTokens iqt = (InsertQueryTokens) tokens;
			insertData(iqt);
		}else if(tokens.getClass().getName().equals("Queries.DeleteQueryTokens")){
			DeleteQueryTokens dqt = (DeleteQueryTokens) tokens;
			for(Qualifiers s : dqt.getQualifierList()){
			}
			for(String s : dqt.getAndOr()){
			}
			deleteQuery(dqt);
		}else if(tokens.getClass().getName().equals("Queries.UpdateQueryTokens")){
			UpdateQueryTokens uqt = (UpdateQueryTokens) tokens;
			for(Qualifiers s : uqt.getSettingValue()){
			}
			for(Qualifiers s : uqt.getQualifierList()){
			}
			for(String s : uqt.getAndOr()){
			}
			updateQuery(uqt);
		}else if(tokens.getClass().getName().equals("Queries.CreateTableTokens")){
			createTable((CreateTableTokens)tokens);
		}else if(tokens.getClass().getName().equals("java.lang.String")){
			String s = (String) tokens;
			if(s.contains("create")){
				createDatabase(s.substring(16, s.length()));
			}else if(s.contains("use")){
				useDatabase(s.substring(13, s.length()).toLowerCase());
			}else if(s.equals("show databases")){
				showDatabases();
			}else if(s.equals("show tables")){
				showTables();
			}else if(s.contains("drop database")){
				dropDatabase(s.replaceAll("drop database ", "").replace("'", ""));
			}else if(s.contains("drop table")){
				dropTable(s.replaceAll("drop table ", "").replace("'", ""));
			}else if(s.contains("describe table")){
				describeTable(s.replaceAll("describe table ", "").replace("'", ""));
			}
		}
	}

	/*
	 * This function shows the list of all the existing databases on the console. this function
	 * was used during the testing phase.
	 */

	public void showDatabases(){
		int j=1;
		if(DriveClass.DBMS.databases.size()==0){
		}
		for(database i:DriveClass.DBMS.databases){
			j++;
		}
	}

	/*
	 * This function shows the list of all the existing tables in the current databases on the console. this function
	 * was used during the testing phase.
	 */

	public void showTables(){
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error, "No database selected!");
			return;
		}
		else if(DriveClass.currentDatabase.tables.size()==0){
			return;
		}
		int j=1;
		for(tables t:DriveClass.currentDatabase.tables){
			j++;
		}
	}

	/*
	 * The following function creates a new table and initializes a new btree objects based
	 * on the values of the columns specified the user and sets the primary key in that table.
	 * This function also updates the list of catalogue conatining the list of databases and tables
	 * and the list of the tables residing in the currently selected datbase.
	 */

	private void createTable(CreateTableTokens ctt){
		//creating Table mechanism of BTree
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error,"No database selected!");
			return;
		}
		for(BtreeTable i:DriveClass.currentTables){
			if(i.tableName.equals(ctt.getRelationName())){
				DBMSGUI.setLabel(DBMSGUI.error,"A table with the same name already exists!");
				return;
			}
		}
		String folderPath=DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+ctt.getRelationName()+"\\";
		new File(folderPath).mkdirs();
		DBMSGUI.setLabel(DBMSGUI.confirmation,"The table has "+ctt.getRelationName()+" been created!");
		tables tb=new tables(ctt.getRelationName(),ctt.getValueList().size(),ctt.getPrimaryKey());
		DriveClass.currentDatabase.tables.add(tb);
		for(int i=0;i<tb.columns.length;i++){
			column c=new column(ctt.getValueList().get(i).getAttributeName(), ctt.getValueList().get(i).getAttributeType());
			tb.columns[i]=c;
		}

		Btree btree=new Btree(ctt.getPrimaryKey(),folderPath);
		BtreeTable bt=new BtreeTable(ctt.getRelationName(), btree);
		DriveClass.currentTables.add(bt);

		File f = new File(DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+ctt.getRelationName()+"\\");
		DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DBMSNode node = new DBMSNode(f);
		for(int i = 0; i < root.getFirstChild().getChildCount(); i++){
			if(root.getFirstChild().getChildAt(i).toString().equals(DriveClass.currentDatabase.databaseName)){
				((DefaultMutableTreeNode) root.getFirstChild().getChildAt(i)).add(new DefaultMutableTreeNode(node));
				model.reload();
				JTreeAndPane.tree.expandPath(new TreePath(model.getPathToRoot(root.getFirstChild().getChildAt(i))));

			}
		}
	}

	/*
	 * This function creates a new database with the name specified by the user in th SQL query
	 * This function applies various checks and criterions needed for a query to qualify the creation of
	 * a database. This function also updates catalogue containing the list of databases and tables.
	 */

	private void createDatabase(String dbName){
		dbName = dbName.toLowerCase();
		for(database i:DriveClass.DBMS.databases){
			if(i.databaseName.equals(dbName)){
				DBMSGUI.setLabel(DBMSGUI.error, "A database with the same name already exists!");
				return;
			}
		}
		database db=new database(dbName);
		DriveClass.DBMS.databases.add(db);
		new File(DriveClass.workingFolder+"\\"+dbName).mkdirs();
		DBMSGUI.setLabel(DBMSGUI.confirmation, "The database "+dbName+" has been created!");
		File f = new File(DriveClass.workingFolder+"\\"+dbName);
		DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DBMSNode node = new DBMSNode(f);
		((DefaultMutableTreeNode) root.getFirstChild()).add(new DefaultMutableTreeNode(node));
		model.reload();
		for(int i = 0; i < root.getFirstChild().getChildCount(); i++){
		}
	}

	/*
	 * The following function updates the values of various tables listed in the list of currently
	 * used database. This function updates the "root.dat" files of all the tables listed in the currently
	 * used database.
	 */

	public void updateRoots(){
		if(DriveClass.currentDatabase==null){
			return;
		}
		if(DriveClass.currentTables.size()>0){
			for(BtreeTable j:DriveClass.currentTables){
				File f=new File(DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+j.tableName+"\\metadata.dat");
				if(!f.exists()){
					try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ObjectOutputStream oos=null;
				try {
					oos=new ObjectOutputStream(new FileOutputStream(f));
					oos.writeObject(j.btree);
					oos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Before performing any operations on the given data, the user will have to use that database.
	 * This function servers the same purpose and updates the value of that particular database, it
	 * also initiallizes few of the data structures so that they can be accessible when the user
	 * want to manipulate the data in any of the tables listed in the current database.
	 */

	public void useDatabase(String dbName){
		if(dbName==null)return;
		dbName = dbName.toLowerCase();
		updateRoots();
		for(database i:DriveClass.DBMS.databases){
			if(i.databaseName.equals(dbName)){
				DriveClass.currentDatabase=i;
				DriveClass.currentTables=new ArrayList<>();
				if(i.tables.size()>0){
					for(tables j:i.tables){
						File f=new File(DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+j.tableName+"\\metadata.dat");
						ObjectInputStream ois=null;
						Btree btree=null;
						try {
							ois = new ObjectInputStream(new FileInputStream(f));
							btree=(Btree)ois.readObject();
							ois.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						DriveClass.currentTables.add(new BtreeTable(j.tableName, btree));
					}
				}
				DBMSGUI.setLabel(DBMSGUI.confirmation,"Database "+dbName+" used!");
				DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
				for(int ii = 0; ii < root.getFirstChild().getChildCount(); ii++){
					if(root.getFirstChild().getChildAt(ii).toString().equals(DriveClass.currentDatabase.databaseName)){
						JTreeAndPane.tree.expandPath(new TreePath(model.getPathToRoot(root.getFirstChild().getChildAt(ii))));
					}else{
						JTreeAndPane.tree.collapsePath((new TreePath(model.getPathToRoot(root.getFirstChild().getChildAt(ii)))));
					}
				}
				return;
			}
		}
		DBMSGUI.setLabel(DBMSGUI.error, "No such database exists!");
	}

	/*
	 * This function validates the inputted data and checks whether the values given by the user
	 * fullfills certain criterion of the insertion of the data into any particular tables.
	 * If the given data is correct in each and every sort, the function will return a true and false
	 * otherwise. 
	 */

	public boolean insertDataCorrect(InsertQueryTokens iqt,tables ct){
		tables currentTable = ct;
		if(currentTable.columns.length != iqt.getValues().size()){
			return false;
		}else if(currentTable.columns.length == iqt.getValues().size()){
			for(int i = 0; i < currentTable.columns.length; i++){
				String regex="[0-9]+";
				if(currentTable.columns[i].columnType.equals("int") && iqt.getValues().get(i).matches(regex) || 
						currentTable.columns[i].columnType.equals("string") && iqt.getValues().get(i).getClass().getName().equals("java.lang.String")){
				}
				else{
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * This function will insert the data given by the user into any one of the tables in the currently
	 * used database, if the data does not pass the criterion to be able to be inserted into the
	 * table, the user will be prompted.
	 */

	public void insertData(InsertQueryTokens iqt){
		tables currentTable = null;
		boolean correct = false;
		for(tables table : DriveClass.currentDatabase.tables){
			if(table.tableName.equals(iqt.getRelation())){
				correct=true;
				currentTable = table;
				break;
			}
		}
		if(correct==false){
			DBMSGUI.setLabel(DBMSGUI.error, "The table does not exist!");
			return;
		}
		if(DriveClass.currentDatabase == null){
			DBMSGUI.setLabel(DBMSGUI.error, "No database was selected!");
		}else{
			if(insertDataCorrect(iqt,currentTable)){
				for(BtreeTable bt:DriveClass.currentTables){
					if(bt.tableName.equals(iqt.getRelation().toLowerCase())){
						if(bt.btree.searchKey(Integer.parseInt(iqt.getValues().get(currentTable.primarayKeyIndex)))!=null){
							DBMSGUI.setLabel(DBMSGUI.error, "There cannot be a duplicate primary key for a table!");
							return;
						}
						ArrayList<Object> arr = new ArrayList<>();
						for(int i = 0; i < iqt.getValues().size(); i ++){
							if(currentTable.columns[i].columnType.equals("int")){
								arr.add(Integer.parseInt(iqt.getValues().get(i)));
							}
							else{
								arr.add(iqt.getValues().get(i));
							}
						}
						Btree btree=bt.btree;
						btree.insert(arr);
						DBMSGUI.setLabel(DBMSGUI.confirmation, "The data has been added successfully!");
					}
				}
			}
			else{
				DBMSGUI.setLabel(DBMSGUI.error, "The data is not correct!");
			}
		}
	}

	/*
	 * The following function will search the data into the any one of the table of the currently
	 * used databases. The data is searched into the b+ tree based on the various arguments given by the
	 * users in their queries. This function uses the search function implemented in the b+ tree
	 * to efficiently search the requested data into the table.
	 */

	public ArrayList<ArrayList> searchQuery(String tableName, ArrayList<Qualifiers> qualifiers,ArrayList<String> AndOr){
		for(BtreeTable i:DriveClass.currentTables){
			if(tableName.equals(i.tableName)){
				if(qualifiers.size()==0){
					ArrayList<ArrayList> data=i.btree.selectStaric();
					if(data==null){
						DBMSGUI.setLabel(DBMSGUI.error, "No enteries were found in the database");
						return null;
					}
					return data;
				}
				else{
					tables currentTable=null;
					for(tables table:DriveClass.currentDatabase.tables){
						if(table.tableName.equals(tableName)){
							currentTable=table;
							break;
						}
					}
					boolean hasotherthanprimarykey=false;
					for(Qualifiers qf:qualifiers){
						if(mapOnIndex(currentTable,qf.getAttribute1())!=currentTable.primarayKeyIndex){
							hasotherthanprimarykey=true;
							break;
						}
					}
					if(hasotherthanprimarykey==true){
						ArrayList<ArrayList> data=i.btree.selectStaric();
						ArrayList<ArrayList> dataToReturn=new ArrayList<>();
						ArrayList<Boolean> booleansArray=null;
						boolean result = false;
						for(ArrayList tuple:data){
							booleansArray=new ArrayList<>();
							for(Qualifiers q:qualifiers){
								int index=mapOnIndex(currentTable,q.getAttribute1());
								if(q.getOperator().equals("=")){
									if(tuple.get(index).getClass().getName().equals("java.lang.Integer")){
										booleansArray.add(tuple.get(index).equals(Integer.parseInt(q.getAttribute2())));
									}
									else{
										booleansArray.add(tuple.get(index).equals(q.getAttribute2()));
									}
								}
								else if(q.getOperator().equals("<")){
									if(tuple.get(index).getClass().getName().equals("java.lang.Integer")){
										booleansArray.add((Integer)tuple.get(index)<Integer.parseInt((q.getAttribute2())));
									}
								}
								else if(q.getOperator().equals(">")){
									if(tuple.get(index).getClass().getName().equals("java.lang.Integer")){
										booleansArray.add((Integer)tuple.get(index)>Integer.parseInt((q.getAttribute2())));
									}
								}
								else if(q.getOperator().equals("<=")){
									if(tuple.get(index).getClass().getName().equals("java.lang.Integer")){
										booleansArray.add((Integer)tuple.get(index)<=Integer.parseInt((q.getAttribute2())));
									}
								}
								else if(q.getOperator().equals(">=")){
									if(tuple.get(index).getClass().getName().equals("java.lang.Integer")){
										booleansArray.add((Integer)tuple.get(index)>=Integer.parseInt((q.getAttribute2())));
									}
								}
							}
							result=false;
							int iterator=0;
							if(AndOr.size()>0){
								if(AndOr.get(0).equals("and")){
									result=booleansArray.get(0) && booleansArray.get(1);
								}
								else if(AndOr.get(0).equals("or")){
									result=booleansArray.get(0) || booleansArray.get(1);
								}
								iterator=1;
								for(;iterator<AndOr.size();iterator++){
									if(AndOr.get(iterator).equals("and")){
										result=result && booleansArray.get(iterator+1);
									}
									else if(AndOr.get(iterator).equals("or")){
										result=result || booleansArray.get(iterator);
									}
								}
							}
							else{
								result=booleansArray.get(0);
							}
							if(result==true){
								dataToReturn.add(tuple);
							}
							else{
								continue;
							}
						}
						if(dataToReturn.size()==0){
							return null;
						}
						else{
							return dataToReturn;
						}
					}
					else{
						ArrayList<ArrayList> dataToReturn=new ArrayList<>();
						if(AndOr.size()==0){
							if(qualifiers.get(0).getOperator().equals("=")){
								ArrayList<Object> arr=i.btree.searchKey(Integer.parseInt(qualifiers.get(0).getAttribute2()));
								if(arr==null){
									return null;
								}
								else{
									dataToReturn.add(arr);
								}
							}
							else if(qualifiers.get(0).getOperator().equals("<")){
								dataToReturn=i.btree.rangeSearchForLessThan(i.btree.root, 0, Integer.parseInt(qualifiers.get(0).getAttribute2()));
							}
							else if(qualifiers.get(0).getOperator().equals("<=")){
								dataToReturn=i.btree.rangeSearchForLessThan(i.btree.root, 1, Integer.parseInt(qualifiers.get(0).getAttribute2()));
							}
							else if(qualifiers.get(0).getOperator().equals(">")){
								dataToReturn=i.btree.rangeSearchForGreaterThan(i.btree.root, 0, Integer.parseInt(qualifiers.get(0).getAttribute2()));
							}
							else if(qualifiers.get(0).getOperator().equals(">=")){
								dataToReturn=i.btree.rangeSearchForGreaterThan(i.btree.root, 1, Integer.parseInt(qualifiers.get(0).getAttribute2()));
							}
							return dataToReturn;
						}
						boolean hasOtherThanEqual=false;
						for(Qualifiers qf:qualifiers){
							if(qf.getOperator().equals("=")==false){
								hasOtherThanEqual=true;
							}
						}
						if(hasOtherThanEqual){
							if(AndOr.size()==1 && AndOr.get(0).equals("and") && ((qualifiers.get(0).getOperator().equals(">")
									&& qualifiers.get(1).getOperator().equals("<")) || (qualifiers.get(0).getOperator().equals(">")
											&& qualifiers.get(1).getOperator().equals("<=")) || (qualifiers.get(0).getOperator().equals(">=")
													&& qualifiers.get(1).getOperator().equals("<")) || (qualifiers.get(0).getOperator().equals(">=")
															&& qualifiers.get(1).getOperator().equals("<=")))){
								if((qualifiers.get(0).getOperator().equals(">")
										&& qualifiers.get(1).getOperator().equals("<"))){
									dataToReturn=i.btree.rangeSearch(i.btree.root, 0, Integer.parseInt(qualifiers.get(0).getAttribute2()),0,Integer.parseInt(qualifiers.get(1).getAttribute2()));
								}
								else if((qualifiers.get(0).getOperator().equals(">")
										&& qualifiers.get(1).getOperator().equals("<="))){
									dataToReturn=i.btree.rangeSearch(i.btree.root, 0, Integer.parseInt(qualifiers.get(0).getAttribute2()),1,Integer.parseInt(qualifiers.get(1).getAttribute2()));
								}
								else if((qualifiers.get(0).getOperator().equals(">=")
										&& qualifiers.get(1).getOperator().equals("<"))){
									dataToReturn=i.btree.rangeSearch(i.btree.root, 1, Integer.parseInt(qualifiers.get(0).getAttribute2()),0,Integer.parseInt(qualifiers.get(1).getAttribute2()));
								}
								if((qualifiers.get(0).getOperator().equals(">=")
										&& qualifiers.get(1).getOperator().equals("<="))){
									dataToReturn=i.btree.rangeSearch(i.btree.root, 1, Integer.parseInt(qualifiers.get(0).getAttribute2()),1,Integer.parseInt(qualifiers.get(1).getAttribute2()));
								}
								return dataToReturn;
							}
							else{
								DBMSGUI.setLabel(DBMSGUI.error, "Wrong query condition.");
								return null;
							}
						}
						else{
							for(String s:AndOr){
								if(s.equals("and")){
									DBMSGUI.setLabel(DBMSGUI.error, "Wrong condition.");
									return null;
								}
							}
							for(Qualifiers qf:qualifiers){
								ArrayList<Object> arr=i.btree.searchKey(Integer.parseInt(qf.getAttribute2()));
								if(arr!=null){
									dataToReturn.add(arr);
								}
							}
							return dataToReturn;
						}
					}
				}
			}
		}
		DBMSGUI.setLabel(DBMSGUI.error, "No table found in the current database!");
		return null;
	}

	/*
	 * The function uses the delete function implemented in the b+ tree to delete the data from
	 * any particular table of the currently used database. This function takes the values from the
	 * query inserted by the user and extracts the required compnents and applies various contdions and
	 * checks on the given set of data to varifiy that the data the user wants to be deleted can be
	 * deleted or it can not be, in the later case the user is promted with error statment.
	 */

	public void deleteQuery(DeleteQueryTokens dqt){
		BtreeTable currentBtreeTable=null;
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error,"No database has been used!");
			return;
		}
		for(BtreeTable table : DriveClass.currentTables){
			if(table.tableName.equals(dqt.getRelation())){
				currentBtreeTable=table;
				break;
			}
		}
		if(currentBtreeTable==null){
			DBMSGUI.setLabel(DBMSGUI.error, "there is a problem");
		}
		int primaryKey=-1;
		for(tables table:DriveClass.currentDatabase.tables){
			if(table.tableName.equals(dqt.getRelation())){
				primaryKey=table.primarayKeyIndex;
				break;
			}
		}
		if(primaryKey==-1){
			DBMSGUI.setLabel(DBMSGUI.error, "No table found in the current database!");
			return;
		}
		ArrayList<ArrayList> arrdata=searchQuery(dqt.getRelation(), dqt.getQualifierList(), dqt.getAndOr());
		if(arrdata==null){
			DBMSGUI.setLabel(DBMSGUI.error, "No entery was found!");
		}
		else{
			for(ArrayList arr: arrdata){
				currentBtreeTable.btree.delete((Integer)arr.get(primaryKey));
			}
			DBMSGUI.setLabel(DBMSGUI.confirmation, arrdata.size()+" entries have been deleted!");
			return;
		}
	}

	/*
	 * This function maps the column name of the specified table to the index of that column in the
	 * b+ tree so to make various data manipulation function easy.
	 */

	public int mapOnIndex(tables table,String columnName){
		for(int i=0;i<table.columns.length;i++){
			if(table.columns[i].columnName.equals(columnName)){
				return i;
			}
		}
		return 0;
	}

	/*
	 * This function serves the purpose of dropping a database from the list of databases. It also
	 * updates the values catalogue carriying the list of databases and tables.
	 */

	public void dropDatabase(String dbName){
		dbName = dbName.toLowerCase();
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error, "No databse has been used");
			return;
		}
		if(DriveClass.currentDatabase.databaseName.equals(dbName)){
			DBMSGUI.setLabel(DBMSGUI.error, "This database is currently in use!");
			return;
		}
		for(int i=0;i<DriveClass.DBMS.databases.size();i++){
			database db=DriveClass.DBMS.databases.get(i);
			if(db.databaseName.equals(dbName)){
				database currentlyUsing=DriveClass.currentDatabase;
				useDatabase(dbName);
				while(DriveClass.currentDatabase.tables.size()>0){
					tables table=DriveClass.currentDatabase.tables.get(0);
					dropTable(table.tableName);
				}
				DriveClass.DBMS.databases.remove(i);
				File f=new File(DriveClass.workingFolder+"\\"+dbName);
				f.delete();
				DriveClass.currentDatabase=null;
				useDatabase(currentlyUsing.databaseName);
				DBMSGUI.setLabel(DBMSGUI.confirmation, "The database "+dbName+" has been dropped!");				
				DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
				for(int ii = 0; ii < root.getFirstChild().getChildCount(); ii++){
					if(root.getFirstChild().getChildAt(ii).toString().equals(dbName)){
						model.removeNodeFromParent((DefaultMutableTreeNode)root.getFirstChild().getChildAt(ii));
						model.reload();
					}
				}

				return;
			}
		}
		DBMSGUI.setLabel(DBMSGUI.error, "No database of this name exists");
	}

	/*
	 * This function updates the values of any particular column in the specified column of the
	 * currently used database.
	 */

	public void updateQuery(UpdateQueryTokens uqt){
		BtreeTable currentBtreeTable=null;
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error,"No database has been used!");
			return;
		}
		for(BtreeTable table : DriveClass.currentTables){
			if(table.tableName.equals(uqt.getRelation())){
				currentBtreeTable=table;
				break;
			}
		}
		int index=-1;
		tables workingTable=null;
		for(tables table:DriveClass.currentDatabase.tables){
			if(table.tableName.equals(uqt.getRelation())){
				workingTable=table;
				index=mapOnIndex(table, uqt.getSettingValue().get(0).getAttribute1());
				break;
			}
		}
		if(index==-1){
			DBMSGUI.setLabel(DBMSGUI.error, "No column found!");
		}
		ArrayList<ArrayList> arrdata=searchQuery(uqt.getRelation(), uqt.getQualifierList(), uqt.getAndOr());
		if(arrdata==null){
			DBMSGUI.setLabel(DBMSGUI.error, "No entries were found to be updated");
		}
		else{
			for(ArrayList data:arrdata){
				currentBtreeTable.btree.updateKey((Integer)data.get(workingTable.primarayKeyIndex),index, uqt.getSettingValue().get(0).getAttribute2());
			}
			DBMSGUI.setLabel(DBMSGUI.confirmation, "Done updating "+arrdata.size()+" values");
		}

	}

	/*
	 * This following two functions are used to reshape the data so that it could easily be inserted into the tables
	 * in any particular table that will be shown on the screen.
	 */

	public String[][] convertToArrays(ArrayList<ArrayList> obj){
		String[][] data=new String[obj.size()][obj.get(0).size()];
		for(int i=0;i<obj.size();i++){
			for(int j=0;j<obj.get(0).size();j++){
				Object o=obj.get(i).get(j);
				if(o.getClass().getName().equals("java.lang.Integer")){
					data[i][j]=Integer.toString((Integer)o);
				}
				else{
					data[i][j]=(String)o;
				}
			}
		}
		return data;
	}
	public String[][] convertToArrays(ArrayList<Integer> columnIndexes, ArrayList<ArrayList> obj){
		String[][] data=new String[obj.size()][obj.get(0).size()];
		for(int i=0;i<obj.size();i++){
			for(int j=0;j<columnIndexes.size();j++){
				if(obj.get(i).get(columnIndexes.get(j)).getClass().getName().equals("java.lang.Integer")){
					data[i][j]=Integer.toString((Integer)obj.get(i).get(columnIndexes.get(j)));
				}
				else{
					data[i][j]=(String)obj.get(i).get(columnIndexes.get(j));
				}
			}
		}
		return data;
	}

	/*
	 * The following query selects and returns the specific data asked by the user. The data is selected
	 * by the DBMS based on the various values and conditions specified by the user.
	 */

	public void selectQuery(SelectQueryTokens sqt){
		String[][] data=null;
		String[] column=null;
		tables table=null;
		if(DriveClass.currentDatabase==null){
			DBMSGUI.setLabel(DBMSGUI.error,"No database has been used!");
			return;
		}
		ArrayList<ArrayList> arrdata=searchQuery(sqt.getRelationList().get(0), sqt.getQualifierList(), sqt.getAndOr());
		for(tables tab:DriveClass.currentDatabase.tables){
			if(tab.tableName.equals(sqt.getRelationList().get(0))){
				table=tab;
			}
		}
		if(arrdata==null){
			JOptionPane.showMessageDialog(null, "No Entries were selected!");
		}
		else{
			if(sqt.getAttributeList().get(0).equals("*")){
				column=new String[table.columns.length];
				for(int i=0;i<table.columns.length;i++){
					column[i]=table.columns[i].columnName;
				}
				data=convertToArrays(arrdata);
			}
			else{
				tables currentTable=null;
				ArrayList<Integer> arrIndex=new ArrayList<>();

				currentTable=table;
				for(int columnIndex=0;columnIndex<table.columns.length;columnIndex++){
					for(int givendata=0;givendata<sqt.getAttributeList().size();givendata++){
						if(table.columns[columnIndex].columnName.equals(sqt.getAttributeList().get(givendata))){
							arrIndex.add(columnIndex);
						}
					}
				}
				column=new String[arrIndex.size()];
				if(arrIndex.size()>0){
					for(int i=0;i<arrIndex.size();i++){
						column[i]=table.columns[arrIndex.get(i)].columnName;
					}
					data=convertToArrays(arrIndex,arrdata);
				}

			}

		}
		TableModel tm=new DefaultTableModel(data,column);
		DBMSGUI.resultTable.setModel(tm);
		DBMSGUI.resultTable.repaint();
		DBMSGUI.resultTable.getTableHeader().setBackground(Color.decode("#42C0FB"));
		Font f=new Font("Courier New", 16, 16);
		DBMSGUI.resultTable.getTableHeader().setFont(f);
		DBMSGUI.resultTable.getTableHeader().setForeground(Color.white);
		if(data!=null){
			DBMSGUI.setLabel(DBMSGUI.confirmation, data.length+" value selected!");
		}
	}
	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 200; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width +1 , width);
			}
			if(width > 300)
				width=300;
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	/*
	 * This is an auxillary function that is used while a database is dropped so all the 
	 * folders and files related to that database will be deleted. This function servers the same purpose.
	 */

	private void deleteFolder(File f){
		if(f.exists() && f.isDirectory()){
			String files[]=f.list();
			for(String s:files){
				File file=new File(f+"\\"+s);
				if(file.isFile()){
					file.delete();
				}
				else if(file.isDirectory()){
					deleteFolder(file);
				}
			}
			f.delete();
		}
	}

	/*
	 * This function will be used to drop the table as specified by the user, it will also update
	 * the catalogue that lists the databases and the folders.
	 */

	public void dropTable(String tableName){
		tableName = tableName.toLowerCase();
		if(DriveClass.currentDatabase == null){
			DBMSGUI.setLabel(DBMSGUI.error, "Please select any database to drop table from!");
			return;
		}
		for(int i=0;i<DriveClass.currentDatabase.tables.size();i++){
			tables table=DriveClass.currentDatabase.tables.get(i);
			if(table.tableName.equals(tableName)){
				File f=new File(DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+table.tableName);
				deleteFolder(f);
				DriveClass.currentDatabase.tables.remove(i);
				for(int j=0;j<DriveClass.currentTables.size();j++){
					BtreeTable btable=DriveClass.currentTables.get(i);
					if(btable.tableName.equals(tableName)){
						DriveClass.currentTables.remove(j);
						DBMSGUI.setLabel(DBMSGUI.confirmation, "The requested table has been dropped!");
						DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
						DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
						for(int ii = 0; ii < root.getFirstChild().getChildCount(); ii++){
							if(root.getFirstChild().getChildAt(ii).toString().equals(DriveClass.currentDatabase.databaseName)){
								for(int jj = 0; jj < root.getFirstChild().getChildAt(ii).getChildCount(); jj++){
									if(root.getFirstChild().getChildAt(ii).getChildAt(jj).toString().equals(tableName)){
										model.removeNodeFromParent((DefaultMutableTreeNode)root.getFirstChild().getChildAt(ii).getChildAt(jj));
										model.reload();
										JTreeAndPane.tree.expandPath(new TreePath(model.getPathToRoot(root.getFirstChild().getChildAt(ii))));
										return;
									}
								}
							}
						}
						return;
					}
				}
			}
		}
		DBMSGUI.setLabel(DBMSGUI.error, "No such table found in current database!");
	}

	/*
	 * This function removes all the databases and tables that are the part of the current
	 * catalogue, all the files and folders will also be deleted. This function was used for debugging
	 * purposes and now it have been deprecated.
	 */

	public void resetDBMS(){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(new File(DriveClass.workingFolder+"\\metadata.dat")));
			Catalogue c=new Catalogue();
			oos.writeObject(c);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void describeTable(String tableName){
		String column[]=null;
		String data[][]=null;
		if(DriveClass.currentDatabase == null){
			DBMSGUI.setLabel(DBMSGUI.error, "Please select any database to see description of table from!");
			return;
		}
		for(tables table : DriveClass.currentDatabase.tables){
			if(table.tableName.equals(tableName)){
				column=new String[table.columns.length];
				data=new String[1][table.columns.length];
				for(int i=0;i<table.columns.length;i++){
					column[i]=table.columns[i].columnName;
					String s=table.columns[i].columnType;
					if(s.equals("int"))s="Integer";
					else s="String";
					data[0][i]=s;
				}
				TableModel tm=new DefaultTableModel(data,column);
				DBMSGUI.resultTable.setModel(tm);
				DBMSGUI.resultTable.repaint();
				DBMSGUI.resultTable.getTableHeader().setBackground(Color.decode("#42C0FB"));
				Font f=new Font("Courier New", 16, 16);
				DBMSGUI.resultTable.getTableHeader().setFont(f);
				DBMSGUI.resultTable.getTableHeader().setForeground(Color.white);
				return;
			}
		}
		DBMSGUI.setLabel(DBMSGUI.error, "No such table found in current database!");
	}
}