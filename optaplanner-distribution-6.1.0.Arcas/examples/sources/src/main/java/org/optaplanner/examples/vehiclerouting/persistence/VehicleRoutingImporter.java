/*
 * Copyright 2012 JBoss Inc
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

package org.optaplanner.examples.vehiclerouting.persistence;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.InitialLocation;
import org.optaplanner.examples.vehiclerouting.domain.Location;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedCustomer;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedDepot;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedVehicleRoutingSolution;

public class VehicleRoutingImporter extends AbstractTxtSolutionImporter {

    public static void main(String[] args) {
        VehicleRoutingImporter importer = new VehicleRoutingImporter();
        importer.convert("capacitated/A-n33-k6.vrp", "cvrp-32customers.xml");
        importer.convert("capacitated/A-n55-k9.vrp", "cvrp-54customers.xml");
        importer.convert("capacitated/F-n72-k4.vrp", "cvrp-72customers.xml");
        importer.convert("timewindowed/Solomon_025_C101.vrp", "cvrptw-25customers.xml");
        importer.convert("timewindowed/Solomon_100_R101.vrp", "cvrptw-100customers-A.xml");
        importer.convert("timewindowed/Solomon_100_R201.vrp", "cvrptw-100customers-B.xml");
        importer.convert("timewindowed/Homberger_0400_R1_4_1.vrp", "cvrptw-400customers.xml");
    }

    public VehicleRoutingImporter() {
        super(new VehicleRoutingDao());
    }

    public VehicleRoutingImporter(boolean withoutDao) {
        super(withoutDao);
    }

    @Override
    public String getInputFileSuffix() {
        return VehicleRoutingFileIO.FILE_EXTENSION;
    }

    public TxtInputBuilder createTxtInputBuilder() {
        return new VrpScheduleInputBuilder();
    }

    public static class VrpScheduleInputBuilder extends TxtInputBuilder {

        private VehicleRoutingSolution schedule;

        private int locationListSize;
        private int vehicleListSize;
        private int capacity;
        private Map<Long, Location> locationMap;
        private List<InitialLocation> initialLocationList;

        @Override
        public Solution readSolution() throws IOException {
            String firstLine = readStringValue();
            if (firstLine.trim().startsWith("NAME :")) {
                schedule = new VehicleRoutingSolution();
                schedule.setId(0L);
                schedule.setName(removePrefixSuffixFromLine(firstLine, "NAME :", ""));
                readBasicFormat();
            } else if (splitBySpace(firstLine).length == 3) {
                schedule = new VehicleRoutingSolution();
                schedule.setId(0L);
                String[] tokens = splitBySpace(firstLine, 3);
                locationListSize = Integer.parseInt(tokens[0]);
                vehicleListSize = Integer.parseInt(tokens[1]);
                capacity = Integer.parseInt(tokens[2]);
                readCourseraFormat();
            } else {
                schedule = new TimeWindowedVehicleRoutingSolution();
                schedule.setId(0L);
                schedule.setName(firstLine);
                readTimeWindowedFormat();
            }
            BigInteger possibleSolutionSize
                    = factorial(locationListSize + vehicleListSize - 1).divide(factorial(vehicleListSize - 1));
            logger.info("VehicleRoutingSolution {} has {} depots, {} vehicles and {} customers with a search space of {}.",
                    getInputId(),
                    schedule.getInitialLocationList().size(),
                    schedule.getVehicleList().size(),
                    schedule.getTaskList().size(),
                    getFlooredPossibleSolutionSize(possibleSolutionSize));
            return schedule;
        }

        // ************************************************************************
        // CVRP normal format. See http://neo.lcc.uma.es/vrp/
        // ************************************************************************

        public void readBasicFormat() throws IOException {
            readBasicHeaders();
            readBasicLocationList();
            readBasicTaskList();
            readBasicInitialLocationList();
            createBasicVehicleList();
            readConstantLine("EOF");
        }

        private void readBasicHeaders() throws IOException {
            readUntilConstantLine("TYPE : CVRP");
            locationListSize = readIntegerValue("DIMENSION :");
            String edgeWeightType = readStringValue("EDGE_WEIGHT_TYPE :");
            if (!edgeWeightType.equalsIgnoreCase("EUC_2D")) {
                // Only Euclidean distance is implemented in Location.getDistance(Location)
                throw new IllegalArgumentException("The edgeWeightType (" + edgeWeightType + ") is not supported.");
            }
            capacity = readIntegerValue("CAPACITY :");
        }

        private void readBasicLocationList() throws IOException {
            readConstantLine("NODE_COORD_SECTION");
            List<Location> locationList = new ArrayList<Location>(locationListSize);
            locationMap = new HashMap<Long, Location>(locationListSize);
            for (int i = 0; i < locationListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpace(line.trim().replaceAll(" +", " "), 3);
                Location location = new Location();
                location.setId(Long.parseLong(lineTokens[0]));
                location.setX(Double.parseDouble(lineTokens[1]));
                location.setY(Double.parseDouble(lineTokens[2]));
                if (lineTokens.length >= 4) {
                    location.setName(lineTokens[3]);
                }
                locationList.add(location);
                locationMap.put(location.getId(), location);
            }
            schedule.setLocationList(locationList);
        }

        private void readBasicTaskList() throws IOException {
            readConstantLine("DEMAND_SECTION");
            List<Task> taskList = new ArrayList<Task>(locationListSize);
            for (int i = 0; i < locationListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpace(line.trim().replaceAll(" +", " "), 2);
                Task customer = new Task();
                long id = Long.parseLong(lineTokens[0]);
                customer.setId(id);
                Location location = locationMap.get(id);
                if (location == null) {
                    throw new IllegalArgumentException("The customer with id (" + id
                            + ") has no location (" + location + ").");
                }
                customer.setLocation(location);
                int demand = Integer.parseInt(lineTokens[1]);
                customer.setDemand(demand);
                // Notice that we leave the PlanningVariable properties on null
                // Do not add a task that has no demand
                if (demand != 0) {
                    taskList.add(customer);
                }
            }
            schedule.setTaskList(taskList);
        }

        private void readBasicInitialLocationList() throws IOException {
            readConstantLine("DEPOT_SECTION");
            initialLocationList = new ArrayList<InitialLocation>(locationListSize);
            long id = readLongValue();
            while (id != -1) {
                InitialLocation depot = new InitialLocation();
                depot.setId(id);
                Location location = locationMap.get(id);
                if (location == null) {
                    throw new IllegalArgumentException("The depot with id (" + id
                            + ") has no location (" + location + ").");
                }
                depot.setLocation(location);
                initialLocationList.add(depot);
                id = readLongValue();
            }
            schedule.setInitialLocationList(initialLocationList);
        }

        private void createBasicVehicleList() throws IOException {
            String inputFileName = inputFile.getName();
            if (inputFileName.toLowerCase().startsWith("tutorial")) {
                vehicleListSize = readIntegerValue("VEHICLES :");
            } else {
                String inputFileNameRegex = "^.+\\-k(\\d+)\\.vrp$";
                if (!inputFileName.matches(inputFileNameRegex)) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") does not match the inputFileNameRegex (" + inputFileNameRegex + ").");
                }
                String vehicleListSizeString = inputFileName.replaceAll(inputFileNameRegex, "$1");
                try {
                    vehicleListSize = Integer.parseInt(vehicleListSizeString);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("The inputFileName (" + inputFileName
                            + ") has a vehicleListSizeString (" + vehicleListSizeString + ") that is not a number.", e);
                }
            }
            createVehicleList();
        }

        private void createVehicleList() {
            List<Vehicle> vehicleList = new ArrayList<Vehicle>(vehicleListSize);
            long id = 0;
            for (int i = 0; i < vehicleListSize; i++) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(id);
                id++;
                vehicle.setCapacity(capacity);
                vehicle.setDepot(initialLocationList.get(0));
                vehicleList.add(vehicle);
            }
            schedule.setVehicleList(vehicleList);
        }

        // ************************************************************************
        // CVRP coursera format. See https://class.coursera.org/optimization-001/
        // ************************************************************************

        public void readCourseraFormat() throws IOException {
            List<Location> locationList = new ArrayList<Location>(locationListSize);
            initialLocationList = new ArrayList<InitialLocation>(1);
            List<Task> taskList = new ArrayList<Task>(locationListSize);
            locationMap = new HashMap<Long, Location>(locationListSize);
            for (int i = 0; i < locationListSize; i++) {
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpace(line.trim().replaceAll(" +", " "), 3);
                Location location = new Location();
                location.setId((long) i);
                location.setX(Double.parseDouble(lineTokens[1]));
                location.setY(Double.parseDouble(lineTokens[2]));
                if (lineTokens.length >= 4) {
                    location.setName(lineTokens[3]);
                }
                locationList.add(location);
                if (i == 0) {
                    InitialLocation initialLocation = new InitialLocation();
                    initialLocation.setId((long) i);
                    initialLocation.setLocation(location);
                    initialLocationList.add(initialLocation);
                } else {
                    Task task = new Task();
                    task.setId((long) i);
                    task.setLocation(location);
                    int demand = Integer.parseInt(lineTokens[0]);
                    task.setDemand(demand);
                    // Notice that we leave the PlanningVariable properties on null
                    // Do not add a task that has no demand
                    if (demand != 0) {
                        taskList.add(task);
                    }
                }
            }
            schedule.setLocationList(locationList);
            schedule.setInitialLocationList(initialLocationList);
            schedule.setTaskList(taskList);
            createVehicleList();
        }

        // ************************************************************************
        // CVRPTW normal format. See http://neo.lcc.uma.es/vrp/
        // ************************************************************************

        public void readTimeWindowedFormat() throws IOException {
            readTimeWindowedHeaders();
            readTimeWindowedDepotAndCustomers();
            createVehicleList();
        }

        private void readTimeWindowedHeaders() throws IOException {
            readEmptyLine();
            readConstantLine("VEHICLE");
            readConstantLine("NUMBER     CAPACITY");
            String[] lineTokens = splitBySpacesOrTabs(readStringValue(), 2);
            vehicleListSize = Integer.parseInt(lineTokens[0]);
            capacity = Integer.parseInt(lineTokens[1]);
            readEmptyLine();
            readConstantLine("CUSTOMER");
            readRegexConstantLine("CUST\\s+NO\\.\\s+XCOORD\\.\\s+YCOORD\\.\\s+DEMAND\\s+READY\\s+TIME\\s+DUE\\s+DATE\\s+SERVICE\\s+TIME");
            readEmptyLine();
        }

        private void readTimeWindowedDepotAndCustomers() throws IOException {
            String line = bufferedReader.readLine();
            int locationListSizeEstimation = 25;
            List<Location> locationList = new ArrayList<Location>(locationListSizeEstimation);
            initialLocationList = new ArrayList<InitialLocation>(1);
            TimeWindowedDepot depot = null;
            List<Task> taskList = new ArrayList<Task>(locationListSizeEstimation);
            boolean first = true;
            while (line != null && !line.trim().isEmpty()) {
                String[] lineTokens = splitBySpacesOrTabs(line.trim(), 7);
                long id = Long.parseLong(lineTokens[0]);

                Location location = new Location();
                location.setId(id);
                location.setX(Double.parseDouble(lineTokens[1]));
                location.setY(Double.parseDouble(lineTokens[2]));
                locationList.add(location);
                int demand = Integer.parseInt(lineTokens[3]);
                int readyTime = Integer.parseInt(lineTokens[4]) * 1000;
                int dueTime = Integer.parseInt(lineTokens[5]) * 1000;
                int serviceDuration = Integer.parseInt(lineTokens[6]) * 1000;
                if (first) {
                    depot = new TimeWindowedDepot();
                    depot.setId(id);
                    depot.setLocation(location);
                    if (demand != 0) {
                        throw new IllegalArgumentException("The depot with id (" + id
                                + ") has a demand (" + demand + ").");
                    }
                    depot.setReadyTime(readyTime);
                    depot.setDueTime(dueTime);
                    if (serviceDuration != 0) {
                        throw new IllegalArgumentException("The depot with id (" + id
                                + ") has a serviceDuration (" + serviceDuration + ").");
                    }
                    initialLocationList.add(depot);
                    first = false;
                } else {
                    TimeWindowedCustomer customer = new TimeWindowedCustomer();
                    customer.setId(id);
                    customer.setLocation(location);
                    customer.setDemand(demand);
                    customer.setReadyTime(readyTime);
                    // Score constraint arrivalAfterDueTimeAtDepot is a build-in hard constraint in VehicleRoutingImporter
                    int maximumDueTime = depot.getDueTime()
                            - serviceDuration - location.getDistance(depot.getLocation());
                    if (dueTime > maximumDueTime) {
                        logger.warn("The customer ({})'s dueTime ({}) was automatically reduced" +
                                " to maximumDueTime ({}) because of the depot's dueTime ({}).",
                                customer, dueTime, maximumDueTime, depot.getDueTime());
                        dueTime = maximumDueTime;
                    }
                    customer.setDueTime(dueTime);
                    customer.setServiceDuration(serviceDuration);
                    // Notice that we leave the PlanningVariable properties on null
                    // Do not add a task that has no demand
                    if (demand != 0) {
                        taskList.add(customer);
                    }
                }
                line = bufferedReader.readLine();
            }
            schedule.setLocationList(locationList);
            schedule.setInitialLocationList(initialLocationList);
            schedule.setTaskList(taskList);
            locationListSize = locationList.size();
        }

    }

}
