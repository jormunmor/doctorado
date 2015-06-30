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

package org.optaplanner.examples.advancedvehiclerouting.domain.location;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * The cost between 2 locations is a straight line: the euclidean distance between their GPS coordinates.
 * Used with {@link DistanceType#AIR_DISTANCE}.
 */
@XStreamAlias("VrpAirLocation")
public class AirLocation extends Location {

    public AirLocation() {
    }

    public AirLocation(long id, double latitude, double longitude) {
        super(id, latitude, longitude);
    }

    @Override
    public int getDistance(Location location) {
        double distance = getAirDistanceDouble(location);
        // Multiplied by 1000 to avoid floating point arithmetic rounding errors
        return (int) (distance * 1000.0 + 0.5);
    }

}
