package com.qa.utils;

import com.qa.BaseTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static java.io.PrintWriter.*;

public class TestUtils {
    public static final int WAIT = 10;

    public HashMap<String, String> parseStringXml(InputStream file) throws IOException, ParserConfigurationException, SAXException {
         HashMap<String, String> stringMap = new HashMap<String, String>();

        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        //Here comes the root node
        Element root = document.getDocumentElement();

        //Get all elements
        NodeList nList = document.getElementsByTagName("string");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                // Store each element key value in map
                stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
            }
        }
        return stringMap;
    }

    public String dateAndTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void log (String txt){
        BaseTest baseTest = new BaseTest();
        String msg = Thread.currentThread().getId() + ":" + baseTest.getPlaformName() + ":" + baseTest.getDeviceName() + ":"
                + Thread.currentThread().getStackTrace()[2].getClassName() + ":" + txt;

        System.out.println(msg);

        String strFile = "logs" + File.separator + baseTest.getPlaformName() + "_" + baseTest.getDeviceName() + File.separator + baseTest.getDateAndTime();

        File logFile = new File(strFile);

        if(!logFile.exists())
        {
            logFile.mkdirs();
        }
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(logFile + File.separator + "log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(msg);
        printWriter.close();

    }

}