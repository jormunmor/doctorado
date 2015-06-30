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

public class Task extends AbstractPersistable {

    private String id;
    private Location location;
    private String locationId;
    private int demand;
    private String type;
    private String jshop2Type;
    private String geometricData;
    private String objectName;
    private String objectLocationId;
    private int goalOrder;

    public Task() {
        super();
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
            
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJshop2Type() {
        return jshop2Type;
    }

    public void setJshop2Type(String jshop2Type) {
        this.jshop2Type = jshop2Type;
    }

    public String getGeometricData() {
        return geometricData;
    }

    public void setGeometricData(String geometricData) {
        this.geometricData = geometricData;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectLocationId() {
        return objectLocationId;
    }

    public void setObjectLocationId(String objectLocationId) {
        this.objectLocationId = objectLocationId;
    }
    
    public int getGoalOrder() {
        return goalOrder;
    }

    public void setGoalOrder(int goalOrder) {
        this.goalOrder = goalOrder;
    }

}
