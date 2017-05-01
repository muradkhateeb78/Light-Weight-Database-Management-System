package Btree;

/*
 * This class has been created in order to return and share the data between various function in the
 * DBMS so that the value of any particular entery can be specifed by the reference of its containing
 * node and the integer containing the index of the spcifed value in the referenced node.
 */

public class keyInNode {
	private int index;
	public leafNode lNode;
	public keyInNode(leafNode lNode,int index){
		this.index=index;
		this.lNode=lNode;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

}
