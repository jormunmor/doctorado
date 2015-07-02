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
package org.optaplanner.examples.vehiclerouting.solver.move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.SubChainChangeMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;

/**
 *
 * @author jorge
 */
public class DifferentVehicleSubChainChangeMoveFilter implements SelectionFilter<SubChainChangeMove> {

    @Override
    public boolean accept(ScoreDirector sd, SubChainChangeMove move) {
        List planningEntities = new ArrayList(move.getPlanningEntities());
        
        // If all the planning entities that are modified by the
        // move have the same vehicle, then the move is in the same
        // anchor chain and we reject it.
        Vehicle firstVehicle = ((Task) planningEntities.get(0)).getVehicle();
        for(int i=1; i<planningEntities.size(); i++)
        {
            if(!((Task) planningEntities.get(i)).getVehicle().equals(firstVehicle))
            {
                 return true;       
            }
            
        }
        
        return false;
    }
    
}
