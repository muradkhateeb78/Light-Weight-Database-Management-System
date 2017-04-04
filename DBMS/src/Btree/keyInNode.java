package Btree;
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
