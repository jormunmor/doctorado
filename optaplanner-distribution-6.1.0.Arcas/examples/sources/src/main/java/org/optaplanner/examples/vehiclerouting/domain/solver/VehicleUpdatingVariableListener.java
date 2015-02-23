package org.optaplanner.examples.vehiclerouting.domain.solver;

import org.optaplanner.core.impl.domain.variable.listener.VariableListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.Standstill;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;

public class VehicleUpdatingVariableListener implements VariableListener<Task> {

    public void beforeEntityAdded(ScoreDirector scoreDirector, Task customer) {
        // Do nothing
    }

    public void afterEntityAdded(ScoreDirector scoreDirector, Task customer) {
        updateVehicle(scoreDirector, customer);
    }

    public void beforeVariableChanged(ScoreDirector scoreDirector, Task customer) {
        // Do nothing
    }

    public void afterVariableChanged(ScoreDirector scoreDirector, Task customer) {
        updateVehicle(scoreDirector, customer);
    }

    public void beforeEntityRemoved(ScoreDirector scoreDirector, Task customer) {
        // Do nothing
    }

    public void afterEntityRemoved(ScoreDirector scoreDirector, Task customer) {
        // Do nothing
    }

    protected void updateVehicle(ScoreDirector scoreDirector, Task sourceCustomer) {
        Standstill previousStandstill = sourceCustomer.getPreviousStandstill();
        Vehicle vehicle = previousStandstill == null ? null : previousStandstill.getVehicle();
        Task shadowCustomer = sourceCustomer;
        while (shadowCustomer != null && shadowCustomer.getVehicle() != vehicle) {
            scoreDirector.beforeVariableChanged(shadowCustomer, "vehicle");
            shadowCustomer.setVehicle(vehicle);
            scoreDirector.afterVariableChanged(shadowCustomer, "vehicle");
            shadowCustomer = shadowCustomer.getNextTask();
        }
    }

}
