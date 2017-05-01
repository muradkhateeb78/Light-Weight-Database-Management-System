package Testing;

import java.util.ArrayList;

public class TestCases {
	
	/*
	 * This class contains all the test cases of the queries that can be executed in the LDBMS.
	 */
	
	private ArrayList<TestCaseAndResult> testCasesList;
	
	public TestCases(){
		testCasesList = new ArrayList<>();
		initTestCasesList();
	}
	
	private void initTestCasesList(){
		testCasesList.add(new TestCaseAndResult("create database database_name;", true));
		testCasesList.add(new TestCaseAndResult("create database database_name", false));
		testCasesList.add(new TestCaseAndResult("drop database database_name;", true));
		testCasesList.add(new TestCaseAndResult("drop databse database_name;", false));
		testCasesList.add(new TestCaseAndResult("use database namal;", true));
		testCasesList.add(new TestCaseAndResult("usee database namal;", false));
		testCasesList.add(new TestCaseAndResult("create table students ('uob' int, 'name' string, 'department' string, 'country' string, 'salary' int) set primary key as 'uob';", true));
		testCasesList.add(new TestCaseAndResult("create table students ('uob' int, 'name' string, 'department' string, 'country' string, 'salary') set primary key as 'uob';", false));
		testCasesList.add(new TestCaseAndResult("drop table teachers;", true));
		testCasesList.add(new TestCaseAndResult("insert into students values ('123456', 'John Wick', 'Computer Science', 'USA', '2000000');", true));//
		testCasesList.add(new TestCaseAndResult("insert into students values 123456, John Wick, 'Computer Science', 'USA', '2000000');", false));
		testCasesList.add(new TestCaseAndResult("describe table students;", true));
		testCasesList.add(new TestCaseAndResult("select * from students;", true));
		testCasesList.add(new TestCaseAndResult("select * from students where uob = 123456;", true));
		testCasesList.add(new TestCaseAndResult("update students set name = 'John Wick John' where uob = 123456;", true));//
		testCasesList.add(new TestCaseAndResult("delete from students where uob = 123456;", true));
	}

	public ArrayList<TestCaseAndResult> getTestCasesList() {
		return testCasesList;
	}
}