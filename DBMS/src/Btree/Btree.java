package Btree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.text.InternationalFormatter;
/*This class implements the whole primary indexing stuff on the bases of the given
primary key. This class is the actual implementation of a customized version of the
B+ trees. This primary indexing using the b+ tree helps to significally reduce the
number of I/O operations, currently it is only indexing the data on the bases of 
integeral primary key.
 */
public class Btree implements Serializable{
	public static int primaryKey;
	public internalNode root;
	public static int t=5;
	private int arrayCounter=1;
	private String filePath;
	public ArrayList<Object> searchKey(int key){
		if(this.root==null)return null;
		keyInNode k=Search(key);
		if(k==null){
			return null;
		}
		leafNode lf=k.lNode;
		int index=k.getIndex();
		return lf.keys[index];
	}
	public keyInNode Search(int key){
		return searchTree(this.root,key);
	}
	public ArrayList<ArrayList> searchInRange(int start, int end){
		return searchRange(this.root,start,end);
	}

	/*
	 * This is the constructor of the class and it sets the values of filepath and the primary
	 * key of the table.
	 */

	public Btree(int primaryKey,String filePath){
		this.filePath=filePath;
		this.primaryKey=primaryKey;
	}

	/*
	 * The following function will return the data of type arraylist of arraylist which will
	 * be greater than start and lesser than the end values specified in the arguments of the function.
	 * The data is stored in the arraylist of arraylist because we neither know how many number of
	 * will the user ask and nor we know that how many number of data items will be there in each node.
	 */

	private ArrayList<ArrayList> searchRange(internalNode head, int start, int end){
		ArrayList<ArrayList> arrdata=new ArrayList<>();
		keyInNode key=Search(start);
		leafNode lf=key.lNode;
		int index=key.getIndex();
		while(end>=(Integer)lf.keys[index].get(primaryKey)){
			while(index<=lf.getNoOfKeys()){
				ArrayList<Object> arr=lf.keys[index];
				arrdata.add(arr);
				index++;
			}
			if(lf.getRightLeafNode()==-1){
				break;
			}
			lf=read_node(lf.getRightLeafNode());
			index=1;
		}
		return arrdata;

	}

	/*
	 * This function searches the B+tree for the input value and returns the data of class
	 * type KeyInNode that basically will contain the reference and particular index of the
	 *  node containing the reqested data.
	 */



	private keyInNode searchTree(internalNode head,int key){
		int i=1;
		while(i<=head.getNoOfKeys() && key>=head.keys[i]){
			i++;
		}
		keyInNode keyNode=null;
		if(head.getIsFinal().equals(true)){
			leafNode lf=(leafNode)read_node((Integer)(head.children[i]));
			if(hasKey(lf,key)){
				for(int j=1;j<=lf.getNoOfKeys();j++){
					if((Integer)lf.keys[j].get(primaryKey)==key){
						keyNode=new keyInNode(lf, j);
						return keyNode;
					}
				}
			}
			else{
				return null;
			}
		}
		else{
			keyNode=searchTree((internalNode)head.children[i], key);
		}
		return keyNode;
	}


	/*
	 * The following function will return the data of type arraylist of arraylist which will
	 * be the data greater than the start range given as argument in the function.
	 * The data is stored in the arraylist of arraylist because we neither know how many number of
	 * will the user ask and nor we know that how many number of data items will be there in each node.
	 */


	public ArrayList<ArrayList> rangeSearchForGreaterThan(internalNode head,int flag,int start){
		int i=1;
		int index;
		ArrayList<ArrayList> arrdata=new ArrayList<>();
		while(i<=head.getNoOfKeys() && start>=head.keys[i]){
			i++;
		}
		keyInNode keyNode=null;
		if(head.getIsFinal().equals(true)){
			leafNode lf=(leafNode)read_node((Integer)(head.children[i]));
			int k=1;
			for(;k<=lf.getNoOfKeys();k++){
				if((Integer)lf.keys[k].get(primaryKey)<start){
					continue;
				}
				else{
					break;
				}
			}

			if(flag==0 && (Integer)lf.keys[k].get(primaryKey)==start){
				index=k+1;
				if(index>lf.getNoOfKeys()){
					lf=read_node(lf.getRightLeafNode());
					index=1;
				}
			}
			else{
				index=k;
			}
			while(true){
				while(index<=lf.getNoOfKeys()){
					ArrayList<Object> arr=lf.keys[index];
					arrdata.add(arr);
					index++;

				}

				if(lf.getRightLeafNode()==-1){
					break;
				}
				lf=read_node(lf.getRightLeafNode());
				index=1;
			}
			return arrdata;
		}
		else{
			return rangeSearchForGreaterThan((internalNode)head.children[i], flag,start);
		}
	}

