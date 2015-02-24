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

package optaplannersolutionparser;

public class Task extends AbstractPersistable implements Standstill {

    protected String name;
    protected Location location;
    protected int demand;

    // Planning variables: changes during planning, between score calculations.
    protected Standstill previousStandstill;

    // Shadow variables
    protected Task nextTask;
    protected Vehicle vehicle;

    public Task(Long id, String name, Location location, int demand, Standstill previousStandstill, Task nextTask, Vehicle vehicle) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.demand = demand;
        this.previousStandstill = previousStandstill;
        this.nextTask = nextTask;
        this.vehicle = vehicle;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
            
    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

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

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getMilliDistanceToPreviousStandstill() {
        if (previousStandstill == null) {
            return 0;
        }
        return getMilliDistanceTo(previousStandstill);
    }

    /**
     * @param standstill never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getMilliDistanceTo(Standstill standstill) {
        return location.getMilliDistance(standstill.getLocation());
    }

    /*
    @Override
    public String toString() {
        return location + "(after " + (previousStandstill == null ? "null" : previousStandstill.getLocation()) + ")";
    }
    */

}
