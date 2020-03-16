
package eg.edu.alexu.csd.oop.JDBC.Statement;

import eg.edu.alexu.csd.oop.JDBC.Connection.DBConnectionPool;
import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdaptar;
import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdapterPool;
import eg.edu.alexu.csd.oop.JDBC.LoggerObj;
import org.apache.log4j.Logger;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class DBStatementAdapter extends DBStatement {
    private Queue<String> SQLQueries = new LinkedList<>();
    private int timeOut = 0;
    private DBMSAdaptar adapter;
    private ResultSet resultSet;
    private String path;
    private int updateCount;
    private ExecutorService excutor;
    static Logger logger = LoggerObj.getLogger();

    public DBStatementAdapter(String path) {
        this.adapter = DBMSAdapterPool.getinstance();
        this.path = path;
        excutor = Executors.newSingleThreadExecutor();
    }


    public void addBatch(String sql) throws SQLException {
        this.SQLQueries.add(sql);
        logger.info("Batch added.");
    }

    public void clearBatch() throws SQLException {
        this.SQLQueries.clear();
        logger.info("Batch cleared.");
    }

    public void close() throws SQLException {
        clearBatch();
        adapter = null;
        DBStatementPool.removeInstance();
        logger.info("Statement closed");
    }

    public int[] executeBatch() throws BatchUpdateException {

        int[] arr = new int[SQLQueries.size()];
        logger.info("Executing batch...");
        for (int i = 0; i < arr.length; i++) {

            String sql = SQLQueries.poll();
            Check check = new Check(sql);
            String checker = check.Checker();
            if (checker.equalsIgnoreCase("C")) {
                try {
                    execute(sql);
                    arr[i] = 0;
                } catch (SQLException e) {
                    throw new BatchUpdateException();
                }
            } else if (checker.equalsIgnoreCase("U")) {
                try {
                    int z = executeUpdate(sql);
                    arr[i] = z;
                } catch (SQLException e) {
                    throw new BatchUpdateException();
                }
            } else {
                throw new BatchUpdateException();
            }
        }
        logger.info("Batch executed");
        return arr;
    }


    public Connection getConnection() throws SQLException {
        logger.info("Connection getting..");
        return DBConnectionPool.getConnection(path);

    }

    public int getQueryTimeout() throws SQLException {
        return this.timeOut;
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        logger.info("Query time out setted to"+seconds);
        this.timeOut = seconds;
    }

    public boolean execute(String sql) throws SQLException {// excute and if done return true
        boolean returned;
        logger.info("Executing Query...");
        Future<Boolean> future = excutor.submit(new Task(sql, adapter));
        if (getQueryTimeout() != 0) {
            try {
                returned = future.get(getQueryTimeout(), TimeUnit.SECONDS);
                return returned;

            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (ExecutionException e) {
                logger.error("Execution exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (TimeoutException e) {
                logger.error("Query time out exception");
                e.printStackTrace();
                throw new SQLException();

            }
        } else {
            try {
                return future.get();
            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();
            } catch (ExecutionException e) {
                logger.error("Execution exception");
                e.printStackTrace();
                throw new SQLException();
            }
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        logger.info("Executing Selecting query...");
        Future<Boolean> future = excutor.submit(new Task(sql, adapter));
        if (getQueryTimeout() != 0) {
            try {
                logger.info("Excuting for time out: "+getQueryTimeout()+" Seconds");
                future.get(getQueryTimeout(), TimeUnit.SECONDS);
                logger.info("Execution done");
                return resultSet;

            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (ExecutionException e) {
                logger.error("Execution exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (TimeoutException e) {
                logger.error("Query time out exception");

                e.printStackTrace();
                throw new SQLException();

            }
        } else {
            try {
                logger.info("Executing query...");
                future.get();
                logger.info("Execution done.");
                return resultSet;

            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (ExecutionException e) {
                logger.error("Execution exception");
                e.printStackTrace();
                throw new SQLException();

            }
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        logger.info("Executing update...");
        Future<Boolean> future = excutor.submit(new Task(sql, adapter));
        if (getQueryTimeout() != 0) {
            try {
                logger.info("Executing update for time out : "+getQueryTimeout()+" Seconds");
                future.get(getQueryTimeout(), TimeUnit.SECONDS);
                return updateCount;

            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (ExecutionException e) {
                logger.error("Execution exception");

                e.printStackTrace();
                throw new SQLException();

            } catch (TimeoutException e) {
                logger.error("Query time out exception");
                e.printStackTrace();
                throw new SQLException();

            }
        } else {
            try {
                future.get();
                logger.info("Execution done");
                return updateCount;

            } catch (InterruptedException e) {
                logger.error("Interrupted exception");
                e.printStackTrace();
                throw new SQLException();

            } catch (ExecutionException e) {
                logger.error("Execution exception");
                e.printStackTrace();
                throw new SQLException();

            }
        }
    }

    class Task implements Callable<Boolean> {
        String query;
        DBMSAdaptar adapter;

        public Task(String query, DBMSAdaptar adapter) {
            this.query = query;
            this.adapter = adapter;
        }

        @Override
        public Boolean call() throws Exception {
            Check check = new Check(query);
            String checker = check.Checker();
            if (checker.equals("C")) {
                return adapter.createDatabase(query);
            } else if (checker.equalsIgnoreCase("U")) {
                updateCount = adapter.update(query);
                return true;
            } else if (checker.equalsIgnoreCase("S")) {
                resultSet = adapter.select(query);
                if (resultSet.first()) {
                    resultSet.beforeFirst();
                    return true;
                } else {
                    logger.warn("NO selection deteced!");
                    return false;
                }
            } else {
                logger.error("SQL Exception");
                throw new SQLException();
            }
        }
    }
}