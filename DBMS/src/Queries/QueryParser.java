package Queries;
import Queries.BTree2;

public class QueryParser {
	
	private String query;
	private Object objectTokens;
	
	public QueryParser(String query) {
		this.query = query.replace(", ", ",").toLowerCase();
	}
	
	public void executeQuery(){
		tokenize();
		BTree2 bTree = new BTree2();
		bTree.runQuery(objectTokens);
	}
	
	private void tokenize(){
		if(query.regionMatches(0, "select", 0, 6)){
			objectTokens = selectQueryTokens();
		}else if(query.regionMatches(0, "insert", 0, 6)){
			objectTokens = insertQueryTokens();
		}else if(query.regionMatches(0, "delete", 0, 6)){
			objectTokens = deleteQueryTokens();
		}else if(query.regionMatches(0, "update", 0, 6)){
			objectTokens = updateQueryTokens();
		}else if(query.regionMatches(0, "create table", 0, 12)){
			objectTokens = createTableTokens();
		}else if(query.regionMatches(0, "create database", 0, 15)){
			objectTokens = query;
		}else if(query.regionMatches(0, "use database", 0, 12)){
			objectTokens = query;
		}else if(query.equals("show databases")){
			objectTokens = query;
		}else if(query.equals("show tables")){
			objectTokens = query;
		}else if(query.contains("drop database") || query.contains("drop table") || query.contains("describe table")){
			objectTokens = query;
		}
	}
	
	private SelectQueryTokens selectQueryTokens(){
		SelectQueryTokens sqt = new SelectQueryTokens();
		String tokens[] = query.split(" ");
		int i = 0;
		i++;
		String tokens2[] = tokens[i].replace(", ", ",").split(",");
		for(int j = 0; j < tokens2.length; j++){
			sqt.getAttributeList().add(tokens2[j]);
		}
		i++;
		i++;
		sqt.getRelationList().add(tokens[i]);
		i++;
		if(i < tokens.length && tokens[i].equals("where")){
			tokenizeWhere((Object)sqt, i++, tokens);
		}
		return sqt;
	}
	
	private InsertQueryTokens insertQueryTokens(){
		InsertQueryTokens iqt = new InsertQueryTokens();
		query = query.replace(", ", ",").replace("values(", "values (").replace(";", "");
		String toks[] = query.split(" ");
		iqt.setRelation(toks[2]);
		String s = query.substring(query.indexOf('('), query.indexOf(')')+1);
		s = s.replace("(", "").replace(")", "").replace("'", "");
		String[] stoks = s.split(",");
		for(String s2:stoks){
			iqt.getValues().add(s2);
		}
		return iqt;
	}
	
	private DeleteQueryTokens deleteQueryTokens(){
		DeleteQueryTokens dqt = new DeleteQueryTokens();
		String tokens[] = query.split(" ");
		dqt.setRelation(tokens[2]);
		tokenizeWhere(dqt, 3, tokens);
		return dqt;
	}
	
	private UpdateQueryTokens updateQueryTokens(){
		UpdateQueryTokens uqt = new UpdateQueryTokens();
		query = query.replaceAll(" =|= | = ", "=");
		String toks[] = query.split(" ");
		uqt.setRelation(toks[1]);
		uqt.getSettingValue().add(new Qualifiers(toks[3]));
		tokenizeWhere(uqt, 4, toks);
		return uqt;
	}
	
	private CreateTableTokens createTableTokens(){
		CreateTableTokens ctt = new CreateTableTokens();
		query = query.replace(", ", ",").replace("' ", "'#").replace("(", " ").replace(")", "");
		query = query.replaceAll("[ ]+"," ");
		String toks[] = query.split(" ");
		ctt.setRelationName(toks[2]);
		String toks2[] = toks[3].split(",");
		String pk = toks[toks.length-1];
		int pkIndex = 0;
		for(int i = 0; i < toks2.length; i++){
			String toks3[] = toks2[i].split("#");
			ctt.getValueList().add(new TableValues(toks3[0].replace("'", ""), toks3[1]));
			if(toks3[0].equals(pk)){
				pkIndex = i;
			}
		}
		ctt.setPrimaryKey(pkIndex);
		return ctt;
	}
	
	private void tokenizeWhere(Object tokens, int i, String[] toks){
		String where = "";
		String whereToks[] = null;
		for(int j = i+1; j < toks.length; j++){
			if(!toks[j].matches("and|or")){
				if(toks[j].matches("'[a-z0-9]+")){
					String ss = toks[j];
					while(j < toks.length){
						if(toks[j].matches("[a-z0-9]+'")){
							ss+="@"+toks[j];
							where+= ss.replace(" ", "");
							break;
						}
						j++;
					}
					System.out.println(ss);
				}else{
					where+= toks[j].replace(" ", "");
				}
			}else{
				where+=" "+toks[j]+" ";
			}
		}
		whereToks = where.split(" ");
		switch(tokens.getClass().getName()){
			case "Queries.SelectQueryTokens":
				SelectQueryTokens queryClass = (SelectQueryTokens) tokens;
				for(int j = 0; j < whereToks.length; j++){
					if(whereToks[j].matches("and|or")){
						queryClass.getAndOr().add(whereToks[j]);
					}else{
						queryClass.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
			break;
			case "Queries.DeleteQueryTokens":
				DeleteQueryTokens queryClass1 = (DeleteQueryTokens) tokens;
				for(int j = 0; j < whereToks.length; j++){
					if(whereToks[j].matches("and|or")){
						queryClass1.getAndOr().add(whereToks[j]);
					}else{
						queryClass1.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
				break;
			case "Queries.UpdateQueryTokens":
				UpdateQueryTokens queryClass2 = (UpdateQueryTokens) tokens;
				for(int j = 0; j < whereToks.length; j++){
					if(whereToks[j].matches("and|or")){
						queryClass2.getAndOr().add(whereToks[j]);
					}else{
						queryClass2.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
				break;
		}
	}
}