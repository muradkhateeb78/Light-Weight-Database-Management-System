package LDBMSADMINGUI;
import java.io.File;

public class DBMSNode {

	/*
	 * This class acts as the node class of the JTree, our catalogue. It helps in the rendering of the JTree where
	 * each node item is represented as DBMSNode.
	 */
	
	private boolean generateGrandchildren;
	private File file;

	public DBMSNode(File file) {
		this.file = file;
		this.generateGrandchildren = true;
	}

	public File getFile() {
		return file;
	}

	public boolean isGenerateGrandchildren() {
		return generateGrandchildren;
	}

	public void setGenerateGrandchildren(boolean generateGrandchildren) {
		this.generateGrandchildren = generateGrandchildren;
	}

	@Override
	public String toString() {
		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}
}