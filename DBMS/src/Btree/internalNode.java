package Btree;
import java.io.Serializable;
import java.util.ArrayList;

public class internalNode implements Serializable{
	private int noOfKeys;
	public int keys[];
	public Object children[];
	private Boolean isFinal;
	private internalNode parent;
	private internalNode leftInternalNode;
	private internalNode rightInternalNode;
	public int getNoOfKeys() {
		return noOfKeys;
	}
	public void printNode(){
		for(int i=1;i<=this.noOfKeys;i++){
			System.out.print(this.keys[i]+",");
		}
	}
	public void setNoOfKeys(int noOfKeys) {
		this.noOfKeys = noOfKeys;
	}
	public Boolean getIsFinal() {
		return isFinal;
	}
	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}
	public internalNode getParent() {
		return parent;
	}
	public void setParent(internalNode parent) {
		this.parent = parent;
	}
	public internalNode(){
		this.leftInternalNode=null;
		this.rightInternalNode=null;
		this.keys=new int[2*Btree.t];
		this.children=new Object[(2*Btree.t)+1];
		noOfKeys=0;
		this.keys=new int[2*Btree.t];
		this.children=new Object[2*Btree.t+1];
		this.parent=null;
		this.isFinal=true;
	}

	public internalNode getLeftInternalNode() {
		return leftInternalNode;
	}
	public void setLeftInternalNode(internalNode leftInternalNode) {
		this.leftInternalNode = leftInternalNode;
	}
	public internalNode getRightInternalNode() {
		return rightInternalNode;
	}
	public void setRightInternalNode(internalNode rightInternalNode) {
		this.rightInternalNode = rightInternalNode;
	}
	public void insert(int data){
		int i=this.noOfKeys;
		for(;i>=1;i--){
			if(data>this.keys[i]){
				this.keys[i+1]=data;
				this.noOfKeys++;
				return;
			}
			this.keys[i+1]=this.keys[i];
			this.children[i+2]=this.children[i+1];
		}
		this.keys[i+1]=data;
		this.noOfKeys++;
	}
}
