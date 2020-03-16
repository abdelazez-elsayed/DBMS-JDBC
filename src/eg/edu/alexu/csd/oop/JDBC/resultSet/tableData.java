package eg.edu.alexu.csd.oop.JDBC.resultSet;

import java.util.*;

public class tableData {
    private String tableName = null;
    private Map<String ,Integer> names = new LinkedHashMap<>();
    private Map<Integer ,String> types = new LinkedHashMap<>();
    private ArrayList<List<Object>> data = new ArrayList<>();

    public Map<String, Integer> getNames() {
        return names;
    }

    public void setNames(Map<String, Integer> names) {
        this.names = names;
    }

    public ArrayList<List<Object>> getData() {
        return data;
    }

    public void setData(Object[][] data) {
        for (int i = 0; i < data.length; i++)
                this.data.add(Arrays.asList(data[i]));
    }

    public Map<Integer, String> getTypes() {
        return types;
    }

    public void setTypes(Map<Integer, String> types) {
        this.types = types;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
