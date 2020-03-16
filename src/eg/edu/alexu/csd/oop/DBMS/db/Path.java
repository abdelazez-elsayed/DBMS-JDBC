package eg.edu.alexu.csd.oop.DBMS.db;

public class Path {
    private MyDatabase database;
   private String path;

    public MyDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MyDatabase database) {
        this.database = database;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
