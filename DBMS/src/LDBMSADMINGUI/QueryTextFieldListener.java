package LDBMSADMINGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import Queries.QueryParser;
import Queries.QueryValidator;

public class QueryTextFieldListener implements KeyListener, ActionListener{

	public static String[] queryBuffer;
	private static int queryCounter;
	private static int indexer;

	public QueryTextFieldListener() {
		queryBuffer = new String[10];
		queryCounter = 0;
		indexer = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	/*
	 *Key listener for the up, down and eneter key. Up and down keys update the queryTextField by either showing the
	 *most recent queries or old ones. Enter executes the query.If it, the query is executed and the queryTextField's query buffer
	 * is updated.
	 */
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		try{
			if(arg0.getKeyCode() == arg0.VK_DOWN){
				if(indexer <= queryCounter){
					DBMSGUI.queryTextField.setText(queryBuffer[indexer++]);
				}
				else if(indexer == queryCounter+1){
					indexer = queryCounter;
					DBMSGUI.queryTextField.setText("");
				}
			}else if(arg0.getKeyCode() == arg0.VK_UP){
				if(indexer >= 0){
					DBMSGUI.queryTextField.setText(queryBuffer[indexer--]);
				}
				if(indexer == -1){
					indexer = 0;
				}
			}
			else if((arg0.getKeyCode() == arg0.VK_ENTER)){
				TableModel tm=new DefaultTableModel(0,0);
				DBMSGUI.resultTable.setModel(tm);
				DBMSGUI.resultTable.repaint();
				addQueryToBuffer();
				runQuery(DBMSGUI.queryTextField.getText());
				DBMSGUI.queryTextField.setText("");
				indexer = queryCounter;
			}
		}catch(IndexOutOfBoundsException ioobe){
			//System.out.println("Index Exception.");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {}

	/*
	 * This listens if the GO button is pressed. If it is, the query is executed and the queryTextField's query buffer
	 * is updated.
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		TableModel tm=new DefaultTableModel(0,0);
		DBMSGUI.resultTable.setModel(tm);
		DBMSGUI.resultTable.repaint();
		addQueryToBuffer();
		runQuery(DBMSGUI.queryTextField.getText());
		DBMSGUI.queryTextField.setText("");
		indexer = queryCounter;
	}
	
	/*
	 * This functions add the used query to the query buffer for later use.
	 */

	private void addQueryToBuffer(){
		if(!DBMSGUI.queryTextField.getText().equals("")){
			if(queryCounter < queryBuffer.length){
				queryBuffer[queryCounter++] = DBMSGUI.queryTextField.getText();
			}else{
				removeOldestQuery();
			}
		}
	}

	/*
	 * If query buffer is full, the oldest query is deleted and latest one is added.
	 */
	
	private void removeOldestQuery(){
		for(int i = 0; i < queryBuffer.length-1; i ++){
			queryBuffer[i] = queryBuffer[i+1];
		}
		queryBuffer[queryBuffer.length-1] = DBMSGUI.queryTextField.getText();
	}

	/*
	 * This function actually runs the query and the process of the query parsing and validation starts here.
	 */
	
	private void runQuery(String query){
		query = query.replaceAll("[ ]+"," ");
		if(!query.matches("[ ]*")){
			if(query.charAt(0) == ' ' && query.length() > 0){
				query = query.substring(1, query.length());
			}
			QueryValidator qv = new QueryValidator(query.toLowerCase());
			if(qv.isQueryValid()){
				QueryParser qp = new QueryParser(query.replaceAll(";", ""));
				qp.executeQuery();
				DefaultTreeModel model = (DefaultTreeModel) JTreeAndPane.tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
				JTreeAndPane.tree.expandPath(new TreePath(model.getPathToRoot(root.getChildAt(0))));
			}else{
				DBMSGUI.errorLabel.setText(qv.queryError());
				String error = qv.queryError();
				DBMSGUI.setLabel(DBMSGUI.error, error);
			}
		}
		query = query.replaceAll("[ ]+"," ");	
	}
}