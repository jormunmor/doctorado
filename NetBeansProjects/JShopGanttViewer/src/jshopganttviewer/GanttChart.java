/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jshopganttviewer;

/**
 *
 * @author jorge
 */

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.ui.TextAnchor;


/**
 * A simple demonstration application showing how to create a Gantt chart.
 * <P>
 * This demo is intended to show the conceptual approach rather than being a polished
 * implementation.
 *
 *
 */
public class GanttChart extends ApplicationFrame {

    Map<String, List> uavsMap;

    public GanttChart(final String title, Map<String, List> uavsMap) {

        super(title);
        this.uavsMap = uavsMap;
        // XY PLOT
        final JFreeChart chart = createChart(uavsMap);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        
    }
        
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private static JFreeChart createChart(Map<String, List> uavsMap) {
        CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Time (ms)"));
        plot.setDomainPannable(true);
        
        // create the plot that contains the task
        plot.add(createSubplot(createDataset(uavsMap), uavsMap));
        
        // set the X axis division
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        //axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND, 1));
        axis.setTickUnit(new DateTickUnit(DateTickUnitType.MILLISECOND, 5));
        axis.setDateFormatOverride(new SimpleDateFormat("sSSS"));
        //axis.setDateFormatOverride(new SimpleDateFormat("SSS"));
        
        // show the plot
        JFreeChart chart = new JFreeChart("JShop Plan", plot);
        chart.setBackgroundPaint(Color.white);
        //ChartUtilities.applyCurrentTheme(chart);
        
        return chart;
        
    }
    
   // this method generates the Y axis and labels the horizontal bars
   private static XYPlot createSubplot(IntervalXYDataset dataset, Map<String, List> uavsMap) {
       DateAxis xAxis = new DateAxis("Time (ms)");
       Object[] objectArray = uavsMap.keySet().toArray();
       String uavs[] = Arrays.copyOf(objectArray, objectArray.length, String[].class);
       SymbolAxis yAxis = new SymbolAxis("UAVs", uavs);
       yAxis.setGridBandsVisible(false);
       //XYBarRenderer renderer = new XYBarRenderer();
       //XYItemRenderer renderer = new XYBarRenderer();
       XYCustomBarRenderer renderer = new XYCustomBarRenderer();
       renderer.setUseYInterval(true);
       renderer.setBaseItemLabelGenerator(new XYItemLabelGenerator() {
           @Override
           public String generateLabel(XYDataset xyd, int series, int item) {
               XYTaskDataset ds = (XYTaskDataset) xyd;
               TaskSeriesCollection tsc = ds.getTasks();
               TaskSeries serie = tsc.getSeries(series);
               Task t = serie.get(item);
               return t.getDescription();
           }
       });
       renderer.setBaseItemLabelsVisible(true);
       //renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER_LEFT));
       renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE7, TextAnchor.TOP_LEFT));
       renderer.setItemLabelFont(new Font("Times New Roman",Font.PLAIN,10));
       //renderer.setBarPainter(new StandardXYBarPainter());
       //renderer.setDefaultBarPainter();
       //renderer.setBarPainter(new StandardXYBarPainter());
       XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
       //((XYBarRenderer) plot.getRenderer()) .setBarPainter(new StandardXYBarPainter());
       
       return plot;
   }

   private static IntervalXYDataset createDataset(Map<String, List> uavsMap) {
       XYTaskDataset dataset = new XYTaskDataset(createTasks(uavsMap));
       dataset.setTransposed(true);
       dataset.setSeriesWidth(0.6);
       return dataset;
       
   }
   
   /*
        Be carefull with this function: it's code is highly dependant on the format used
        in the JShop2 domain.
   */
   private static TaskSeriesCollection createTasks(Map<String, List> uavsMap) {
       TaskSeriesCollection dataset = new TaskSeriesCollection();
       Map<String, String> taskNameCorrespondenceMap = new HashMap<>();
       taskNameCorrespondenceMap.put("takeoff", "tkf");
       taskNameCorrespondenceMap.put("do_move", "mv");
       taskNameCorrespondenceMap.put("synchro_wait", "wait");
       taskNameCorrespondenceMap.put("pick_object", "pick");
       taskNameCorrespondenceMap.put("assemble", "asbl");
       taskNameCorrespondenceMap.put("surveillance", "surv");
       taskNameCorrespondenceMap.put("mapGeneration", "mapg");
       
       Set uavNames = uavsMap.keySet();
       Iterator it = uavNames.iterator();
       while(it.hasNext()){
           String uavName = (String) it.next();
           TaskSeries ts = new TaskSeries(uavName);
           List uavTasks = uavsMap.get(uavName);
           for (Object uavTask : uavTasks) {
               String tsk = (String) uavTask;
               String decomposedTask[] = tsk.split(" ");
               int numTaskFields = decomposedTask.length;
               String taskName = decomposedTask[0].contains("mission")? decomposedTask[1] : decomposedTask[0];
               int initTime = (int) Double.parseDouble(decomposedTask[numTaskFields-2]);
               int duration = (int) Double.parseDouble(decomposedTask[numTaskFields-1]);
               int endTime = initTime + duration;
               System.out.println(uavName + " " + taskName + " initTime: " + initTime + " endTime: " + endTime);
               //Task task = new Task(tsk, new Date(initTime), new Date(endTime));
               Task task = new Task(taskNameCorrespondenceMap.get(taskName), new Date(initTime), new Date(endTime));
               //task.setDescription(taskName);
               ts.add(task);
           }
           dataset.add(ts);
                
       }
       
       return dataset;
       
       
   }
    
}