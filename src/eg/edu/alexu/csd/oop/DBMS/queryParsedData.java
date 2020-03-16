package eg.edu.alexu.csd.oop.DBMS;

import java.util.LinkedHashMap;
import java.util.Map;

public class queryParsedData {
    private String operationName = "";
    private String tableName = "";
    private String dataBaseName = "";
    private String condition = "";
    private boolean selectAll=false;
    private Map<String,String> data = new LinkedHashMap<>();

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void setSelectAll(Boolean selectAll){
        this.selectAll=selectAll;
    }

    public boolean isSelectAll(){
        return selectAll;
    }
}