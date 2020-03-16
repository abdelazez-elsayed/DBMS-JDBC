package eg.edu.alexu.csd.oop.JDBC.Connection;

import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdaptar;
import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdapterPool;
import eg.edu.alexu.csd.oop.JDBC.LoggerObj;
import eg.edu.alexu.csd.oop.JDBC.Statement.DBStatement;
import eg.edu.alexu.csd.oop.JDBC.Statement.DBStatementPool;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionAdapter extends DBConnection{
    static Logger logger = LoggerObj.getLogger();

    private DBMSAdaptar adaptar;
    private DBStatement statement;
    private String path = null;

    public DBConnectionAdapter(String path){
        adaptar = DBMSAdapterPool.getinstance();
        this.path = path;
        try {
            adaptar.createDatabase("Create Database "+path.trim())  ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement createStatement() throws SQLException {
        logger.info("Creating statement...");
        statement = DBStatementPool.getStatement(path);
        logger.info("Statement created");
        return statement;
    }

    public void close() throws SQLException {
        logger.info("Closing connection...");
        adaptar = null;
        statement = null;
        DBConnectionPool.removeInstance();
        DBStatementPool.removeInstance();
        DBMSAdapterPool.remove();
        logger.info("connection closed.");
    }

}
