package LDBMSADMINGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
 
public class DBMSGUI{
     
    private DBMSModel model;
    private JFrame frame;
    private JPanel mainPanel;
    private JTreeAndPane treeScrollPane;
    public static JTextArea resultTextArea;
    public static JTextField queryTextField;
    public static JLabel errorLabel;
 
    public DBMSGUI(DBMSModel model){
        this.model = model;
        resultTextArea = new JTextArea();
        queryTextField = new JTextField();
        errorLabel = new JLabel("Error here...");
        setLookAndFeel();
        createPartControl();
    }
     
    private void createPartControl(){  
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.white);
        frame.setTitle("L-Admin");
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    private void createMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        treeScrollPane = new JTreeAndPane(this, model);
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
        
        JButton goBtn = new JButton("GO");
        goBtn.setOpaque(true);
        goBtn.setFocusable(false);
        goBtn.setFont(new Font("serif", Font.PLAIN, 14));
        goBtn.addActionListener(queryTextFieldListener);
        queryPanel.add(goBtn, BorderLayout.EAST);
        
        queryTextField.setFont(new Font("serif", Font.PLAIN, 14));
        queryTextField.setPreferredSize(new Dimension(0, 30));
        queryTextField.addKeyListener(queryTextFieldListener);
        queryPanel.add(queryTextField, BorderLayout.CENTER);
        
        errorLabel.setOpaque(true);
        errorLabel.setBackground(Color.white);
        errorLabel.setForeground(Color.red);
        errorLabel.setFont(new Font("serif", Font.PLAIN, 12));
        queryPanel.add(errorLabel, BorderLayout.SOUTH);
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("Results");
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.white);
        resultLabel.setFont(new Font("serif", Font.PLAIN, 14));
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
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
    }
     
    public void exitProcedure(){
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