package eg.edu.alexu.csd.oop.DBMS.db;

import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.XML.SchemaReader;
import eg.edu.alexu.csd.oop.DBMS.executeCommand.ChooseCommand;
import eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter.Interpreter;
import eg.edu.alexu.csd.oop.DBMS.parseInput.*;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import eg.edu.alexu.csd.oop.tableHeadline;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.Object ;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

public class MyDatabase implements Database {
    public static String path;
    static MyDatabase dataBase = null ;
    interpreterFactory factory = new interpreterFactory();
    String dbName = "";

    private Path cPath = new Path();

    public static MyDatabase getDatabase(){
        if(dataBase==null)
            dataBase = new MyDatabase();
        return dataBase;
    }

    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        try {
            if (!dropIfExists)
                executeStructureQuery("CREATE DATABASE "+databaseName.trim()+" ;");
            else {
                executeStructureQuery("drop DATABASE "+databaseName.trim()+ " ;");
                executeStructureQuery("create DATABASE "+databaseName.trim()+ " ;");
            }
//            cPath.setPath(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path ;
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        query = query.toLowerCase();
        queryParsedData parsedData;
        try {
            Interpreter interpreter = factory.getInterpreter(query);
            parsedData = interpreter.interpret(query);
            if (parsedData.getOperationName().equals("CREATEDATABASE"))
                dbName = parsedData.getDataBaseName();
        } catch (ClassNotFoundException e) {
           SQLException E = new SQLException();
           throw E;
        } catch (NoSuchMethodException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (IllegalAccessException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (InvocationTargetException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (InstantiationException e) {
            SQLException E = new SQLException();
            throw E;
        }
        if (parsedData.getOperationName().equals("CREATETABLE")||parsedData.getOperationName().equals("DROPTABLE"))
            parsedData.setDataBaseName(dbName);
        ChooseCommand chooseCommand = new ChooseCommand(parsedData);
        commandState cS = chooseCommand.returnCommand();
        System.out.println("---------------------------------");
        System.out.println("Execute Structure Query : " + cS.isExecuted());
        System.out.println("Execute Structure Query : " + cS.getPath());
        System.out.println("---------------------------------");
        path = cS.getPath();
        return cS.isExecuted();
    }

    @Override
    public Object[][] executeQuery(String query) throws SQLException {
        query = query.toLowerCase();
        queryParsedData parsedData;
        try {
            Interpreter interpreter = factory.getInterpreter(query);
            parsedData = interpreter.interpret(query);
            parsedData.setDataBaseName(dbName);
        } catch (ClassNotFoundException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (NoSuchMethodException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (IllegalAccessException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (InvocationTargetException e) {
            SQLException E = new SQLException();
            throw E;
        } catch (InstantiationException e) {
            SQLException E = new SQLException();
            throw E;
        }
        ChooseCommand chooseCommand = new ChooseCommand(parsedData);
        commandState cS = chooseCommand.returnCommand();
        System.out.println("---------------------------------");
        System.out.println("Execute Query : ");
        Object[][] elements = cS.getElements();
        for (int i =0;i<elements.length;i++){
            for (int j =0 ;j<elements[i].length;j++)
                System.out.print(elements[i][j] + "   ");
            System.out.println();
        }

        try {
            SchemaReader reader = new SchemaReader();
            String schemapath = dbName+System.getProperty("file.separator")+parsedData.getTableName()+".xsd";
            Map<String,String> schemaMap =reader.Read(schemapath);
            tableHeadline h = tableHeadline.getInstance();
//            h.setSchemaMap(schemaMap);
            System.out.println("MAP "+schemaMap);
//            System.out.println();
            int n = -1;
            for(String s : schemaMap.keySet()) {
                n++;
                if (elements.length!=0)
                    if (elements[0].length <= n)
                        break;
                h.put(s,n);
                h.putInTypes(n,schemaMap.get(s));
            }
            h.setTableName(parsedData.getTableName());
            for (int i=0;i<elements.length;i++){
                int j =-1;
                for(String s : schemaMap.keySet()){
                    j++;
                    if (elements[i].length <= j)
                        break;
                    if (schemaMap.get(s).equalsIgnoreCase("integer")  ) {
                        System.out.println("SCHEMA "+schemaMap.get(s)+" ELEMENT " + s + " ELEMENT "+ elements[i][j]);
                        elements[i][j] = Integer.parseInt((String) elements[i][j]);
                    }
                }
            }
        } catch (IOException e) {
        } catch (SAXException e) {
        } catch (ParserConfigurationException e) {
        }
        System.out.println("---------------------------------");
        return elements;
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
        query = query.toLowerCase();
        queryParsedData parsedData;
        System.out.println(query);
        try {
            Interpreter interpreter = factory.getInterpreter(query);
            parsedData = interpreter.interpret(query);
            parsedData.setDataBaseName(dbName);
        } catch (ClassNotFoundException e) {
            SQLException E = new SQLException();
            return 0;
//            throw E;
        } catch (NoSuchMethodException e) {
            SQLException E = new SQLException();
            return 0;
//            throw E;
        } catch (IllegalAccessException e) {
            SQLException E = new SQLException();
            return 0;
//            throw E;
        } catch (InvocationTargetException e) {
            SQLException E = new SQLException();
            return 0;
//            throw E;
        } catch (InstantiationException e) {
            SQLException E = new SQLException();
            return 0;
//            throw E;
        }
        ChooseCommand chooseCommand = new ChooseCommand(parsedData);
        commandState cS = chooseCommand.returnCommand();
        cS.setPath(path);
        path = cS.getPath();
        System.out.println("---------------------------------");
        System.out.println("Execute Update Query : " + cS.getUpdateCount());
        System.out.println("---------------------------------");
        return cS.getUpdateCount();
    }
}
