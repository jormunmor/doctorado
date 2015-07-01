/*
 * Copyright 2015 JBoss by Red Hat.
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
package org.optaplanner.examples.vehiclerouting.domain.solver.nearby;

import org.optaplanner.core.impl.heuristic.selector.common.nearby.NearbyDistanceMeter;
import org.optaplanner.examples.vehiclerouting.domain.Location;
import org.optaplanner.examples.vehiclerouting.domain.Standstill;
import org.optaplanner.examples.vehiclerouting.domain.Task;

/**
 *
 * @author jorge
 */
public class TaskNearbyDistanceMeter implements NearbyDistanceMeter<Task, Standstill> {

    /*
        By the moment, we measure the distance between the part location
        of the tasks. But we could do a mean between the part locations and
        the assembly locations.
    
        To take into account: a standstill may be a task or a vehicle.
    */
    @Override
    public double getNearbyDistance(Task o, Standstill d) {
        Location taskObjectLocation = o.getObjectLocation();
        Location standstillLocation = (d instanceof Task)? ((Task) d).getObjectLocation() : d.getLocation();
        
        return taskObjectLocation.getEuclideanDistanceTo(standstillLocation);

    }
    
}
