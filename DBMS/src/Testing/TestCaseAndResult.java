package Testing;

public class TestCaseAndResult {

	/*
	 * This class contains the tokens of the test cases. This class helps the the tests cases to be populated in an
	 * array.
	 */
	
	private String queryTestCase;
	private boolean isValid;
	
	public TestCaseAndResult(String queryTestCase, boolean isValid){
		this.queryTestCase = queryTestCase;
		this.isValid = isValid;
	}
	
	public String getQueryTestCase() {
		return queryTestCase;
	}
	public void setQueryTestCase(String queryTestCase) {
		this.queryTestCase = queryTestCase;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}