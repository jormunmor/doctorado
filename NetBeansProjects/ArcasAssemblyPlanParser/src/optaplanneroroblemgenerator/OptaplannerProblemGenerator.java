/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optaplanneroroblemgenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * .
 * This class will parse a SystemConfig.xml file to generate
 * a optaplanner problem file in a generic form. The class expects
 * that the config file has a location list, takeOffPlace list, uav list
 * and task list, which are the main elements of all problems. In addition 
 * to the config file, the class may receive another file(s) to complete
 * the SystemConfig information.
 * .
 * @author jorge
 */
public class OptaplannerProblemGenerator {

    private static Document optaplannerDocument;
    
    private static Document systemConfigDocument;
    
    private static int idCounter = 1;
    
    private static int discreteValue = 0;
    
    private static Map <String, String> locationReferenceMap = new HashMap<String, String>();
    // Key: xml id
    // Value: id counter casted to a string (the xml id attribute in the output file)
    
    private static Map <String, String> initialLocationReferenceMap = new HashMap();
    
    private static Map <String, String> autonomousVehicleReferenceMap = new HashMap();
    
    private static Map <String, String> taskReferenceMap = new HashMap();
    
    private static Map <String, Element> taskElementMap = new HashMap();
    // Key: xml id attribute
    // Value: Task xml element
    
    private static Map <String, List> systemConfigUpdateTaskMap = new HashMap();
    // Key: SystemConfig Task id
    // Value: List containing new xml elements to add to the tasks of the SystemConfig
    
    private static Map <String, Map<String, String>> mapsMap = new HashMap(); 
    // Key: object type (i.e. "Location", "InitialLocation", "AutonomousVehicle", "Task")
    // Value: corresponding reference map for that type of object
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Files for the Arcas problem
        String systemConfigFileString = "../../SystemPlanner/SystemConfig.xml";
        String additionalInfoFileString = "../../SystemPlanner/AssemblyPlan.xml";
        
        // Files for the multiUAV problem
        //String systemConfigFileString = "/home/jorge/Escritorio/SystemPlanner/SysCon.xml";
        //String additionalInfoFileString = "";
        
        /*
        if(args.length > 1){
            systemConfigFileString = args[0];
            additionalInfoFileString = args[1];
                        
        } else if(args.length == 1){
            systemConfigFileString = args[0];
            
        } else {
            System.out.println("Incorrect arguments. Aborting...");
            return;
            
        }
        */
        
        //Map <String, Integer> referenceMap = new HashMap<String, Integer>();
        //Map <String, NodeList> preconditionsMap = new HashMap<String, NodeList>();
        
        // Open the assembly plan file and the system config to parse it
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            
            //File assemblyPlanFile = new File(assemblyPlanFileString);
            File systemConfigFile = new File(systemConfigFileString);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Document assemblyPlanDocument = builder.parse(assemblyPlanFile);
            systemConfigDocument = builder.parse(systemConfigFile);
            
            //assemblyPlanDocument.getDocumentElement().normalize();
            systemConfigDocument.getDocumentElement().normalize();
            
            // Create the optaplanner xml root element, as well as the
            // location list, depot list and vehicle list. These elements
            // are usually present in all our problems.
            
            // Create the optaplanner output xml file
            optaplannerDocument = builder.newDocument();
            
            // Create the root element
            Element rootElement = optaplannerDocument.createElement("OptaplannerProblem");
            
            // Add the id attribute
            rootElement.setAttribute("id", "" + idCounter);
            idCounter++;            
            
            // Add the id child node
            Element id = optaplannerDocument.createElement("id");
            id.appendChild(optaplannerDocument.createTextNode("0"));
            rootElement.appendChild(id);
            
            // Add the name child node from the system config. We supose
            // that there is only a name node wich is child of the root element
            // in the system config file.
            Element systemConfigName = (Element) systemConfigDocument.getElementsByTagName("name").item(0);
            Element optaplannerRootName = optaplannerDocument.createElement("name");
            optaplannerRootName.appendChild(optaplannerDocument.createTextNode(systemConfigName.getTextContent()));
            rootElement.appendChild(optaplannerRootName);
            
