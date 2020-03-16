package eg.edu.alexu.csd.oop.DBMS.GUI;

import eg.edu.alexu.csd.oop.DBMS.db.MyDatabase;

import java.sql.SQLException;

public class FunctionFacasd {
    private String query = new String();

    public void setQuery(String query){
        this.query = query;
    }

    public String operate() throws SQLException {
        String output = new String();
        MyDatabase db =  MyDatabase.getDatabase();
        String newquery =  query.trim() ;
        newquery= newquery.toUpperCase();
        if(newquery.startsWith("CR")||newquery.startsWith("DR")){
            boolean ex = db.executeStructureQuery(query);
            output = output.concat("Executed : ");
            if (ex)
                output = output.concat("True");
            else output = output.concat("False");
            return output;
        }
        else if(newquery.startsWith("S")){
            Object[][] elements = db.executeQuery(query);
            output = output.concat("Selected : "+"\n");
            for (int i =0;i<elements.length;i++){
                for (int j =0 ;j<elements[i].length;j++) {
                    try {
                        output = output.concat((String) elements[i][j]).concat("\t");
                    }
                    catch (Exception e){
                        output = output.concat(String.valueOf(elements[i][j])).concat("\t");
                    }
                }
                output = output.concat("\n");
            }
            return output;
        }
        else if (newquery.startsWith("U")||newquery.startsWith("DE")||newquery.startsWith("I")){
            int updated = db.executeUpdateQuery(query);
            output = output.concat("Number of Rown Updated : ");
            output = output.concat(String.valueOf(updated));
            return output;
        }
        else{
            throw new SQLException();
        }
    }
}
