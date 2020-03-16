package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertInterpreter implements Interpreter {
    private boolean valid(String query){
        query=query.trim();
        String RI1 = "INSERT+\\s+INTO+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *\\( *(([a-zA-Z_][a-zA-Z0-9_]*)( *, *([a-zA-Z_][a-zA-Z0-9_]*))*) *\\) *"
                + "VALUES *\\( *((('[^']+')|(\\d+)|(\"[^\"]+\"))( *, *(('[^']+')|(\\d+)|(\"[^\"]+\")))*) *\\) *+;*";;
        String RI2 = "INSERT+\\s+INTO+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *"
                + "VALUES *\\( *((('[^']+')|(\\d+)|(\"[^\"]+\"))( *, *(('[^']+')|(\\d+)|(\"[^\"]+\")))*) *\\) *+;*";
        String[] deleteRegex = {RI1,RI2};
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
        String[] divided = query.split("(?i)into|\\(|\\)|(?i)values");
        try {
            parsed.setOperationName(divided[0].trim().toUpperCase());
            parsed.setTableName(divided[1].trim());
            String[] columns = divided[2].split(",");
            String[] values = divided[5].split(",");
            java.sql.SQLException E = new java.sql.SQLException();
            if (columns.length != values.length)
                throw E;
            Map<String, String> data = new LinkedHashMap();
            for (int i = 0; i < columns.length; i++)
                data.put(columns[i].trim(), values[i].trim());
            parsed.setData(data);
            return parsed;
        }
        catch (Exception E ){
            return parsed;
        }
    }
}