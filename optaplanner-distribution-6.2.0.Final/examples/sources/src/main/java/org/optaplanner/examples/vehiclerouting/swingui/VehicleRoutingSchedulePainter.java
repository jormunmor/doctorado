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

package org.optaplanner.examples.vehiclerouting.swingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.ImageIcon;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.examples.common.swingui.TangoColorFactory;
import org.optaplanner.examples.common.swingui.latitudelongitude.LatitudeLongitudeTranslator;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.InitialLocation;
import org.optaplanner.examples.vehiclerouting.domain.Location;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedCustomer;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedDepot;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.TimeWindowedVehicleRoutingSolution;

public class VehicleRoutingSchedulePainter {

    private static final int TEXT_SIZE = 12;
    private static final int TIME_WINDOW_DIAMETER = 26;
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("#,##0.00");

    private static final String IMAGE_PATH_PREFIX = "/org/optaplanner/examples/vehiclerouting/swingui/";

    private ImageIcon depotImageIcon;
    private ImageIcon[] vehicleImageIcons;

    private BufferedImage canvas = null;
    private LatitudeLongitudeTranslator translator = null;

    public VehicleRoutingSchedulePainter() {
        depotImageIcon = new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "depot.png"));
        vehicleImageIcons = new ImageIcon[] {
                new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleChameleon.png")),
                new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleButter.png")),
                new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleSkyBlue.png")),
                new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehicleChocolate.png")),
                new ImageIcon(getClass().getResource(IMAGE_PATH_PREFIX + "vehiclePlum.png")),
        };
        if (vehicleImageIcons.length != TangoColorFactory.SEQUENCE_1.length) {
            throw new IllegalStateException("The vehicleImageIcons length (" + vehicleImageIcons.length
                    + ") should be equal to the TangoColorFactory.SEQUENCE length ("
                    + TangoColorFactory.SEQUENCE_1.length + ").");
        }
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

    public LatitudeLongitudeTranslator getTranslator() {
        return translator;
    }

    public void reset(VehicleRoutingSolution schedule, Dimension size, ImageObserver imageObserver) {
        translator = new LatitudeLongitudeTranslator();
        for (Location location : schedule.getLocationList()) {
            translator.addCoordinates(location.getX(), location.getY());
        }

        int maximumTimeWindowTime = determineMaximumTimeWindowTime(schedule);

        double width = size.getWidth();
        double height = size.getHeight();
        translator.prepareFor(width, height - 10 - TEXT_SIZE);

        Graphics2D g = createCanvas(width, height);
        g.setFont(g.getFont().deriveFont((float) TEXT_SIZE));
        g.setStroke(TangoColorFactory.NORMAL_STROKE);
        for (Task customer : schedule.getTaskList()) {
            Location location = customer.getLocation();
            int x = translator.translateLongitudeToX(location.getY());
            int y = translator.translateLatitudeToY(location.getX());
            g.setColor(TangoColorFactory.ALUMINIUM_4);
            g.fillRect(x - 1, y - 1, 3, 3);
            String demandString = Integer.toString(customer.getDemand());
            g.drawString(demandString, x - (g.getFontMetrics().stringWidth(demandString) / 2), y - TEXT_SIZE/2);
            if (customer instanceof TimeWindowedCustomer) {
                TimeWindowedCustomer timeWindowedCustomer = (TimeWindowedCustomer) customer;
                g.setColor(TangoColorFactory.ALUMINIUM_3);
                int circleX = x - (TIME_WINDOW_DIAMETER / 2);
                int circleY = y + 5;
                g.drawOval(circleX, circleY, TIME_WINDOW_DIAMETER, TIME_WINDOW_DIAMETER);
                g.fillArc(circleX, circleY, TIME_WINDOW_DIAMETER, TIME_WINDOW_DIAMETER,
                        90 - calculateTimeWindowDegree(maximumTimeWindowTime, timeWindowedCustomer.getReadyTime()),
                        calculateTimeWindowDegree(maximumTimeWindowTime, timeWindowedCustomer.getReadyTime())
                                - calculateTimeWindowDegree(maximumTimeWindowTime, timeWindowedCustomer.getDueTime()));
                if (timeWindowedCustomer.getArrivalTime() != null) {
                    if (timeWindowedCustomer.isArrivalAfterDueTime()) {
                        g.setColor(TangoColorFactory.SCARLET_2);
                    } else if (timeWindowedCustomer.isArrivalBeforeReadyTime()) {
                        g.setColor(TangoColorFactory.ORANGE_2);
                    } else {
                        g.setColor(TangoColorFactory.ALUMINIUM_6);
                    }
                    g.setStroke(TangoColorFactory.THICK_STROKE);
                    int circleCenterY = y + 5 + TIME_WINDOW_DIAMETER / 2;
                    int angle = calculateTimeWindowDegree(maximumTimeWindowTime, timeWindowedCustomer.getArrivalTime());
                    g.drawLine(x, circleCenterY,
                            x + (int) (Math.sin(Math.toRadians(angle)) * (TIME_WINDOW_DIAMETER / 2 + 3)),
                            circleCenterY - (int) (Math.cos(Math.toRadians(angle)) * (TIME_WINDOW_DIAMETER / 2 + 3)));
                    g.setStroke(TangoColorFactory.NORMAL_STROKE);
                }
            }
        }
        g.setColor(TangoColorFactory.ALUMINIUM_3);
        for (InitialLocation depot : schedule.getInitialLocationList()) {
            int x = translator.translateLongitudeToX(depot.getLocation().getY());
            int y = translator.translateLatitudeToY(depot.getLocation().getX());
            g.fillRect(x - 2, y - 2, 5, 5);
            g.drawImage(depotImageIcon.getImage(),
                    x - depotImageIcon.getIconWidth() / 2, y - 2 - depotImageIcon.getIconHeight(), imageObserver);
        }
        int colorIndex = 0;
        // TODO Too many nested for loops
        for (Vehicle vehicle : schedule.getVehicleList()) {
            g.setColor(TangoColorFactory.SEQUENCE_2[colorIndex]);
            Task vehicleInfoCustomer = null;
            int longestNonDepotDistance = -1;
            int load = 0;
            for (Task task : schedule.getTaskList()) {
                if (task.getPreviousStandstill() != null && task.getVehicle() == vehicle) {
                    load += task.getDemand();
                    // retrieve the object location
                    Location objectLocation = task.getObjectLocation();
                    int objectLocationX = translator.translateLongitudeToX(objectLocation.getY());
                    int objectLocationY = translator.translateLatitudeToY(objectLocation.getX());
                    // retrieve the task location
                    Location taskLocation = task.getLocation();
                    int taskLocationX = translator.translateLongitudeToX(taskLocation.getY());
                    int taskLocationY = translator.translateLatitudeToY(taskLocation.getX());
                    // retieve the pre task location
                    Location previousLocation = task.getPreviousStandstill().getLocation();
                    int previousLocationX = translator.translateLongitudeToX(previousLocation.getY());
                    int previousLocationY = translator.translateLatitudeToY(previousLocation.getX());
                    // retireve de vehicle initial location
                    Location vehicleLocation = vehicle.getLocation();
                    int vehicleX = translator.translateLongitudeToX(vehicleLocation.getY());
                    int vehicleY = translator.translateLatitudeToY(vehicleLocation.getX());
                    
                    // ***************
                    // now between customers the vehicle first passes through the mount
                    // location of the previous standstill (if exists)
                    if(task.getPreviousStandstill() instanceof Task){ // intermediate task, previousstandstill is a task
                        g.drawLine(previousLocationX, previousLocationY, objectLocationX, objectLocationY); // from prev task to object
                        g.drawLine(objectLocationX, objectLocationY, taskLocationX, taskLocationY); // from object to task
                        
                    } else{ // first task, previousstandstill is the vehicle
                        
                        g.setStroke(TangoColorFactory.LIGHT_DASHED_STROKE);
                        g.drawLine(previousLocationX, previousLocationY, objectLocationX, objectLocationY); // from initialLocation to objectLocation
                        g.setStroke(TangoColorFactory.NORMAL_STROKE);
                        g.drawLine(objectLocationX, objectLocationY, taskLocationX, taskLocationY); // from objectLocation to taskLocation
                        
                    }
                                        
                    // Determine where to draw the vehicle info
                    int distance = task.getDistanceToPreviousStandstill();
                    if (task.getPreviousStandstill() instanceof Task) {
                        if (longestNonDepotDistance < distance) {
                            longestNonDepotDistance = distance;
                            vehicleInfoCustomer = task;
                        }
                    } else if (vehicleInfoCustomer == null) {
                        // If there is only 1 task in this chain, draw it on a line to the InitialLocation anyway
                        vehicleInfoCustomer = task;
                    }
                    // Line back to the vehicle initialLocation
                    // we ignore the back path to clarify the visual solution
                    // but in the score calculator take it into account
                    
                    if (task.getNextTask() == null) {
                        g.setStroke(TangoColorFactory.FAT_DASHED_STROKE);
                        g.drawLine(taskLocationX, taskLocationY, vehicleX, vehicleY);
                        g.setStroke(TangoColorFactory.NORMAL_STROKE);
                        /*
                        Location finalLocation = task.getObjectLocation(); // prev getMountLocation
                        int finalX = translator.translateLongitudeToX(finalLocation.getLongitude());
                        int finalY = translator.translateLatitudeToY(finalLocation.getLatitude());
                        g.drawLine(x, y, finalX, finalY);
                        Location vehicleLocation = vehicle.getLocation();
                        int vehicleX = translator.translateLongitudeToX(vehicleLocation.getLongitude());
                        int vehicleY = translator.translateLatitudeToY(vehicleLocation.getLatitude());
                        g.setStroke(TangoColorFactory.FAT_DASHED_STROKE);
                        g.drawLine(finalX, finalY, vehicleX, vehicleY);
                        g.setStroke(TangoColorFactory.NORMAL_STROKE);
                        */
                    }
                    
                }
            }
            // Draw vehicle info
            if (vehicleInfoCustomer != null) {
                if (load > vehicle.getCapacity()) {
                    g.setColor(TangoColorFactory.SCARLET_2);
                }
                Location previousLocation = vehicleInfoCustomer.getPreviousStandstill().getLocation();
                Location location = vehicleInfoCustomer.getLocation();
                double longitude = (previousLocation.getY() + location.getY()) / 2.0;
                int x = translator.translateLongitudeToX(longitude);
                double latitude = (previousLocation.getX() + location.getX()) / 2.0;
                int y = translator.translateLatitudeToY(latitude);
                boolean ascending = (previousLocation.getY() < location.getY())
                        ^ (previousLocation.getX() < location.getX());

                ImageIcon vehicleImageIcon = vehicleImageIcons[colorIndex];
                int vehicleInfoHeight = vehicleImageIcon.getIconHeight() + 2 + TEXT_SIZE;
                g.drawImage(vehicleImageIcon.getImage(),
                        x + 1, (ascending ? y - vehicleInfoHeight - 1 : y + 1), imageObserver);
                //g.drawString(load + " / " + vehicle.getCapacity(), x + 1, (ascending ? y - 1 : y + vehicleInfoHeight + 1));
            }
            colorIndex = (colorIndex + 1) % TangoColorFactory.SEQUENCE_2.length;
        }

        // Legend
        g.setColor(TangoColorFactory.ALUMINIUM_3);
        g.fillRect(5, (int) height - 12 - TEXT_SIZE - (TEXT_SIZE / 2), 5, 5);
        g.drawString("Depot", 15, (int) height - 10 - TEXT_SIZE);
        String vehiclesSizeString = schedule.getVehicleList().size() + " vehicles";
        g.drawString(vehiclesSizeString,
                ((int) width - g.getFontMetrics().stringWidth(vehiclesSizeString)) / 2, (int) height - 10 - TEXT_SIZE);
        g.setColor(TangoColorFactory.ALUMINIUM_4);
        g.fillRect(6, (int) height - 6 - (TEXT_SIZE / 2), 3, 3);
        g.drawString((schedule instanceof TimeWindowedVehicleRoutingSolution)
                ? "Customer: demand, time window and arrival time" : "Customer: demand", 15, (int) height - 5);
        String customersSizeString = schedule.getTaskList().size() + " customers";
        g.drawString(customersSizeString,
                ((int) width - g.getFontMetrics().stringWidth(customersSizeString)) / 2, (int) height - 5);
        String clickString = "Click anywhere in the map to add a customer.";
        g.drawString(clickString, (int) width - 5 - g.getFontMetrics().stringWidth(clickString), (int) height - 5);
        // Show soft score
        g.setColor(TangoColorFactory.ORANGE_3);
        HardMediumSoftScore score = schedule.getScore();
        if (score != null) {
            String fuelString;
            if (!score.isFeasible()) {
                fuelString = "Not feasible";
            } else {
                double fuel = ((double) - score.getSoftScore()) / 1000.0;
                fuelString = NUMBER_FORMAT.format(fuel) + " fuel";
            }
            g.setFont(g.getFont().deriveFont(Font.BOLD, (float) TEXT_SIZE * 2));
            g.drawString(fuelString,
                    (int) width - g.getFontMetrics().stringWidth(fuelString) - 10, (int) height - 10 - TEXT_SIZE);
        }
    }

    private int determineMaximumTimeWindowTime(VehicleRoutingSolution schedule) {
        int maximumTimeWindowTime = 0;
        for (InitialLocation initialLocation : schedule.getInitialLocationList()) {
            if (initialLocation instanceof TimeWindowedDepot) {
                int timeWindowTime = ((TimeWindowedDepot) initialLocation).getDueTime();
                if (timeWindowTime > maximumTimeWindowTime) {
                    maximumTimeWindowTime = timeWindowTime;
                }
            }
        }
        for (Task task : schedule.getTaskList()) {
            if (task instanceof TimeWindowedCustomer) {
                int timeWindowTime = ((TimeWindowedCustomer) task).getDueTime();
                if (timeWindowTime > maximumTimeWindowTime) {
                    maximumTimeWindowTime = timeWindowTime;
                }
            }
        }
        return maximumTimeWindowTime;
    }

    private int calculateTimeWindowDegree(int maximumTimeWindowTime, int timeWindowTime) {
        return (360 * timeWindowTime / maximumTimeWindowTime);
    }

    public Graphics2D createCanvas(double width, double height) {
        int canvasWidth = (int) Math.ceil(width) + 1;
        int canvasHeight = (int) Math.ceil(height) + 1;
        canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = canvas.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        return g;
    }

}
