package LDBMSADMINGUI;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class SelectionListener implements TreeSelectionListener {
	
	/*
	 *This class and the function in it changes the the queryTextField. It hears if any of the element of the JTree
	 *is clicked and updates the queryTextField accordingly. 
	 */

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
		DBMSNode fileNode = (DBMSNode) node.getUserObject();
		if(!node.toString().equals(DriveClass.folderOfDatabase)){
			if(!node.isRoot()){
				if(node.getParent().toString().equals(DriveClass.folderOfDatabase)){
					DBMSGUI.queryTextField.setText("use database " + fileNode.getFile().getName() + ";");
				}else{
					DBMSGUI.queryTextField.setText("select * from " + fileNode.getFile().getName() + ";");
				}
			}
		}
	}
}