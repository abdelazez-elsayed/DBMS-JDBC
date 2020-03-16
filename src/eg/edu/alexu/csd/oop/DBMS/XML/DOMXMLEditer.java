package eg.edu.alexu.csd.oop.DBMS.XML;

import eg.edu.alexu.csd.oop.DBMS.Objects.commandState;
import eg.edu.alexu.csd.oop.DBMS.conditionFilter.Condition;
import eg.edu.alexu.csd.oop.DBMS.conditionFilter.chooseCriteria;
import eg.edu.alexu.csd.oop.DBMS.conditionFilter.conditionParser;
import eg.edu.alexu.csd.oop.DBMS.queryParsedData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class DOMXMLEditer {

    File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    queryParsedData data ;
    Map<String, String> elementsMap;
    Document doc;
    commandState cSU = new commandState();
    SchemaReader reader = new SchemaReader();

    public DOMXMLEditer(String filename,queryParsedData data){
        xmlFile = new File(filename);
        this.elementsMap=data.getData();
        this.data = data;
        dbFactory =  DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
        }catch (SAXException | ParserConfigurationException | IOException  e1) {
            e1.printStackTrace();
        }
    }

    private void setupDoc(){
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void addElement() {
        NodeList table = doc.getElementsByTagName("Table");
        Node Table = table.item(0);
        Element row = doc.createElement("Row");
        for(String s : elementsMap.keySet()){
            Element col = null;
            col = doc.createElement(s);
            String value = elementsMap.get(s);
            if(isQuoted(value)){
                value = elementsMap.get(s).substring(1,elementsMap.get(s).length()-1);
            }
            col.setTextContent(value);
            row.appendChild(col);
        }
        Table.appendChild(row);
        setupDoc();
    }

    public commandState selectElement() throws SQLException, ParserConfigurationException, SAXException, IOException {
        NodeList RowList = doc.getElementsByTagName("Row");
        ArrayList<ArrayList<Object>> selected = new ArrayList<>();
        String conditionString = data.getCondition();
        String[] condition = conditionString.split(" *= *| *< *| *> *");
        if (data.getCondition().contains("=")|data.getCondition().contains(">")|data.getCondition().contains("<")) {
            NodeList conditionsList = doc.getElementsByTagName(condition[0]);
            for (int j = 0; j < conditionsList.getLength(); j++) {
                ArrayList<Object> row = new ArrayList<>();
                if (conditionValidator(conditionsList.item(j).getNodeName(),conditionsList.item(j).getTextContent())) {
                    for (int i = 0; i < conditionsList.item(j).getParentNode().getChildNodes().getLength(); i++) {
//                        if (RowList.item(j).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                        if (data.isSelectAll()){
                            row.add(conditionsList.item(j).getParentNode().getTextContent());
                        }
                        else
                            for (String s : elementsMap.keySet()) {
                                System.out.println("KEY Maps "+elementsMap.get(s));
                                System.out.println(elementsMap.get(s)+ " =? "+ conditionsList.item(j).getNodeName());
                                if (elementsMap.get(s).trim().equals(conditionsList.item(j).getParentNode().getChildNodes().item(i).getNodeName())) {
                                    row.add(conditionsList.item(j).getParentNode().getChildNodes().item(i).getTextContent());
                                    System.out.println("LROW" + row);
                                }
                            }
//                        }
                    }
                }
                if (!row.isEmpty()) {
                    selected.add(row);
                }
            }
        }
        else{
            for(int j =0;j<RowList.getLength();j++) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 0; i < RowList.item(j).getChildNodes().getLength(); i++) {
                    if (RowList.item(j).getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                        if (data.isSelectAll()){
                            row.add(RowList.item(j).getChildNodes().item(i).getParentNode().getTextContent());
                        }
                        else
                            for (String s : elementsMap.keySet()) {
                                System.out.println("KEY Maps "+elementsMap.get(s));
                                System.out.println(elementsMap.get(s)+ " =? "+ RowList.item(j).getChildNodes().item(j).getNodeName());
                                if (elementsMap.get(s).trim().equals(RowList.item(j).getChildNodes().item(j).getParentNode().getChildNodes().item(i).getNodeName())) {
                                    row.add(RowList.item(j).getChildNodes().item(j).getParentNode().getChildNodes().item(i).getTextContent());
                                    System.out.println("LROW" + row);
                                }
                            }
                    }
                }
                if (!row.isEmpty()) {
                    selected.add(row);
                }
            }
        }
        commandState cS = new commandState();
        cS.setExecuted(true);
        cS.setArray(selected);
        return cS;
    }

    public commandState deleteElement() throws SQLException, ParserConfigurationException, SAXException, IOException {
        cSU = new commandState();
        cSU.setUpdateCount(0);
        USD('D');
        setupDoc();
        return cSU;
    }

    public commandState updateElement() throws SQLException, ParserConfigurationException, SAXException, IOException {
        USD('U');
        setupDoc();
        return cSU;
    }

    private void USD(char Operation) throws SQLException, IOException, SAXException, ParserConfigurationException {//update delete
        boolean flag = false;
        NodeList RowList = doc.getElementsByTagName("Row");
        for(int i=0; i<RowList.getLength();i++){
            Node R = RowList.item(i);
            if (R.getNodeType() == Node.ELEMENT_NODE){
                Element Row = (Element) R ;
                NodeList ColumnList = Row.getChildNodes();
                for (int j = 0 ; j<ColumnList.getLength() ; j++){
                    Node C = ColumnList.item(j);
                    if (C.getNodeType() == Node.ELEMENT_NODE){
                        Element column = (Element) C ;
                        if (data.getCondition().contains("=")|data.getCondition().contains(">")|data.getCondition().contains("<")) {
                            if (conditionValidator(column.getTagName().trim(), column.getTextContent().trim()))
                                if (Operation == 'U')
                                    update(ColumnList);
                                else if (Operation == 'D') {
                                    delete(ColumnList);
                                }
                        }
                        else {
                            if (Operation == 'U') {
                                update(ColumnList);
                                flag = true;
                            }
                            else if (Operation == 'D')
                                delete(ColumnList);
                        }
                    }
                }
            }
        }
        if (flag) {
            String XMLpath = data.getDataBaseName() + System.getProperty("file.separator") + data.getTableName() + ".xml";
            DomXMLReader reader = new DomXMLReader();
            reader.readXML(XMLpath);
            System.out.println("UPDATE COUNT " + cSU.getUpdateCount());
            cSU.setUpdateCount(cSU.getUpdateCount() / reader.getRowCount());
        }
    }

    private void update(NodeList columns){
        for(int j =0 ; j<columns.getLength();j++){
            Node C = columns.item(j);
            if (C.getNodeType() == Node.ELEMENT_NODE)
                C.setTextContent(elementsMap.get(C.getNodeName().trim()).toUpperCase());
        }
        cSU.setUpdateCount(cSU.getUpdateCount()+1);
    }

    private void delete(NodeList columns){
        for(int j =0 ; j<columns.getLength();j++){
            Node C = columns.item(j);
            if (C.getNodeType() == Node.ELEMENT_NODE)
                C.getParentNode().removeChild(C);
        }
        cSU.setUpdateCount(cSU.getUpdateCount()+1);
    }

    private boolean conditionValidator(String columnActualName,String columnActualValue) throws SQLException, ParserConfigurationException, SAXException, IOException {
        SchemaReader reader = new SchemaReader();
        String schemapath = data.getDataBaseName()+System.getProperty("file.separator")+data.getTableName()+".xsd";
        Map<String,String> schemaMap =reader.Read(schemapath);
        if (schemaMap.get(columnActualName).equalsIgnoreCase("varchar")&&(data.getCondition().contains(">")||data.getCondition().contains("<")))
            return false;
        conditionParser cP = new conditionParser();
        Condition c ;
        c = cP.parseCondition(data.getCondition());
        Condition con1 = new Condition(removeQuote(columnActualValue).trim(),c.getOperation(),removeQuote(c.getSecondOperand()).trim());
        Condition con2 = new Condition(removeQuote(columnActualName).trim(), "=",removeQuote(c.getFirstOperand()).trim());
        chooseCriteria cr1 = new chooseCriteria(con1);
        chooseCriteria cr2 = new chooseCriteria(con2);
        System.out.println("=========================");
        System.out.println("Condition "+data.getCondition());
        System.out.println("Criteria1 "+removeQuote(columnActualValue) + c.getOperation() + removeQuote(c.getSecondOperand()));
        System.out.println("Criteria2 "+ removeQuote(columnActualName) + "=" + removeQuote(c.getFirstOperand()));
        System.out.println("Criteria1 "+ cr1.returnCriteria());
        System.out.println("Criteria2 "+ cr2.returnCriteria());
        System.out.println("=========================");
        System.out.println("CBBBBBBBOOOLEAAAAAN : " + (cr1.returnCriteria()&&cr2.returnCriteria()) );
        return cr1.returnCriteria()&&cr2.returnCriteria();
    }

    private   boolean isQuoted(String str) {
        return Pattern.matches("['].+[']",str);
    }

    private String removeQuote(String Quoted){
        if (isQuoted(Quoted.trim())) {
            if (data.getCondition().contains("<") || data.getCondition().contains(">"))
                return "";
            return Quoted.trim().substring(1, Quoted.trim().length() - 1);
        }
        else return Quoted;
    }
}