            // Recover the discrete value, if it exists. Those tasks wich have a demand value greater than the
            // discrete value will be discretized in subtasks wich a demand value equal or less than the 
            // discrete value.
            NodeList discreteValueList = systemConfigDocument.getElementsByTagName("discreteValue");
            if(discreteValueList.getLength() > 0){ // The systemConfig has a discreteValue, so we have to divide nodes into subnodes
                Element systemConfigDiscreteValue = (Element) discreteValueList.item(0);
                discreteValue = Integer.parseInt(systemConfigDiscreteValue.getTextContent());
            
            }
            
            // Add the locationList
            addObjectList(rootElement, "locationList", "Location", locationReferenceMap);
            
            // Add the takeOffPlaceList
            addObjectList(rootElement, "initialLocationList", "InitialLocation", initialLocationReferenceMap);
            
            // Add the uavList
            addObjectList(rootElement, "autonomousVehicleList", "AutonomousVehicle", autonomousVehicleReferenceMap);
            
            // Add the taskList
            if(discreteValue > 0){ // case where the tasks have to be discretized
                addDemandObjectList(rootElement, "taskList", "Task", taskReferenceMap);
                
            } else{ // case where the tasks don't have to be discretized
                addObjectList(rootElement, "taskList", "Task", taskReferenceMap);
                
            }
            
            
            // create helper map taskElementMap
            createTaskElementMap(rootElement.getElementsByTagName("Task"), taskElementMap);
            
            // Fills the mapsMap
            mapsMap.put("Location", locationReferenceMap);
            mapsMap.put("InitialLocation", initialLocationReferenceMap);
            mapsMap.put("AutonomousVehicle", autonomousVehicleReferenceMap);
            mapsMap.put("Task", taskReferenceMap);
            
            // the following is specific code dependant of the type of problem
            switch(systemConfigName.getTextContent()){
                case "ArcasAssemblyProblem": 
                    {
                        Map<String, String> partNameTaskIdMap = getIDMap(rootElement, "objectName");
                        complementArcasSystemConfig(rootElement, additionalInfoFileString, partNameTaskIdMap);
                    
                    }
                    break;
                    
                case "MultiUAVProblem": 
                {
                    // Nothing to do, the SystemConfig contains all necessary information and at his moment no external info is required
                    // to complement it.

                }
                break;
                    
                default:  break; /// TODO add code to parse a generic complementary xml, for the moment the specific xml is valid
                
            }
                                    
            optaplannerDocument.appendChild(rootElement);
            
            
            // write to a text file
            // write the content into xml file
            String outputXmlFileString = "../../SystemPlanner/" + systemConfigName.getTextContent() + ".xml";
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(optaplannerDocument);
            StreamResult result = new StreamResult(new File(outputXmlFileString));
            transformer.transform(source, result);
            
            System.out.println("OptaPlanner problem file saved!");
            
            // now we update the SystemConfig with all possible new info obtained 
            // from the previous process. We do it at the end of the main function
            // to avoid modifying the SystemConfig while doing other actions.
            updateSystemConfig();
            outputXmlFileString = "../../SystemPlanner/" + "UpdatedSystemConfig.xml";
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            source = new DOMSource(systemConfigDocument);
            result = new StreamResult(new File(outputXmlFileString));
            transformer.transform(source, result);
            
