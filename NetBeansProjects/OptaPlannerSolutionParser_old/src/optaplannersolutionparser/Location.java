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

public class Location extends AbstractPersistable {

    private String name = null;
    private double latitude;
    private double longitude;
    
    public Location(Long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    /**
     * The distance is not in miles or km, but in the TSPLIB's unit of measurement.
     * @param location never null
     * @return a positive number, the distance multiplied by 1000 to avoid floating point arithmetic rounding errors
     */
    public int getMilliDistance(Location location) {
        // Implementation specified by TSPLIB http://www2.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
        // Euclidean distance (Pythagorean theorem) - not correct when the surface is a sphere
        double latitudeDifference = location.latitude - latitude;
        double longitudeDifference = location.longitude - longitude;
        double distance = Math.sqrt(
                (latitudeDifference * latitudeDifference) + (longitudeDifference * longitudeDifference));
        return (int) (distance * 1000.0 + 0.5);
    }
    
    // returns distance in km
    public double getDistance(Location location) {
        double earthRadius = 6371.0; // km
        double toRadians = Math.PI/180;
        
        double dLat = (location.getLatitude()-this.getLatitude()) * toRadians;
        double dLon = (location.getLongitude()-this.getLongitude())  * toRadians;
        double lat1 = this.getLatitude() * toRadians;
        double lat2 = location.getLatitude() * toRadians;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        double d = earthRadius * c;
        
        return d;
        
    }

    /*
    @Override
    public String toString() {
        if (name == null) {
            return id.toString();
        }
        return id.toString() + "-" + name;
    }
    */

}
