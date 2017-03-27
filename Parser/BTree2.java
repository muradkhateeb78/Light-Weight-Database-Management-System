import java.io.File;

public class BTree2 {
	
	public BTree2(){}
	
	public void runQuery(Object tokens){
		if(tokens.getClass().getName().equals("SelectQueryTokens")){
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
		}else if(tokens.getClass().getName().equals("InsertQueryTokens")){
			InsertQueryTokens iqt = (InsertQueryTokens) tokens;
			System.out.println("Relation: " + iqt.getRelation());
			for(String s : iqt.getValues()){
				System.out.println("Value: " + s);
			}
		}else if(tokens.getClass().getName().equals("DeleteQueryTokens")){
			DeleteQueryTokens dqt = (DeleteQueryTokens) tokens;
			System.out.println("Relation: " + dqt.getRelation());
			for(Qualifiers s : dqt.getQualifierList()){
				System.out.println("Qualifiers: " + s.getAttribute1()+""+s.getOperator()+""+s.getAttribute2());
			}
			for(String s : dqt.getAndOr()){
				System.out.println("AndOr: " + s);
			}
		}else if(tokens.getClass().getName().equals("UpdateQueryTokens")){
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
		}else if(tokens.getClass().getName().equals("CreateTableTokens")){
			createTable((CreateTableTokens)tokens);
		}else if(tokens.getClass().getName().equals("java.lang.String")){
			String s = (String) tokens;
			if(s.contains("create")){
				createDatabase(s.substring(16, s.length()));
			}else{
				useDatabase(s.substring(13, s.length()));
			}
		}
	}
	
	private void createTable(CreateTableTokens ctt){
		//creating Table mechanism of BTree
		System.out.println("Creating Table: " + ctt.getRelationName());
		for(TableValues tv : ctt.getValueList()){
			System.out.println("Attribute Name: " + tv.getAttributeName() + " Attribute Type: " + tv.getAttributeType());
		}
		System.out.println(ctt.getPrimaryKey());
	}
	
	private void createDatabase(String dbName){
		//creating DB mechanism of BTree
		System.out.println("Creating Database: " + dbName);
	}
	
	private void useDatabase(String dbName){
		//using DB mechanism of BTree
		System.out.println("Using Database: " + dbName);
	}
}