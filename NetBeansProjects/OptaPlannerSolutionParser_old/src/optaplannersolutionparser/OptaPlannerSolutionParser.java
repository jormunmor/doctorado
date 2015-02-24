/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package optaplannersolutionparser;

import coordinateconverter.LL2UTM;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;


/**
 *
 * @author jorge
 */
public class OptaPlannerSolutionParser {

    // old tasks
    //public static String solutionPath = "/home/jorge/Escritorio/SystemPlanner/MultiUAVProblemSolution.xml"; 
    //public static String systemConfigPath = "/home/jorge/Escritorio/SystemPlanner/SysCon.xml";
    
    //arcas tasks
    public static String solutionPath = "/home/jorge/Escritorio/SystemPlanner/ArcasAssemblyProblemSolution.xml"; 
    public static String systemConfigPath = "/home/jorge/Escritorio/SystemPlanner/UpdatedSystemConfig.xml";
    
    public static String tasksPath = "/home/jorge/Escritorio/SystemPlanner/Tasks/";
    public static String outPddlPath = "/home/jorge/Escritorio/SystemPlanner/JSHOP2problem";
    
    // this map contains the task type for each location
    public static Map<String, String> taskTypesMap = new HashMap(); // <id,type> i.e.: <2,surveillance> 
    public static Map<String, String> jshop2TaskTypesMap = new HashMap();
    
    // This map contains the String representation of the polygons and forbidden
    // area's xml nodes associated to a task. The map will be empty in missions 
    // without that data in the tasks.
    public static Map<String, String> taskGeometricDataMap = new HashMap();
    
    // These maps contains the node values for objectName and objectLocation nodes,
    // if they exist.
    public static Map<String, String> taskObjectNameMap = new HashMap(); //<taskId, objectName> (an object associated with a task)
    public static Map<String, String> taskObjectLocationMap = new HashMap(); //<objectName, locationId> (where the object is placed)
    public static Map<String, String> taskLocationMap = new HashMap(); //<taskId, locationId> (where the task has to be done)
    
    // This map contains the demands for each of the tasks, given their id node.
    // It is created in the parseConfigFile function
    public static Map<String, Integer> taskDemandMap = new HashMap(); //<taskId, demand>
        
    public static List<Location> locationList = new ArrayList();
    public static List<InitialLocation> initialLocationList = new ArrayList();
    public static List<Vehicle> autonomousVehicleList = new ArrayList();
    public static Map<Long, Object> objectMap = new HashMap();
    public static Map<Location, String> locationsMap = new HashMap(); // <location object, location string> i.e.: <locationObject,'location 0'>
    public static Map<String, String> locationsPercentagesMap = new HashMap(); // <location number, percentage> i.e.: <'2','50 50'>
    public static Map<Vehicle, String> autonomousVehicleMap = new HashMap();
    
    // strings to create the JShop2 PDDL
    public static String usedUavsString = "";
    public static String unusedUavsString = "";
    public static String locationsString = "";
    public static String objectsString = "";
    public static String uavsStatesString = "";
    public static String objectsStatesString = "";
    public static String dependenciesString = "";
    public static String uavRWString = "";
    public static String objectsRWString = "";
    public static String goalsString = "";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            
            // Parse the config file
            File systemConfig = new File(systemConfigPath);
            DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
            Document configDoc = dBuilder1.parse(systemConfig);
            configDoc.getDocumentElement().normalize();
            parseConfigFile(configDoc);
            
            
            
            // Parse the solution file
            File optaplannerSolution = new File(solutionPath);
            DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
            Document solutionDoc = dBuilder2.parse(optaplannerSolution);
            solutionDoc.getDocumentElement().normalize();

            // Get xml nodes
            System.out.println("Xml root tag: " + solutionDoc.getDocumentElement().getNodeName());
            NodeList locationNodes = solutionDoc.getElementsByTagName("Location");
            System.out.println("Number of Location xml nodes: " + locationNodes.getLength());
            NodeList initialLocationNodes = solutionDoc.getElementsByTagName("InitialLocation");
            System.out.println("Number of InitialLocation xml nodes: " + initialLocationNodes.getLength());
            NodeList autonomousVehicleNodes = solutionDoc.getElementsByTagName("AutonomousVehicle");
            System.out.println("Number of AutonomousVehicle xml nodes: " + autonomousVehicleNodes.getLength());
            NodeList taskNodes = solutionDoc.getElementsByTagName("Task");
            System.out.println("Number of Task xml nodes: " + taskNodes.getLength());
            System.out.println();
            
