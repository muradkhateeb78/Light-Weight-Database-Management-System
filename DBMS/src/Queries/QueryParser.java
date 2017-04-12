package Queries;
import Queries.BTree2;

public class QueryParser {
	
	private String query;
	private Object objectTokens;
	
	public QueryParser(String query) {
//		this.query = query.replace(", ", ",").toLowerCase();
		this.query = query.replace(", ", ",");
		this.query = query.replace("=", " = ");
	}
	
	public void executeQuery(){
		tokenize();
		BTree2 bTree = new BTree2();
		bTree.runQuery(objectTokens);
	}
	
	private void tokenize(){
		if(query.toLowerCase().regionMatches(0, "select", 0, 6)){
			objectTokens = selectQueryTokens();
		}else if(query.toLowerCase().regionMatches(0, "insert", 0, 6)){
			objectTokens = insertQueryTokens();
		}else if(query.toLowerCase().regionMatches(0, "delete", 0, 6)){
			objectTokens = deleteQueryTokens();
		}else if(query.toLowerCase().regionMatches(0, "update", 0, 6)){
			objectTokens = updateQueryTokens();
		}else if(query.toLowerCase().regionMatches(0, "create table", 0, 12)){
			objectTokens = createTableTokens();
		}else if(query.toLowerCase().regionMatches(0, "create database", 0, 15)){
			objectTokens = query;
		}else if(query.toLowerCase().regionMatches(0, "use database", 0, 12)){
			objectTokens = query;
		}else if(query.toLowerCase().equals("show databases")){
			objectTokens = query;
		}else if(query.toLowerCase().equals("show tables")){
			objectTokens = query;
		}else if(query.toLowerCase().contains("drop database") || query.toLowerCase().contains("drop table") || query.toLowerCase().contains("describe table")){
			objectTokens = query;
		}
	}
	
	private SelectQueryTokens selectQueryTokens(){
		SelectQueryTokens sqt = new SelectQueryTokens();
		query = query.replace(", ", ",");
		String tokens[] = query.split(" ");
		int i = 0;
		i++;
		String tokens2[] = tokens[i].replace(", ", ",").split(",");
		for(int j = 0; j < tokens2.length; j++){
			System.out.println(tokens2[j]);
			sqt.getAttributeList().add(tokens2[j].toLowerCase());
		}
		i++;
		i++;
		sqt.getRelationList().add(tokens[i].toLowerCase());
		i++;
		if(i < tokens.length && tokens[i].toLowerCase().equals("where")){
			tokenizeWhere((Object)sqt, i++, tokens);
		}
		return sqt;
	}
	
	private InsertQueryTokens insertQueryTokens(){
		InsertQueryTokens iqt = new InsertQueryTokens();
		query = query.replace(", ", ",").replace(("values(").toLowerCase(), "values (").replace(";", "");
		String toks[] = query.split(" ");
		iqt.setRelation(toks[2].toLowerCase());
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
		dqt.setRelation(tokens[2].toLowerCase());
		tokenizeWhere(dqt, 3, tokens);
		return dqt;
	}
	
	private UpdateQueryTokens updateQueryTokens(){
		query = query.replaceAll("[ ]+", " ");
		UpdateQueryTokens uqt = new UpdateQueryTokens();
		query = query.replaceAll(" =|= | = ", "=");
		String toks[] = query.split(" ");
		int i = 1;
		uqt.setRelation(toks[i].toLowerCase());
		String ss = "";
		i = 3;
		while(!toks[i].toLowerCase().equals("where")){
			if(!toks[i+1].toLowerCase().equals("where")){
				ss+=toks[i]+" ";
			}else{
				ss+=toks[i];
			}
			i++;
		}
		uqt.getSettingValue().add(new Qualifiers(ss));
		tokenizeWhere(uqt, i, toks);
		return uqt;
	}
	
	private CreateTableTokens createTableTokens(){
		CreateTableTokens ctt = new CreateTableTokens();
		query = query.replace(", ", ",").replace("' ", "'#").replace("(", " ").replace(")", "");
		query = query.replaceAll("[ ]+"," ");
		String toks[] = query.split(" ");
		ctt.setRelationName(toks[2].toLowerCase());
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
			if(!toks[j].toLowerCase().matches("and|or")){
				if(toks[j].toLowerCase().matches("'[a-z0-9]+")){
					String ss = toks[j];
					while(j < toks.length){
						j++;
						if(toks[j].toLowerCase().matches("[a-z0-9]+'")){
							ss+="@"+toks[j];
							//where+= ss.replace(" ", "");
							break;
						}else{
							ss+="@"+toks[j];
//							where+= ss.replace(" ", "");
						}
					}
					where+= ss.replace(" ", "");
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
					if(whereToks[j].toLowerCase().matches("and|or")){
						queryClass.getAndOr().add(whereToks[j]);
					}else{
						queryClass.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
			break;
			case "Queries.DeleteQueryTokens":
				DeleteQueryTokens queryClass1 = (DeleteQueryTokens) tokens;
				for(int j = 0; j < whereToks.length; j++){
					if(whereToks[j].toLowerCase().matches("and|or")){
						queryClass1.getAndOr().add(whereToks[j]);
					}else{
						queryClass1.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
				break;
			case "Queries.UpdateQueryTokens":
				UpdateQueryTokens queryClass2 = (UpdateQueryTokens) tokens;
				for(int j = 0; j < whereToks.length; j++){
					if(whereToks[j].toLowerCase().matches("and|or")){
						queryClass2.getAndOr().add(whereToks[j]);
					}else{
						queryClass2.getQualifierList().add(new Qualifiers(whereToks[j]));
					}
				}
				break;
		}
	}
}