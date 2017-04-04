package LDBMSADMINGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	
	@Override
	public void keyReleased(KeyEvent arg0) {
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
			addQueryToBuffer();
			DBMSGUI.queryTextField.setText("");
			indexer = queryCounter;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		addQueryToBuffer();
		DBMSGUI.queryTextField.setText("");
		indexer = queryCounter;
	}
	
	private void addQueryToBuffer(){
		if(!DBMSGUI.queryTextField.getText().equals("")){
			if(queryCounter < queryBuffer.length){
				queryBuffer[queryCounter++] = DBMSGUI.queryTextField.getText();
			}else{
				removeOldestQuery();
			}
		}
	}
	
	private void removeOldestQuery(){
		for(int i = 0; i < queryBuffer.length-1; i ++){
			queryBuffer[i] = queryBuffer[i+1];
		}
		queryBuffer[queryBuffer.length-1] = DBMSGUI.queryTextField.getText();
	}
}