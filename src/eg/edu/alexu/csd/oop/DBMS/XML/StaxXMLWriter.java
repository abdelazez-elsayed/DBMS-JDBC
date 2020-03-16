package eg.edu.alexu.csd.oop.DBMS.XML;

import java.io.*;
import java.util.*;
import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxXMLWriter {

    public void writeXML(String fileName, Map<String, String> elementsMap){
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        try {

            FileOutputStream fout = new FileOutputStream(fileName);
            File file = new File(fileName);

            XMLEventWriter xmlEventWriter = xmlOutputFactory.createXMLEventWriter(fout, "UTF-8");
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            xmlEventWriter.add(end);
            StartElement configStartElement = eventFactory.createStartElement("", "", "Table");
            xmlEventWriter.add(configStartElement);
            xmlEventWriter.add(end);

            // Add Record
            if(elementsMap.size()!=0)
            newRecord(xmlEventWriter,elementsMap);

            xmlEventWriter.add(eventFactory.createEndElement("", "", "Table"));
            xmlEventWriter.add(end);
            xmlEventWriter.add(eventFactory.createEndDocument());
            xmlEventWriter.close();
            fout.close();
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void newRecord(XMLEventWriter eventWriter, Map<String, String> elementsMap) throws XMLStreamException {
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");
        //Create Start node
        StartElement sElement = xmlEventFactory.createStartElement("", "", "Row");
        eventWriter.add(tab);
        eventWriter.add(sElement);
        eventWriter.add(end);
        //Create Content
        Set<String> elementsNodes = elementsMap.keySet();
        for(String key : elementsNodes)
            createNode(eventWriter, key, elementsMap.get(key));
        // Create End node
        EndElement eElement = xmlEventFactory.createEndElement("", "", "Row");
        eventWriter.add(tab);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
    private static void createNode(XMLEventWriter eventWriter, String element, String value) throws XMLStreamException {
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent doubleTab = xmlEventFactory.createDTD("\t\t");
        //Create Start node
        StartElement sElement = xmlEventFactory.createStartElement("", "", element);
        eventWriter.add(doubleTab);
        eventWriter.add(sElement);
        //Create Content
        Characters characters = xmlEventFactory.createCharacters(value);
        eventWriter.add(characters);
        // Create End node
        EndElement eElement = xmlEventFactory.createEndElement("", "", element);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }
}