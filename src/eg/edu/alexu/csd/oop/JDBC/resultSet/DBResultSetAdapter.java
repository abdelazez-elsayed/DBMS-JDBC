package eg.edu.alexu.csd.oop.JDBC.resultSet;

import eg.edu.alexu.csd.oop.JDBC.LoggerObj;
import eg.edu.alexu.csd.oop.JDBC.ResultSetMetaData.DBResultSetMetaData;
import eg.edu.alexu.csd.oop.JDBC.ResultSetMetaData.DBResultSetMetaDataAdabter;
import eg.edu.alexu.csd.oop.JDBC.Statement.DBStatementPool;
import org.apache.log4j.Logger;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBResultSetAdapter extends DBResultSet {
    private tableData tD;
    private int cursor;
    private Statement statement;
    private boolean closed;
    static Logger logger = LoggerObj.getLogger();

    public DBResultSetAdapter(tableData tD) {
        this.tD = tD;
        this.closed = false;
        this.statement = DBStatementPool.get();
        this.cursor = -1;
    }

    public boolean absolute(int row) throws SQLException {
        if (row>0) {
            if (tD.getData().size() < row ) {
                cursor = row - 1;
                return true;
            }
        }
        else {
            if (tD.getData().size() < tD.getData().size()- Math.abs(row)) {
                cursor = tD.getData().size()-Math.abs(row);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean previous() throws SQLException {
        if (cursor!=-1) {
            cursor--;
            return true;
        }
        return false;
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.statement;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.closed;
    }

    public boolean next() throws SQLException{
        System.out.println("next "+cursor);
        if (tD.getData().size() > cursor+1) {
            cursor++;
            return true;
        }
        return false;
    }

    @Override
    public void close() throws SQLException {
        logger.info("Closing Result Set");
        this.closed = true;
        this.statement = null;
        this.tD = null;
        this.cursor = -1;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {

        System.out.println("getString "+cursor);
        Object ob = tD.getData().get(cursor).get(columnIndex-1);
        System.out.println("getString object "+ob);
        if(ob instanceof String)
            return (String) ob;
        throw new SQLException();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        System.out.println("getInt "+cursor);
        Object ob = tD.getData().get(cursor).get(columnIndex-1);
        System.out.println("getInt "+ ob);
        if(ob instanceof Integer)
            return (int) ob;
        throw new SQLException();
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        int columnIndex = tD.getNames().get(columnLabel);
        Object ob = tD.getData().get(cursor).get(columnIndex-1);
        if(ob instanceof String)
            return (String) ob;
        throw new SQLException();
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        int columnIndex = tD.getNames().get(columnLabel);
        Object ob = tD.getData().get(cursor).get(columnIndex-1);
        if(ob instanceof Integer)
            return (int) ob;
        throw new SQLException();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        logger.info("Getting meta data");

        DBResultSetMetaData DBRMD = new DBResultSetMetaDataAdabter(tD);
        return DBRMD;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        logger.info("Getting object at column index"+columnIndex);
        return tD.getData().get(cursor).get(columnIndex-1);
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        logger.info("Trying to get column "+columnLabel);
        try {
            return tD.getNames().get(columnLabel);

        }
        catch (Exception e){
            logger.info("Sql exception");
            throw new SQLException();
        }
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        if (cursor==-1&&tD.getData().size()!=0)
            return true;
        return false;
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        if (tD.getData().size()!=0&&cursor==tD.getData().size())
            return true;
        return false;
    }

    @Override
    public boolean isFirst() throws SQLException {
        if (cursor==1&&tD.getData().size()!=0)
            return true;
        return false;
    }

    @Override
    public boolean isLast() throws SQLException {
        if (tD.getData().size()!=0&&cursor==tD.getData().size()-1)
            return true;
        return false;
    }

    @Override
    public void beforeFirst() throws SQLException {
        cursor = -1;
    }

    @Override
    public void afterLast() throws SQLException {
        cursor = tD.getData().size();
    }

    @Override
    public boolean first() throws SQLException {
        if(tD.getData().size()!=0){
            cursor = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean last() throws SQLException {
        if (tD.getData().size()!=0){
            cursor = tD.getData().size()-1;
            return true;
        }
        return false;
    }

}