	/*
	 * The following function will return the data of type arraylist of arraylist which will
	 * be the data smaller than the end range given as argument in the function.
	 * The data is stored in the arraylist of arraylist because we neither know how many number of
	 * will the user ask and nor we know that how many number of data items will be there in each node.
	 */

	public ArrayList<ArrayList> rangeSearchForLessThan(internalNode head,int flag,int end){
		if(head.getIsFinal().equals(true)){
			ArrayList<ArrayList> arrdata=new ArrayList<>();
			leafNode lf=(leafNode)read_node((Integer)(head.children[1]));
			int index=1;
			boolean loopbreak=false;
			while(true){
				while(index<=lf.getNoOfKeys()){
					if(flag==1 && (Integer)lf.keys[index].get(primaryKey)==end){
						ArrayList<Object> arr=lf.keys[index];
						arrdata.add(arr);
						loopbreak=true;
						break;
					}
					else if((Integer)lf.keys[index].get(primaryKey)==end || end<(Integer)lf.keys[index].get(primaryKey)){
						loopbreak=true;
						break;
					}
					ArrayList<Object> arr=lf.keys[index];
					arrdata.add(arr);
					index++;

				}
				if(loopbreak==true){
					break;
				}
				if(lf.getRightLeafNode()==-1){
					break;
				}
				lf=read_node(lf.getRightLeafNode());
				index=1;
			}
			return arrdata;
		}
		else{
			return rangeSearchForLessThan((internalNode)head.children[1], flag,end);
		}

	}

	/*
	 * The following function will update the value at the particular index of the key specified
	 * in the arguments of this function with the object named as newValue in the arguments of
	 * the function.
	 */

	public void updateKey(int key,int index,Object newValue){
		keyInNode k=Search(key);
		leafNode lf=k.lNode;
		lf.keys[k.getIndex()].set(index, newValue);
		write_node(lf);
	}

	/*
	 * The following function will return the data of type arraylist of arraylist which will
	 * be greater than start and lesser than the end values specified in the arguments of the function.
	 * The data is stored in the arraylist of arraylist because we neither know how many number of
	 * will the user ask and nor we know that how many number of data items will be there in each node.
	 */

	public ArrayList<ArrayList> rangeSearch(internalNode head,int flag1, int start , int flag2, int end){
		int i=1;
		int index;
		ArrayList<ArrayList> arrdata=new ArrayList<>();
		while(i<=head.getNoOfKeys() && start>=head.keys[i]){
			i++;
		}
		keyInNode keyNode=null;
		if(head.getIsFinal().equals(true)){
			leafNode lf=(leafNode)read_node((Integer)(head.children[i]));
			int k=1;
			for(;k<=lf.getNoOfKeys();k++){
				if((Integer)lf.keys[k].get(primaryKey)<start){
					continue;
				}
				else{
					break;
				}
			}

			if(flag1==0 && (Integer)lf.keys[k].get(primaryKey)==start){
				index=k+1;
				if(index>lf.getNoOfKeys()){
					lf=read_node(lf.getRightLeafNode());
					index=1;
				}
			}
			else{
				index=k;
			}

			boolean loopbreak=false;
			while(true){
				while(index<=lf.getNoOfKeys()){
					if(flag2==1 && (Integer)lf.keys[index].get(primaryKey)==end){
						ArrayList<Object> arr=lf.keys[index];
						arrdata.add(arr);
						loopbreak=true;
						break;
					}
					else if((Integer)lf.keys[index].get(primaryKey)==end || end<(Integer)lf.keys[index].get(primaryKey)){
						loopbreak=true;
						break;
					}
					ArrayList<Object> arr=lf.keys[index];
					arrdata.add(arr);
					index++;

				}
				if(loopbreak==true){
					break;
				}
				if(lf.getRightLeafNode()==-1){
					break;
				}
				lf=read_node(lf.getRightLeafNode());
				index=1;
			}
			return arrdata;
		}
		else{
			return rangeSearch((internalNode)head.children[i], flag1,start,flag2,end);
		}
	}

