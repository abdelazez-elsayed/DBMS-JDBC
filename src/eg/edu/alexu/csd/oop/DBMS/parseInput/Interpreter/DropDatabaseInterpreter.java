package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropDatabaseInterpreter implements Interpreter {
    private boolean valid(String query){
        query=query.trim();
        String dropRegex = "DROP+\\s+DATABASE+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *;*";
        Pattern pattern  = Pattern.compile(dropRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches())
            return true;
        return false;
    }
    @Override
    public queryParsedData interpret(String query) throws SQLException {
        queryParsedData parsed = new queryParsedData();
        query = query.trim();
        System.out.println(query);
        java.sql.SQLException e = new java.sql.SQLException();
        if (!valid(query)) {
            System.out.println(valid(query));
            throw e;
        }
        String[] splitted = query.split(" ");
        parsed.setOperationName(splitted[0].trim().toUpperCase()+splitted[1].trim().toUpperCase());
        if(splitted[2].endsWith(";"))
            splitted[2] = splitted[2].replace(";","");
        parsed.setDataBaseName(splitted[2].trim());
        return parsed;
    }
}
