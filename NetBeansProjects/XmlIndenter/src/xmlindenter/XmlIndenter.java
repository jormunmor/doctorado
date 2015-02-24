/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlindenter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author jorge
 */
public class XmlIndenter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String xmlFileString = "/home/jorge/Escritorio/piramide.xml";
            if(args.length > 0){
                xmlFileString = args[0];
                
            }
            
            String outputXmlFileString = xmlFileString.substring(0, xmlFileString.lastIndexOf(".")) + "-indented.xml";
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            File xmlFile = new File(xmlFileString);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(xmlFile);
            xmlDocument.getDocumentElement().normalize();
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new File(outputXmlFileString));
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlIndenter.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (SAXException ex) {
            Logger.getLogger(XmlIndenter.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(XmlIndenter.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XmlIndenter.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (TransformerException ex) {
            Logger.getLogger(XmlIndenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
