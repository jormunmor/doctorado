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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Node;


/**
 *
 * @author jorge
 */
public class OptaPlannerSolutionParser {

    //arcas tasks
    //private String solutionPath = "/home/jorge/Escritorio/SystemPlanner/ArcasAssemblyProblemSolution.xml"; 
    //private String updatedSystemConfigPath = "/home/jorge/Escritorio/SystemPlanner/UpdatedSystemConfig.xml";
    
    private final String solutionPath;
    private final String updatedSystemConfigPath;
    
    //private String tasksPath = "/home/jorge/Escritorio/SystemPlanner/Tasks/"; // In this path will be all related task data
    //private String outPddlPath = "/home/jorge/Escritorio/SystemPlanner/JSHOP2problem";
    private String tasksPath;
    private String outPddlPath;
    
    private final Map<String, Task> taskMap = new HashMap(); //<taskId, taskObject>
    private final Map<String, String> taskObjectLocationMap = new HashMap(); //<objectName, locationId> (where the object is placed)
    private final Map<String, String> locationTaskMap = new HashMap(); //<locationId, taskId> the task that has to be done in the locationId
    private final Map<String, String> initialLocationMap = new HashMap(); //<initialLocationId, locationId> the task that has to be done in the locationId
        
    //private final List<Location> locationList = new ArrayList();
    //private final List<InitialLocation> initialLocationList = new ArrayList();
    //private final List<Vehicle> autonomousVehicleList = new ArrayList();
    private final Map<String, String> objectReferenceMap = new HashMap(); //<referenceId, id>
    //private final Map<Location, String> locationsMap = new HashMap(); // <location object, locationId string> i.e.: <locationObject,'locationId 0'>
    //private final Map<String, String> locationsPercentagesMap = new HashMap(); // <location number, percentage> i.e.: <'2','50 50'>
    //private final Map<Vehicle, String> autonomousVehicleMap = new HashMap();
    
    // strings to create the JShop2 PDDL
    private String usedUavsString = "";
    private String unusedUavsString = "";
    private String locationsString = "";
    private String taskLocationsString = "";
    private String taskAssignsString = "";
    private String objectsString = "";
    private String uavsStatesString = "";
    private String objectsStatesString = "";
    private String dependenciesString = "";
    private String uavRWString = "";
    private String objectsRWString = "";
    private String goalsString = "";
    private int tasksNumber;
    
    /**
     *
     * @param solutionPath
     * @param updatedSystemConfigPath
     * @param tasksPath
     * @param outPddlPath
     * 
     */
    public OptaPlannerSolutionParser(String solutionPath, String updatedSystemConfigPath, String tasksPath, String outPddlPath){
        this.solutionPath = solutionPath;
        this.updatedSystemConfigPath = updatedSystemConfigPath;
        this.tasksPath = tasksPath;
        this.outPddlPath = outPddlPath;        
        
    }
    
    public static void main (String[] args)
    {        
        String solutionPath = "../../SystemPlanner/ArcasAssemblyProblemSolution.xml"; 
        String updatedSystemConfigPath = "../../SystemPlanner/UpdatedSystemConfig.xml";
        String tasksPath = "../../SystemPlanner/Tasks/"; // In this path will be all related task data
        //String outPddlPath = "/home/jorge/Escritorio/SystemPlanner/JSHOP2problem";
        String outPddlPath = "../../JSHOP2/examples/quadrotor_arcas/problem";
        OptaPlannerSolutionParser parser = new OptaPlannerSolutionParser(solutionPath, updatedSystemConfigPath, tasksPath, outPddlPath);
        parser.parseSolution();        
        
    } 
    
