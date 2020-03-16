package eg.edu.alexu.csd.oop.JDBC.Connection;

public abstract class DBConnectionPool{

    private static DBConnectionAdapter adapter;

    public static DBConnectionAdapter getConnection(String path) {
        if(adapter==null) {
            adapter = new DBConnectionAdapter(path);
            return  adapter;
        }
        else {
            return adapter;
        }
    }

    public static void removeInstance() {
        adapter = null;
    }
}