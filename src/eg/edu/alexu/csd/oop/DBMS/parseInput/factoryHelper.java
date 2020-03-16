package eg.edu.alexu.csd.oop.DBMS.parseInput;
import eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter.Interpreter;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class factoryHelper {
    public void test(String query) throws SQLException {
        SQLException E = new SQLException();
        try {
            interpreterFactory queryFactory = new interpreterFactory();
            Interpreter interpreter = queryFactory.getInterpreter(query);
            queryParsedData pD = interpreter.interpret(query);
            System.out.println("Output from input parser");
            System.out.println("---------------------------------------------");
            System.out.println("Operation : " + pD.getOperationName());
            System.out.println("Table Name : " + pD.getTableName());
            System.out.println("DataBase Name : " + pD.getDataBaseName());
            System.out.println("Data : " + pD.getData());
            System.out.println("Condition : " + pD.getCondition());
            System.out.println("---------------------------------------------");
        }
        catch (InstantiationException e) {
            throw E;
        } catch (InvocationTargetException e) {
            throw E;
        } catch (NoSuchMethodException e) {
            throw E;
        } catch (SQLException e) {
            throw E;
        } catch (IllegalAccessException e) {
            throw E;
        } catch (ClassNotFoundException e) {
            throw E;
        }
    }
}