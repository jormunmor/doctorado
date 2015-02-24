/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jshopganttviewer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.axis.DateAxis;

/**
 *
 * @author jorge
 */
public class JShopPlanParser {
    public static List parsePlan(String fileUrl) throws FileNotFoundException{
        List list = new ArrayList();
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileUrl));
            String line = br.readLine();
            while (line != null) {
                int exclamationIndex = line.indexOf('!');
                line = line.substring(exclamationIndex + 1, line.length()-1);
                list.add(line);
                line = br.readLine();

            }
            br.close();
            
        } catch(IOException ex){
            Logger.getLogger(JShopPlanParser.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return list;
        
    }
    
    /*
        Be carefull with this function: it's code is highly dependant on the format used
        in the JShop2 domain. It expects that the vehicles contains the string 'uav' in their
        names, and that this sequence is not included as part of the name of the missions.
        The position of the vehicle name is dependant too of the type of mission.
    */
    public static Map<String, List> getTaskLists(List planTasks){
        // create a Map that contains the list of tasks for each UAV
        Map<String, List> map = new HashMap(); // uavName->taskList
        ListIterator it = planTasks.listIterator();
        while(it.hasNext()){
            String task = (String) it.next();
            String[] taskDecomposition = task.split(" ");
            String uavName = taskDecomposition[1].contains("uav")? taskDecomposition[1] : taskDecomposition[2];
            if(!map.containsKey(uavName)){ // check if uav in the map
                map.put(uavName, new ArrayList()); // if not, add it

            }
            map.get(uavName).add(task);

        }
        
        Map<String, List> finalMap = new TreeMap(); 
        SortedSet<String> orderedSet = new TreeSet<>(map.keySet());
        Object[] objectArray = orderedSet.toArray();
        List orderedList = Arrays.asList(objectArray);
        Collections.reverse(orderedList);
        it = orderedList.listIterator();
        while(it.hasNext()){
            String key = (String) it.next();
            finalMap.put(key, map.get(key));

        }

        return finalMap;
        
    } 
    
}
