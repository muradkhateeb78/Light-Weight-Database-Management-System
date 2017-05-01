package Queries;
import java.util.ArrayList;

public class SelectQueryTokens {
	
	/*
	 * This class holds the tokens of the select query which is parsed in the QueryParser class.
	 */

	private ArrayList<String> attributeList;
	private ArrayList<String> relationList;
	private ArrayList<Qualifiers> qualifierList;
	private ArrayList<String> andOr;

	public SelectQueryTokens(){
		attributeList = new ArrayList<>();
		relationList = new ArrayList<>();
		qualifierList = new ArrayList<>();
		andOr = new ArrayList<>();
	}

	public ArrayList<String> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(ArrayList<String> attributeList) {
		this.attributeList = attributeList;
	}

	public ArrayList<String> getRelationList() {
		return relationList;
	}

	public void setRelationList(ArrayList<String> relationList) {
		this.relationList = relationList;
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