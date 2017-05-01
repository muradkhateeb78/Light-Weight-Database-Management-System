package Queries;
public class TableValues {
	
	/*
	 * This class contains the information of the attributes of the table and their data type.
	 */

	private String attributeName;
	private String attributeType;

	public TableValues(String attributeName, String attributeType){
		this.setAttributeName(attributeName.toLowerCase());
		this.setAttributeType(attributeType.toLowerCase());
	}

	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
}