/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.examples.advancedvehiclerouting.persistence.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.advancedvehiclerouting.app.VehicleRoutingApp;
import org.optaplanner.examples.advancedvehiclerouting.domain.Customer;
import org.optaplanner.examples.advancedvehiclerouting.domain.Standstill;
import org.optaplanner.examples.advancedvehiclerouting.domain.Vehicle;
import org.optaplanner.examples.advancedvehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.advancedvehiclerouting.persistence.VehicleRoutingDao;

public class VehicleRoutingDistanceTypeComparison extends LoggingMain {

    private final ScoreDirectorFactory scoreDirectorFactory;

    public static void main(String[] args) {
        new VehicleRoutingDistanceTypeComparison().compare(
                "solved/tmp-p-belgium-n50-k10.xml",
                "solved/tmp-p-belgium-road-km-n50-k10.xml",
                "solved/tmp-p-belgium-road-time-n50-k10.xml");
    }

    protected final VehicleRoutingDao vehicleRoutingDao;

    public VehicleRoutingDistanceTypeComparison() {
        vehicleRoutingDao = new VehicleRoutingDao();
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(VehicleRoutingApp.SOLVER_CONFIG);
        scoreDirectorFactory = solverFactory.buildSolver().getScoreDirectorFactory();
    }

    public void compare(String... filePaths) {
        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            File file = new File(vehicleRoutingDao.getDataDir(), filePaths[i]);
            if (!file.exists()) {
                throw new IllegalArgumentException("The file (" + file + ") does not exist.");
            }
            files[i] = file;
        }
        for (File varFile : files) {
            logger.info("  Results for {}:", varFile.getName());
            // Intentionally create a new instance instead of reusing the older one.
            VehicleRoutingSolution variablesSolution = (VehicleRoutingSolution) vehicleRoutingDao.readSolution(varFile);
            for (File inputFile : files) {
                HardSoftScore score;
                if (inputFile == varFile) {
                    score = variablesSolution.getScore();
                } else {
                    VehicleRoutingSolution inputSolution = (VehicleRoutingSolution) vehicleRoutingDao.readSolution(inputFile);
                    applyVariables(inputSolution, variablesSolution);
                    score = inputSolution.getScore();
                }
                logger.info("    {} (according to {})", score.getSoftScore(), inputFile.getName());
            }
        }
    }

    private void applyVariables(VehicleRoutingSolution inputSolution, VehicleRoutingSolution varSolution) {
        List<Vehicle> inputVehicleList = inputSolution.getVehicleList();
        Map<Long, Vehicle> inputVehicleMap = new LinkedHashMap<Long, Vehicle>(inputVehicleList.size());
        for (Vehicle vehicle : inputVehicleList) {
            inputVehicleMap.put(vehicle.getId(), vehicle);
        }
        List<Customer> inputCustomerList = inputSolution.getCustomerList();
        Map<Long, Customer> inputCustomerMap = new LinkedHashMap<Long, Customer>(inputCustomerList.size());
        for (Customer customer : inputCustomerList) {
            inputCustomerMap.put(customer.getId(), customer);
        }

        for (Vehicle varVehicle : varSolution.getVehicleList()) {
            Vehicle inputVehicle = inputVehicleMap.get(varVehicle.getId());
            Customer varNext = varVehicle.getNextCustomer();
            inputVehicle.setNextCustomer(varNext == null ? null : inputCustomerMap.get(varNext.getId()));
        }
        for (Customer varCustomer : varSolution.getCustomerList()) {
            Customer inputCustomer = inputCustomerMap.get(varCustomer.getId());
            Standstill varPrevious = varCustomer.getPreviousStandstill();
            inputCustomer.setPreviousStandstill(varPrevious == null ? null :
                    varPrevious instanceof Vehicle ? inputVehicleMap.get(((Vehicle) varPrevious).getId())
                    : inputCustomerMap.get(((Customer) varPrevious).getId()));
            Customer varNext = varCustomer.getNextCustomer();
            inputCustomer.setNextCustomer(varNext == null ? null : inputCustomerMap.get(varNext.getId()));
        }
        ScoreDirector scoreDirector = scoreDirectorFactory.buildScoreDirector();
        scoreDirector.setWorkingSolution(inputSolution);
        scoreDirector.calculateScore();
    }

}