    public void parseSolution() {
        try {
            // Clean the taskData directory
            FileUtils.cleanDirectory(new File(tasksPath));
            
            // Parse the config file
            File systemConfig = new File(updatedSystemConfigPath);
            DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder1 = dbFactory1.newDocumentBuilder();
            Document configDoc = dBuilder1.parse(systemConfig);
            configDoc.getDocumentElement().normalize();
            parseUpdatedConfigFile(configDoc);
            
            // Parse the solution file
            File optaplannerSolution = new File(solutionPath);
            DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder2 = dbFactory2.newDocumentBuilder();
            Document solutionDoc = dBuilder2.parse(optaplannerSolution);
            solutionDoc.getDocumentElement().normalize();

            // Get xml nodes
            NodeList locationNodes = solutionDoc.getElementsByTagName("Location");
            NodeList initialLocationNodes = solutionDoc.getElementsByTagName("InitialLocation");
            NodeList autonomousVehicleNodes = solutionDoc.getElementsByTagName("AutonomousVehicle");
            
            // Create Location objects list
            for (int i = 0; i < locationNodes.getLength(); i++) {
                Node node = locationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String referenceId = element.getAttribute("id"); // we use the xml attribute, and not the xml referenceId node
                    String id = getNodeValue("id", element);
                    //double latitude = Double.parseDouble(getNodeValue("latitude", element));
                    //double longitude = Double.parseDouble(getNodeValue("longitude", element));
                    //Location location = new Location(referenceId, id, latitude, longitude);
                    //locationList.add(location);
                    String locationVarString = "location " + id;
                    locationsString = locationsString.concat("\t\t(" + locationVarString + ")\n");
                    //locationsMap.put(location, locationVarString);
                    //locationsPercentagesMap.put(id, "");
                    objectReferenceMap.put(referenceId, id);
                    
                }

            }
            
            // Create InitialLocation objects list
            for (int i = 0; i < initialLocationNodes.getLength(); i++) {
                Node node = initialLocationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String referenceId = element.getAttribute("id"); // we use the tag attribute, and not the tag field
                    String id = getNodeValue("id", element);
                    Element locationChild = (Element) element.getElementsByTagName("location").item(0);
                    String referenceLocation = locationChild.getAttribute("reference");
                    //Location loc = (Location) objectMap.get(locationReference);
                    //InitialLocation initialLocation = new InitialLocation(id, name, loc);
                    //initialLocationList.add(initialLocation);
                    objectReferenceMap.put(referenceId, id);
                    initialLocationMap.put(id, objectReferenceMap.get(referenceLocation)); //<initialLocationId, locationId>

                }

            }

            
            // Create AutonomousVehicle objects list
            for (int i = 0; i < autonomousVehicleNodes.getLength(); i++) {
                Node node = autonomousVehicleNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element autonomousVehicleElement = (Element) node;
                    String referenceId = autonomousVehicleElement.getAttribute("id"); // we use the tag attribute, and not the tag field
                    String id = getNodeValue("id", autonomousVehicleElement);
                    objectReferenceMap.put(referenceId, id);
                    //int capacity = Integer.parseInt(getNodeValue("capacity", autonomousVehicleElement));
                    Element initialLocationChild = (Element) autonomousVehicleElement.getElementsByTagName("initialLocation").item(0);
                    String initialLocationReference = initialLocationChild.getAttribute("reference");
                    String initialLocationId = objectReferenceMap.get(initialLocationReference);
                    String locationId = initialLocationMap.get(initialLocationId);
                    //InitialLocation place = (InitialLocation) objectMap.get(initialLocationReference);
                    //Vehicle uav = new Vehicle(id, name, capacity, place);
                    //autonomousVehicleList.add(uav);
                    String uavVarString = "quadrotor uav" + id; ///TODO: insert the robot type as a xml node
                    //autonomousVehicleMap.put(uav, uavVarString);                     
                    //objectMap.put(uav.getReferenceId(), uav);
                    
                    uavsStatesString = uavsStatesString.concat("\t\t; UAV" + id + " state defs\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(battery uav" + id + " 1200)\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(at uav" + id + " " + locationId + ")\n");
                    uavsStatesString = uavsStatesString.concat("\t\t(landed uav" + id + ")\n");
                    uavRWString = uavRWString.concat("\t\t; read/write times UAV" + id + " \n");
                    uavRWString = uavRWString.concat("\t\t(write-time at uav" + id + " 0)\n");
                    uavRWString = uavRWString.concat("\t\t(read-time at uav" + id + " 0)\n\n");
                    uavRWString = uavRWString.concat("\t\t(write-time battery uav" + id + " 0)\n");
                    uavRWString = uavRWString.concat("\t\t(read-time battery uav" + id + " 0)\n\n");

                    NodeList taskNodeList = autonomousVehicleElement.getElementsByTagName("nextTask");
                    if(taskNodeList.getLength() == 0){ // uav is unused
                        unusedUavsString = unusedUavsString.concat("\t\t(" + uavVarString + ")\n");
                        uavsStatesString = uavsStatesString.concat("\t\t(unused uav" + id + ")\n\n");
                                                
                        continue;
                        
                    }
                    uavsStatesString += "\n";
                    // uav is used
                    usedUavsString = usedUavsString.concat("\t\t(" + uavVarString + ")\n");
                                        
                }

            }
           
            // Generate the tasksNumber. Those tasks which have the same Task Id are considered
            // to be the same and must be reduced, which means that the percentages related
            // to each involved UAV must be computed. 
            goalsString = generateGoals(autonomousVehicleNodes);

                        
            // Generate the area division in those mapGeneration and surveillance tasks with multiple percentages.
            // In the case of not having these types of missions, do nothing and return null.
            /*
            String arrayString[] = null;
            arrayString = execute_area_partition_and_zigzag();
            */          
            // Create the PDDL file
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
            if(arrayString != null){            
                out.println("\t\t; subareas defs");
                out.println(arrayString[0]);
                out.println("\t\t; subarea assignations defs");
                out.println(arrayString[1]);
            
            }
            */
            out.println("\t\t; object state defs");
            out.println(objectsStatesString);
            out.println(dependenciesString);
            out.println("\t\t; assembly locations");
            out.println(taskLocationsString);
            out.println("\t\t; part assignments");
            out.println(taskAssignsString);
            out.println(uavsStatesString);
            out.println(uavRWString);
            out.println(objectsRWString);
            out.println("\t\t; remaining tasks");
            out.println("\t\t(remaining_tasks " + tasksNumber + ")\n");
            out.println("\t)\n");
            out.println("\t; GOALS\n\t(\n");
            //out.println(goalsString);
            out.println("\t\t(mission assemble)\n"); ///TODO: this is mission-dependant
            out.println("\t)\n)");
            out.close();
            System.out.println("JSHOP2 file saved!");
            
            
        
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException ex) {
            ex.printStackTrace();
        
        }
        
        
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
    This function returns a String representation of a xml element in a document.
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

    /*
        This function receives a taskNodeList that contains a OptaPlanner chain, and
        returns a new Task object with the information contained in the node i.
    */
    /*
    private Task getTask(NodeList taskList, int i) {
        Element task = (Element) taskList.item(i);
        Long taskReferenceId = Long.parseLong(task.getAttribute("id")); // we use the tag attribute, and not the tag field
        String taskId = getNodeValue("id", task);
        Element taskLocationChild = (Element) task.getElementsByTagName("location").item(0);
        Long taskLocationReference = Long.parseLong(taskLocationChild.getAttribute("reference"));
        Location taskLoc = (Location) objectMap.get(taskLocationReference);
        int taskDemand = Integer.parseInt(getNodeValue("demand", task));
        Task taskObject = new Task();
        taskObject.setReferenceId(taskReferenceId);
        taskObject.setId(taskId);
        taskObject.setLocation(taskLoc);
        taskObject.setDemand(taskDemand);
        
        return taskObject;

    }
    */

    /*
        This function parses the UpdatedSystemConfig.
    */
    private void parseUpdatedConfigFile(Document configDoc) {
        NodeList tasks = configDoc.getElementsByTagName("Task");
        // the number of tasksNumber is equal to the number of tasks that
        // are present in the updatedSystemConfig. But in the optaplanner
        // solution one task may appear multiple times.
        tasksNumber = tasks.getLength(); 
        for(int i=0; i<tasks.getLength(); i++){
            Task taskObject = new Task();
            Element task = (Element) tasks.item(i);
            // recover the locationId node an get its referenceId
            String locationId = getNodeAttributeValue("location", "referenceId", task);
            taskObject.setLocationId(locationId);
            // recover the task type and jshop2type
            String taskType = getNodeValue("type", task);
            taskObject.setType(taskType);
            String jshop2TaskType = getNodeValue("JSHOP2type", task);
            taskObject.setJshop2Type(jshop2TaskType);
            // recover the task referenceId (different tasks, different referenceId's)
            String taskId = getNodeValue("id", task);
            taskObject.setId(taskId);
            taskMap.put(taskId, taskObject);
            // recover the task order
            String taskOrder_ = getNodeValue("goalOrder", task);
            int taskOrder = i;
            // if goalOrder node doesn't exists, order is irrelevant so the 
            // order will be given by the appereance of tasks in the 
            // updatedSystemConfig file.
            if(!taskOrder_.equals("")){
                taskOrder = Integer.parseInt(taskOrder_);
                
            }
            taskObject.setGoalOrder(taskOrder);
            // recover the task demand
            int taskDemand = Integer.parseInt(getNodeValue("demand", task));
            taskObject.setDemand(taskDemand);
            locationTaskMap.put(locationId, taskId);
            
            // If there is a GeometricData node
            // associated to the task, then we create a xml string representation
            // for it to store in a map.
            String geometricDataString = "";
            // Retrieve the geometric data (if any)
            NodeList taskGeometricDataList = task.getElementsByTagName("GeometricData");
            if(taskGeometricDataList.getLength() > 0){ // there is a GeometricData node
                geometricDataString =  getElementAsString((Element) taskGeometricDataList.item(0));
                
            }
            taskObject.setGeometricData(geometricDataString);
            
            // Create a String document from the xml nodes in the case of
            // existing, by adding the xml header.
            if(!geometricDataString.equals("")){
                generateTaskDataFiles(taskId);
                            
            }
            
            // Retrieve the associated objectName
            NodeList taskObjectNameList = task.getElementsByTagName("objectName");
            if(taskObjectNameList.getLength() > 0){ // there is a objectName node
                String objectName = ((Element) taskObjectNameList.item(0)).getTextContent();
                taskObject.setObjectName(objectName);
                objectsString += "\t\t(object " + objectName + ")\n";
                
                // Retrieve the associated objectLocationId
                NodeList taskObjectLocationList = task.getElementsByTagName("objectLocation");
                if(taskObjectLocationList.getLength() > 0){ // there is a objectLocationId node
                    String objectLocationId = ((Element) taskObjectLocationList.item(0)).getAttribute("referenceId");
                    taskObject.setObjectLocationId(objectLocationId);
                    taskObjectLocationMap.put(objectName, objectLocationId);
                    objectsStatesString += "\t\t(at " + objectName + " " + objectLocationId  + ")\n";
                    objectsRWString += "\t\t; read/write times " + objectName + "\n";
                    objectsRWString += "\t\t(write-time at " + objectName + " 0)\n";
                    objectsRWString += "\t\t(read-time at " + objectName + " 0)\n\n";
                    taskLocationsString += "\t\t(assembly_location " + objectName + " " + locationId + ")\n"; ///TODO: this is mission-dependant
                    
                }
                                
            }
            
        }
        dependenciesString = getTasksDependencies(tasks);
    
    }

    /*
    This function is only used when the tasks have associated a polygon and/or
    its forbidden areas, to generate a xml document containing the polygon as
    well as a text file containing the utm coordinates of the polygon. The
    taskId argument is the xml referenceId node of the locationId where the task has to be
    done.
    */
    private void generateTaskDataFiles(String taskId) {
        PrintWriter xml_out = null;
        
        try {
            File dir = new File(tasksPath + "task_" + taskId);
            dir.mkdir();
            String xmlFilename = tasksPath + "task_" + taskId + "/task_" + taskId + ".xml";
            String txtFilename = tasksPath + "task_" + taskId + "/polygon_" + taskId + ".txt";
            xml_out = new PrintWriter(xmlFilename);
            //xml_out.println(taskGeometricDataMap.get(taskId));
            xml_out.println(taskMap.get(taskId).getGeometricData());
            xml_out.close();
            
            // parse the xml and generate the 
            generatePolygonFile(xmlFilename, txtFilename);
                        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptaPlannerSolutionParser.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            xml_out.close();
            
        }
                        
    }

    private void generatePolygonFile(String xmlFilename, String txtFilename) {
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

    private void printPoints(NodeList pointsList, PrintWriter txt_out) {
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
    /*
    private String[] execute_area_partition_and_zigzag() {
        String subareaString = "";
        String unassignedString = "";
        
        // Iterate over all tasks to try to execute the area partition and zig zag if it's the case
        Iterator it = locationList.iterator();
        while(it.hasNext()){
            String locationId = ((Location) it.next()).getName();
        //for (Map.Entry<String, String> entry : taskTypesMap.entrySet()) {
            //String locationId = entry.getKey();
            //String task = entry.getNodeValue();
            //String mission = taskTypesMap.get(locationId);
            String mission = taskTypesMap.get(locationId); ///TODO: correct this
            String filename = tasksPath + "location_" + locationId + "/polygon_" + locationId + ".txt";
            if(mission.contains("mapGeneration") || mission.contains("surveillance")){
                String prctg = locationsPercentagesMap.get(locationId);
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
                        //System.out.println("Area partition return value for locationId" + locationId + ": " + res);
                        
                        
                        for(int i=0; i<percentages.length; i++){
                            String subfilename = tasksPath + "location_" + locationId + "/polygon_" + locationId + "_subarea" + i + "_" + percentages[i] + ".txt";
                            rt = Runtime.getRuntime();
                            execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_zig_zag/area_zig_zag" + " " + subfilename;
                            System.out.println("Execution string: " + execString);
                            ps = rt.exec(execString);
                            ps.waitFor();
                            //res = ps.exitValue();
                            String resultFile = new StringBuilder(subfilename).insert(subfilename.length()-4, "_distance").toString();
                            Scanner scanner = new Scanner(new File(resultFile)).useLocale(Locale.US);
                            res = (int) Math.ceil(scanner.nextDouble());
                            
                            System.out.println("Zigzag return value for location" + locationId + " subarea" + i + ": " + res);
                            subareaString = subareaString.concat("\t\t(subarea " + locationId + " " + i + " " + percentages[i] + " " + res + ")\n");
                            unassignedString = unassignedString.concat("\t\t(unassigned " + locationId + " " + i + ")\n");
                            
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
                        //System.out.println("Area partition return value for locationId" + locationId + ": " + res);
                                             
                        
                        rt = Runtime.getRuntime();
                        execString = "/home/jorge/Escritorio/SystemPlanner/bin/area_zig_zag/area_zig_zag" + " " + filename;
                        System.out.println("Execution string: " + execString);
                        ps = rt.exec(execString);
                        ps.waitFor();
                        //res = ps.exitValue();
                        
                        String resultFile = new StringBuilder(filename).insert(filename.length()-4, "_distance").toString();
                        Scanner scanner = new Scanner(new File(resultFile)).useLocale(Locale.US);
                        res = (int) Math.ceil(scanner.nextDouble());
                        System.out.println("Zigzag return value for location" + locationId + ": " + res);
                        subareaString = subareaString.concat("\t\t(subarea " + locationId + " " + 0 + " " + 100 + " " + res + ")\n");
                        unassignedString = unassignedString.concat("\t\t(unassigned " + locationId + " " + 0 + ")\n");

                    }
                } catch(IOException | InterruptedException exception) {
                            exception.printStackTrace();

                }
                
            }

        }
        
        String arrayString[] = {subareaString, unassignedString}; 
        
        return arrayString;
        
    }
    */

    // Here we retrieve the dependencies for the object. Due to the specific JSHOP2 domain
    // for the arcas, the task dependencies are mapped to part dependencies. Also,
    // the Task dependencies are only looked when there is a objectName node. All this reduces
    // the independency of the missions.
    /// TODO: change the JSHOP2 model to accept the dependencies at the level of tasks (Task Id) and not
    // at the level of objects (objectName). This implies some changes for this parser.
    private String getTasksDependencies(NodeList tasks) {
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
                        String taskDependencyObjectName = taskMap.get(taskDependencyId).getObjectName();
                        depend += taskDependencyObjectName + " ";

                    }
                    dependencies += depend.substring(0, depend.length()-1) + "))\n";


                } else{ // no dependencies, but an empty list must appear in the JSHOP2 problem
                    dependencies += "\t\t(depends " + taskObjectName + " ())\n";
                    
                }

            }

        }
        return dependencies;
        
    }

    private String generateGoals(NodeList autonomousVehicleNodes) {
        String returnString = "";
        Map<Integer, List> goalsMap = new HashMap(tasksNumber);
        for(int i=0; i<tasksNumber; i++){
            goalsMap.put(i, new ArrayList()); //<taskId, List>, the taskId go from 0 to taskNumber-1
            
        }
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
                String uavName = "uav" + vehicleId;
                String partName = getNodeValue("objectName", nextTask);
                taskAssignsString += "\t\t(assigned " + partName + " " + uavName + ")\n"; ///TODO: this is mission-dependant
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
                double percentage = (((double) spentDemand)/taskMap.get(taskId).getDemand())*100;
                //System.out.println("TaskId: " + taskId + " SpentDemand: " + spentDemand);
                // create the goal
                String type = taskMap.get(taskId).getType() + " ";
                String jshopType = taskMap.get(taskId).getJshop2Type() + " ";
                String uavName = "uav" + vehicleId + " ";
                String location = taskMap.get(taskId).getLocationId() + " ";
                String objectName = taskMap.get(taskId).getObjectName();
                if(objectName == null){
                    objectName = "";
                    
                }else{
                    objectName += " ";
                    
                }
                int prctg = (int) percentage;
                String goal = "\t\t(" + jshopType + type + uavName + location + objectName + prctg + ")\n";
                int goalOrder = taskMap.get(taskId).getGoalOrder();
                List addList = goalsMap.get(goalOrder);
                addList.add(goal);
                
            }
            
        }
        
        for(int i=0; i<tasksNumber; i++){
            List goalList = goalsMap.get(i);
            Iterator it = goalList.iterator();
            while(it.hasNext()){
                String goal = (String) it.next();
                returnString += goal;
                
            }
            
        }
                
        return returnString;
        
    }
    
}
