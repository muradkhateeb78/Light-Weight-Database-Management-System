package LDBMSADMINGUI;
import javax.swing.SwingUtilities;

public class DriveClass implements Runnable {
 
    @Override
    public void run() {
        new DBMSGUI(new DBMSModel());
    }
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DriveClass());
    }
}