package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateInterpreter implements Interpreter {
    private boolean valid(String query){
        String RU1 = "UPDATE+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\?+\\s+SET+\\s+([a-zA-Z_][a-zA-Z0-9_]*) *= *(('[^']+')|(\\d+)|(\"[^\"]+\"))"
                + "( *, *([A-Za-z_][A-Za-z0-9_]*) *= *((\".+\")|('.+')|(\\d+)))* +WHERE +([^;]+) *;*";
        String RU2 = "UPDATE+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\?+\\s+SET+\\s+([a-zA-Z_][a-zA-Z0-9_]*) *= *(('[^']+')|(\\d+)|(\"[^\"]+\"))"
                + "( *, *([A-Za-z_][A-Za-z0-9_]*) *= *((\".+\")|('.+')|(\\d+)))* *;*";
        String[] deleteRegex = {RU1,RU2};
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
        if (!valid(query)) {
            throw e;
        }
        try {
            String[] divided = query.split("(?i)update|(?i)set|(?i)where");
            parsed.setOperationName("UPDATE");
            parsed.setTableName(divided[1].trim());
            String[] splitted = divided[2].split(",");
            Map data = new LinkedHashMap();
            for (int i=0;i<splitted.length;i++){
                String[] expression = splitted[i].split("=|,");
                if (expression[1].trim().endsWith(";"))
                    expression[1] = expression[1].replace(";", "");
                data.put(expression[0].trim(),expression[1].trim());
            }
            parsed.setData(data);
            if (divided[3].endsWith(";"))
                divided[3] = divided[3].replace(";", "");
            parsed.setCondition(divided[3].trim());
        }
        catch (Exception E){
            parsed.setCondition("No where clause");
        }
        return parsed;
    }
}