package Testing;
import org.junit.Test;
import Queries.QueryValidator;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestClass {
	
	/*
	 * This class runs and executes the test cases. It has the methods implemented to check all the test cases
	 * stored in the arraylist of the test cases.
	 */

	private TestCases testCases;
	private ArrayList<TestCaseAndResult> testCaseAndResultList;
	public TestClass(){
		testCases = new TestCases();
		testCaseAndResultList = testCases.getTestCasesList();
	}
	
	@Test
	public void unitTest1(){
		assertEquals(testCaseAndResultList.get(0).isValid(), (new QueryValidator(testCaseAndResultList.get(0).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest2(){
		assertEquals(testCaseAndResultList.get(1).isValid(), (new QueryValidator(testCaseAndResultList.get(1).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest3(){
		assertEquals(testCaseAndResultList.get(2).isValid(), (new QueryValidator(testCaseAndResultList.get(2).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest4(){
		assertEquals(testCaseAndResultList.get(3).isValid(), (new QueryValidator(testCaseAndResultList.get(3).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest5(){
		assertEquals(testCaseAndResultList.get(4).isValid(), (new QueryValidator(testCaseAndResultList.get(4).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest6(){
		assertEquals(testCaseAndResultList.get(5).isValid(), (new QueryValidator(testCaseAndResultList.get(5).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest7(){
		assertEquals(testCaseAndResultList.get(6).isValid(), (new QueryValidator(testCaseAndResultList.get(6).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest8(){
		assertEquals(testCaseAndResultList.get(7).isValid(), (new QueryValidator(testCaseAndResultList.get(7).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest9(){
		assertEquals(testCaseAndResultList.get(8).isValid(), (new QueryValidator(testCaseAndResultList.get(8).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest10(){
		assertEquals(testCaseAndResultList.get(9).isValid(), (new QueryValidator(testCaseAndResultList.get(9).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest11(){
		assertEquals(testCaseAndResultList.get(10).isValid(), (new QueryValidator(testCaseAndResultList.get(10).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest12(){
		assertEquals(testCaseAndResultList.get(11).isValid(), (new QueryValidator(testCaseAndResultList.get(11).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest13(){
		assertEquals(testCaseAndResultList.get(12).isValid(), (new QueryValidator(testCaseAndResultList.get(12).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest14(){
		assertEquals(testCaseAndResultList.get(13).isValid(), (new QueryValidator(testCaseAndResultList.get(13).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest15(){
		assertEquals(testCaseAndResultList.get(14).isValid(), (new QueryValidator(testCaseAndResultList.get(14).getQueryTestCase()).isQueryValid()));
	}
	@Test
	public void unitTest16(){
		assertEquals(testCaseAndResultList.get(15).isValid(), (new QueryValidator(testCaseAndResultList.get(15).getQueryTestCase()).isQueryValid()));
	}
}