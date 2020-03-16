package eg.edu.alexu.csd.oop.DBMS.executeCommand;

import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.XML.DOMXMLEditer;
import eg.edu.alexu.csd.oop.DBMS.XML.DomXMLReader;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Utilities {
    private queryParsedData data = new queryParsedData();
    private commandState cS = new commandState();
    private eg.edu.alexu.csd.oop.DBMS.Objects.Utilities util = new eg.edu.alexu.csd.oop.DBMS.Objects.Utilities();
    public Utilities (){

    }

    public commandState setupDoc() throws SQLException {
        java.sql.SQLException ex = new java.sql.SQLException();
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        String XMLpath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xml";

        File schemaFile = new File(schemapath);
        if(!schemaFile.exists()){
            cS.setExecuted(false);
            System.out.println(schemapath + " Schema Not found");
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
