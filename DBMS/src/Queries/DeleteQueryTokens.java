package Queries;
import java.util.ArrayList;

public class DeleteQueryTokens {

	private String relation;
	private ArrayList<Qualifiers> qualifierList;
	private ArrayList<String> andOr;
	
	public DeleteQueryTokens(){
		relation = null;
		qualifierList = new ArrayList<>();
		andOr = new ArrayList<>();
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation.toLowerCase();
	}

	public ArrayList<Qualifiers> getQualifierList() {
		return qualifierList;
	}

	public void setQualifierList(ArrayList<Qualifiers> qualifierList) {
		this.qualifierList = qualifierList;
	}

	public ArrayList<String> getAndOr() {
		return andOr;
	}

	public void setAndOr(ArrayList<String> andOr) {
		this.andOr = andOr;
	}
}