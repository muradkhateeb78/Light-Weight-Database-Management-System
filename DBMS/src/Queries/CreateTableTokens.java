package Queries;
import java.util.ArrayList;

public class CreateTableTokens {

	private String relationName;
	private ArrayList<TableValues> valueList;
	private int primaryKey;

	public CreateTableTokens(){
		valueList = new ArrayList<>();
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName.toLowerCase();
	}

	public ArrayList<TableValues> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<TableValues> valueList) {
		this.valueList = valueList;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
}