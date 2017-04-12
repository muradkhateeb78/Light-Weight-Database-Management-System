package Queries;
import java.util.ArrayList;

public class InsertQueryTokens {
	
	private String relation;
	private ArrayList<String> values;
	
	public InsertQueryTokens(){
		relation = null;
		values = new ArrayList<>();
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation.toLowerCase();
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}