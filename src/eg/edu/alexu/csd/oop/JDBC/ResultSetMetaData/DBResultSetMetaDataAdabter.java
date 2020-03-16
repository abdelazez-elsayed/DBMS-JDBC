package eg.edu.alexu.csd.oop.JDBC.ResultSetMetaData;

import eg.edu.alexu.csd.oop.JDBC.resultSet.tableData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBResultSetMetaDataAdabter extends DBResultSetMetaData {
    tableData tD = null;
    public DBResultSetMetaDataAdabter(tableData tD){
        this.tD = tD;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return tD.getNames().size();
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        Map<String ,Integer> colNames = tD.getNames();
        for (String k :colNames.keySet()){
            if(colNames.get(k)==column-1)
                return k;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        for (String k :tD.getNames().keySet()){
            if(tD.getNames().get(k).equals(column-1))
                return k;
        }
        return null;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return tD.getTableName();
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        String type=tD.getTypes().get(column-1);
        if(type.equalsIgnoreCase("varchar"))
            return java.sql.Types.VARCHAR;
        else
            return java.sql.Types.INTEGER;
    }
}