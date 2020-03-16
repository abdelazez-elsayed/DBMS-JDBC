package eg.edu.alexu.csd.oop.DBMS.Objects;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import eg.edu.alexu.csd.oop.DBMS.XML.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class MyTable {

    Utilities util = new Utilities();
    commandState cS = new commandState();

    public commandState create(queryParsedData data) throws SQLException {
        java.sql.SQLException ex = new java.sql.SQLException();
        String databasePath = data.getDataBaseName();
        String schemaPath= databasePath+System.getProperty("file.separator")+data.getTableName();
        File xsd = new File(schemaPath+".xsd");
        if(databasePath != "") {
            if (!xsd.exists()) {
                XSDSchemaWriter schemaWriter = new XSDSchemaWriter();
                StaxXMLWriter xmlWriter = new StaxXMLWriter();
                String[][] atr;
                atr = new String[data.getData().size()][2];
                Map<String, String> prob = data.getData();
                int i = 0;
                for (String s : prob.keySet()) {
                    atr[i][0] = s;
                    atr[i][1] = prob.get(s);
                    i++;
                }
                try {
                    schemaWriter.schemaWriter(schemaPath, atr);
                    prob.clear();
                    xmlWriter.writeXML(schemaPath + ".xml", prob);
                    cS.setExecuted(true);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                return cS;
            } else {
                cS.setExecuted(false);
                return cS;
                    }
        }
             else{
                    throw ex;
                }
        }

    public commandState drop(queryParsedData data){
        String TableName = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName();
        File xml = new File(TableName+".xml");
        File xsd = new File(TableName+".xsd");
        if(xsd.exists()) {
           // System.out.println("Table xsd Found");
            xsd.delete();
            cS.setExecuted(true);
        }else {
           // System.out.println("Table xsd Not Found");

            cS.setExecuted(false);
        }if(xml.exists()) {
            //System.out.println("Table xml Found");
            if(xml.delete()){
              //  System.out.println("Xml deleted");
            }else {
            //    System.out.println("Not deleted");
            }
            cS.setExecuted(true);
        }else {
           // System.out.println("Table xml Not Found");
            cS.setExecuted(false);
        }
        return cS;
    }

    public commandState insert(queryParsedData data) throws SQLException {
        java.sql.SQLException ex = new java.sql.SQLException();
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        String XMLpath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xml";

        File schemaFile = new File(schemapath);
        if(!schemaFile.exists()){
            cS.setExecuted(false);
            System.out.println(schemapath + " : Schema Not found");
            throw ex;
        }else {
            try {
                if(!util.validation(schemapath,data)) {
                    cS.setExecuted(false);
                    System.out.println("Schema Not valid");
                    throw ex;
                }
                else{
                    DOMXMLEditer editer = new  DOMXMLEditer(XMLpath,data);
                    editer.addElement();
                    DomXMLReader reader= new DomXMLReader();
                    reader.readXML(XMLpath);
                    cS.setRowsCount(reader.getRowCount());
                    cS.setUpdateCount(1);
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cS;
    }

    public commandState delete(queryParsedData data) throws SQLException {
        java.sql.SQLException ex = new java.sql.SQLException();
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        String XMLpath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xml";
        System.out.println(data.getData());

        File schemaFile = new File(schemapath);
        if(!schemaFile.exists()){
            cS.setExecuted(false);
            throw ex;
        }else {
            try {
                if(!util.validation(schemapath,data)) {
                    cS.setExecuted(false);
                    throw ex;
                }
                else{
                    DOMXMLEditer editer = new  DOMXMLEditer(XMLpath,data);
                    cS = editer.deleteElement();
                    DomXMLReader reader = new DomXMLReader();
                    reader.readXML(XMLpath);
                    cS.setRowsCount(reader.getRowCount());
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cS;
    }

    public commandState update(queryParsedData data) throws SQLException {
        java.sql.SQLException ex = new java.sql.SQLException();
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        String XMLpath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xml";
        System.out.println("Update ");

        File schemaFile = new File(schemapath);
        if(!schemaFile.exists()){
            cS.setExecuted(false);
            throw ex;
        }else {
            try {
                if(!util.validation(schemapath,data)) {
                    cS.setExecuted(false);
                    throw ex;
                }
                else{
                    DOMXMLEditer editer = new  DOMXMLEditer(XMLpath,data);
                    cS = editer.updateElement();
                    DomXMLReader reader = new DomXMLReader();
                    reader.readXML(XMLpath);
                    cS.setRowsCount(reader.getRowCount());
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cS;
    }

    public commandState select(queryParsedData data) throws SQLException {
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        String XMLpath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xml";

        File schemaFile = new File(schemapath);
        if(!schemaFile.exists()){
            System.out.println(schemapath + " Schema Not Found");
            java.sql.SQLException ex = new java.sql.SQLException();
            cS.setExecuted(false);
            throw ex;
        }else {
            try {
                if(!util.validation(schemapath,data)) {
                    cS.setExecuted(false);
                    java.sql.SQLException ex = new java.sql.SQLException();
                    throw ex;
                }
                else{
                    DOMXMLEditer editer = new  DOMXMLEditer(XMLpath,data);
                    System.out.println("Select query output ");
                    cS = editer.selectElement();
                    DomXMLReader reader = new DomXMLReader();
                    reader.readXML(XMLpath);
                    cS.setRowsCount(reader.getRowCount());
                }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cS;
    }

}