	/*
	 * This particular function will check whether the value key is present in the leafNode
	 * referenced as "x" in the arguments of this function.
	 */

	public boolean hasKey(leafNode x,int key){
		for(int i=1;i<=x.getNoOfKeys();i++){
			if((Integer)x.keys[i].get(primaryKey)==key){
				return true;
			}
		}
		return false;
	}

	/*
	 * The following function will allocate a new file number for a new node. This particular node
	 * will be stored in the file named as the integer returned from this function. So that the values
	 * can easily be retrieved.
	 */

	public int allocateNode(){
		String filename=filePath+arrayCounter+".dat";
		File f=new File(filename);
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Can't create file");
		}
		arrayCounter++;
		return arrayCounter-1;
	}

	/*
	 * The following two function will retrieve a specific kind of data from the table.
	 * This function will return an ArrayList<ArrayList> of the all of the data of the table.
	 */

	public ArrayList<ArrayList> selectStaric(){
		return selectAll(this.root);
	}
	private ArrayList<ArrayList> selectAll(internalNode head){
		if(head==null){
			return null;
		}
		ArrayList<ArrayList> arrStaric=new ArrayList<>();
		while(head.getIsFinal()==false){
			head=(internalNode)head.children[1];
		}
		leafNode lf=read_node((Integer)head.children[1]);
		while(lf!=null){
			for(int i=1;i<=lf.getNoOfKeys();i++){
				arrStaric.add(lf.keys[i]);
			}
			lf=read_node(lf.getRightLeafNode());
		}
		return arrStaric;
	}
	public leafNode read_node(int index){
		leafNode b=null;
		String filename=filePath+index+".dat";
		File f=new File(filename);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(f));
			b=(leafNode)ois.readObject();
			ois.close();
			return b;
		} catch (IOException e) {
			return b;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;	
	}

	/*
	 * The following function was written to test the progress of the b+tree during the intitial
	 * stages of writing the code. Although this function is not needed anymore, yet we have decided
	 * to keep it in the code, just as a convention.
	 */

	public void print(internalNode head){
		if(head==null){
			return;
		}
		head.printNode();
		do{
			if(head.getIsFinal().equals(true))break;
			head=(internalNode)head.children[1];
			internalNode temp=head;
			while(temp!=null){
				temp.printNode();
				temp=temp.getRightInternalNode();
			}
		}
		while(head.getIsFinal()==false);
		leafNode lf=read_node((Integer)head.children[1]);
		while(lf!=null){
			lf.printNode();
			lf=read_node(lf.getRightLeafNode());
		}

	}

	/*
	 * This function is used as an auxillary function to delete the data from the leaf. There are
	 * certain rules we are supposed to follow in order to delete a data from any leaf node. This
	 * function shifts a value from one leaf node to another leaf node based on the value of the
	 * flag given as input in the arguments of this function.
	 */

	public boolean shift(leafNode left,leafNode right,int flag){
		if((flag==1 || flag==-1)==false){
			return false;
		}
		internalNode leftParent=findParent(this.root, left.getFileNumber());
		internalNode rightParent=findParent(this.root, right.getFileNumber());
		internalNode anchor=null;
		int childOfAnchorLeaf=left.getFileNumber();
		internalNode childOfAnchorInternal=null;
		int pivitIndex=-1;
		while(leftParent.equals(rightParent)==false){
			childOfAnchorInternal=leftParent;
			leftParent=leftParent.getParent();
			rightParent=rightParent.getParent();
		}
		anchor=rightParent;
		if(childOfAnchorInternal==null){
			for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
				if(((Integer)anchor.children[i]).equals(childOfAnchorLeaf)==true){
					pivitIndex=i;
					break;
				}
			}
		}
		else{
			for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
				if(((internalNode)anchor.children[i]).equals(childOfAnchorInternal)==true){
					pivitIndex=i;
					break;
				}
			}
		}
		if(flag==-1){      //from Left to Right
			for(int i=right.getNoOfKeys()+1;i>1;i--){
				right.keys[i]=right.keys[i-1];
			}
			right.keys[1]=left.keys[left.getNoOfKeys()];
			left.setNoOfKeys(left.getNoOfKeys()-1);
			right.setNoOfKeys(right.getNoOfKeys()+1);
		}
		else if(flag==1){
			left.keys[left.getNoOfKeys()+1]=right.keys[1];
			left.setNoOfKeys(left.getNoOfKeys()+1);
			right.delete((Integer)right.keys[1].get(primaryKey),primaryKey);
		}
		anchor.keys[pivitIndex]=(Integer)right.keys[1].get(primaryKey);
		write_node(left);
		write_node(right);
		return true;
	}

	/*
	 * This function is used as an auxillary function to delete the data from the internal nodes. There are
	 * certain rules we are supposed to follow in order to delete a data from any internal node. This
	 * function shifts a value from one internal node to another internal node node based on the value of the
	 * flag given as input in the arguments of this function.
	 */

	public boolean directedShift(internalNode left,internalNode right,int flag){
		if((flag==1 || flag==-1)==false){
			return false;
		}
		internalNode leftParent=left.getParent();
		internalNode rightParent=right.getParent();
		internalNode anchor=null;
		internalNode childOfAnchorInternal=left;
		int pivitIndex=-1;
		while(leftParent.equals(rightParent)==false){
			childOfAnchorInternal=leftParent;
			leftParent=leftParent.getParent();
			rightParent=rightParent.getParent();
		}
		anchor=leftParent;
		for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
			internalNode isChild=(internalNode)anchor.children[i];
			if(isChild.equals(childOfAnchorInternal)==true){
				pivitIndex=i;
				break;
			}
		}
		if(flag==-1){      //from Left to Right
			int i=right.getNoOfKeys()+1;
			for(;i>1;i--){
				right.keys[i]=right.keys[i-1];
				right.children[i+1]=right.children[i];
			}
			right.children[i+1]=right.children[i];
			int temp=anchor.keys[pivitIndex];
			anchor.keys[pivitIndex]=left.keys[left.getNoOfKeys()];
			right.keys[1]=temp;
			right.children[1]=left.children[left.getNoOfKeys()+1];
			if(right.getIsFinal().equals(false)){
				internalNode child=(internalNode)right.children[1];
				child.setParent(right);
			}
			left.setNoOfKeys(left.getNoOfKeys()-1);
			right.setNoOfKeys(right.getNoOfKeys()+1);
		}
		else if(flag==1){
			int temp=anchor.keys[pivitIndex];
			anchor.keys[pivitIndex]=right.keys[1];
			left.keys[left.getNoOfKeys()+1]=temp;
			left.setNoOfKeys(left.getNoOfKeys()+1);
			left.children[left.getNoOfKeys()+1]=right.children[1];
			if(left.getIsFinal().equals(false)){
				internalNode child=(internalNode)left.children[left.getNoOfKeys()+1];
				child.setParent(left);
			}
			int i=1;
			for(;i<right.getNoOfKeys();i++){
				right.keys[i]=right.keys[i+1];
				right.children[i]=right.children[i+1];
			}
			right.children[i]=right.children[i+1];
			right.setNoOfKeys(right.getNoOfKeys()-1);
		}
		return true;
	}

	/*
	 * This function writes the value of the node given as argument into the pertaining files
	 * in the hard drive.
	 */

	public void write_node(leafNode node){
		String filename=filePath+node.getFileNumber()+".dat";
		File f=new File(filename);
		try {
			f.createNewFile();
		} catch (IOException e) {
			System.err.println("Can't create a new file.");
		}
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(node);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This function inserts the data given as argument to the specific node and
	 * the corresponding file is updated dynamically.
	 */

	public void insert(ArrayList data){
		if(root==null){
			int fileindex=allocateNode();
			internalNode b=new internalNode();
			leafNode lf=new leafNode(data, fileindex);
			b.children[1]=(Integer)lf.getFileNumber();
			root=b;
			write_node(lf);
			return;
		}
		else if(this.root.getNoOfKeys()==2*t-1){
			internalNode tempRoot=root;
			internalNode b=new internalNode();
			b.children[1]=tempRoot;
			this.root=b;
			this.root.setIsFinal(false);
			tempRoot.setParent(b);
			splitInternalNode(tempRoot);
		}

		insertNonFullNode(this.root,data);
	}

	/*
	 * This is an important function that will recursivley delete maintain the balance of the tree
	 * as the data to be deleted is in the leaf nodes of the tree so when some data is deleted from
	 * the b+ tree, the tree gets unbalanced so we are supposed to maintain the balance of the tree
	 * by recursivly traversing and updating the tree upwards.
	 */

	private boolean recersiveDelete(internalNode currentNode){
		if(currentNode.getNoOfKeys()>=t-1){
			return true;
		}
		if(currentNode.getLeftInternalNode()!=null && currentNode.getLeftInternalNode().getNoOfKeys()>=t){
			return directedShift(currentNode.getLeftInternalNode(), currentNode, -1);
		}
		else if(currentNode.getRightInternalNode()!=null && currentNode.getRightInternalNode().getNoOfKeys()>=t){
			return directedShift(currentNode, currentNode.getRightInternalNode(),1);
		}
		else{
			if(currentNode.getLeftInternalNode()!=null && currentNode.getParent().equals(currentNode.getLeftInternalNode().getParent())){
				merge(currentNode.getLeftInternalNode(), currentNode,1);
				currentNode=currentNode.getLeftInternalNode();
			}
			else if(currentNode.getRightInternalNode()!=null && currentNode.getParent().equals(currentNode.getRightInternalNode().getParent())){
				merge(currentNode,currentNode.getRightInternalNode(),1);
			}
			if(currentNode.getParent().equals(root) && root.getNoOfKeys()==0){
				root=currentNode;
				currentNode.setParent(null);
				return true;
			}
			else if(currentNode.getParent().equals(root)){
				return true;
			}
			else{
				return recersiveDelete(currentNode.getParent());
			}
		}
	}

	/*
	 * This function deletes the specified value from any existing node and then it calls the recursive
	 * delete function so that the tree cab be balanced again.
	 */

	public boolean delete(int key){
		keyInNode k=Search(key);
		if(k==null){
			return false;
		}
		leafNode lf=k.lNode;
		int index=k.getIndex();
		lf.delete(key, primaryKey);
		write_node(lf);
		if(lf.getNoOfKeys()<t-1){
			leafNode left=read_node(lf.getLeftLeafNode());
			leafNode right=read_node(lf.getRightLeafNode());
			internalNode lfParent=findParent(root, lf.getFileNumber());
			internalNode leftParent=null;
			internalNode rightParent=null;
			if(left!=null){
				leftParent=findParent(root, left.getFileNumber());
			}
			if(right!=null){
				rightParent=findParent(root, right.getFileNumber());
			}
			if(lfParent.equals(root)){
				if(lf.getNoOfKeys()==0 && lfParent.getNoOfKeys()==0){
					root=null;
					return true;
				}
			}
			if(left!=null && left.getNoOfKeys()>=t){
				return shift(left, lf, -1);
			}
			else if(right!=null && right.getNoOfKeys()>=t){
				return shift(lf,right,1);
			}
			else{
				if(left!=null && lfParent.equals(leftParent)){
					merge(left, lf, -1);
				}
				else if(right!=null && lfParent.equals(rightParent)){
					merge(lf, right, -1);
				}
				if(lfParent.equals(root)==false){
					return recersiveDelete(lfParent);
				}
			}
		}
		return true;
	}

	/*
	 * This is another auxillary function that will be used by the delete function in the process
	 * of rebalancing the tree after the specified value is deleted from the leaf node of the tree.
	 */

	public boolean merge(Object A,Object B,int flag){
		if((flag==1 || flag==-1)==false){
			return false;
		}
		if(flag==-1){ //LeafNodes
			leafNode left=(leafNode)A;
			leafNode right=(leafNode)B;
			internalNode anchor=findParent(this.root, left.getFileNumber());
			int pivitIndex=-1;
			for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
				if(((Integer)anchor.children[i]).equals(left.getFileNumber())==true){
					pivitIndex=i;
					break;
				}
			}
			for(int i=1;i<=right.getNoOfKeys();i++){
				left.insert(right.keys[i]);
			}
			for(int i=pivitIndex;i<anchor.getNoOfKeys();i++){
				anchor.keys[i]=anchor.keys[i+1];
				anchor.children[i+1]=anchor.children[i+2];
			}
			anchor.setNoOfKeys(anchor.getNoOfKeys()-1);
			left.setRightLeafNode(right.getRightLeafNode());
			leafNode rightkaright=read_node(right.getRightLeafNode());
			if(rightkaright!=null){
				rightkaright.setLeftLeafNode(left.getFileNumber());
			}
			File file=new File(filePath+right.getFileNumber()+".dat");
			if(file.exists()){
				if(right.getFileNumber()==4){
					right.setFileNumber(5);
					right.setFileNumber(4);
				}
				file.delete();
			}
			write_node(left);
			if(rightkaright!=null){
				write_node(rightkaright);
			}
		}
		else if(flag==1){
			internalNode left=(internalNode)A;
			internalNode right=(internalNode)B;
			internalNode anchor=left.getParent();
			int pivitIndex=-1;
			for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
				internalNode isChild=(internalNode)anchor.children[i];
				if(isChild.equals(left)==true){
					pivitIndex=i;
					break;
				}
			}
			if(pivitIndex==-1){
				anchor.printNode();
				System.out.println();
				System.out.println(anchor.getNoOfKeys());
				for(int i=1;i<=anchor.getNoOfKeys()+1;i++){
					((internalNode)anchor.children[i]).printNode();
				}
				right.printNode();
				right.getNoOfKeys();
			}
			left.insert(anchor.keys[pivitIndex]);
			int i=1;
			for(;i<=right.getNoOfKeys();i++){
				left.insert(right.keys[i]);
				left.children[left.getNoOfKeys()]=right.children[i];
				if(left.getIsFinal().equals(false)){
					((internalNode)(left.children[left.getNoOfKeys()])).setParent(left);
				}
			}
			left.children[left.getNoOfKeys()+1]=right.children[i];
			if(left.getIsFinal().equals(false)){
				((internalNode)(left.children[left.getNoOfKeys()+1])).setParent(left);
			}
			left.setRightInternalNode(right.getRightInternalNode());
			if(right.getRightInternalNode()!=null){
				right.getRightInternalNode().setLeftInternalNode(left);
			}
			for(int j=pivitIndex;j<anchor.getNoOfKeys();j++){
				anchor.keys[j]=anchor.keys[j+1];
				anchor.children[j+1]=anchor.children[j+2];
			}
			anchor.setNoOfKeys(anchor.getNoOfKeys()-1);
		}
		return true;
	}

	/*
	 * This function is an auxillary function that will be used by the insert function to rearrange
	 * the references of the data in the internal node after the insertion of the specified data into
	 * the leaf node of the b+ tree.
	 */

	public void insertNonFullNode(internalNode head,ArrayList data){
		int pKey=(Integer)data.get(primaryKey);
		int i=head.getNoOfKeys();
		while(head.keys[i]>pKey){
			i=i-1;
			if(i==0){
				break;
			}
		}
		i=i+1;
		if(head.getIsFinal()==true){
			leafNode lf=null;
			try {
				lf = (leafNode)read_node((Integer)head.children[i]);
			} catch (Exception e) {
			}
			if(lf.getNoOfKeys()<2*t-1){
				lf.insert(data);
				write_node(lf);
				return;
			}
			else{
				splitLeafNode(head,lf,data);
			}
			return;
		}
		insertNonFullNode((internalNode)head.children[i], data);
	}

	/*
	 * This function will find the parent of the specified leaf node. this function is used
	 * by the splitting and delete functions while inserting or deleting the data.
	 */

	public internalNode findParent(internalNode head,int leafFileNumber){
		while(head.getIsFinal().equals(false))head=(internalNode)head.children[1];
		while(head!=null){
			for(int i=1;i<=head.getNoOfKeys()+1;i++){
				if((Integer)head.children[i]==leafFileNumber){
					return head;
				}
			}
			head=head.getRightInternalNode();
		}
		return null;
	}

	/*
	 * Split is an important procedure while inserting the data into the table, the following funciton
	 * is an auxiallary function for the insert function and will be used to split the leaf node if
	 * the number of enteries in that leaf node exceeds any particular range.
	 */

	public void splitLeafNode(internalNode x,leafNode splitNode,ArrayList data){
		int pKey=(Integer)data.get(primaryKey);
		if(x.getNoOfKeys()==2*t-1){
			splitInternalNode(x);
			x=findParent(this.root, splitNode.getFileNumber());
		}
		int child=allocateNode();
		leafNode newNode=new leafNode(child);
		int i;
		for(i=0;i<=t-1;i++){
			newNode.insert(splitNode.keys[i+t]);
		}
		splitNode.setNoOfKeys(t-1);
		int key=(Integer)newNode.keys[1].get(primaryKey);
		int j=x.getNoOfKeys();
		while(j>0 && x.keys[j]>key){
			x.keys[j+1]=x.keys[j];
			x.children[j+2]=x.children[j+1];
			j=j-1;
			if(j==0){
				break;
			}
		}
		j=j+1;
		x.keys[j]=key;
		x.setNoOfKeys(x.getNoOfKeys()+1);
		x.children[j+1]=child;
		if(pKey>(Integer)newNode.keys[1].get(primaryKey)){
			newNode.insert(data);
		}
		else{
			splitNode.insert(data);
		}
		leafNode rightkaright=read_node(splitNode.getRightLeafNode());
		if(rightkaright!=null){
			rightkaright.setLeftLeafNode(newNode.getFileNumber());
			write_node(rightkaright);
		}
		newNode.setLeftLeafNode(splitNode.getFileNumber());
		int rightNode=splitNode.getRightLeafNode();
		splitNode.setRightLeafNode(newNode.getFileNumber());
		newNode.setRightLeafNode(rightNode);
		write_node(newNode);
		write_node(splitNode);
	}

	/*
	 * Same like the splitting of the leaf node this is an auxillary function that will be 
	 * used by the insert function to split the internal node while the values in any particular
	 * internal node exceeds the specific values.
	 */

	public void splitInternalNode(internalNode splitNode){
		internalNode x=splitNode.getParent();
		if(x.getNoOfKeys()==2*t-1){
			splitInternalNode(x);
			x=splitNode.getParent();
		}
		x=splitNode.getParent();
		internalNode newNode=new internalNode();
		newNode.setIsFinal(splitNode.getIsFinal());
		int i;
		for(i=1;i<=t-1;i++){
			newNode.insert(splitNode.keys[i+t]);
			newNode.children[i]=splitNode.children[i+t];
			if(newNode.getIsFinal().equals(false)){
				((internalNode)newNode.children[i]).setParent(newNode);
			}

		}
		newNode.children[i]=splitNode.children[i+t];
		if(newNode.getIsFinal().equals(false)){
			((internalNode)newNode.children[i]).setParent(newNode);
		}
		splitNode.setNoOfKeys(t-1);
		int key=splitNode.keys[t];
		int j=x.getNoOfKeys();
		while(j>0 && x.keys[j]>key){
			x.keys[j+1]=x.keys[j];
			x.children[j+2]=x.children[j+1];
			j=j-1;
			if(j==0){
				break;
			}
		}
		j=j+1;
		x.keys[j]=key;
		x.setNoOfKeys(x.getNoOfKeys()+1);
		x.children[j+1]=newNode;
		x.children[j]=splitNode;
		newNode.setParent(x);
		splitNode.setParent(x);
		x.setIsFinal(false);
		if(splitNode.getRightInternalNode()!=null){
			splitNode.getRightInternalNode().setLeftInternalNode(newNode);
		}
		newNode.setLeftInternalNode(splitNode);
		internalNode tempNode=splitNode.getRightInternalNode();
		splitNode.setRightInternalNode(newNode);
		newNode.setRightInternalNode(tempNode);
	}
}



