package Queries;
public class TableValues {
	
	private String attributeName;
	private String attributeType;
	
	public TableValues(String attributeName, String attributeType){
		this.setAttributeName(attributeName);
		this.setAttributeType(attributeType);
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