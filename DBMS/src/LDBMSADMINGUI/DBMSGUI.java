package LDBMSADMINGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Queries.BTree2;
import Queries.InsertQueryTokens;
import Queries.database;

public class DBMSGUI{

	public static DBMSModel model;
	private JFrame frame;
	public static String confirmation="confirmation";
	public static String error="error";
	public static DBMSGUI myOwn;
	public static JPanel mainPanel;
	public static JTreeAndPane treeScrollPane;
	public static JTextArea resultTextArea;
	public static JTextField queryTextField;
	public static JLabel errorLabel;
	public static JTable resultTable;
	public static    JPanel resultPanel;

	public DBMSGUI(DBMSModel model){
		this.model = model;
		resultTextArea = new JTextArea();
		queryTextField = new JTextField();
		resultTable=new JTable();
		//        resultTable.setAutoResizeMode(resultTable.AUTO_RESIZE_OFF);
		resultTable.setEnabled(false);
		errorLabel = new JLabel(" ");
		setLookAndFeel();
		createPartControl();
	}

	private void createPartControl(){  
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.white);
		frame.setTitle("Admin");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitProcedure();
			}
		});
		createMainPanel();
		frame.add(mainPanel);
		frame.pack();
		frame.setSize(1000, 600);
		//        resultTable.setPreferredScrollableViewportSize(new Dimension(frame.getWidth(), frame.getHeight()));
		//        resultTable.setFillsViewportHeight(true);
		ImageIcon i=new ImageIcon("Files\\icon.png");
		frame.setIconImage(i.getImage());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void setLabel(String indication, String value){
		if(indication.equals(confirmation)){
			errorLabel.setForeground(Color.decode("#008000"));
			errorLabel.setText("Result: " + value);
		}
		else if(indication.equals(error)){
			errorLabel.setForeground(Color.red);
			errorLabel.setText("Error: " + value);
		}

	}
	private void createMainPanel(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		treeScrollPane = new JTreeAndPane(this, model);
		myOwn=this;
		JLabel catalogueLabel = new JLabel("Catalogue");
		catalogueLabel.setFont(new Font("serif", Font.PLAIN, 14));
		mainPanel.add(catalogueLabel, BorderLayout.NORTH);
		mainPanel.setBackground(Color.white);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(treeScrollPane.getScrollPane(), BorderLayout.WEST);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		JPanel queryPanel = new JPanel(new BorderLayout());
		JLabel queryLabel = new JLabel("Command");
		queryLabel.setOpaque(true);
		queryLabel.setBackground(Color.white);
		queryLabel.setFont(new Font("serif", Font.PLAIN, 14));
		queryPanel.add(queryLabel, BorderLayout.NORTH);

		QueryTextFieldListener queryTextFieldListener = new QueryTextFieldListener();

		JButton goBtn = new JButton("Go");
		JButton importBtn = new JButton("Import Table");
		importBtn.setFocusable(false);
		importBtn.setFont(new Font("serif", Font.PLAIN, 14));
		importBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String filePath=chooseFile();
				if(filePath!=null){
					String databaseName=JOptionPane.showInputDialog("Pleas enter the database name:");
					String tableName=JOptionPane.showInputDialog("Please enter the table name");
					populateData(filePath, databaseName, tableName);
				}
			}
		});
		goBtn.setOpaque(true);
		goBtn.setFocusable(false);
		goBtn.setFont(new Font("serif", Font.PLAIN, 14));
		goBtn.addActionListener(queryTextFieldListener);

		JPanel goImportPanel = new JPanel(new FlowLayout());
		goImportPanel.setBorder(BorderFactory.createEmptyBorder(-5, -5, -7, -5));
		goImportPanel.add(goBtn);
		goImportPanel.add(importBtn);
		goImportPanel.setBackground(Color.white);

		queryPanel.add(goImportPanel, BorderLayout.EAST);

		queryTextField.setFont(new Font("serif", Font.PLAIN, 14));
		queryTextField.setPreferredSize(new Dimension(0, 28));
		queryTextField.addKeyListener(queryTextFieldListener);
		queryPanel.add(queryTextField, BorderLayout.CENTER);

		errorLabel.setOpaque(true);
		errorLabel.setBackground(Color.white);
		errorLabel.setForeground(Color.red);
		errorLabel.setFont(new Font("serif", Font.PLAIN, 14));
		queryPanel.add(errorLabel, BorderLayout.SOUTH);

		resultPanel = new JPanel(new BorderLayout());
		JLabel resultLabel = new JLabel("Results");
		resultLabel.setOpaque(true);
		resultLabel.setBackground(Color.white);
		resultLabel.setFont(new Font("serif", Font.PLAIN, 14));
		resultPanel.add(resultLabel, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray));
		resultPanel.add(scrollPane, BorderLayout.CENTER);
		resultTextArea.setFont(new Font("serif", Font.PLAIN, 14));
		queryPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rightPanel.add(queryPanel, BorderLayout.NORTH);
		queryPanel.setBackground(Color.white);
		resultPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rightPanel.add(resultPanel, BorderLayout.CENTER);
		resultPanel.setBackground(Color.white);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		mainPanel.add(rightPanel, BorderLayout.CENTER);
		//      resultTable.setBackground(Color.lightGray);
	}
	//    public static void checking(String[][] data,String[] column){
	//    	System.err.println("Abbasi");
	//    	
	//    	JScrollPane sp=new JScrollPane(jt);
	//    	resultPanel.add(sp);
	//    	resultPanel.revalidate();
	//    	resultPanel.repaint();
	//    }
	public String chooseFile(){
		long abbasi=34;
		String s="";
		JFileChooser jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result=jfc.showOpenDialog(null);
		if(result==JFileChooser.CANCEL_OPTION){
			return null;
		}
		else if(result==JFileChooser.APPROVE_OPTION){
			s= jfc.getSelectedFile().getAbsolutePath();
		}
		return s;
	}
	public static void populateData(String fileName,String databaseName,String tableName){
		File f=new File(fileName);
		BTree2 btree=new BTree2();
		btree.useDatabase(databaseName);
		//int x=0;
		try {
			Scanner input=new Scanner(f);
			while(input.hasNextLine()){
				InsertQueryTokens iqt=new InsertQueryTokens();
				iqt.setRelation(tableName);
				ArrayList<String> arrvalues=new ArrayList<>();
				String data=input.nextLine();
				String[] tokens=data.split(",");
				for(String s:tokens){
					arrvalues.add(s);
				}
				iqt.setValues(arrvalues);
				btree.insertData(iqt);
				//x++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(x);
	}
	public void exitProcedure(){
		DriveClass.performExitingTasks();
		frame.dispose();
		System.exit(0);
	}

	private void setLookAndFeel(){
		try{
			// Significantly improves the look of the output in terms of the file names returned by FileSystemView!
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception weTried) {
			weTried.printStackTrace();
		}
	}
}