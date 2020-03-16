package eg.edu.alexu.csd.oop.DBMS.Objects;

import eg.edu.alexu.csd.oop.DBMS.XML.SchemaReader;
import eg.edu.alexu.csd.oop.DBMS.conditionFilter.Condition;
import eg.edu.alexu.csd.oop.DBMS.conditionFilter.conditionParser;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Utilities {

    public boolean validation(String schemaPath, queryParsedData data) throws ParserConfigurationException, SAXException, IOException {
        SchemaReader reader = new SchemaReader();
        Map<String,String> prob= data.getData();
        Map<String,String> newprob = new HashMap<>();
        Map<String,String> schemaMap =reader.Read(schemaPath);
        for(String s : prob.keySet()){
            String value = prob.get(s);
            String type;
            if(isNumeric(value)) {
                type = "integer";
                if(!schemaMap.get(s).equalsIgnoreCase(type))
                    return false;

            }else if(isQuoted(value)){
                type="varchar";
                if (!schemaMap.get(s).equalsIgnoreCase(type))
                    return false;
            }
        }

        conditionParser parser = new conditionParser();
        Condition condition = parser.parseCondition(data.getCondition());
        if(condition != null) {
            if (schemaMap.get(condition.getFirstOperand().trim()) == null) {
                if (schemaMap.get(condition.getSecondOperand().trim()) == null) {
                    return false;
                }
            }
        }
        if (data.getOperationName().equalsIgnoreCase("INSERT")) {
            newprob = reArrange(prob, schemaMap);
            data.setData(newprob);
        }
        return true;
    }
    public boolean validationByCaching(Map<String,String> schemaMap, queryParsedData data) throws ParserConfigurationException, SAXException, IOException {
        Map<String,String> prob= data.getData();
        Map<String,String> newprob = new HashMap<>();
        for(String s : prob.keySet()){
            String value = prob.get(s);
            String type;
            if(isNumeric(value)) {
                type = "integer";
                if(!schemaMap.get(s).equalsIgnoreCase(type))
                    return false;

            }else if(isQuoted(value)){
                type="varchar";
                if (!schemaMap.get(s).equalsIgnoreCase(type))
                    return false;
            }
        }
        conditionParser parser = new conditionParser();
        Condition condition = parser.parseCondition(data.getCondition());
        if(condition != null) {
            if (schemaMap.get(condition.getFirstOperand().trim()) == null) {
                if (schemaMap.get(condition.getSecondOperand().trim()) == null) {
                    return false;
                }
            }
        }
        if (data.getOperationName().equalsIgnoreCase("INSERT")) {
            newprob = reArrange(prob, schemaMap);
            data.setData(newprob);
        }
        return true;
    }

    public  boolean isNumeric(String str) {
        return str.matches("-?\\d+");  //match a number with optional '-' and decimal.
    }
    public  boolean isQuoted(String str) {
        return Pattern.matches("'.+'",str);
    }
    public  Map<String,String> reArrange( Map<String,String> prob ,  Map<String,String> schemaMap){
        Map<String,String> newprob = new HashMap<>();

        for(String s : schemaMap.keySet()){
            String probColumn ;
            probColumn =  prob.get(s);
            if(probColumn!= null){
                newprob.put(s,prob.get(s));
            }
        }

        return newprob;
    }
}