            System.out.println("Updated SystemConfig file saved!");
            
  
        } catch (SAXException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (TransformerException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
            
        } 
        
    }
    
    public static Element addTextChildNode(Element element, String childName, String textNode){
        Element child = optaplannerDocument.createElement(childName);
        child.appendChild(optaplannerDocument.createTextNode(textNode));
        element.appendChild(child);
        
        return child;
        
    }
    
    public static Element addChildNode(Element element, String childName){
        Element child = optaplannerDocument.createElement(childName);
        element.appendChild(child);
        
        return child;
        
    }

    private static void addObjectList(Element rootElement, String listName, String objectName, Map<String, String> objectReferenceMap) {
        // retrieve the list element from the SystemConfig
        Element listElement = (Element) systemConfigDocument.getElementsByTagName(listName).item(0); // only one list with that name
                
        // create the list element for the output xml
        Element outputListElement = optaplannerDocument.createElement(listName);
        outputListElement.setAttribute("id", "" + idCounter);
        idCounter++;  
                
        // add the childs of the list element
        NodeList childs = listElement.getElementsByTagName(objectName);
        for(int i=0; i<childs.getLength(); i++){
            Element child = (Element) childs.item(i); // the node from the SystemConfig
            String childTag = child.getTagName();
            String id = "";
            String name = ""; // useful when having
            // create the child element for the output xml
            Element outputChildElement = optaplannerDocument.createElement(childTag);
            // add the childs of the child element (id, type, location, demand...)
            NodeList childNodes = child.getChildNodes();
            for(int j=0; j<childNodes.getLength(); j++){
                if(!(childNodes.item(j) instanceof Element)){
                    continue;
                
                }
                Element elem = (Element) childNodes.item(j);
                if(!elem.getAttribute("optaplannerIgnore").equals("true")){ // the element must be included in the optaplanner problem file
                    // check if it's a id element
                    String tag = elem.getTagName();
                    if(tag.equals("id")){ // there must be a id tag per child
                        id = elem.getTextContent();
                        
                    }
                    
                    // create the child child element
                    Element outputChildChildElement = optaplannerDocument.createElement(tag);
                    // insert the text content
                    outputChildChildElement.setTextContent(elem.getTextContent());
                    // check if references another object
                    String referenceId = elem.getAttribute("referenceId");
                    if(!referenceId.equals("")){
                        //System.out.println("referenceId=" + referenceId);
                        if(tag.equals("initialLocation")) {
                            outputChildChildElement.setAttribute("reference", initialLocationReferenceMap.get(referenceId));
                            
                        } else if(tag.equals("location") || tag.equals("objectLocation")){ // the attribute references a Location object
                            outputChildChildElement.setAttribute("reference", locationReferenceMap.get(referenceId));
                            
                        } 
                        
                    }
                    
                    // append the child child element
                    outputChildElement.appendChild(outputChildChildElement);
                    
                }
                
            }

            outputChildElement.setAttribute("id", "" + idCounter);
            outputListElement.appendChild(outputChildElement);
            objectReferenceMap.put(id, "" + idCounter);
            //System.out.println("Id Node: " + id + " Id attribute: " + objectReferenceMap.get(id));
            idCounter++;
            
        }
        rootElement.appendChild(outputListElement);
        
    }
    
    /*
    This function is a clone of the function before, due to have to take into account the case of an object list whose childNodes have
    a demand value that must not surpass the discrete value. So, this function will divide a node with a high demand value
    into subnodes with lower demand value. It is cearly oriented to tasks that must be discretized into subtasks.
    */
    private static void addDemandObjectList(Element rootElement, String listName, String objectName, Map<String, String> objectReferenceMap) {
        // retrieve the list element from the SystemConfig
        Element listElement = (Element) systemConfigDocument.getElementsByTagName(listName).item(0); // only one list with that name
                
        // create the list element for the output xml
        Element outputListElement = optaplannerDocument.createElement(listName);
        outputListElement.setAttribute("id", "" + idCounter);
        idCounter++;  
                
        // add the childs of the list element
        
        NodeList childs = listElement.getElementsByTagName(objectName);
        for(int i=0; i<childs.getLength(); i++){
            /*
             We have to face the case of a node with a demand value greater than the
             discrete value. If that's the case, we have to discretize.
            */
            Element child = (Element) childs.item(i); // the node from the SystemConfig
            
            int childDemand = Integer.parseInt(child.getElementsByTagName("demand").item(0).getTextContent());
            int demandPutValue = 0;
            
            String childTag = child.getTagName();
            String id = "";
            String name = ""; // useful when having
            NodeList childNodes = child.getChildNodes();
            
            do{
                
                childDemand -= discreteValue;
                demandPutValue = (childDemand > 0)? discreteValue : childDemand + discreteValue;
                

                // create the child element for the output xml
                Element outputChildElement = optaplannerDocument.createElement(childTag);
                // add the childs of the child element (id, type, location, demand...)
                for(int j=0; j<childNodes.getLength(); j++){
                    if(!(childNodes.item(j) instanceof Element)){
                        continue;

                    }
                    Element elem = (Element) childNodes.item(j);
                    if(!elem.getAttribute("optaplannerIgnore").equals("true")){ // the element must be included in the optaplanner problem file
                        // check if it's a id element
                        String tag = elem.getTagName();
                        if(tag.equals("id")){ // there must be a id tag per child
                            id = elem.getTextContent();

                        }

                        // create the child child element
                        Element outputChildChildElement = optaplannerDocument.createElement(tag);
                        // insert the text content
                        if(tag.equals("demand")){
                            outputChildChildElement.setTextContent("" + demandPutValue);
                            
                        }else{
                            outputChildChildElement.setTextContent(elem.getTextContent());
                            
                        }
                        
                        // check if references another object
                        String referenceId = elem.getAttribute("referenceId");
                        if(!referenceId.equals("")){
                            //System.out.println("referenceId=" + referenceId);
                            if(tag.equals("initialLocation")) {
                                outputChildChildElement.setAttribute("reference", initialLocationReferenceMap.get(referenceId));

                            } else if(tag.equals("location") || tag.equals("objectLocation")){ // the attribute references a Location object
                                outputChildChildElement.setAttribute("reference", locationReferenceMap.get(referenceId));

                            } 

                        }
                        

                        // append the child child element
                        outputChildElement.appendChild(outputChildChildElement);

                    }

                }

                outputChildElement.setAttribute("id", "" + idCounter);
                outputListElement.appendChild(outputChildElement);
                objectReferenceMap.put(id, "" + idCounter);
                //System.out.println("Id Node: " + id + " Id attribute: " + objectReferenceMap.get(id));
                idCounter++;
                
            } while(childDemand > 0);
                     
        }
        rootElement.appendChild(outputListElement);
        
    }

    /*
        The function returns a Map whose keys are the 'field' nodes
        and the values are ID nodes of the corresponding tasks.
    */
    private static Map<String, String> getIDMap(Element rootElement, String field) {
        NodeList nodes = rootElement.getElementsByTagName("Task");
        Map<String, String> objectIDMap = new HashMap(nodes.getLength());
        for(int i=0; i<nodes.getLength(); i++){
            Element element = (Element) nodes.item(i);
            String fieldContent = ((Element) element.getElementsByTagName(field).item(0)).getTextContent(); // suposing there is only one
            String id = ((Element) element.getElementsByTagName("id").item(0)).getTextContent();
            objectIDMap.put(fieldContent, id);
            
        }
        
        return objectIDMap;
        
        
    }

    private static void complementArcasSystemConfig(Element rootElement, String additionalInfoFileString, Map<String, String> taskPartNameIDMap) {
        try {

            File assemblyPlanFile = new File(additionalInfoFileString);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document assemblyPlanDocument = builder.parse(assemblyPlanFile);
            assemblyPlanDocument.getDocumentElement().normalize();
            
            
            // Supposing there is only one assembly plan, retrieve the
            // assembly operations and iterate over them. Assembly operations appear
            // in order, so the Task node must be enriched with the order on which
            // the operations have to be done. The order is given by the i index
            // of the loop.
            NodeList assemblyOperationNodes = assemblyPlanDocument.getElementsByTagName("assemblyOperation");
            for(int i=0; i<assemblyOperationNodes.getLength(); i++){
                Element assemblyOperationNode = (Element) assemblyOperationNodes.item(i);
                // get the action
                String action = assemblyOperationNode.getAttribute("action");
            
                // get the partName
                Element effect = (Element) assemblyOperationNode.getElementsByTagName("effect").item(0);
                Element effectAt = (Element) effect.getElementsByTagName("at").item(0);
                String partName = effectAt.getAttribute("part");

                // get the priority
                String priority = (action.equalsIgnoreCase("base"))? "1" : "2"; ///TODO assign priorities based on the construction level
                        
                // Obtain the Task XML nodes to be completed. Due to the possibiliti of discretizing tasks,
                // those nodes pertaining to the same task will have the same part assigned, so the preconditionList
                // is equal for all of them and must appear in all.
                NodeList tasks = rootElement.getElementsByTagName("Task");
                //System.out.println("Number of tasks: " + tasks.getLength());
                List toCompleteTaskList = new ArrayList();
                for(int z=0; z<tasks.getLength(); z++){
                    String objectName = ((Element) tasks.item(z)).getElementsByTagName("objectName").item(0).getTextContent();
                    if(objectName.equals(partName)){
                        toCompleteTaskList.add((Element) tasks.item(z));
                        
                    }
                    //System.out.println("objectName " + partName2);
                    
                }
                // get the preconditions
                NodeList preconditions = assemblyOperationNode.getElementsByTagName("precondition");
                Element  preconditionList = null;
                //Element task = taskElementMap.get(taskPartNameReferenceMap.get(partName));
                Element task = (Element) toCompleteTaskList.get(0);
                Iterator it = toCompleteTaskList.iterator();
                while(it.hasNext()){
                    Element aTask = (Element) it.next();
                    addTextChildNode(aTask, "priority", "" + priority);
                    addTextChildNode(aTask, "action", action);
                    preconditionList = addChildNode(aTask, "preconditionList");
                    for(int j=0; j<preconditions.getLength(); j++){
                        Node n = (((Element) preconditions.item(j)).getElementsByTagName("at").item(0));
                        String precondPartName = ((Element) n).getAttribute("part");
                        Element el = addChildNode(preconditionList, "TaskDependency");
                        //el.setAttribute("reference", "" + taskNameReferenceMap.get(precondPartName));
                        // add the id reference atribute
                        el.setAttribute("id", "" + idCounter);
                        idCounter++;
                        // add the id node
                        addTextChildNode(el, "id", taskPartNameIDMap.get(precondPartName));

                    }
                    
                }

                
                String id = ((Element) task.getElementsByTagName("id").item(0)).getTextContent(); // a Task will always hace a id
                List<Element> addList = systemConfigUpdateTaskMap.get(id);
                if(addList == null){
                    addList = new ArrayList();
                    systemConfigUpdateTaskMap.put(id, addList);
                    
                }
                addList.add(preconditionList); // the preconditionList is a new information that must appear in the SystemConfig
                Element goalOrder = systemConfigDocument.createElement("goalOrder");
                goalOrder.setTextContent("" + i);
                addList.add(goalOrder);
                ///TODO: the addList contains elements which have attributes from OptaPlanner (the id i.e.).
                /// They don't hurt but would be desirable to erase them in future versions because
                /// those attributes will appear in the Updated SystemConfig.
            
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OptaplannerProblemGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private static void createTaskElementMap(NodeList tasks, Map<String, Element> taskElementMap) {
        for(int i=0; i<tasks.getLength(); i++){
            Element task = (Element) tasks.item(i);
            String id = task.getAttribute("id");
            taskElementMap.put(id, task);
            
        }
        
        
    }

    /*
    This function updates the SystemConfig tasks with all data present at the
    momment of the execution in the systemConfigUpdateTaskMap. The functions
    delete the added info from the map once inserted in the SystemConfig document.
    */
    private static void updateSystemConfig() {
        // now we insert all the new complementary information into the SystemConfig file
        NodeList taskNodeList = systemConfigDocument.getElementsByTagName("Task");
        for(int i=0; i<taskNodeList.getLength(); i++){
            Element task = (Element) taskNodeList.item(i);
            String taskId = ((Element) task.getElementsByTagName("id").item(0)).getTextContent();
            List<Element> addList = systemConfigUpdateTaskMap.get(taskId);
            if(addList == null){ // nothing to add
                continue;

            }else if(addList.size() == 0){ // noting to add
                continue;

            } else{ // add all elements in the list
                for(int j=0; j<addList.size(); j++){
                    Element addElement = addList.get(j);
                    Node importedNode = systemConfigDocument.importNode(addElement, true); // import a copy of the node
                    task.appendChild(importedNode); // append it to the document;
                    
                }

            }

        }        
        
    }
    
}
