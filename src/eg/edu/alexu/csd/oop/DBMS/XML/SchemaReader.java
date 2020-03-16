package eg.edu.alexu.csd.oop.DBMS.XML;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchemaReader {

    public Map<String,String> Read(String schemaPath) throws IOException, SAXException, ParserConfigurationException {
        Map<String,String> prob = new HashMap<>();

        DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance() ;
        File file = new File(schemaPath) ;
        DocumentBuilder builder = Factory.newDocumentBuilder() ;
        Document doc = builder.parse(file);
        NodeList row = doc.getElementsByTagName("xs:all");
        NodeList elements = row.item(0).getChildNodes();
        for(int i=1; i<elements.getLength();i+=2){
        Node r = elements.item(i);
        Element element = (Element)r;
        String col = element.getAttribute("name");
        String typp = element.getAttribute("type");
        typp = typp.substring(3);
        if(typp.equalsIgnoreCase("string"))
            typp="varchar";
        prob.put(col,typp);

        }
        return prob;
    }
}
