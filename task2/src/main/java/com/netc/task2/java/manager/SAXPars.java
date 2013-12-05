package com.netc.task2.java.manager;

import com.netc.task2.java.entity.Task;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SAXPars extends DefaultHandler{
public static ArrayList<Task> list;
private static final Logger LOGGER = LoggerFactory.getLogger(SAXPars.class);
private String thisElement = "";

public SAXPars() {
     list = new ArrayList<Task>();
  }


@Override
public void startDocument() throws SAXException {
}

@Override
public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
  thisElement = qName;
  String name= new String();
  int delay=0,workTime=0,runCount=0;
  if (atts != null) 
  {
        int len = atts.getLength();
        for (int i = 0; i < len; i++) 
        {
            switch (atts.getLocalName(i))
            {
                case "name": 
                    name=atts.getValue(i);
                    break;
                case "delayFirstStart": 
                    delay=Integer.parseInt(atts.getValue(i));
                    break;
                case "operationTime": 
                    workTime=Integer.parseInt(atts.getValue(i));
                    break;
                case "runCount": 
                    runCount=Integer.parseInt(atts.getValue(i));
                    break;
            }
        }
        if (workTime!=0 && runCount!=0) 
        {
                list.add(new Task(name, delay, workTime, runCount));
        }
  }
}

@Override
public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
  thisElement = "";
}

@Override
public void characters(char[] ch, int start, int length) throws SAXException {
    String value = new String(ch,start,length); 
}

@Override
public void endDocument() {
    
}

public static void begin (String fileName) {

SAXParserFactory factory = SAXParserFactory.newInstance();
SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException ex) {
            LOGGER.info("SAXParser: " +ex.getMessage());
        } catch (SAXException ex) {
            LOGGER.info("SAXParser: " +ex.getMessage());
        }
SAXPars saxp = new SAXPars();
        try {
            parser.parse(new File(fileName), saxp);
            LOGGER.info("Parsing was complete.");
        } catch (SAXException ex) {
            LOGGER.info("SAXPars: " +ex.getMessage());
        } catch (IOException ex) {
            LOGGER.info("SAXPars: " +ex.getMessage());
        }
         
}
}