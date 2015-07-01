/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.vehiclerouting.solver.score;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

//import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.Standstill;
import org.optaplanner.examples.vehiclerouting.domain.TaskDependency;

public class VehicleRoutingEasyScoreCalculator implements EasyScoreCalculator<VehicleRoutingSolution> {
    
    @Override
    public HardMediumSoftScore calculateScore(VehicleRoutingSolution schedule) {
        //System.out.println("-----------------------------------------------------");
        
        
        List<Task> taskList = schedule.getTaskList(); // The list of all tasks that must be done (parts to assemble in this case)
        
        List<Vehicle> vehicleList = schedule.getVehicleList(); // The list of all vehicles present in the mission
        
        Map<Vehicle, Integer> vehicleNumBasesMap = new HashMap(vehicleList.size()); // the number of bases assigned for each vehicle
        
        Map<Vehicle, List> vehicleTasksMap = new HashMap(vehicleList.size()); // the tasks assigned to each vehicle
        
        Set<TaskDependency> dependencySet = new TreeSet();

        int roofTasks = 0;
        
        /*
            First we initialize the vehicleNumBasesMap to all zeros.
        */
        int numBases = 0;
        for (Vehicle vehicle : vehicleList) {
            vehicleNumBasesMap.put(vehicle, 0);
            
        }
        
        /*
            Initialize all the scores to zero.
        */
        int hardScore = 0;
        int mediumScore = 0;
        int softScore = 0;
        
        /*
            This loop is to calculate the softscore produced by navigating
            between locations.
        */
        for (Task task : taskList) {
            Standstill previousStandstill = task.getPreviousStandstill(); // The previous task or vehicle
            if (previousStandstill != null) { 
                // update the demand for the vehicle
                Vehicle vehicle = task.getVehicle();
                int capacity = vehicle.getCapacity();
                int demand = task.getDemand();
                // HARD-CONSTRAINT: a vehicle can only lift parts with weight lower or equal to its capacity
                if(demand > capacity){ // if part weight is greater than load capacity
                    hardScore -= 1;
                    
                }
                // Create the list of tasks for the vehicle, if it doesn't exists.
                if(!vehicleTasksMap.containsKey(vehicle)){ 
                    vehicleTasksMap.put(vehicle, new ArrayList(100));
                    
                    
                }

                /*
                    The name TaskDependency is not correct. The taskToAdd var
                    represents a task that is assigned to a vehicle, and thus is added to
                    the task list for the vehicle. However, we reuse the TaskDependency
                    class because its really the same as the Task class but with less
                    attributes and methods.
                */
                TaskDependency taskToAdd = new TaskDependency();
                taskToAdd.setId(task.getId());
                vehicleTasksMap.get(vehicle).add(taskToAdd);
                if(task.getPriority() == 1) { // If the task is a base, we update the vehicleNumBasesMap.
                    vehicleNumBasesMap.put(vehicle, vehicleNumBasesMap.get(vehicle) + 1);
                    numBases++;
                    
                }
                
                /*
                    Now we add to  dependencySet all the pieces that are dependencies for this piece. This will
                    help us to divide the pieces that are not dependent in a more balanced way.
                */
                
                dependencySet.addAll(task.getPreconditionList());
                
                // Here comes the travelling cost calculation. We can comment it for now.
                /*
                if (task.getNextTask() == null) { // Last task
                    softScore -= task.getLocation().getDistance(vehicle.getLocation()); // Add distance from assembly location to vehicle's initial location
                    
                }
                
                if(previousStandstill instanceof Task){ // The task have a preceding task
                    Task previousTask = (Task) previousStandstill;
                    softScore -= previousTask.getLocation().getDistance(task.getObjectLocation()); // Add distance from prev assembly location to part
                    softScore -= task.getObjectLocation().getDistance(task.getLocation()); // Add distance from part to assembly location
                    
                } else{ // First task
                    softScore -= vehicle.getLocation().getDistance(task.getObjectLocation()); // Add distance from vehicle to object
                    softScore -= task.getObjectLocation().getDistance(task.getLocation()); // Add distance from object to assembly location
                    
                }
                */             
                
            }
        }
        /*
        Set<TaskDependency> seti = new HashSet();
        TaskDependency t1 = new TaskDependency();
        t1.setId((long)8);
        TaskDependency t2 = new TaskDependency();
        
        t2.setId((long)9);
        System.out.println("Son iguales?: " + t1.equals(t2));
        System.out.println("Son compares?: " + t1.compareTo(t2));
        t2.setId((long)8);
        System.out.println("Son iguales?: " + t1.equals(t2));
        System.out.println("Son compares?: " + t1.compareTo(t2));
        
        t1.setId((long) 10);
        t2.setId((long) 10);
        seti.add(t1);
        seti.add(t1);
        System.out.println("SET SIZE: " + seti.size());
        seti.add(t2);
        System.out.println("SET SIZE: " + seti.size());
        */
        
        // Now the concept of demand is different than that of the older missions
        // so we comment this piece of code.
        /*
        for (Map.Entry<Vehicle, Integer> entry : vehicleDemandMap.entrySet()) {
            int capacity = entry.getKey().getCapacity();
            int demand = entry.getValue();
            if (demand > capacity) {
                // Score constraint vehicleCapacity
                hardScore -= (demand - capacity);
            }
        }
        */
        
        /*
            Mediumscore related with parallelism, applied only if more than
            one vehicle.
            MEDIUMCONSTRAINT: given a piece and its dependencies, they must be assigned 
            to disctint vehicles if possible.
        */
        int numVehicles = vehicleList.size();
        Set<Long> checkedTaskSet = new TreeSet(); // To avoid discretized tasks to duplicate the penalties.
        if(numVehicles > 1){ // Parallelism constraints are only applied if multiple vehicles are present.
            for (Task aTask : taskList) { // For each task, we check the preconditions.
                List<TaskDependency> preconditionTasks = aTask.getPreconditionList();
                int numPreconditions = preconditionTasks.size();
                if(checkedTaskSet.contains(aTask.getId())){ // to avoid re-checking task with same Id (discretized tasks)
                    continue;
                    
                }
                
                checkedTaskSet.add(aTask.getId());
                /*
                    A task that only depends on another task can be assigned
                    to the same UAV because assigning it to another doesn't 
                    increase parallelism. For so, for each task
                    we check if it has more than one precondition.
                */
                if(numPreconditions > 1){
                    //System.out.println("PARALLELISM HARD-CONSTRAINTS FOR TASK ID: " + aTask.getId());
                    //System.out.println("Number of preconditions: " + numPreconditions);
                    /*
                        max is the maximum number of preconditions that can be assigned for each vehicle
                        to produce a balanced distribution.
                    */
                    double max = Math.ceil(((double)numPreconditions) / numVehicles); 
                    int maxAssigments = (int) max; // cast to int
                    /*
                    Iterate over each vehicle to see the assigned tasks
                    and check the number of preconditions for this task
                    that they have assigned. If it surpass maxAssigments, then a
                    hard-constraint penalty will be applyed.
                     */
                    for (Vehicle specificVehicle : vehicleList) {
                        List<TaskDependency> assignedTasks = vehicleTasksMap.get(specificVehicle);
                        if(assignedTasks == null){ // The vehicle is unused.
                            continue;
                            
                        }
                        List<TaskDependency> assignedTasksCopy = new ArrayList(assignedTasks);
                        assignedTasksCopy.retainAll(preconditionTasks); // Intersection to see the number of assigned preconditions
                        if(assignedTasksCopy.isEmpty()){ // The vehicle has no assigned precondition tasks, so no penalty.
                            continue;
                            
                        }
                        int numAssignedPreconditions = assignedTasksCopy.size();
                        if(numAssignedPreconditions > maxAssigments){
                            mediumScore -= (numAssignedPreconditions - maxAssigments);
                            /*
                            System.out.println("VEHICLE ID: " + specificVehicle.getId() + " TASK ID: " + aTask.getId());
                            System.out.println("PARALLELISM PENALTY: " + (numAssignedPreconditions - maxAssigments));
                            for(int y=0; y<assignedTasksCopy.size(); y++){
                            System.out.println("---PART ID: " + assignedTasksCopy.get(y).getId());
                            
                            }
                            */
                            //System.out.println("NUM ASSIGNED PRECONDITIONS: " + numAssignedPreconditions);
                            //System.out.println("MAX ASSIGNED PRECONDITIONS: " + maxAssigments);
                            
                        }
                    }
                    
                } 
            
            }
            
        }
        
        /*
            Iterate over the tasks to get the number of tasks that are roof.
            Discretized tasks are allowed (must be counted).
        */
        
        for(Task task: taskList){
            TaskDependency t = new TaskDependency();
            t.setId(task.getId());
            if(!dependencySet.contains(t)){
                roofTasks++;
                
            }
            
        }
        //System.out.println("Number of roofs: " + roofTasks);
        
        
        /*
        System.out.println("---------------");
        System.out.println("dependencySet size: " + dependencySet.size());
        Iterator iter = dependencySet.iterator();
        while (iter.hasNext()) {
            TaskDependency t = (TaskDependency) iter.next();
            System.out.println("Normal piece id: " + t.getId());
            
        }
        System.out.println("---------------");
        */
        
        /*
            Hard-score related with parallelism, applied only if more than
            one vehicle.
            HARD-CONSTRAINT: given a roof piece, it must be assigned 
            in a balanced way.
        */
        
        if(numVehicles > 1){ // Parallelism constraints are only applied if multiple vehicles are present.
            double max = Math.ceil(((double)roofTasks) / numVehicles); 
            int maxAssigments = (int) max; // cast to int
            for (Vehicle vehicle : vehicleList) { // For each vehicle, we count the tasks that not depend on other
                List<TaskDependency> assignedTasks = vehicleTasksMap.get(vehicle);
                if(assignedTasks == null){
                    break;
                    
                }
                int localRoofTasks = 0;
                for(TaskDependency task: assignedTasks){
                    if(!dependencySet.contains(task)){
                        localRoofTasks++;
                        
                    }
                    
                }
                if(roofTasks >= vehicleList.size()) {
                    // Hard-constraint: if the number of roofs is equal or greater
                    // than the number of vehicles, each vehicle must have assigned 
                    // one roof at least and maxAssignments as max
                    if(localRoofTasks == 0){ // Penalty: one vehicle without any roof assigned.
                        hardScore -= 1;

                    } else if(localRoofTasks > maxAssigments){
                        hardScore -= (localRoofTasks - maxAssigments);
                        
                    }
                
                } else{
                    // Hard-constraint: if the number of roofs is less than the
                    // number of vehicles, each roof must be assigned to a distinct
                    // vehicle
                    if(localRoofTasks > 1){ // Penalty: a vehicle has more than one roof assigned.
                        hardScore -= (localRoofTasks - 1);

                    }

                }
                
            }           
            
        }
        
        
        /*
            Hardscore related with the bases asignment.
            HARDCONSTRAINT: if the number of bases is equal or greater than the
            number of vehicles, then each vehicle must have assigned at least
            one base. If the number of bases is less than the number of vehicles,
            then each vehicle must have only one base as maximum.
        */
        for (Map.Entry<Vehicle, Integer> entry : vehicleNumBasesMap.entrySet()) {
            int assigned = entry.getValue();
            if(numBases >= vehicleList.size()) {
                // Hard-constraint: if the number of bases is equal or greater
                // than the number of vehicles, each vehicle must have assigned 
                // one base at least
                if(assigned == 0){ // Penalty: one vehicle without any base assigned.
                    hardScore -= 1;
                    
                }
                
            } else{
                // Hard-constraint: if the number of bases is less than the
                // number of vehicles, each base must be assigned to a distinct
                // vehicle
                if(assigned > 1){ // Penalty: a vehicle has more than one base assigned.
                    hardScore -= (assigned - 1);
                    
                }
                
            }

        }
        
        /*
            Some tasks have the same id, meaning that they have been discretized.
            This means that the pieces will be carried among multiple UAVs.
            So tasks with the same id have to be assigned to different vehicles.
        */
        for (Map.Entry<Vehicle, List> entry : vehicleTasksMap.entrySet()) {
            List list = entry.getValue();
            Set<Long> set = new TreeSet();
            Iterator it = list.iterator();
            while(it.hasNext()){
                TaskDependency t = (TaskDependency) it.next(); // They are not TaskDependency, are Task but we are using the TaskDependency class for simplicity. They mean the same.
                Long id = t.getId();
                if(set.contains(id)){
                    hardScore -= 1; // One penalty for each repeated task.
                    
                }else{
                    set.add(id);
                    
                }
                
            }

        }
        
        // old JSHOP2 communication
        softScore -= generateAssignmentFile(taskList, vehicleList, hardScore, mediumScore);        
        
        // new JSHOP2 communication
        /*
        cd examples/quadrotor_arcas; java JSHOP2.InternalDomain quadrotor
	cd examples/quadrotor_arcas; java JSHOP2.InternalDomain -r problem
	cd examples/quadrotor_arcas; javac gui.java
	cd examples/quadrotor_arcas; java -Xmx256M gui
	cd examples/quadrotor_arcas; rm quadrotor.java; rm quadrotor.txt; rm problem.java; rm *.class
        */
        /*
        try{
            String[] domainArgs = {"/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/quadrotor"};
            String[] problemArgs = {"-r", "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem"};
            System.out.println("Calling InternalDomain.main twice");
            InternalDomain.main(domainArgs);
            InternalDomain.main(problemArgs);
            
            //Compile the new class
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            File javaDirectory = new File("/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/");
            File javaFile = new File("/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/Ceil.java");
            compiler.run(null, null, null, javaFile.getPath());
            
            // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { javaDirectory.toURI().toURL() });
            Class<?> cls = Class.forName("problem", true, classLoader); // Should print "hello".
            Object instance = cls.newInstance();
            System.out.println(instance); // Should print "test.Test@hashcode".
            

            
        } catch(Exception e)
        {
            System.out.println("Exception thrown calling JSHOP2");
            
        }
        softScore = -500;
        */
                
        //System.out.println("HARD: " + hardScore);
        //System.out.println("MEDIUM: " + mediumScore);
        //System.out.println("SOFT: " + softScore);
        //System.out.println("TASKS: " + schedule.getTaskList().size());
        //System.out.println("FACTS: " + schedule.getProblemFacts().size());
        return HardMediumSoftScore.valueOf(hardScore, mediumScore, softScore);
        
    }
    
