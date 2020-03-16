package eg.edu.alexu.csd.oop.JDBC.Statement;

import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdapterPool;

public abstract class DBStatementPool {
    static DBStatementAdapter adapter = null;
    static DBMSAdapterPool DBAdapter;

    public static DBStatementAdapter getStatement(String path) {
        if(adapter==null) {
            adapter = new DBStatementAdapter(path);
            return  adapter;
        }
        else {
            return adapter;
        }
    }

    public static DBStatementAdapter get (){
        return adapter;
    }

    public static void removeInstance () {
        adapter = null;
        DBAdapter = null;
    }
}
