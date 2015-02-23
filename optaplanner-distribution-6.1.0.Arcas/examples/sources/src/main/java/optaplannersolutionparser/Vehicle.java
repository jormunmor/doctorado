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

public class Vehicle extends AbstractPersistable{

    private String id;

    private int capacity;
    
    private String locationId;

    public Vehicle(Long referenceId, String id, int capacity, String locationId){
        this.referenceId = referenceId;
        this.id = id;
        this.capacity = capacity;
        this.locationId = locationId;
        
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String loccationId) {
        this.locationId = locationId;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return this;
    }

}
