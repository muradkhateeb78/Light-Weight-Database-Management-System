package LDBMSADMINGUI;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class JTreeAndPane {

	/*
	 * This class creates and renders the JTree where our database catalogue is shown and sets the scrollpane for
	 * the complete list of the databases in the JTree.
	 */
	
	private DBMSModel model;
	private JScrollPane scrollPane;
	public static JTree tree;

	public JTreeAndPane(DBMSGUI frame, DBMSModel model) {
		this.model = model;
		createPartControl();
	}
	private void createPartControl() {
		tree = new JTree(model.createTreeModel());
		tree.addTreeSelectionListener(new SelectionListener());
		tree.expandRow(1);
		tree.setRootVisible(false);
		tree.setCellRenderer(new DBMSModelStyler(model));
		tree.setShowsRootHandles(true);
		scrollPane = new JScrollPane(tree);
		Dimension preferredSize = scrollPane.getPreferredSize();
		Dimension widePreferred = new Dimension(200, (int) preferredSize.getHeight());
		scrollPane.setPreferredSize( widePreferred );
	}
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public static void repaintTree(){
		tree.validate();
		tree.repaint();
	}
}