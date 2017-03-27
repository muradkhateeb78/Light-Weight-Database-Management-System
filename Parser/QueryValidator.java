import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryValidator {
	//select (\\*|([a-z]+(, )?)+) from [a-z]+
	private String query;
	private final String createDBRegex = "create database [a-z]+;";
	private final String createTableRegex = "create table [a-z]+([ ]?)\\((([ ]?)'[a-z]+' (int|string)((,([ ]?)'[a-z]+' "
			+ "(int|string))+)?)\\) set primary key as '[a-z]+';";
	private final String useDB = "use database [a-z]+;";
	private final String where = "( where [a-z]+([ ]?)[=|!=|>|<|>=|<=]([ ]?)(('[a-z]+')|([0-9]+))(([( [or|and] [a-z]+([ ]?)"
			+ "[=|!=|>|<|>=|<=]([ ]?)(('[a-z]+')|[0-9]+))]+)?))?;";
	private final String selectRegex = "select (\\*|([a-z]+)((,([ ]?)[a-z]+)+)?) from [a-z]+"+where;
	private final String insertRegex = "insert into [a-z]+ values([ ]?)\\('[a-z0-9 ]+'((([ ]?),([ ]?)'[a-z0-9 ]+')+)?\\);";
	private final String deleteRegex = "delete from [a-z]+"+where;
	private final String updateRegex = "update [a-z]+ set [a-z]+([ ]?=[ ]?)('[a-z]+'|[0-9]+)"+where;
	
	public QueryValidator(String query){
		this.query = query;
	}
	
	public boolean isQueryValid(){
		String matchQuery = requiredRegex();
		if(matchQuery != null){
			if(query.matches(matchQuery)){
				if(query.contains("create table")){
					String toks[] = query.split(" ");
					String s = query.substring(0, query.length()-toks[toks.length-1].length());
					return s.contains(toks[toks.length-1].replace(";", ""));
				}
				return true;
			}
		}
		return false;
	}
	
	public String queryError(){
		String error = "";
		String regexToCompile = requiredRegex();
		if(regexToCompile == null){
			return "Unidentified Query!";
		}
		Pattern pattern = Pattern.compile(regexToCompile);
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
		switch(query.charAt(0)){
			case 's':
				return selectRegex;
			case 'i':
				return insertRegex;
			case 'd':
				return deleteRegex;
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