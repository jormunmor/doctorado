/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jshopganttviewer;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author jorge
 */
public class JShopGanttViewer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Plan parser code
            List tasks = JShopPlanParser.parsePlan(System.getProperty("user.home") + "/planJShop2.txt");
            Map<String, List> uavsMap = JShopPlanParser.getTaskLists(tasks);
            // Gant chart code
            
            GanttChart ganttChart = new GanttChart("JShop Plan Scheduling Chart", uavsMap);
            ganttChart.pack();
            RefineryUtilities.centerFrameOnScreen(ganttChart);
            ganttChart.setVisible(true);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JShopGanttViewer.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
}
