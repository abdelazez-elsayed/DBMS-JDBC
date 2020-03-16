package eg.edu.alexu.csd.oop.DBMS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class cachedTable {
    private String databaseName = new String();
    private String path = new String();
    private String tableName = new String();
    private Map<String,String> coulmnsValues = new LinkedHashMap<>();
    private Map<String,String> coulmnsTypes = new LinkedHashMap<>();
    private ArrayList<Map> rows = new ArrayList<>();

    public void addCoulnm(String name,String value){
        coulmnsValues.put(name,value);
    }

    public void addRow(){
//        coulmnsValues.put(coulmn,value);
        rows.add(coulmnsValues);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getCoulmnsValues() {
        return coulmnsValues;
    }

    public Map<String, String> getCoulmnsTypes() {
        return coulmnsTypes;
    }

    public void setCoulmnsTypes(Map<String, String> coulmnsTypes) {
        this.coulmnsTypes = coulmnsTypes;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}