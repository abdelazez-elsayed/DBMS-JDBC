package eg.edu.alexu.csd.oop.JDBC.Statement;

public class Check {

    private String newquery;
    public String Checker() {
        newquery = newquery.toUpperCase();
        if(newquery .startsWith("CR")||newquery.startsWith("DR")) {
            return "C";
        }
        else if(newquery.startsWith("S")) {
            return "S";
        }
        else if(newquery.startsWith("U")||newquery.startsWith("DE")||newquery.startsWith("I")) {
            return "U";
        }
        else {
            return "null";
        }
    }
    public Check(String newquery) {
        this.newquery = newquery.trim().toUpperCase();
    }
}
