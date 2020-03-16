package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteInterpreter implements Interpreter{
    private boolean valid(String query){
        query=query.trim();
        String RD1 = "DELETE +FROM +([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? +WHERE +(.+) *;*";
        String RD2 = "DELETE +FROM +([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *;*";
        String[] deleteRegex = {RD1,RD2};
        Pattern pattern;
        Matcher matcher;
        for(int i=0;i<deleteRegex.length;i++){
            pattern = Pattern.compile(deleteRegex[i], Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(query);
            if (matcher.matches())
                return true;
        }
        return false;
    }
    @Override
    public queryParsedData interpret(String query) throws SQLException {
        queryParsedData parsed = new queryParsedData();
        query = query.trim();
        java.sql.SQLException e = new java.sql.SQLException();
        if (!valid(query))
            throw e;
        try {
            String[] divided = query.split("(?i)where");
            String[] splitted = divided[0].split(" ");
            parsed.setOperationName(splitted[0].trim().toUpperCase());
            if (splitted[2].endsWith(";"))
                splitted[2] = splitted[2].replace(";", "");
            parsed.setTableName(splitted[2].trim());
            if (divided[1].endsWith(";"))
                divided[1] = divided[1].replace(";", "");
            parsed.setCondition(divided[1].trim());
        }
        catch (Exception E){
            parsed.setCondition("No where clause");
        }
        return parsed;
    }
}
