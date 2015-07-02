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

import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.SubChainSwapMove;
import org.optaplanner.core.impl.heuristic.selector.value.chained.SubChain;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.vehiclerouting.domain.Standstill;

/**
 *
 * @author jorge
 */
public class DifferentVehicleSubChainSwapMoveFilter  implements SelectionFilter<SubChainSwapMove> {

    @Override
    public boolean accept(ScoreDirector sd, SubChainSwapMove move) {
        SubChain leftChain = move.getLeftSubChain();
        SubChain rightChain = move.getRightSubChain();
        
        Standstill firstLeft = (Standstill) leftChain.getFirstEntity();
        Standstill firstRight = (Standstill) rightChain.getFirstEntity();
        
        return !firstLeft.getVehicle().equals(firstRight.getVehicle());
        
    }
    
}
