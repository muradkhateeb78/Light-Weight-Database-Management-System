package Queries;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryValidator {
	private String query;
	private final String createDBRegex = "create database [a-z_]+;";
	private final String createTableRegex = "create table [a-z_]+([ ]?)\\((([ ]?)'[a-z_]+' (int|string)((,([ ]?)'[a-z_]+' "
			+ "(int|string))+)?)\\) set primary key as '[a-z_]+';";
	private final String useDB = "use database [a-z_]+;";
	private final String where = "( where [a-z_]+([ ]?)([=<>]|(!=)|(<=)|(>=))([ ]?)(('[a-z _]+')|([0-9]+))(([( [or|and] [a-z_]+([ ]?)"
			+ "([=<>]|(!=)|(<=)|(>=))([ ]?)(('[a-z ]+')|[0-9]+))]+))*)?;";
	private final String selectRegex = "select (\\*|([a-z_]+)((,([ ]?)[a-z]+)+)?) from [a-z_]+"+where;
	private final String insertRegex = "insert into [a-z_]+ values([ ]?)\\('[a-z0-9 ]+'((([ ]?),([ ]?)'[a-z0-9 ]+')+)?\\);";
	private final String deleteRegex = "delete from [a-z_]+"+where;
	private final String updateRegex = "update [a-z_]+ set [a-z_]+([ ]?=[ ]?)('[a-z ]+'|[0-9]+)"+where;
	private final String showDatabasesRegex = "show databases;";
	private final String showTablesRegex = "show tables;";
	private final String dropDatabase = "drop database [a-z_]+;";
	private final String dropTable = "drop table [a-z_]+;";
	private final String describeTable = "describe table [a-z_]+;";

	public QueryValidator(String query){
		this.query = query;//.replace("(=)|( =)|(= )", " = ").replace("(!=)|( !=)|(!= )", " != ").replace("(<)|( <)|(< )", " < ").replace("(>)|( >)|(> )", " > ").replace("(>=)|( >=)|(>= )", " >= ").replace("(<=)|( <=)|(<= )", " <= ");
	}

	public boolean isQueryValid(){
		String matchQuery = requiredRegex();
		if(matchQuery != null){
			if(query.matches(matchQuery)){
				return true;
			}
		}
		return false;
	}

	public String queryError(){
		String error = "";
		String requiredRegex = requiredRegex();
		if(requiredRegex == null){
			return "Unidentified query!";
		}
		Pattern pattern = Pattern.compile(requiredRegex());
		int errorAt = queryError(pattern);
		error += (query.substring(0, errorAt) + "^" + query.substring(errorAt, query.length()));
		return error;
	}

	private int queryError(Pattern pattern){
		Matcher queryMatcher = pattern.matcher(query);
		for(int i = query.length(); i > 0; i--){
			Matcher matcherRegion = queryMatcher.region(0, i);
			if(matcherRegion.matches() || matcherRegion.hitEnd()){
				return i;
			}
		}
		return 0;
	}

	private String requiredRegex(){
		if(query.equals(showDatabasesRegex)){
			return showDatabasesRegex;
		}else if(query.equals(showTablesRegex)){
			return showTablesRegex;
		}
		switch(query.charAt(0)){
		case 's':
			return selectRegex;
		case 'i':
			return insertRegex;
		case 'd':
			if(query.contains("drop database")){
				return dropDatabase;
			}else if(query.contains("drop table")){
				return dropTable;
			}else if(query.contains("describe table")){
				return describeTable;
			}else {
				return deleteRegex;
			}
		case 'u':
			if(!query.contains("use database")){
				return updateRegex;
			}
		default:
			if(query.contains("create table") || query.contains("set primary key as")){
				return createTableRegex;
			}else if(query.contains("create database")){
				return createDBRegex;
			}else if(query.contains("use database")){
				return useDB;
			}else{
				return null;
			}
		}
	}
}