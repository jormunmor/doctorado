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

public class Vehicle extends AbstractPersistable implements Standstill {

    private String name = null;

    protected int capacity;
    
    protected InitialLocation initialLocation;

    protected Task nextTask;

    public Vehicle(Long id, String name, int capacity, InitialLocation initialLocation, Task nextTask ){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.initialLocation = initialLocation;
        
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public InitialLocation getDepot() {
        return initialLocation;
    }

    public void setDepot(InitialLocation depot) {
        this.initialLocation = depot;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Task getNextTask() {
        return nextTask;
    }

    @Override
    public void setNextTask(Task nextCustomer) {
        this.nextTask = nextCustomer;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @Override
    public Vehicle getVehicle() {
        return this;
    }

    @Override
    public Location getLocation() {
        return initialLocation.getLocation();
    }

    /*
    @Override
    public String toString() {
        return getLocation() + "[uav-" + id + "]";
    }
    */

}
