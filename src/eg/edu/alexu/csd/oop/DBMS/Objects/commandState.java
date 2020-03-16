package eg.edu.alexu.csd.oop.DBMS.Objects;

import java.util.ArrayList;

public class commandState {
    private boolean executed = false;
    private String path = "";
    private Object[][] Elements;
    private int RowsCount;
    private int updateCount = 0 ;
    private ArrayList<ArrayList<Object>> array = new ArrayList<>();

    public int getRowsCount() {
        return RowsCount;
    }

    public void setRowsCount(int rowsCount) {
        RowsCount = rowsCount;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object[][] getElements() {
//        Elements = new Object[array.size()][array.get(0).get(0).toString().split(" ").length];
////System.out.println(array.get(0).get(0)+ " LLLL");
//        Object[][] Elements = new Object[array.size()][];
//        for (int i = 0; i < Elements.length; i++) {
//            Elements[i] = new Object[array.get(i).size()];
//        }
//        for(int i=0; i<array.size(); i++){
//            for (int j = 0; j < array.get(i).size(); j++) {
//                Elements[i][j] = array.get(i).get(j);
//            }
//        }

        Object[][] Elements = new Object[array.size()][];
        for (int i = 0; i < array.size(); i++) {
            for (int j=0;j<array.get(i).size();j++) {
                String[] split = array.get(i).get(0).toString().trim().split("\n");
                Elements[i] = new Object[split.length];
                for (int n = 0;n <split.length;n ++)
                    Elements[i][n] = split[n];
            }
        }
        return Elements;
    }

    public void setElements(Object[][] elements) {
        this.Elements = elements;
    }

    public void setArray(ArrayList<ArrayList<Object>> array) {
        this.array = array;
    }

    public ArrayList<ArrayList<Object>> getArray() {
        return array;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }
}
