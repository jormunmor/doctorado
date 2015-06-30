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

package org.optaplanner.examples.vehiclerouting.domain.location.segmented;

import org.optaplanner.examples.advancedvehiclerouting.domain.location.segmented.HubSegmentLocation;
import org.optaplanner.examples.advancedvehiclerouting.domain.location.segmented.RoadSegmentLocation;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.optaplanner.examples.advancedvehiclerouting.domain.location.Location;

import static org.junit.Assert.*;

public class RoadSegmentLocationTest {

    @Test
    public void getDistance() {
        long id = 0;
        RoadSegmentLocation a = new RoadSegmentLocation(id++, 0.0, 0.0);
        RoadSegmentLocation b = new RoadSegmentLocation(id++, 0.0, 4.0);
        RoadSegmentLocation c = new RoadSegmentLocation(id++, 2.0, 0.0);
        RoadSegmentLocation d = new RoadSegmentLocation(id++, 100.0, 2.0);
        HubSegmentLocation x = new HubSegmentLocation(id++, 1.0, 0.0);
        HubSegmentLocation y = new HubSegmentLocation(id++, 1.0, 3.0);
        HubSegmentLocation z = new HubSegmentLocation(id++, 99.0, 3.0);
        a.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(a, b, c));
        a.setHubTravelDistanceMap(createHubTravelDistanceMap(a, x, y));
        b.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(b, a));
        b.setHubTravelDistanceMap(createHubTravelDistanceMap(b, x, y));
        c.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(c, a));
        c.setHubTravelDistanceMap(createHubTravelDistanceMap(c, x, y));
        d.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(d));
        d.setHubTravelDistanceMap(createHubTravelDistanceMap(d, z));
        x.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(x, a, b, c));
        x.setHubTravelDistanceMap(createHubTravelDistanceMap(x, y, z));
        y.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(y, a, b, c));
        y.setHubTravelDistanceMap(createHubTravelDistanceMap(y, x, z));
        z.setNearbyTravelDistanceMap(createNearbyTravelDistanceMap(z, d));
        z.setHubTravelDistanceMap(createHubTravelDistanceMap(z, x, y));

        assertEquals(sumOfArcs(a, b), a.getDistance(b));
        assertEquals(sumOfArcs(a, c), a.getDistance(c));
        assertEquals(sumOfArcs(a, x, z, d), a.getDistance(d));
        assertEquals(sumOfArcs(b, a), b.getDistance(a));
        assertEquals(sumOfArcs(b, y, c), b.getDistance(c));
        assertEquals(sumOfArcs(b, y, z, d), b.getDistance(d));
        assertEquals(sumOfArcs(c, a), c.getDistance(a));
        assertEquals(sumOfArcs(c, y, b), c.getDistance(b));
        assertEquals(sumOfArcs(c, x, z, d), c.getDistance(d));
        assertEquals(sumOfArcs(d, z, x, a), d.getDistance(a));
        assertEquals(sumOfArcs(d, z, y, b), d.getDistance(b));
        assertEquals(sumOfArcs(d, z, x, c), d.getDistance(c));
    }

    protected int sumOfArcs(Location fromLocation, Location... stopLocations) {
        Location previousLocation = fromLocation;
        int distance = 0;
        for (Location stopLocation : stopLocations) {
            distance += (int) (previousLocation.getAirDistanceDouble(stopLocation) * 1000.0 + 0.5);
            previousLocation = stopLocation;
        }
        return distance;
    }

    private Map<HubSegmentLocation, Double> createHubTravelDistanceMap(Location fromLocation, HubSegmentLocation... toLocations) {
        Map<HubSegmentLocation, Double> map = new LinkedHashMap<HubSegmentLocation, Double>(toLocations.length);
        for (HubSegmentLocation toLocation : toLocations) {
            map.put(toLocation, fromLocation.getAirDistanceDouble(toLocation));
        }
        return map;
    }

    protected Map<RoadSegmentLocation, Double> createNearbyTravelDistanceMap(Location fromLocation,
            RoadSegmentLocation... toLocations) {
        Map<RoadSegmentLocation, Double> map = new LinkedHashMap<RoadSegmentLocation, Double>(toLocations.length);
        for (RoadSegmentLocation toLocation : toLocations) {
            map.put(toLocation, fromLocation.getAirDistanceDouble(toLocation));
        }
        return map;
    }

}
