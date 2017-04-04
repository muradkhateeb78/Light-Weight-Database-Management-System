package Queries;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Btree.Btree;

public class BTree2 {

	public BTree2(){}

	public void runQuery(Object tokens){
		if(tokens.getClass().getName().equals("Queries.SelectQueryTokens")){
			SelectQueryTokens sqt = (SelectQueryTokens) tokens;
			for(String s : sqt.getAttributeList()){
				System.out.println("Attribute: " + s);
			}
			for(String s : sqt.getRelationList()){
				System.out.println("Relation: " + s);
			}
			for(Qualifiers s : sqt.getQualifierList()){
				System.out.println("Qualifiers: " + s.getAttribute1()+""+s.getOperator()+""+s.getAttribute2());
			}
			for(String s : sqt.getAndOr()){
				System.out.println("AndOr: " + s);
			}
			System.out.println(12334);
			selectQuery(sqt);
		}else if(tokens.getClass().getName().equals("Queries.InsertQueryTokens")){
			InsertQueryTokens iqt = (InsertQueryTokens) tokens;
			insertData(iqt);
		}else if(tokens.getClass().getName().equals("Queries.DeleteQueryTokens")){
			DeleteQueryTokens dqt = (DeleteQueryTokens) tokens;
			System.out.println("Relation: " + dqt.getRelation());
			for(Qualifiers s : dqt.getQualifierList()){
				System.out.println("Qualifiers: " + s.getAttribute1()+""+s.getOperator()+""+s.getAttribute2());
			}
			for(String s : dqt.getAndOr()){
				System.out.println("AndOr: " + s);
			}
		}else if(tokens.getClass().getName().equals("Queries.UpdateQueryTokens")){
			UpdateQueryTokens uqt = (UpdateQueryTokens) tokens;
			System.out.println("Relation: " + uqt.getRelation());
			for(Qualifiers s : uqt.getSettingValue()){
				System.out.println("Setting: " + s.getAttribute1()+""+s.getOperator()+""+s.getAttribute2());
			}
			for(Qualifiers s : uqt.getQualifierList()){
				System.out.println("Qualifiers: " + s.getAttribute1()+""+s.getOperator()+""+s.getAttribute2());
			}
			for(String s : uqt.getAndOr()){
				System.out.println("AndOr: " + s);
			}
		}else if(tokens.getClass().getName().equals("Queries.CreateTableTokens")){
			createTable((CreateTableTokens)tokens);
		}else if(tokens.getClass().getName().equals("java.lang.String")){
			String s = (String) tokens;
			if(s.contains("create")){
				createDatabase(s.substring(16, s.length()));
			}else if(s.contains("use")){
				useDatabase(s.substring(13, s.length()));
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
	public void showDatabases(){
		int j=1;
		if(DriveClass.DBMS.databases.size()==0){
			System.out.println("No database exists");
		}
		for(database i:DriveClass.DBMS.databases){
			System.out.println(j+": "+i.databaseName);
			j++;
		}
	}
	public void showTables(){
		if(DriveClass.currentDatabase==null){
			System.out.println("No database selected!");
			return;
		}
		else if(DriveClass.currentDatabase.tables.size()==0){
			System.out.println("No table exists!");
			return;
		}
		int j=1;
		for(tables t:DriveClass.currentDatabase.tables){
			System.out.println(j+": "+t.tableName);
			j++;
		}
	}
	private void createTable(CreateTableTokens ctt){
		//creating Table mechanism of BTree
		if(DriveClass.currentDatabase==null){
			System.out.println("No database selected!");
			return;
		}
		for(BtreeTable i:DriveClass.currentTables){
			if(i.tableName.equals(ctt.getRelationName())){
				System.out.println("A table with the same name already exists!");
				return;
			}
		}
		String folderPath=DriveClass.workingFolder+"\\"+DriveClass.currentDatabase.databaseName+"\\"+ctt.getRelationName()+"\\";
		new File(folderPath).mkdirs();
		tables tb=new tables(ctt.getRelationName(),ctt.getValueList().size(),ctt.getPrimaryKey());
		DriveClass.currentDatabase.tables.add(tb);
		for(int i=0;i<tb.columns.length;i++){
			column c=new column(ctt.getValueList().get(i).getAttributeName(), ctt.getValueList().get(i).getAttributeType());
			tb.columns[i]=c;
		}

		Btree btree=new Btree(ctt.getPrimaryKey(),folderPath);
		BtreeTable bt=new BtreeTable(ctt.getRelationName(), btree);
		DriveClass.currentTables.add(bt);
		System.out.println("The table has been created");
	}

	private void createDatabase(String dbName){
		//creating DB mechanism of BTree
		//System.out.println("Creating Database: " + dbName);
		for(database i:DriveClass.DBMS.databases){
			if(i.databaseName.equals(dbName)){
				System.out.println("Database Already exists");
				return;
			}
		}
		database db=new database(dbName);
		DriveClass.DBMS.databases.add(db);
		new File(DriveClass.workingFolder+"\\"+dbName).mkdirs();
	}
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
	private void useDatabase(String dbName){
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
				System.out.println("Database used");
				return;
			}
		}
		System.out.println("No such database exists!");
	}

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
			System.out.println("The table does not exist!");
			return;
		}
		if(DriveClass.currentDatabase == null){
			System.out.println("No database is used!");
		}else{
			if(insertDataCorrect(iqt,currentTable)){
				for(BtreeTable bt:DriveClass.currentTables){
					if(bt.tableName.equals(iqt.getRelation().toLowerCase())){
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
						System.out.println("The data has been added successfully!");
					}
				}
			}
			else{
				System.out.println("The data is not correct!");
			}
		}
	}
	public ArrayList<ArrayList> searchQuery(String tableName,ArrayList<String> attributes, ArrayList<Qualifiers> qualifiers,ArrayList<String> AndOr){
		for(BtreeTable i:DriveClass.currentTables){
			if(tableName.equals(i.tableName)){
				if(qualifiers.size()==0){
					ArrayList<ArrayList> data=i.btree.selectStaric();
					if(data==null){
						System.out.println("There are not entries in the table!");
						return null;
					}
					if(attributes.get(0).equals("*")){
						return data;
//						for(ArrayList a:data){
//							for(Object o:a){
//								System.out.print(o+",");
//							}
//							System.out.println();
//						}
//						return;
					}
					else{
						ArrayList<Integer> arrIndex=new ArrayList<>();
						for(tables table:DriveClass.currentDatabase.tables){
							if(table.tableName.equals(tableName)){
								for(int columnIndex=0;columnIndex<table.columns.length;columnIndex++){
									for(int givendata=0;givendata<attributes.size();givendata++){
										if(table.columns[columnIndex].columnName.equals(attributes.get(givendata))){
											arrIndex.add(columnIndex);
										}
									}
								}
								break;
							}
						}
						return data;
//						for(int dataindex=0;dataindex<data.size();dataindex++){
//							for(Integer datavaluesIndex:arrIndex){
//								System.out.print(data.get(dataindex).get(datavaluesIndex)+",");
//							}
//							System.out.println();
//						}
//						return;
					}
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
								data.add(tuple);
//								for(Object a:tuple){
//									System.out.print(a+",");
//								}
//								System.out.println();
							}
							else{
								continue;
							}
							return data;
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
							System.out.println(dataToReturn.size());
//							for(ArrayList data: dataToReturn){
//								System.out.println(data.size());
//								for(Object obj:data){
//									System.out.println(obj+",");
//								}
//								System.out.println();
//							}
//							return;
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
//									for(ArrayList data: dataToReturn){
//										for(Object obj:data){
//											System.out.println(obj+",");
//										}
//										System.out.println();
//									}
//									return;
									return dataToReturn;
							}
							else{
								System.out.println("Wrong query condition.");
								return null;
							}
						}
						else{
							for(String s:AndOr){
								if(s.equals("and")){
									System.out.println("Wrong condition.");
									return null;
								}
							}
							for(Qualifiers qf:qualifiers){
								ArrayList<Object> arr=i.btree.searchKey(Integer.parseInt(qf.getAttribute2()));
								if(arr!=null){
									dataToReturn.add(arr);
								}
							}
//							for(ArrayList data: dataToReturn){
//								for(Object obj:data){
//									System.out.println(obj+",");
//								}
//								System.out.println();
//							}
//							return;
							return dataToReturn;
						}
					}
				}
			}
		}
		System.out.println("No table found in the current database!");
		return null;
	}
	public int mapOnIndex(tables table,String columnName){
		for(int i=0;i<table.columns.length;i++){
			if(table.columns[i].columnName.equals(columnName)){
				return i;
			}
		}
		return 0;
	}
	public void dropDatabase(String dbName){
		if(DriveClass.currentDatabase.databaseName.equals(dbName)){
			System.out.println("This database is currently in use!");
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
				System.out.println("The database has beeb droped!");
				return;
			}
		}
		System.out.println("No database of this name exists");
	}
	public void selectQuery(SelectQueryTokens sqt){
 		ArrayList<ArrayList> arrdata=searchQuery(sqt.getRelationList().get(0), sqt.getAttributeList(), sqt.getQualifierList(), sqt.getAndOr());
 		if(arrdata==null){
 			System.out.println("Nothing found");
 		}
 		else{
 			for(ArrayList data:arrdata){
 				for(Object obj:data){
 					System.out.print(obj+",");
 				}
 				System.out.println();
 			}
 		}
	}
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
	public void dropTable(String tableName){
		if(DriveClass.currentDatabase == null){
			System.out.println("Please select any database to drop table from!");
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
						System.out.println("Deleted");
						return;
					}
				}
			}
		}
		System.out.println("No such table found in current database!");
	}
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
		if(DriveClass.currentDatabase == null){
			System.out.println("Please select any database to see description of table from!");
			return;
		}
		for(tables table : DriveClass.currentDatabase.tables){
			if(table.tableName.equals(tableName)){
				for(column c: table.columns){
					System.out.print(c.columnName+","+c.columnType+"****");
				}
				System.out.println();
				return;
			}
		}
		System.out.println("No such table found in current database!");
	}
}