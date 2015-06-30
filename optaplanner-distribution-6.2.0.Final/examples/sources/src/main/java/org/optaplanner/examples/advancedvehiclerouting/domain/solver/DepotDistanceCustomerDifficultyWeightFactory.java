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

package org.optaplanner.examples.advancedvehiclerouting.domain.solver;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import org.optaplanner.examples.advancedvehiclerouting.domain.Customer;
import org.optaplanner.examples.advancedvehiclerouting.domain.Depot;
import org.optaplanner.examples.advancedvehiclerouting.domain.VehicleRoutingSolution;

/**
 * On large datasets, the constructed solution looks like a Matryoshka doll.
 */
public class DepotDistanceCustomerDifficultyWeightFactory
        implements SelectionSorterWeightFactory<VehicleRoutingSolution, Customer> {

    public Comparable createSorterWeight(VehicleRoutingSolution vehicleRoutingSolution, Customer customer) {
        Depot depot = vehicleRoutingSolution.getDepotList().get(0);
        return new DepotDistanceCustomerDifficultyWeight(customer,
                customer.getLocation().getDistance(depot.getLocation())
                        + depot.getLocation().getDistance(customer.getLocation()));
    }

    public static class DepotDistanceCustomerDifficultyWeight
            implements Comparable<DepotDistanceCustomerDifficultyWeight> {

        private final Customer customer;
        private final int depotRoundTripDistance;

        public DepotDistanceCustomerDifficultyWeight(Customer customer,
                int depotRoundTripDistance) {
            this.customer = customer;
            this.depotRoundTripDistance = depotRoundTripDistance;
        }

        public int compareTo(DepotDistanceCustomerDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(depotRoundTripDistance, other.depotRoundTripDistance) // Ascending (further from the depot are more difficult)
                    .append(customer.getDemand(), other.customer.getDemand())
                    .append(customer.getLocation().getLatitude(), other.customer.getLocation().getLatitude())
                    .append(customer.getLocation().getLongitude(), other.customer.getLocation().getLongitude())
                    .append(customer.getId(), other.customer.getId())
                    .toComparison();
        }

    }

}
