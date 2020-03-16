package eg.edu.alexu.csd.oop.DBMS;

import eg.edu.alexu.csd.oop.DBMS.db.MyDatabase;
import eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter.Interpreter;
import eg.edu.alexu.csd.oop.DBMS.parseInput.interpreterFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class SQLCommand {
    private JPanel Panel;
    private JTextField textField1;
    private JTextArea textArea1;
    private JTable table1;
    private MyDatabase datafromgui;
    private String query ;

    public SQLCommand() {
        datafromgui = MyDatabase.getDatabase();
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    query = textField1.getText() ;
                    String [] regex = Regex(query) ;
                    for(int i =0 ; i<regex.length ; i++ ) {
                       String Checker =  Check(regex[i]) ;
                       if(Checker != "null"){
                       if(Checker.equalsIgnoreCase("C")){
                           try {
                               boolean Out  = datafromgui.executeStructureQuery(regex[i]) ;
                               if(Out == true) {
                                    textArea1.setAlignmentX(300);
                                    textArea1.setAlignmentY(200);
                                    textArea1.append("DONE");
                               }}
                           catch (SQLException ex) {
                               ex.printStackTrace();
                               JOptionPane.showMessageDialog(null , "SQLException");
                           }
                       }else if (Checker.equalsIgnoreCase("S")){
                           try {
                               Object[][] out = datafromgui.executeQuery(regex[i]);
                               interpreterFactory factory = new interpreterFactory();
                               Interpreter interpreter = factory.getInterpreter(regex[i]);
                               queryParsedData parsedData = interpreter.interpret(regex[i]);
                               Map<String,String> data =parsedData.getData();
                               Set<String> set = data.keySet();
                               Object[] Columns = new String[set.size()];
                               int k=0;
                               for(String s : set){
                                   Columns[k++]=s;
                               }
                           DefaultTableModel model = new DefaultTableModel();
                               model.setDataVector(out,Columns);
                               table1.setModel(model);

                              // PrintSelection(Out) ;
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                               JOptionPane.showMessageDialog(null , "SQLException");
                           } catch (InstantiationException e1) {
                               e1.printStackTrace();
                           } catch (InvocationTargetException e1) {
                               e1.printStackTrace();
                           } catch (NoSuchMethodException e1) {
                               e1.printStackTrace();
                           } catch (IllegalAccessException e1) {
                               e1.printStackTrace();
                           } catch (ClassNotFoundException e1) {
                               e1.printStackTrace();
                           }
                       }else{
                           try {
                               int RowCount  = datafromgui.executeUpdateQuery(regex[i]);
                               textArea1.setAutoscrolls(true);
                               textArea1.setAlignmentX(300);
                               textArea1.setAlignmentY(200);
                               textArea1.append(""+RowCount);
                           } catch (SQLException ex) {
                               ex.printStackTrace();
                           }
                       }}
                       else if (Checker == "null"){
                           JOptionPane.showMessageDialog(null , "SQLException");
                       }
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("SQLCommand");
        frame.setContentPane(new SQLCommand().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    private String[] Regex (String query ){
            query.trim();
            String [] RegexString ;
           return   RegexString = query.split(";");
    }
    private String Check (String query){
           String newquery =  query.trim() ;
          newquery= newquery.toUpperCase();
            if(newquery.startsWith("CR")||newquery.startsWith("DR")){
                return "C" ;
            }
            else if(newquery.startsWith("S")){
                return "S" ;
            }
            else if (newquery.startsWith("U")||newquery.startsWith("DE")||newquery.startsWith("I")){
                return  "U" ;
            }
            else{
                return "null" ;
            }
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
