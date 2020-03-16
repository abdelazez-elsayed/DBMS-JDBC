package eg.edu.alexu.csd.oop.JDBC.DBMSAdapter;
import eg.edu.alexu.csd.oop.DBMS.db.Database;
import eg.edu.alexu.csd.oop.DBMS.db.MyDatabase;
import eg.edu.alexu.csd.oop.JDBC.Statement.DBStatementAdapter;
import eg.edu.alexu.csd.oop.JDBC.Statement.DBStatementPool;
import eg.edu.alexu.csd.oop.JDBC.resultSet.DBResultSetAdapter;
import eg.edu.alexu.csd.oop.JDBC.resultSet.tableData;
import eg.edu.alexu.csd.oop.tableHeadline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DBMSAdaptar {

   Database adpterData =null;

   public DBMSAdaptar(){
        adpterData = MyDatabase.getDatabase();
    }
    public boolean  createDatabase (String sql ) throws SQLException {
        return adpterData.executeStructureQuery(sql) ;
    }

    public int update (String sql) throws SQLException {
        return adpterData.executeUpdateQuery(sql) ;
    }

    public ResultSet select (String sql ) throws SQLException {
       Object [][] returnedData  =  adpterData.executeQuery(sql);
       tableHeadline tB = tableHeadline.getInstance();
       tableData tD = new tableData();
       Map<String,Integer> names = tB.getSchemaMap();
       tD.setNames(names);
       tD.setNames(tD.getNames());
       tD.setData(returnedData);
       tD.setTypes(tB.getTypes());
       tD.setTableName(tB.getTableName());
       tB.removeInstance();
       return  new DBResultSetAdapter(tD) ;
    }

}
