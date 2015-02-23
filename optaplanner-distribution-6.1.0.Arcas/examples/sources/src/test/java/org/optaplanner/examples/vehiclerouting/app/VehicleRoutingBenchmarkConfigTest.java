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

package org.optaplanner.examples.vehiclerouting.app;

import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.optaplanner.benchmark.api.PlannerBenchmark;
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory;
//import org.optaplanner.examples.cloudbalancing.persistence.CloudBalancingDao;
import org.optaplanner.examples.common.app.CommonBenchmarkApp;
import org.optaplanner.examples.common.app.PlannerBenchmarkConfigTest;

import static org.junit.Assert.*;

public class VehicleRoutingBenchmarkConfigTest extends PlannerBenchmarkConfigTest {

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getArgOptionsAsParameters() {
        return getArgOptionsAsParameters(new VehicleRoutingBenchmarkApp());
    }

    public VehicleRoutingBenchmarkConfigTest(CommonBenchmarkApp.ArgOption argOption) {
        super(argOption);
    }

}
