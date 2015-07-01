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

package org.optaplanner.examples.vehiclerouting.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamInclude;
import java.util.List;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.CustomShadowVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.vehiclerouting.domain.solver.VehicleUpdatingVariableListener;
import org.optaplanner.examples.vehiclerouting.domain.solver.VrpCustomerDifficultyComparator;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedCustomer;

@PlanningEntity(difficultyComparatorClass = VrpCustomerDifficultyComparator.class)
@XStreamAlias("Task")
@XStreamInclude({
        TimeWindowedCustomer.class
})
public class Task extends AbstractPersistable implements Standstill {

    protected Location location; // Location where the part will be assembled.
    protected Location objectLocation; // Location of the part to be picked.
    protected int demand;
    protected int priority;
    protected String objectName; // Name of the part.
    protected String action;
    protected List<TaskDependency> preconditionList;
    protected String type;

    // Planning variables: changes during planning, between score calculations.
    protected Standstill previousStandstill;

    // Shadow variables
    protected Task nextTask;
    protected Vehicle vehicle;

    /*
        Returns the location where the assembly takes place (where 
        the part will be placed).
    */
    @Override
    public Location getLocation() {
        return location;
        
    }
    
    public void setLocation(Location location) {
        this.location = location;
        
    }
    
    public String getType() {
        return type;
        
    }
    
    public void setType(String type) {
        this.type = type;
        
    }
    
    /*
        Returns the location of the part that must be assembled.
    */
    public Location getObjectLocation() {
        return objectLocation;
        
    }
    
    public void setObjectLocation(Location objectLocation) {
        this.objectLocation = objectLocation;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public String getObjectName() {
        return objectName;
        
    }
    
    public void setObjectName(String objectName) {
        this.objectName = objectName;
        
    }

    @PlanningVariable(valueRangeProviderRefs = {"vehicleRange", "customerRange"},
            graphType = PlanningVariableGraphType.CHAINED)
    public Standstill getPreviousStandstill() {
        return previousStandstill;
    }

    public void setPreviousStandstill(Standstill previousStandstill) {
        this.previousStandstill = previousStandstill;
    }

    @Override
    public Task getNextTask() {
        return nextTask;
    }

    @Override
    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    @CustomShadowVariable(variableListenerClass = VehicleUpdatingVariableListener.class,
            sources = {@CustomShadowVariable.Source(variableName = "previousStandstill")})
    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public String getAction() {
        return action;
        
    }
        
    public void setAction(String action) {
        this.action = action;
        
    }
    
    public void setPreconditionList(List preconditionList) {
        this.preconditionList = preconditionList;
        
    } 
    
    public List getPreconditionList() {
        return preconditionList;
        
    }

    public int getDistanceToPreviousStandstill() {
        if (previousStandstill == null) {
            return 0;
        }
        return getDistanceTo(previousStandstill);
    }

    public int getDistanceTo(Standstill standstill) {
        return location.getDistance(standstill.getLocation());
    }

    @Override
    public String toString() {
        return location + "(after " + (previousStandstill == null ? "null" : previousStandstill.getLocation()) + ")";
    }

}
