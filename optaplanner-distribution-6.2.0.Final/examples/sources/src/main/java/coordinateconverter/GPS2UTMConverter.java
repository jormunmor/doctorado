/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coordinateconverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jorge
 */
public class GPS2UTMConverter {
    public static void generateUTMPolygon(int location, String configPath, String polygonsPath, Document root) {
        try {
        // we must load the config file and retrieve the xml node for the location
        // Parse the config file
        /*
        File systemConfig = new File(configPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        Document configDoc;
        configDoc = dBuilder.parse(systemConfig);
        configDoc.getDocumentElement().normalize();
        */
        
        NodeList taskList = root.getElementsByTagName("Task");
        Node task = null;
        int loc;
        boolean found = false;
        for(int i=0; i<taskList.getLength() && !found; i++){
            task = taskList.item(i);
            loc = Integer.parseInt(getValue("Location", (Element) task));
            if(loc == location){
                found = true;
                
            }
            
        }
        
        if(!found){
            System.out.println("Unable to find xml node for location" + location + ", do nothing.");
            return;
            
        }
        
        Node taskDataNode = ((Element) task).getElementsByTagName("TaskData").item(0);
        NodeList polygonList = ((Element) task).getElementsByTagName("Polygon");
        if(polygonList.getLength() != 1) {
            System.out.println("Required only one polygon per task. Polygon text file not generated for location" + location + ".");
            return;
            
        }
        Node polygonNode = polygonList.item(0);
        NodeList coordinatesList = ((Element) polygonNode).getElementsByTagName("Point");
        File polygonFile = new File(polygonsPath + "polygon" + location + ".txt");
        PrintWriter printWriter = new PrintWriter(polygonFile);
        for(int i=0; i<coordinatesList.getLength() ;i++){
            Element point = (Element) coordinatesList.item(i);
            double latitude = Double.parseDouble(getValue("latitude", point));
            double longitude = Double.parseDouble(getValue("longitude", point));
            LL2UTM converter = LL2UTM.convert(latitude, longitude);
            printWriter.println(converter.getEasting());
            printWriter.println(converter.getNorthing());
        
        }
        printWriter.println(-1);
        printWriter.println(-1);
        printWriter.close();        
        
        } catch (IOException ex) {
            Logger.getLogger(GPS2UTMConverter.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    
        
    }
    
    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        
        return node.getNodeValue();
        
    }
    
}
