import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class btreeTest {
	public static void main(String[] args) throws FileNotFoundException{
		int primaryKey=0;
		Random rand=new Random();
		File file=new File("abbasi.txt");
		PrintWriter pw=new PrintWriter(file);
//		Scanner input=new Scanner(file);
		String columnNames[]={"UOB#","Name","Department"};
		Btree btree=new Btree(primaryKey, columnNames);
		ArrayList<Integer> arrdata=new ArrayList<>();
		for(int i=1;i<50;i++){
			arrdata.add(i);
		}
		Collections.shuffle(arrdata);
		for(int i:arrdata){
			pw.println(i);
			ArrayList<Object> abbasi3=new ArrayList<>();
			abbasi3.add(i);
			abbasi3.add("Umair Aziz"+i);
			abbasi3.add("computer science"+i);
			btree.insert(abbasi3);
		}
		btree.print(btree.root);
		pw.close();
		for(int i:arrdata){
			if(i==19){
				i++;
				i--;
			}
			System.out.println("\n&&deleting&& "+i);
			btree.delete(i);
			System.out.println("\n\n\n\n\n\n\n\n");
			btree.print(btree.root);
		}
//		
		
		
		
		
//		System.out.println("Now deleting");
//		System.out.println(btree.delete(45));
//		System.out.println("\n\n\n");
//		btree.print(btree.root);
//		while(true){
//			Scanner input=new Scanner(System.in);
//			System.out.println("\nPlease enter a value to be deleted:");
//			int x=input.nextInt();
//			btree.delete(x);
//			System.out.println("\n\n\n\n");
//			btree.print(btree.root);
//		}
//		for(int i:arrRandom){
//			System.out.println("*********");
//			System.out.println("*********");
//			System.out.println(i);
//			System.out.println("*********");
//			System.out.println("*********");
//			btree.delete(i);
//			System.out.println("\n\n\n\n\n");
//			btree.print(btree.root);
//		}
//		System.out.println();
//		btree.print(btree.root);
//		btree.delete(5);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		btree.delete(10);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		btree.delete(15);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		btree.delete(20);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		ArrayList<Object> abbasi3=new ArrayList<>();
//		abbasi3.add(22);
//		abbasi3.add("Umair Aziz"+22);
//		abbasi3.add("computer science"+22);
//		btree.insert(abbasi3);
//		abbasi3=new ArrayList<>();
//		abbasi3.add(16);
//		abbasi3.add("Umair Aziz"+16);
//		abbasi3.add("computer science"+16);
//		btree.insert(abbasi3);
//		abbasi3=new ArrayList<>();
//		abbasi3.add(17);
//		abbasi3.add("Umair Aziz"+17);
//		abbasi3.add("computer science"+17);
//		btree.insert(abbasi3);
//		abbasi3=new ArrayList<>();
//		abbasi3.add(18);
//		abbasi3.add("Umair Aziz"+18);
//		abbasi3.add("computer science"+18);
//		btree.insert(abbasi3);
//		btree.print(btree.root);
//		btree.delete(17);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		btree.delete(5);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
//		btree.delete(25);
//		System.out.println("\n\n\n\n");
//		btree.print(btree.root);
		
//		internalNode left=(internalNode)((internalNode)(btree.root.children[2])).children[1];
//		internalNode right=(internalNode)((internalNode)(btree.root.children[2])).children[2];
//		System.out.println("\n\n\n\n");
//		btree.merge(left, right, 1);
//		btree.print(btree.root);
//		keyInNode k=btree.Search(50);
//		keyInNode kk=btree.Search(55);
//		leafNode left=k.lNode;
//		leafNode right=kk.lNode;
//		System.out.println(btree.merge(left, right,-1));
//		System.out.println("\n\n\n\n\n\n\n\n");
//		btree.print(btree.root);
//		internalNode right=(internalNode)btree.root.children[2];
//		right=(internalNode)right.children[3];
//		internalNode left=(internalNode)btree.root.children[2];
//		System.out.println("\n\n\n\n");
//		left=(internalNode)left.children[2];
//		left.printNode();
//		System.out.println();
//		right.printNode();
//		System.out.println();
//		System.out.println(btree.directedShift(left, right, 1));
//		System.out.println();
//		keyInNode k=btree.Search(55);
//		keyInNode kk=btree.Search(65);
//		
//		leafNode left=k.lNode;
//		leafNode right=kk.lNode;
//		
//		System.out.println("\n\n\n\n");
//		System.out.println(btree.shift(left, right, 1));
//		btree.print(btree.root);
//		for(int i=1;i<=btree.root.getNoOfKeys();i++){
//			System.out.println(btree.root.keys[i]);
//		}
//		internalNode node=(((internalNode)btree.root.children[1]));
//		while(node!=null){
//			for(int i=1;i<=node.getNoOfKeys();i++){
//				System.out.println(node.keys[i]);
//			}
//			node=node.getRightInternalNode();
//		}
	
//		int primaryKey=0;
//		String columnNames[]={"UOB#","Name","Department"};
//		Btree btree=new Btree(primaryKey, columnNames);
//		for(int i=0;i<100;i++){		
//			ArrayList<Object> abbasi3=new ArrayList<>();
//			abbasi3.add(i*3);
//			abbasi3.add("Umair Aziz"+i);
//			abbasi3.add("computer science"+i);
//			btree.insert(abbasi3);
//		}
//		int start=5;
//		int end=678;
//		ArrayList<ArrayList> arrdata=btree.searchInRange(start, end);
//		for(ArrayList aar:arrdata){
//			for(int i=0;i<aar.size();i++){
//				System.out.println(aar.get(i));
//			}
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//		}
//		keyInNode delete=btree.Search(65);
//		leafNode lf=delete.lNode;
//		lf=lf.delete(65,0);
//		btree.write_node(lf);
//		System.out.println("AbbasiAbbasiAbbasi\n\n\n\n\n\n\n\n\n\n");
//		start=5;
//		end=678;
//		arrdata=btree.searchInRange(start, end);
//		for(ArrayList aar:arrdata){
//			for(int i=0;i<aar.size();i++){
//				System.out.println(aar.get(i));
//			}
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//			System.out.println("**********");
//		}
		//btree.print(btree.root);
		System.out.println("ALLAH-HU-AKBAR");
	}
//	}
	
	
	//NOTE:::::::
	//is range search ko exactley wohi values dain k jo db main exist karti hain to yeh kaam karta hay warna nai.
	
}
