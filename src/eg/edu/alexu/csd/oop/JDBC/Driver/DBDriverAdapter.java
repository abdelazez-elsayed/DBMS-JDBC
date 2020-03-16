package eg.edu.alexu.csd.oop.JDBC.Driver;
import eg.edu.alexu.csd.oop.JDBC.LoggerObj;
import org.apache.log4j.Logger;
import eg.edu.alexu.csd.oop.JDBC.Connection.DBConnectionAdapter;
import eg.edu.alexu.csd.oop.JDBC.Connection.DBConnectionPool;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBDriverAdapter extends DBDriver {
    static Logger logger = LoggerObj.getLogger();
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (acceptsURL(url)){
            logger.info("Setting connection..");
            File file = (File) info.get("path");
            String path = file.getAbsolutePath();
            return  DBConnectionPool.getConnection(path);
        }
        logger.error("SQL Exception! Unaccepted URL");
        throw new SQLException();
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        String URLRegex ="jdbc:+([a-zA-Z])([a-zA-Z0-9_])*:+//+([a-zA-Z])([a-zA-Z0-9_])*";
        Pattern pattern  = Pattern.compile(URLRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches())
            return true;
        throw new SQLException();
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        DriverPropertyInfo driverPropertyInfo = new DriverPropertyInfo(url,info.getProperty("path"));
        DriverPropertyInfo[] arr = new DriverPropertyInfo[1];
        arr[0]=driverPropertyInfo;
        return arr;
    }
}
