package eg.edu.alexu.csd.oop.JDBC.ResultSetMetaData;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class DBResultSetMetaData implements ResultSetMetaData {
    @Override
    public abstract int getColumnCount() throws SQLException;

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public abstract String getColumnLabel(int column) throws SQLException;

    @Override
    public abstract String getColumnName(int column) throws SQLException;

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public abstract String getTableName(int column) throws SQLException;

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public abstract int getColumnType(int column) throws SQLException;

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new java.lang.UnsupportedOperationException();    }
}
