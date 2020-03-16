package eg.edu.alexu.csd.oop.DBMS.XML;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XSDSchemaWriter {

    public static void schemaWriter(String fileName, String[][] attributes) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // root element
        Element rootName = doc.createElement("xs:schema");
        rootName.setAttribute("xmlns:xs","http://www.w3.org/2001/XMLSchema");
        doc.appendChild(rootName);

        // root element
        Element subElement = doc.createElement("xs:element");
        subElement.setAttribute("name","Table");
        Element complexElement = doc.createElement("xs:complexType");
        Element sequence = doc.createElement("xs:sequence");
        Element rowComplexElement = doc.createElement("xs:complexType");
        Element rowSequence = doc.createElement("xs:all");
        Element row = doc.createElement("xs:element");
        row.setAttribute("name","Row");
        row.setAttribute("maxOccurs","unbounded");
        row.appendChild(rowComplexElement);
        rowComplexElement.appendChild(rowSequence);
       complexElement.appendChild(sequence);
       sequence.appendChild(row);
        subElement.appendChild(complexElement);
        rootName.appendChild(subElement);

        // setting attribute to element
       /* Attr attr = doc.createAttribute("name");
        attr.setValue(attributes[0]);
        subElement.setAttributeNode(attr);
*/
        // table columns
        int i =0;
        for(i=0;i<attributes.length;i++){
            Element column = doc.createElement("xs:element");
            column.setAttribute("minOccurs","0");
            Attr Type = doc.createAttribute("type");
            Attr Name = doc.createAttribute("name");
            Name.setValue(attributes[i][0]);
            column.setAttributeNode(Name);
            if(attributes[i][1].equalsIgnoreCase("varchar"))
            Type.setValue("xs:string");
            else
                Type.setValue("xs:integer");
            column.setAttributeNode(Type);
            rowSequence.appendChild(column);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName+".xsd"));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}