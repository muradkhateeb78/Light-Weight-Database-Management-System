import java.util.Scanner;

public class DriveClass {

	public static void main(String[] args) {
		System.out.println("Welcome to Lite Database Management System!");
		Scanner input = new Scanner(System.in);
		System.out.println("Start entering queries below.");
		System.out.print("LDBMS?-> ");
		String query = input.nextLine();
		query = query.replaceAll("[ ]+"," ");
		while(!query.equals("exit")){
			QueryValidator qv = new QueryValidator(query.toLowerCase());
			if(qv.isQueryValid()){
				System.out.println("Valid Query!");
				QueryParser qp = new QueryParser(query.replaceAll(";", "").toLowerCase());
				qp.executeQuery();
			}else{
				System.out.println("Invalid query!");
				System.out.println(qv.queryError());
			}
			System.out.print("LDBMS?-> ");
			query = input.nextLine();
			query = query.replaceAll("[ ]+"," ");
		}
		input.close();
		System.out.println("Exiting LDBMS!");
		//some functions or destructions of data or reallocation of memory...
	}
}