    /*
        Method to generate the softScore value by calling the JSHOP2 process.
    */
    public int generateAssignmentFile(List<Task> taskList, List<Vehicle> vehicleList, int hardScore, int mediumScore)
    {
        if(hardScore != 0)
        {
            return Integer.MAX_VALUE;
                
        } 
        
        int softScore = Integer.MAX_VALUE;
        
        // Set the assignment strings for each of the vehicles (set to empty string).
        String[] assignmentStrings = new String[vehicleList.size()];
        for(int i=0; i<assignmentStrings.length; i++)
        {
            assignmentStrings[i] = "";
            
        }
                
        // Complete the assignment strings (set the objectName part).
        int assignedTasks = 0;
        for (Task task : taskList) {
            Standstill previousStandstill = task.getPreviousStandstill(); // The previous task or vehicle.
            if (previousStandstill != null) { // It is a task.
                assignedTasks++;
                // Get the vehicle for the task.
                Long vehicleId = task.getVehicle().getId();
                int index = vehicleId.intValue();
                String objectName = task.getObjectName();  
                String assignment = "\t\t(assigned " + objectName + " " + "uav" + index + ")\n";
                assignmentStrings[index-1] += assignment; // Zero-based index.                
                
            }
        }
        
        // Finish the assignment strings (set the uav part).
        String assignmentPart = "\t\t; part assignments\n";
        for(int i=0; i<assignmentStrings.length; i++)
        {
            assignmentPart += assignmentStrings[i];
            
        }
        
        if(assignedTasks == taskList.size()) { 
            /* 
                All planning entities assigned, call JSHOP2 and take
                the cost of the plan as the soft-constraint value. The 
                JSHOP2 cost is the time duration of the plan.
            */
            
            try {
                // Read the different problem parts as strings and create plan.
                String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
                String assemblyPlannerPart = FileUtils.readFileToString(new File(path + "assembly_planner_part.txt"), "UTF8");
                String firstProblemPart = FileUtils.readFileToString(new File(path + "first_problem_part.txt"), "UTF8");
                String secondProblemPart = FileUtils.readFileToString(new File(path + "second_problem_part.txt"), "UTF8");
                String jshopPlan = firstProblemPart + assemblyPlannerPart + assignmentPart + secondProblemPart;
                
                // Generate plan file
                path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/";
                File file = new File("/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem");
                file.delete();
                PrintWriter out = new PrintWriter(path + "problem");
                out.println(jshopPlan);
                out.close();
                
                // Call the JSHOP2 process.
                String execString = "bash " + System.getProperty("user.home") + "/launch_jshop2.sh";
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec(execString);
                // Wait for JSHOP2 to finish.
                process.waitFor();

                // Check if the cost file (planCost.txt) was created.
                String costFilePath = System.getProperty("user.home") + "/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/planCost.txt";
                File costFile = new File(costFilePath);
                if(costFile.isFile()) {
                    String cost = FileUtils.readFileToString(costFile, "UTF8");
                    //System.out.println(cost);
                    if(cost.contains("unfeasible"))
                    {
                        return Integer.MAX_VALUE;
                        
                    }else {
                        return (int) Double.parseDouble(cost);
                        
                    }
                    

                }else {
                    System.out.println("Missing cost file. Some error occurred!.");
                    System.out.println("Cost file path: " + costFilePath);
                    return Integer.MAX_VALUE;
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(VehicleRoutingEasyScoreCalculator.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(VehicleRoutingEasyScoreCalculator.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        } else {
            /*
                Not all planning entities have been assigned, for the moment
                we decide not to call JSHOP2 because it will return a void
                plan (unfeasible due to not having all the part assignments).
                Thus, do not spend time in the call and set an inifinite soft
                score value.
            */
            return Integer.MAX_VALUE;
            
        }
        
        return softScore;
        
    }
    

}
