import java.io.Serializable;
import java.util.ArrayList;

public class leafNode implements Serializable{
	private int noOfKeys=0;
	private int fileNumber;
	public ArrayList keys[];
	private int leftLeafNode;
	private int rightLeafNode;
	public int getLeftLeafNode() {
		return leftLeafNode;
	}
	public void setLeftLeafNode(int leftLeafNode) {
		this.leftLeafNode = leftLeafNode;
	}
	public void printNode(){
		for(int i=1;i<=this.noOfKeys;i++){
			System.out.print(this.keys[i].get(0)+",");
		}
	}
	public int getRightLeafNode() {
		return rightLeafNode;
	}
	public void setRightLeafNode(int rightLeafNode) {
		this.rightLeafNode = rightLeafNode;
	}
	public leafNode(ArrayList data,int fileNumber){
		this.keys=new ArrayList[2*Btree.t];
		noOfKeys++;
		leftLeafNode=-1;
		rightLeafNode=-1;
		this.keys[noOfKeys]=data;
		this.fileNumber=fileNumber;
	}
	public leafNode(int fileNumber){
		leftLeafNode=-1;
		rightLeafNode=-1;
		this.keys=new ArrayList[2*Btree.t];
		this.fileNumber=fileNumber;
	}
	public int getNoOfKeys() {
		return noOfKeys;
	}
	public void setNoOfKeys(int noOfKeys) {
		this.noOfKeys = noOfKeys;
	}
	public int getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}
	public ArrayList[] getKeys() {
		return keys;
	}
	public void setKeys(ArrayList[] keys) {
		this.keys = keys;
	}
	public void insert(ArrayList data){
		int pKey=(Integer)data.get(Btree.primaryKey);
		int i=this.noOfKeys;
		for(;i>=1;i--){
			if(pKey>(Integer)this.keys[i].get(Btree.primaryKey)){
				this.keys[i+1]=data;
				this.noOfKeys++;
				return;
			}
			this.keys[i+1]=this.keys[i];
		}
		this.keys[i+1]=data;
		this.noOfKeys++;
	}
	public leafNode delete(int key,int primaryKey){
		for(int i=1;i<=this.noOfKeys;i++){
			if((Integer)this.keys[i].get(primaryKey)==key){
				while(i<this.noOfKeys){
					this.keys[i]=this.keys[i+1];
					i++;
				}
				this.noOfKeys--;
				return this;
			}
		}
		return this;
	}
}
