
public class Qualifiers {
	
	private String attribute1;
	private String operator;
	private String attribute2;
	
	public Qualifiers(String expression){
		String attribute1=null, operator=null, attribute2=null;
		if(expression.contains("=")){
			String t[] = expression.split("=");
			attribute1 = t[0];
			operator = "=";
			attribute2 = t[1];
		}else if(expression.contains("!=")){
			String t[] = expression.split("!=");
			attribute1 = t[0];
			operator = "!=";
			attribute2 = t[1];
		}else if(expression.contains(">")){
			String t[] = expression.split(">");
			attribute1 = t[0];
			operator = ">";
			attribute2 = t[1];
		}else if(expression.contains(">=")){
			String t[] = expression.split(">=");
			attribute1 = t[0];
			operator = ">=";
			attribute2 = t[1];
		}else if(expression.contains("<")){
			String t[] = expression.split("<");
			attribute1 = t[0];
			operator = "<";
			attribute2 = t[1];
		}else if(expression.contains("<=")){
			String t[] = expression.split("<=");
			attribute1 = t[0];
			operator = "<=";
			attribute2 = t[1];
		}
		this.setAttribute1(attribute1);
		this.setOperator(operator);
		this.setAttribute2(attribute2);
	}

	public String getAttribute1() {
		return attribute1;
	}

	private void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getOperator() {
		return operator;
	}

	private void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAttribute2() {
		return attribute2;
	}

	private void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
}