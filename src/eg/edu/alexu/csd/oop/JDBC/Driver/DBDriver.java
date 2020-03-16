package eg.edu.alexu.csd.oop.JDBC.Driver;

import eg.edu.alexu.csd.oop.JDBC.Connection.DBConnection;
import eg.edu.alexu.csd.oop.JDBC.Connection.DBConnectionAdapter;

import java.io.File;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DBDriver implements Driver {
    @Override
    public abstract Connection connect(String url, Properties info) throws SQLException;

    @Override
    public abstract boolean acceptsURL(String url) throws SQLException;

    @Override
    public abstract DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException;

    @Override
    public int getMajorVersion() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public int getMinorVersion() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public boolean jdbcCompliant() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new java.lang.UnsupportedOperationException();
    }
}
