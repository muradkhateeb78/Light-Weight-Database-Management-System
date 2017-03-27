import java.util.ArrayList;

public class UpdateQueryTokens {
	
	private String relation;
	private ArrayList<Qualifiers> settingValue;
	private ArrayList<Qualifiers> qualifierList;
	private ArrayList<String> andOr;
	
	public UpdateQueryTokens(){
		settingValue = new ArrayList<>();
		qualifierList = new ArrayList<>();
		andOr = new ArrayList<>();
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
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
	public ArrayList<Qualifiers> getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(ArrayList<Qualifiers> settingValue) {
		this.settingValue = settingValue;
	}
}