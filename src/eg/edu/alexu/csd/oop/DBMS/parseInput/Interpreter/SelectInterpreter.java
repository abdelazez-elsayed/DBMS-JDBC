package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectInterpreter implements Interpreter{
    private boolean valid(String query){
        query=query.trim();
        String RS1 = "SELECT+\\s+(\\*) +FROM+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *+WHERE +([^;]+) *;*";
        String RS2 ="SELECT+\\s+(\\*) +FROM+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *;*";
        String RS3 = "SELECT+\\s+(([A-Za-z_][A-Za-z0-9_]*)( *, *[A-Za-z_][A-Za-z0-9_]*)*) +FROM+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *;*";
        String RS4 = "SELECT+\\s+(([A-Za-z_][A-Za-z0-9_]*)( *, *[A-Za-z_][A-Za-z0-9_]*)*) +FROM+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *+WHERE +([^;]+) *;*";
        String[]selectRegex={RS1,RS2,RS3,RS4};
        for(int i=0;i<selectRegex.length;i++)
        {
            Pattern pattern  = Pattern.compile(selectRegex[i], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);
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
        int count=1;
        try{
            String[] divided = query.split("(?i)select|(?i)where|(?i)from");
            if (divided[2].endsWith(";"))
                divided[2] = divided[2].replace(";", "");
            parsed.setTableName(divided[2].trim());
            if(divided[1].trim().equals("*")){
                parsed.setSelectAll(true);
                parsed.setOperationName("SELECT");
            }else{
                String[] splitted =divided[1].split(",");
                parsed.setOperationName("SELECT");
                Map<String,String> data = new LinkedHashMap<>();
                for(int i=0;i<splitted.length;i++)
                    data.put("coulmn"+count++,splitted[i].trim());
                parsed.setData(data);
            }

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