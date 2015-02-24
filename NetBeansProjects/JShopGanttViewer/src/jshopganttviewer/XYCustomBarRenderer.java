/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jshopganttviewer;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author jorge
 */
public class XYCustomBarRenderer extends XYBarRenderer{
    public final static Color TAKEOFF_COLOR = Color.GREEN;
    public final static Color PICK_COLOR = Color.BLACK;
    public final static Color MOVE_COLOR = Color.ORANGE;
    public final static Color MAP_GENERATION_COLOR = Color.MAGENTA;
    public final static Color SURVEILLANCE_COLOR = Color.BLUE;
    public final static Color SYNCHRO_COLOR = Color.RED;
    public final static Color ASSEMBLE_COLOR = Color.WHITE;
    
    public XYCustomBarRenderer(){
        super();
        this.setBarPainter(new StandardXYBarPainter());
        
    }
    
    public XYCustomBarRenderer(double margin){
        super(margin);
        this.setBarPainter(new StandardXYBarPainter());
        
    }
    
    public Paint getItemPaint(int series, int item) {
        // here we assume we're working with the primary dataset
        Map<String, String> taskNameCorrespondenceMap = new HashMap<>();
        taskNameCorrespondenceMap.put("takeoff", "tkf");
        taskNameCorrespondenceMap.put("do_move", "mv");
        taskNameCorrespondenceMap.put("synchro_wait", "wait");
        taskNameCorrespondenceMap.put("pick_object", "pick");
        taskNameCorrespondenceMap.put("assemble", "asbl");
        taskNameCorrespondenceMap.put("surveillance", "surv");
        taskNameCorrespondenceMap.put("mapGeneration", "mapg");
        Color color = null;
        XYDataset ds = getPlot().getDataset();
        XYTaskDataset dataset = (XYTaskDataset) ds;
        TaskSeriesCollection tsc = dataset.getTasks();
        TaskSeries serie = tsc.getSeries(series);
        Task t = serie.get(item);
        String description = t.getDescription();
        if(description.contains(taskNameCorrespondenceMap.get("takeoff"))){
            color = XYCustomBarRenderer.TAKEOFF_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("do_move"))){
            color = XYCustomBarRenderer.MOVE_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("pick_object"))){
            color = XYCustomBarRenderer.PICK_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("surveillance"))){
            color = XYCustomBarRenderer.SURVEILLANCE_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("mapGeneration"))){
            color = XYCustomBarRenderer.MAP_GENERATION_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("assemble"))){
            color = XYCustomBarRenderer.ASSEMBLE_COLOR;

        } else if(description.contains(taskNameCorrespondenceMap.get("synchro_wait"))){
            color = XYCustomBarRenderer.SYNCHRO_COLOR;

        }

        return color;

    }
    
}
