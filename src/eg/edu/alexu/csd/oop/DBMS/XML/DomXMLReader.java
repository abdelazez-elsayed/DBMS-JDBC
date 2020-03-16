package eg.edu.alexu.csd.oop.DBMS.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomXMLReader {
    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int rowCount) {
        RowCount = rowCount;
    }

    private static int RowCount;

    public static void readXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
        int flag = 0 ;
        DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance() ;
        File file = new File(fileName) ;
        DocumentBuilder builder = Factory.newDocumentBuilder() ;
        Document doc = builder.parse(file);
        NodeList RowList = doc.getElementsByTagName("Row");
        RowCount = RowList.getLength();
        for (int i = 0 ; i < RowList.getLength() ; i++){
            Node R = RowList.item(i);
            if (R.getNodeType() == Node.ELEMENT_NODE){
                Element Row = (Element) R ;
                NodeList ColumnList = Row.getChildNodes();
                for (int j = 0 ; j<ColumnList.getLength() ; j++){
                    Node C = ColumnList.item(j);
                    if (C.getNodeType() == Node.ELEMENT_NODE){
                        Element column = (Element) C ;
                        if(i==0 && flag == 0) {
                            System.out.print(column.getTagName() + "  ");
                        }
                        else if (flag == 1 )
                            System.out.print(column.getTextContent()+"   ");
                    }
                }
                System.out.println();
                if(i==0 && flag == 0){
                    i-- ;
                    flag =1 ;
                }
            }
        }
    }
}