            //System.out.println("==========================");
            //System.out.println("Generating OptaPlanner data structures...");
            //System.out.println("==========================");
            
            // Create Location objects list, clean the Locations dir
            for (int i = 0; i < locationNodes.getLength(); i++) {
                Node node = locationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    Long id = Long.parseLong(element.getAttribute("id")); // we use the xml attribute, and not the xml id node
                    String name = getNodeValue("id", element);
                    double latitude = Double.parseDouble(getNodeValue("latitude", element));
                    double longitude = Double.parseDouble(getNodeValue("longitude", element));
                    Location location = new Location(id, name, latitude, longitude);
                    locationList.add(location);
                    String locationVarString = "location " + name;
                    locationsString = locationsString.concat("\t\t(" + locationVarString + ")\n");
                    locationsMap.put(location, locationVarString);
                    locationsPercentagesMap.put(name, "");
                    objectMap.put(location.getId(), location);
                    if(taskGeometricDataMap.get(name) != null){ // the task has a GeometricData associated
                        generateTaskDataFiles(name);
                        
                    }

                }

            }
            
            // Create InitialLocation objects list
            for (int i = 0; i < initialLocationNodes.getLength(); i++) {
                Node node = initialLocationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    Long id = Long.parseLong(element.getAttribute("id")); // we use the tag attribute, and not the tag field
                    String name = getNodeValue("id", element);
                    Element locationChild = (Element) element.getElementsByTagName("location").item(0);
                    Long locationReference = Long.parseLong(locationChild.getAttribute("reference"));
                    Location loc = (Location) objectMap.get(locationReference);
                    InitialLocation initialLocation = new InitialLocation(id, name, loc);
                    initialLocationList.add(initialLocation);
                    objectMap.put(initialLocation.getId(), initialLocation);

                }

            }

            
            // Create AutonomousVehicle objects list
            for (int i = 0; i < autonomousVehicleNodes.getLength(); i++) {
                Node node = autonomousVehicleNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element autonomousVehicleElement = (Element) node;
                    Long id = Long.parseLong(autonomousVehicleElement.getAttribute("id")); // we use the tag attribute, and not the tag field
                    String name = getNodeValue("id", autonomousVehicleElement);
                    int capacity = Integer.parseInt(getNodeValue("capacity", autonomousVehicleElement));
                    Element initialLocationChild = (Element) autonomousVehicleElement.getElementsByTagName("initialLocation").item(0);
                    Long initialLocationReference = Long.parseLong(initialLocationChild.getAttribute("reference"));
                    InitialLocation place = (InitialLocation) objectMap.get(initialLocationReference);
                    Vehicle uav = new Vehicle(id, name, capacity, place, null);
                    autonomousVehicleList.add(uav);
                    String uavVarString = "quadrotor uav" + name;
                    //uavsString = uavsString.concat("\t\t(" + uavVarString + ")\n");
                    autonomousVehicleMap.put(uav, uavVarString);                     
                    objectMap.put(uav.getId(), uav);
                    
                    // we have to create a linked list of next task due to optaplanner chains
                    // get the first to configure the vehicle's next task
                    NodeList taskNodeList = autonomousVehicleElement.getElementsByTagName("nextTask");
                    if(taskNodeList.getLength() == 0){ // uav is unused
                        unusedUavsString = unusedUavsString.concat("\t\t(" + uavVarString + ")\n");
                        String location = locationsMap.get(uav.getLocation()).substring(9);
                        uavsStatesString = uavsStatesString.concat("\t\t; UAV" + name + " state defs\n");
                        uavsStatesString = uavsStatesString.concat("\t\t(battery uav" + name + " 1200)\n");
                        uavsStatesString = uavsStatesString.concat("\t\t(at uav" + name + " " + location + ")\n");
                        uavsStatesString = uavsStatesString.concat("\t\t(landed uav" + name + ")\n");
                        uavsStatesString = uavsStatesString.concat("\t\t(unused uav" + name + ")\n\n");
                        
                        uavRWString = uavRWString.concat("\t\t; read/write times UAV" + name + " \n");
                        uavRWString = uavRWString.concat("\t\t(write-time at uav" + name + " 0)\n");
                        uavRWString = uavRWString.concat("\t\t(read-time at uav" + name + " 0)\n\n");
                        uavRWString = uavRWString.concat("\t\t(write-time battery uav" + name + " 0)\n");
                        uavRWString = uavRWString.concat("\t\t(read-time battery uav" + name + " 0)\n\n");
                        
                        continue;
                        
                    }
                    
                    // uav is used
                    usedUavsString = usedUavsString.concat("\t\t(" + uavVarString + ")\n");
                    Task firstTask = getTask(taskNodeList, 0);
                    firstTask.setPreviousStandstill(uav);
                    uav.setNextTask(firstTask);
                    objectMap.put(firstTask.getId(), firstTask);
                    //System.out.println("Task" + firstTask.getId());
                    
                    // to take only the location final number we use substring, because in the map
                    // the locations appear as 'location 0' i.e.
                    String pddlPreviousLoc = locationsMap.get(uav.getLocation()).substring(9); 
                    //String pddlCurrentLoc = locationsMap.get(firstTask.getLocation()).substring(9);
                    //String locationName = firstTask.getLocation().getName();
                    //String taskType = (String) taskTypesMap.get(locationName);
                    //String jshop2TaskType = (String) jshop2TaskTypesMap.get(locationName);
                    
                    uavsStatesString = uavsStatesString.concat("\t\t; UAV" + name + " state defs\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(battery uav" + name + " 1200)\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(at uav" + name + " " + pddlPreviousLoc + ")\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(landed uav" + name + ")\n\n");
                    
                    //pddlPreviousLoc = pddlCurrentLoc;
                    
                    uavRWString = uavRWString.concat("\t\t; read/write times UAV" + name + " \n");
                    uavRWString = uavRWString.concat("\t\t(write-time at uav" + name + " 0)\n");
                    uavRWString = uavRWString.concat("\t\t(read-time at uav" + name + " 0)\n\n");
                    uavRWString = uavRWString.concat("\t\t(write-time battery uav" + name + " 0)\n");
                    uavRWString = uavRWString.concat("\t\t(read-time battery uav" + name + " 0)\n\n");
                    
                    // depending on the jshop2TaskType the JSHOP2 goal must be different. For the arcas
                    // the last parameter is the part that must be assembled.
                    /*
                    if(jshop2TaskType.equals("synchro_mission")){
                        goalsString = goalsString.concat("\t\t(" + jshop2TaskType + " " + taskType + " uav" + name + " " + pddlCurrentLoc + ")\n");
                        
                    } else{
                        goalsString = goalsString.concat("\t\t(" + jshop2TaskType + " " + taskType + " uav" + name + " " + pddlCurrentLoc + ")\n");
                        
                    }
                    
                    Task previousTask = firstTask;
                    for(int j=1; j<taskNodeList.getLength();j++){
                        Task currentTask = getTask(taskNodeList, j);
                        locationName = currentTask.getLocation().getName();
                        taskType = (String) taskTypesMap.get(locationName);
                        jshop2TaskType = (String) jshop2TaskTypesMap.get(locationName);
                        previousTask.setNextTask(currentTask);
                        currentTask.setPreviousStandstill(previousTask);
                        objectMap.put(currentTask.getId(), currentTask);
                        pddlCurrentLoc = locationsMap.get(currentTask.getLocation()).substring(9);
                        previousTask = currentTask;
                        //pddlPreviousLoc = pddlCurrentLoc;
                        goalsString = goalsString.concat("\t\t(" + taskType + " uav" + name + " " + pddlCurrentLoc + ")\n");
                        
                    }*/
                    
                }

            }

            /*
            System.out.println();
            System.out.println("Location list generated:");
            System.out.println("--------------------------");
            Iterator it1 = locationList.iterator();
            while(it1.hasNext()){
                Location loc = (Location) it1.next();
                System.out.println("Location id=" + loc.getId() + " name=" + loc.getName() + " latitude=" + loc.getLatitude() + " longitude=" + loc.getLongitude());
                
            }
            
            System.out.println();
            System.out.println("InitialLocation list generated:");
            System.out.println("--------------------------");
            Iterator it2 = initialLocationList.iterator();
            while(it2.hasNext()){
                InitialLocation place = (InitialLocation) it2.next();
                System.out.println("InitialLocation id=" + place.getId() + " name=" + place.getName() + " LocationId=" + place.getLocation().getId() + " LocationName=" + place.getLocation().getName());
                
            }
            
            
            System.out.println();
            System.out.println("Uav list generated:");
            System.out.println("--------------------------");
            Iterator it3 = autonomousVehicleList.iterator();
            while(it3.hasNext()){
                Vehicle uav = (Vehicle) it3.next();
                System.out.println("Vehicle id=" + uav.getId() + " name=" + uav.getName() + " capacity=" + uav.getCapacity() + " TakeOffPlaceId=" + uav.getDepot().getId() + " TakeOffPlaceName=" + uav.getDepot().getName());
                
            }
            */
            /*
            System.out.println();
            System.out.println("Plan chains generated:");
            System.out.println("--------------------------");
            it3 = autonomousVehicleList.iterator();
            while(it3.hasNext()){
                Vehicle uav = (Vehicle) it3.next();
                System.out.println("Task locations for uav" + uav.getName() + ", starting at " + uav.getLocation() + ":");
                // get first task location
                Task nextTask = uav.getNextTask(); // will always have one at least
                int taskCount = 0;
                while(nextTask != null){
                    taskCount++;
                    Location tLoc = nextTask.getLocation();
                    System.out.println(taskCount + "th task at " + tLoc + " Latitude=" + tLoc.getLatitude() + " Longitude=" + tLoc.getLongitude());
                    nextTask = nextTask.getNextTask();
                    
                }
                                
            }
            
            // parse the goals string to reduce the multi-goals
            goalsString = reduceGoals(goalsString);
            */
            // generate the area division in those mapGeneration and surveillance tasks with multiple percentages
            /*
            String arrayString[] = execute_area_partition_and_zigzag();
            */
            
            // Generate the goals. Those tasks which have the same Task Id are considered
            // to be the same and must be reduced, which means that the percentages related
            // to each involved UAV must be computed. 
            goalsString = generateGoals(autonomousVehicleNodes);
            
            // create the PDDL file
            PrintWriter out = new PrintWriter(outPddlPath);
            String header = 
            "(defproblem problem quadrotor\n" +
            "\n" +
            "\t; FACTS\n" +
            "\t(";
            out.println(header);
            out.println("\t\t; UAV defs");
            out.println(usedUavsString.concat(unusedUavsString)); // the used uavs must appear first for JShop2 to try first the used
            out.println("\t\t; location defs");
            out.println(locationsString);
            out.println("\t\t; object defs");
            out.println(objectsString);
            /*
            out.println("\t\t; subareas defs");
            out.println(arrayString[0]);
            out.println("\t\t; subarea assignations defs");
            out.println(arrayString[1]);
            */
            out.println("\t\t; object state defs");
            out.println(objectsStatesString);
            out.println(dependenciesString);
            out.println(uavsStatesString);
            out.println(uavRWString);
            out.println(objectsRWString);
            out.println("\t)\n");
            out.println("\t; GOALS\n\t(\n");
            out.println(goalsString);
            out.println("\t)\n)");
            out.close();
            
            
        
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException ex) {
        
        }
        
        
    }
    
    static String readFile(String path, Charset encoding) throws IOException {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
      
    }
    
    /* 
    Returns from the Element argument the value of the first child node 
    with tag nodeTag, or the empty String.
    */
    private static String getNodeValue(String nodeTag, Element element) {
        NodeList childElements = element.getElementsByTagName(nodeTag);
        if(childElements.getLength() == 0){
            return "";
            
        }
        Element childElement = (Element) childElements.item(0);
        
        return childElement.getTextContent();
        
    }
    
    /*
    Returns from the given autonomousVehicleElement and the given child nodeTag, the value of the given attribute,
    or the empty String.
    */
    private static String getNodeAttributeValue(String nodeTag, String attributeName, Element element) {
        NodeList childElements = element.getElementsByTagName(nodeTag);
        if(childElements.getLength() == 0){
            return "";
            
        }
        Element childElement = (Element) childElements.item(0);
        
        return childElement.getAttribute(attributeName);
        
    }

    /*
    This function receives a taskNodeList that contains a OptaPlanner chain, and
    returns a new Task object with the information contained in the node i.
    */
    private static Task getTask(NodeList taskList, int i) {
        Element task = (Element) taskList.item(i);
        Long taskId = Long.parseLong(task.getAttribute("id")); // we use the tag attribute, and not the tag field
        String taskName = getNodeValue("id", task);
        Element taskLocationChild = (Element) task.getElementsByTagName("location").item(0);
        Long taskLocationReference = Long.parseLong(taskLocationChild.getAttribute("reference"));
        Location taskLoc = (Location) objectMap.get(taskLocationReference);
        Element taskVehicleChild = (Element) task.getElementsByTagName("vehicle").item(0);
        Long taskVehicleReference = Long.parseLong(taskVehicleChild.getAttribute("reference"));
        Vehicle taskVehicle = (Vehicle) objectMap.get(taskVehicleReference);
        int taskDemand = Integer.parseInt(getNodeValue("demand", task));
        Task taskObject = new Task(taskId, taskName, taskLoc, taskDemand, null, null, taskVehicle);
        
        return taskObject;

    }

    
    private static void parseConfigFile(Document configDoc) {
        try {
            FileUtils.cleanDirectory(new File(tasksPath));
            //System.out.println("==========================");
            //System.out.println("     System tasks");
            //System.out.println("==========================");
            NodeList tasks = configDoc.getElementsByTagName("Task");
            // Recover the basic task info
            for(int i=0; i<tasks.getLength(); i++){
                Element task = (Element) tasks.item(i);
                // recover the location node an get its referenceId
                String locationId = getNodeAttributeValue("location", "referenceId", task);
                // recover the task type
                String taskType = getNodeValue("type", task);
                // recover the task id (different tasks, different id's)
                String taskId = getNodeValue("id", task);
                // recover the task demand
                int taskDemand = Integer.parseInt(getNodeValue("demand", task));
                taskDemandMap.put(taskId, taskDemand);
                String jshop2TaskType = getNodeValue("JSHOP2type", task);
                taskTypesMap.put(taskId, taskType);
                jshop2TaskTypesMap.put(taskId, jshop2TaskType);
                taskLocationMap.put(taskId, locationId);
                //System.out.println("Task ID: " + taskId +  "\nTask location ID: " + locationId + "\nTask type: " + taskType + "\nJSHOP2 Task type: " + jshop2TaskType);
                
                // If there is a GeometricData node
                // associated to the task, then we create a xml string representation 
                // for it to store in a map.

                String geometricDataString = "";
                // Retrieve the geometric data (if any)
                NodeList taskGeometricDataList = task.getElementsByTagName("GeometricData");
                if(taskGeometricDataList.getLength() > 0){ // there is a Polygon node
                    geometricDataString =  getElementAsString((Element) taskGeometricDataList.item(0));
                    
                }
                
                // Create a String document from the xml nodes in the case of 
                // existing, by adding the xml header.
                if(!geometricDataString.equals("")){
                    taskGeometricDataMap.put(locationId, geometricDataString);
                    //System.out.println("geometricDataString:\n" + geometricDataString);
                    
                }
                
                // Retrieve the associated objectName
                NodeList taskObjectNameList = task.getElementsByTagName("objectName");
                if(taskObjectNameList.getLength() > 0){ // there is a objectName node
                    String objectName = ((Element) taskObjectNameList.item(0)).getTextContent();
                    taskObjectNameMap.put(taskId, objectName);
                    objectsString += "\t\t(object " + objectName + ")\n";
                    //objectsStatesString += "\t\t; " + objectName + " state defs\n";
                    
                    // Retrieve the associated objectLocation
                    NodeList taskObjectLocationList = task.getElementsByTagName("objectLocation");
                    if(taskObjectLocationList.getLength() > 0){ // there is a objectLocation node
                        String objectLocation = ((Element) taskObjectLocationList.item(0)).getAttribute("referenceId");
                        taskObjectLocationMap.put(objectName, objectLocation);
                        objectsStatesString += "\t\t(at " + objectName + " " + objectLocation  + ")\n";
                        objectsRWString += "\t\t; read/write times " + objectName + "\n";
                        objectsRWString += "\t\t(write-time at " + objectName + " 0)\n";
                        objectsRWString += "\t\t(read-time at " + objectName + " 0)\n\n";
                        //System.out.println("Task objectName: " + objectName + "\nTask objectLocation Id: " + objectLocation);

                    }
                                        
                }
                
                //System.out.println("----------");
                                
            }
            
            // Recover the tasks dependencies
            dependenciesString = getTasksDependencies(tasks);
            
            
        } catch (IOException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    
    }

    private static String reduceGoals(String goalsString) {
        System.out.println(goalsString);
        List processedList = new ArrayList();
        String reducedGoals = "";
        //System.out.println("Imprimiendo goals originales: ");
        //System.out.println(goalsString);
        
        String[] goals = goalsString.split("\n");
        //System.out.println("Imprimiendo goals reducidos: ");
        Set addedOne = new HashSet();
        for(int i=0; i<goals.length; i++){
            // first delete the '\t' '(' and ')' characters
            String goal = goals[i];
            if(processedList.contains(goal)){
                continue;
                
            }
            if(goals[i].contains("surveillance") || goals[i].contains("mapGeneration")) {
                // delete unnecesary characters '\t' '(' ')'
                goals[i] = goals[i].replaceAll("[\t()]", ""); 
            
                // second split the arguments
                String[] args = goals[i].split(" ");
            
                // third search all the occurrences of the location
                int totalOccurences = StringUtils.countMatches(goalsString, " " + args[2] + ")");
                if(totalOccurences > 1) { // 100% percentage or multiples precentages
                    int specificOccurences = StringUtils.countMatches(goalsString, args[1] + " " + args[2] + ")");
                    double percentage = ((double) specificOccurences) / totalOccurences;
                    int prctg = (int) (percentage * 100);
                    if(addedOne.contains(args[2])){
                        reducedGoals = reducedGoals + "\t\t(" + args[0] + " " + args[1] + " " + args[2] + " " + (int) (percentage * 100) + ")\n";
                    }else{ ///TODO: buscar otra forma de evitar que la suma de porcentages sea < 100, esta es una autentica chapuza
                        prctg = ((int) (percentage * 100)) + 1;
                        if(prctg > 100){
                            prctg = 100;
                        }
                        reducedGoals = reducedGoals + "\t\t(" + args[0] + " " + args[1] + " " + args[2] + " " + prctg + ")\n";
                        addedOne.add(args[2]);
                        
                    }
                    processedList.add(goal);

                    String cmds = locationsPercentagesMap.get(args[2]);
                    
                    if(cmds.equals("")){
                        cmds = "" + prctg;
                        
                    }else{
                        cmds = cmds.concat(" " + prctg);
                    }
                    //System.out.println("cmds: " + cmds);
                    locationsPercentagesMap.put(args[2], cmds);
                    //System.out.println("cmds: " + locationsPercentagesMap.get(args[2]));

                } else { // 100% percentage
                    reducedGoals = reducedGoals + "\t\t(" + args[0] + " " + args[1] + " " + args[2] + " " + 100 + ")\n";                    
                    locationsPercentagesMap.put(args[2], "100");
                    
                }
                                
            } else {
                reducedGoals = reducedGoals + goals[i] + "\n";               
                
            }
            
            //System.out.println(goals[i]);
            
        }
        
        //System.out.println(reducedGoals);
        
        return reducedGoals;
    }

    /*
    This function is only used when the tasks have associated a polygon and/or
    its forbidden areas, to generate a xml document containing the polygon as
    well as a text file containing the utm coordinates of the polygon. The
    name argument is the xml id node of the location where the task has to be
    done.
    */
    private static void generateTaskDataFiles(String name) {
        PrintWriter xml_out = null;
        try {
            File dir = new File(tasksPath + "location_" + name);
            dir.mkdir();
            String xmlFilename = tasksPath + "location_" + name + "/task_" + name + ".xml";
            String txtFilename = tasksPath + "location_" + name + "/polygon_" + name + ".txt";
            xml_out = new PrintWriter(xmlFilename);
            xml_out.println(taskGeometricDataMap.get(name));
            xml_out.close();
            
            // parse the xml and generate the 
            generatePolygonFile(xmlFilename, txtFilename);
                        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            xml_out.close();
            
        }
                        
    }

    private static void generatePolygonFile(String xmlFilename, String txtFilename) {
        try {
            File xmlFile = new File(xmlFilename);
            PrintWriter txt_out = new PrintWriter(txtFilename);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            // parse the polygon part
            Element polygonNode = (Element) doc.getElementsByTagName("Polygon").item(0);
            NodeList pointsList = polygonNode.getElementsByTagName("Point");
            printPoints(pointsList, txt_out);
            txt_out.println("-1.0 -1.0");
            
            // parse the forbidden areas part
            Element forbiddenAreasNode = (Element) doc.getElementsByTagName("ForbiddenAreas").item(0);
            if(forbiddenAreasNode != null){
                NodeList areasList = forbiddenAreasNode.getElementsByTagName("Area");
                for(int i=0; i<areasList.getLength(); i++){
                    Element area = (Element) areasList.item(i);
                    pointsList = area.getElementsByTagName("Point");
                    printPoints(pointsList, txt_out);
                    txt_out.println("-1.0 -1.0");

                }
                
            }
            
            txt_out.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
                
        }
        
        
    }

    private static void printPoints(NodeList pointsList, PrintWriter txt_out) {
        Double latitude, longitude;
        for(int i=0; i<pointsList.getLength(); i++){
            Element point = (Element) pointsList.item(i);
            latitude = Double.parseDouble(getNodeValue("latitude", point));
            longitude = Double.parseDouble(getNodeValue("longitude", point));
            LL2UTM conversion = LL2UTM.convert(latitude, longitude);
            txt_out.println(conversion.getEasting() + " " + conversion.getNorthing());

        }
        
        
    }

    /*
        Iterate over the tasks map to recover the mapGeneration and surveillance tasks
        and execute the area partition algorithm in the case there are multiple percentages,
        as well as the zigzag algorithm.
    */
    private static String[] execute_area_partition_and_zigzag() {
        String subareaString = "";
        String unassignedString = "";
        
        // locationsString = locationsString.concat("\t\t(" + locationVarString + ")\n");
        // Iterate over all locations to try to execute the area partition and zig zag if it's the case
        Iterator it = locationList.iterator();
        while(it.hasNext()){
            String location = ((Location) it.next()).getName();
        //for (Map.Entry<String, String> entry : taskTypesMap.entrySet()) {
            //String location = entry.getKey();
            //String task = entry.getNodeValue();
            String mission = taskTypesMap.get(location);
            String filename = tasksPath + "location_" + location + "/polygon_" + location + ".txt";
            if(mission.contains("mapGeneration") || mission.contains("surveillance")){
                String prctg = locationsPercentagesMap.get(location);
                try{
                    if(!prctg.equals("") && !prctg.equals("100")){ // multiple percentages
                        String[] percentages = prctg.split(" ");
                        // now for each fo the subareas wi will call the zig zag algorithm
                        Runtime rt = Runtime.getRuntime();
                        String execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_partition/area_partition" + " " + filename + " " + prctg;
                        System.out.println("Execution string: " + execString);
                        Process ps = rt.exec(execString);
                        ps.waitFor();
                        //int res = ps.exitValue();
                        int res = 0;
                        //System.out.println("Area partition return value for location" + location + ": " + res);
                        
                        
                        for(int i=0; i<percentages.length; i++){
                            String subfilename = tasksPath + "location_" + location + "/polygon_" + location + "_subarea" + i + "_" + percentages[i] + ".txt";
                            rt = Runtime.getRuntime();
                            execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_zig_zag/area_zig_zag" + " " + subfilename;
                            System.out.println("Execution string: " + execString);
                            ps = rt.exec(execString);
                            ps.waitFor();
                            //res = ps.exitValue();
                            String resultFile = new StringBuilder(subfilename).insert(subfilename.length()-4, "_distance").toString();
                            Scanner scanner = new Scanner(new File(resultFile)).useLocale(Locale.US);
                            res = (int) Math.ceil(scanner.nextDouble());
                            
                            System.out.println("Zigzag return value for location" + location + " subarea" + i + ": " + res);
                            subareaString = subareaString.concat("\t\t(subarea " + location + " " + i + " " + percentages[i] + " " + res + ")\n");
                            unassignedString = unassignedString.concat("\t\t(unassigned " + location + " " + i + ")\n");
                            
                        }

                    } else if(prctg.equals("100")){ // 100% percentage
                        // execute the algorithm, we could fusion this if inside the before
                        // because now we execute the area partition even if the polygon
                        // is 100% used by the same UAV
                        int res = 0;
                        Runtime rt = Runtime.getRuntime();
                        String execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_partition/area_partition" + " " + filename + " " + prctg;
                        System.out.println("Execution string: " + execString);
                        Process ps = rt.exec(execString);
                        ps.waitFor();
                        //int res = ps.exitValue();
                        //System.out.println("Area partition return value for location" + location + ": " + res);
                                             
                        
                        rt = Runtime.getRuntime();
                        execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_zig_zag/area_zig_zag" + " " + filename;
                        System.out.println("Execution string: " + execString);
                        ps = rt.exec(execString);
                        ps.waitFor();
                        //res = ps.exitValue();
                        
                        String resultFile = new StringBuilder(filename).insert(filename.length()-4, "_distance").toString();
                        Scanner scanner = new Scanner(new File(resultFile)).useLocale(Locale.US);
                        res = (int) Math.ceil(scanner.nextDouble());
                        System.out.println("Zigzag return value for location" + location + ": " + res);
                        subareaString = subareaString.concat("\t\t(subarea " + location + " " + 0 + " " + 100 + " " + res + ")\n");
                        unassignedString = unassignedString.concat("\t\t(unassigned " + location + " " + 0 + ")\n");

                    }
                } catch(IOException | InterruptedException exception) {
                            exception.printStackTrace();

                }
                
            }

        }
        
        String arrayString[] = {subareaString, unassignedString}; 
        
        return arrayString;
        
    }

    /*
    This function returns a String representation of a xml autonomousVehicleElement in a document.
    */
    private static String getElementAsString(Element element) {
        String xmlString = "";
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(element);
            transformer.transform(source, result);
            xmlString = result.getWriter().toString();
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (TransformerException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return xmlString;
   
    }

    // Here we retrieve the dependencies for the object. Due to the specific JSHOP2 domain
    // for the arcas, the task dependencies are mapped to part dependencies. Also,
    // the Task dependencies are only looked when there is a objectName node. All this reduces
    // the independency of the missions.
    /// TODO: change the JSHOP2 model to accept the dependencies at the level of tasks (Task Id) and not
    // at the level of objects (objectName). This implies some changes for this parser.
    private static String getTasksDependencies(NodeList tasks) {
        String dependencies = "\t\t; ObjectDependencies\n";
        for(int i=0; i<tasks.getLength(); i++){
            Element task = (Element) tasks.item(i);
            String taskId = getNodeValue("id", task);
            String taskObjectName = getNodeValue("objectName", task);
            NodeList preconditionsList = task.getElementsByTagName("preconditionList");
            if(preconditionsList.getLength() > 0){
                Element preconditionList = (Element) preconditionsList.item(0);
                NodeList taskDependencies = preconditionList.getElementsByTagName("TaskDependency");
                if(taskDependencies.getLength() > 0){
                    String depend = "\t\t(depends " + taskObjectName + " (";
                    for(int j=0; j<taskDependencies.getLength(); j++){
                        String taskDependencyId = getNodeValue("id", ((Element) taskDependencies.item(j)));
                        String taskDependencyObjectName = taskObjectNameMap.get(taskDependencyId);
                        depend += taskDependencyObjectName + " ";

                    }
                    dependencies += depend.substring(0, depend.length()-1) + "))\n";


                }

            }

        }
        return dependencies;
        
    }

    private static String generateGoals(NodeList autonomousVehicleNodes) {
        String returnString = "";
        for(int i=0; i<autonomousVehicleNodes.getLength(); i++){
            Element vehicle = (Element) autonomousVehicleNodes.item(i);
            String vehicleId = getNodeValue("id", vehicle);
            // see first if the vehicle has at least one task
            NodeList nodeList = vehicle.getElementsByTagName("nextTask");
            if(nodeList.getLength() == 0){ // the vehicle has no assigned tasks
                continue;
                
            }
            // at least one assigned task
            Map<String, Integer> taskSpentDemandMap = new HashMap();
            while(nodeList.getLength() > 0){ // there is a nextTask node
                Element nextTask = (Element) nodeList.item(0);
                String nextTaskId = getNodeValue("id", nextTask);
                int demand = Integer.parseInt(getNodeValue("demand", nextTask));
                if(taskSpentDemandMap.containsKey(nextTaskId)){
                    taskSpentDemandMap.put(nextTaskId, taskSpentDemandMap.get(nextTaskId) + demand);
                    
                } else{
                    taskSpentDemandMap.put(nextTaskId, demand);
                    
                }
                nodeList = nextTask.getElementsByTagName("nextTask");
            
            }
            // iterate over the created map
            for (Map.Entry<String, Integer> entry : taskSpentDemandMap.entrySet())
            {
                String taskId = entry.getKey(); // recover the task Id
                int spentDemand = entry.getValue(); // recover the spent demand for that vehicle in this task
                double percentage = (((double) spentDemand)/taskDemandMap.get(taskId))*100;
                //System.out.println("TaskId: " + taskId + " SpentDemand: " + spentDemand);
                // create the goal
                String type = taskTypesMap.get(taskId) + " ";
                String jshopType = jshop2TaskTypesMap.get(taskId) + " ";
                String uavName = "uav" + vehicleId + " ";
                String location = taskLocationMap.get(taskId) + " ";
                String objectName = taskObjectNameMap.get(taskId);
                if(objectName == null){
                    objectName = "";
                    
                }else{
                    objectName += " ";
                    
                }
                int prctg = (int) percentage;
                returnString += "\t\t(" + jshopType + type + uavName + location + objectName + prctg + ")\n" ;
                
            }
            
        }
                
        return returnString;
        
    }
    
}
