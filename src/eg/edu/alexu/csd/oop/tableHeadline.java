package eg.edu.alexu.csd.oop;

import java.util.LinkedHashMap;
import java.util.Map;

public class tableHeadline extends LinkedHashMap<String,Integer> {
    private String tableName = null;
    private static tableHeadline h = null;
    private static Map<Integer,String> types = null;

    public static tableHeadline getInstance(){
        if (h == null) {
            h = new tableHeadline();
            types = new LinkedHashMap<>();
        }
        return h;
    }

    public void putInTypes (int n,String t){
        this.types.put(n,t);
    }

    public Map<Integer,String> getTypes(){
        return this.types;
    }

    public Map<String,Integer> getSchemaMap (){
        return this.h;
    }

    public void removeInstance() {
        this.h = null;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
