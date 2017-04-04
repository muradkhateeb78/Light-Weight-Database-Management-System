package Btree;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BtreeTest {
	
	//public static void main(String[] abbasi){
//		Object s="murad";
//		String y=new String("murad");
//		System.out.println(s==y);
//		System.out.println(s.equals(y));
//		String r = "([=<>]|(!=)|(<=)|(>=))";
//		String s = "";
//		System.out.println(s.matches(r));
//	}
	
//	Integer x=12;
//	System.out.println(x.getClass().getName());
//	
//	}
//		int x=4,y=77,z=87;
//		chekcing[] c=new chekcing[4];
//		c[0]=new chekcing(x, 2, ">");
//		c[1]=new chekcing(y, 77, "=");
//		c[2]=new chekcing(z, 43, ">");
//		c[3]=new chekcing(y, 120, "<");
//		
//		String abba="x>2 and y=77 and z<43 and y < 120";
//		String[] arroperators={"and","and","and"};
//		ArrayList<Boolean> arrboolean=new ArrayList<>();
//		for(int i=0;i<c.length;i++){
//			String s=c[i].operator;
//			if(s.equals(">")){
//				arrboolean.add(c[i].x > c[i].y);
//			}
//			else if(s.equals("=")){
//				arrboolean.add(c[i].x == c[i].y);
//				
//			}
//			else if(s.equals("<")){
//				arrboolean.add(c[i].x < c[i].y);
//			}
//		}
//		System.out.println(arrboolean.size());
//		boolean result=true;
//		int index;
//		if(arroperators[0].equals("and")){
//			result=arrboolean.get(0) && arrboolean.get(1);
//			
//		}
//		else if(arroperators[0].equals("or")){
//			result=arrboolean.get(0) || arrboolean.get(1);
//			
//		}
//		for(index=1;index<arroperators.length;index++){
//			if(arroperators[index].equals("and")){
//				result=result && arrboolean.get(index+1);
//			}
//			else if(arroperators[index].equals("or")){
//				result=result || arrboolean.get(index+1);
//			}
//		}
//		System.out.println(result);
//		
//	}
//public static void main(String[] args) throws IOException{
//	Btree btree=new Btree(0, "C:\\Users\\AMMAR\\Desktop\\shebi\\");
//	ArrayList<Object> abbasi3=new ArrayList<>();
//	int i=20;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//    abbasi3=new ArrayList<>();
//    
//    i=5;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=156;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=69;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=2;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=198;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=75;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=61;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=40;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=7;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=120;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=195;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=100;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=13;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=33;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=185;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=1;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//
//	abbasi3=new ArrayList<>();
//    i=159;
//	abbasi3.add(i);
//	abbasi3.add("Umair Aziz"+i);
//	abbasi3.add("computer science"+i);
//	abbasi3.add("Shoaib Ahmed"+i);
//	btree.insert(abbasi3);
//	btree.print(btree.root);
//	System.out.println("\n\n\n\n");
//	ArrayList<ArrayList> arr=btree.rangeSearchForLessThan(btree.root, 1, 40);
//	for(ArrayList arrdata: arr){
//		for(Object obj:arrdata){
//			System.out.println(obj+",");
//		}
//		System.out.println();
//	}

	
//	ArrayList<ArrayList> datadata= btree.rangeSearch(btree.root, 0, 40, 0, 102);
//	for(int j=0;j<datadata.size();j++){
//		for(Object k:datadata.get(j)){
//			System.out.print(k + ",");
//		}
//		System.out.println();
//	}
//		Btree btree=new Btree(0, "F:\\metadata\\");
	    
//		File f=new File("Abbasi.txt");
//		f.createNewFile();
//		PrintWriter pw=new PrintWriter(f);
//		//Scanner input=new Scanner(f);
//		ArrayList<Integer> data=new ArrayList<>();
//		for(int i=1;i<=501;i++){
//			data.add(i);
//		}
//		Collections.shuffle(data);
//		for(int i:data){
//			ArrayList arr=new ArrayList<>();
//			ArrayList<Object> abbasi3=new ArrayList<>();
//			abbasi3.add(i);
//			abbasi3.add("Umair Aziz"+i);
//			abbasi3.add("computer science"+i);
//			abbasi3.add("Shoaib Ahmed"+i);
//			btree.insert(abbasi3);
//		}
//		btree.print(btree.root);
//		System.out.println("\n\n\n\n");
//		ArrayList<ArrayList> arr=btree.selectStaric();
//		for(ArrayList i:arr){
//			for(int j=0;j<i.size();j++){
//				System.out.print(i.get(j)+",");
//				
//			}
//			System.out.println();
//		}
//		for(int i:data){
//			System.out.println("\n\n\n");
//			System.out.println("Deleting "+i);
//			btree.delete(i);
//			btree.print(btree.root);
//			System.out.println("\n\n\n");
//		}
//}

}
