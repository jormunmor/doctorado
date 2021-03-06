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
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Location")
public class Location extends AbstractPersistable {

    private double x;
    private double y;
    private double z;
    private String name; // unused, delete

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double z) {
        this.z = z;
    }
    
    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public void setName(String name){
        this.name = name;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * The distance is not in miles or km, but in the TSPLIB's unit of measurement.
     * @param location never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getDistance(Location location) {
        // Implementation specified by TSPLIB http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
        double xDifference = location.x - x;
        double yDifference = location.y - y;
        double zDifference = location.z - z;
        double distance = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference) + (zDifference * zDifference));
        return (int) (distance * 1000.0 + 0.5);
    }
    
    public double getEuclideanDistanceTo(Location location) {
        double xDifference = location.x - x;
        double yDifference = location.y - y;
        double zDifference = location.z - z;
        double distance = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference) + (zDifference * zDifference));
        
        return distance;
    }

    @Override
    public String toString() {
        if (id == null) {
            return id.toString();
        }
        return id.toString() + "-" + id;
    }

    public String getSafeName() {
        if (id == null) {
            return id.toString();
        }
        return id + "";
    }

}
