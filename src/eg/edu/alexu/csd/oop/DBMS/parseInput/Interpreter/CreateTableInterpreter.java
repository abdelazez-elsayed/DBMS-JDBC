package eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter;

import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTableInterpreter implements Interpreter{

    private boolean valid(String query){
        query=query.trim();
        String RCT = "CREATE+\\s+TABLE+\\s+([a-zA-Z]:)?(\\\\?[a-zA-Z0-9_.-]+)+\\\\? *\\( *([a-zA-Z_][a-zA-Z0-9_]* + *(int|varchar))( *, *([a-zA-Z_][a-zA-Z0-9_]* *+(int|varchar)))* *\\) *;*";
        Pattern pattern  = Pattern.compile(RCT, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches())
            return true;
        return false;
    }
    private queryParsedData parse (String query){
        int count=1;
        queryParsedData parsed = new queryParsedData();
        try{
            String[] divided = query.split("\\(");
            if (divided[1].trim().endsWith(";"))
                divided[1] = divided[1].replace(";", "");
            if (divided[1].trim().endsWith(")"))
                divided[1] = divided[1].replace(")", "");
            String [] splittedName =divided[0].split("\\s");
            splittedName[splittedName.length-1]= splittedName[splittedName.length-1].trim();
            parsed.setTableName(splittedName[splittedName.length-1]);
            parsed.setOperationName("CREATETABLE");
            String[] splittedCoulmn =divided[1].split(",");
            Map<String,String> data = new LinkedHashMap<>();
            for(int i=0;i<splittedCoulmn.length;i++){
                splittedCoulmn[i]=splittedCoulmn[i].trim();
                String []splittedType =splittedCoulmn[i].split(" ");
                data.put(splittedType[0].trim(),splittedType[1].trim());
            }
            parsed.setData(data);
        }
        catch (Exception e){
        }
        return parsed;
    }
    @Override
    public queryParsedData interpret(String query) throws SQLException {
        queryParsedData parsed;
        query = query.trim();
        java.sql.SQLException e = new java.sql.SQLException();
        if (!valid(query))
            throw e;
        parsed = parse(query);
        return parsed;
    }
}