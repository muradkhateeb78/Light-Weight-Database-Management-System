package LDBMSADMINGUI;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
 
public class SelectionListener implements TreeSelectionListener {
 
    @Override
    public void valueChanged(TreeSelectionEvent event) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
        DBMSNode fileNode = (DBMSNode) node.getUserObject();
        if(!node.toString().equals("NAMAL")){
	        if(!node.isRoot()){
		        if(node.getParent().toString().equals("NAMAL")){
		        	DBMSGUI.queryTextField.setText("use database " + fileNode.getFile().getName() + ";");
		        }else{
		        	DBMSGUI.queryTextField.setText("select * from " + fileNode.getFile().getName() + ";");
		        }
	        }
        }
    }
}