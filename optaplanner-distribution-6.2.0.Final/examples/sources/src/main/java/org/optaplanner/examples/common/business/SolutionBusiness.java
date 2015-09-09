/*
 * Copyright 2010 JBoss Inc
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

package org.optaplanner.examples.common.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.optaplanner.core.impl.domain.entity.descriptor.EntityDescriptor;
import org.optaplanner.core.impl.domain.solution.descriptor.SolutionDescriptor;
import org.optaplanner.core.impl.domain.variable.descriptor.GenuineVariableDescriptor;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.move.generic.ChangeMove;
import org.optaplanner.core.impl.heuristic.selector.move.generic.SwapMove;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.ChainedChangeMove;
import org.optaplanner.core.impl.heuristic.selector.move.generic.chained.ChainedSwapMove;
import org.optaplanner.core.impl.score.director.InnerScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolverAndPersistenceFrame;
import org.optaplanner.examples.vehiclerouting.domain.Location;
import org.optaplanner.examples.vehiclerouting.domain.Task;
import org.optaplanner.examples.vehiclerouting.domain.TaskDependency;
import org.optaplanner.examples.vehiclerouting.domain.Vehicle;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionBusiness {

    private static final ProblemFileComparator FILE_COMPARATOR = new ProblemFileComparator();

    protected final transient Logger logger = LoggerFactory.getLogger(getClass());

    private final CommonApp app;
    private SolutionDao solutionDao;

    private AbstractSolutionImporter importer;
    private AbstractSolutionExporter exporter;

    private File importDataDir;
    private File unsolvedDataDir;
    private File solvedDataDir;
    private File exportDataDir;

    // volatile because the solve method doesn't come from the event thread (like every other method call)
    private volatile Solver solver;
    private String solutionFileName = null;
    private ScoreDirector guiScoreDirector;

    public SolutionBusiness(CommonApp app) {
        this.app = app;
    }

    public String getAppName() {
        return app.getName();
    }

    public String getAppDescription() {
        return app.getDescription();
    }

    public String getAppIconResource() {
        return app.getIconResource();
    }

    public void setSolutionDao(SolutionDao solutionDao) {
        this.solutionDao = solutionDao;
    }

    public void setImporter(AbstractSolutionImporter importer) {
        this.importer = importer;
    }

    public void setExporter(AbstractSolutionExporter exporter) {
        this.exporter = exporter;
    }

    public String getDirName() {
        return solutionDao.getDirName();
    }

    public boolean hasImporter() {
        return importer != null;
    }

    public boolean hasExporter() {
        return exporter != null;
    }

    public void updateDataDirs() {
        File dataDir = solutionDao.getDataDir();
        if (hasImporter()) {
            importDataDir = new File(dataDir, "import");
            if (!importDataDir.exists()) {
                throw new IllegalStateException("The directory importDataDir (" + importDataDir.getAbsolutePath()
                        + ") does not exist.");
            }
        }
        unsolvedDataDir = new File(dataDir, "unsolved");
        if (!unsolvedDataDir.exists()) {
            throw new IllegalStateException("The directory unsolvedDataDir (" + unsolvedDataDir.getAbsolutePath()
                    + ") does not exist.");
        }
        solvedDataDir = new File(dataDir, "solved");
        if (!solvedDataDir.exists() && !solvedDataDir.mkdir()) {
            throw new IllegalStateException("The directory solvedDataDir (" + solvedDataDir.getAbsolutePath()
                    + ") does not exist and could not be created.");
        }
        if (hasExporter()) {
            exportDataDir = new File(dataDir, "export");
            if (!exportDataDir.exists() && !exportDataDir.mkdir()) {
                throw new IllegalStateException("The directory exportDataDir (" + exportDataDir.getAbsolutePath()
                        + ") does not exist and could not be created.");
            }
        }
    }

    public File getImportDataDir() {
        return importDataDir;
    }

    public boolean acceptImportFile(File file) {
        return importer.acceptInputFile(file);
    }

    public boolean isImportFileDirectory() {
        return importer.isInputFileDirectory();
    }

    public String getImportFileSuffix() {
        return importer.getInputFileSuffix();
    }

    public File getUnsolvedDataDir() {
        return unsolvedDataDir;
    }

    public File getSolvedDataDir() {
        return solvedDataDir;
    }

    public File getExportDataDir() {
        return exportDataDir;
    }

    public String getExportFileSuffix() {
        return exporter.getOutputFileSuffix();
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
        ScoreDirectorFactory scoreDirectorFactory = solver.getScoreDirectorFactory();
        guiScoreDirector = scoreDirectorFactory.buildScoreDirector();
    }

    public List<File> getUnsolvedFileList() {
        List<File> fileList = new ArrayList<File>(
                FileUtils.listFiles(unsolvedDataDir, new String[]{solutionDao.getFileExtension()} , true));
        Collections.sort(fileList, FILE_COMPARATOR);
        return fileList;
    }

    public List<File> getSolvedFileList() {
        List<File> fileList = new ArrayList<File>(
                FileUtils.listFiles(solvedDataDir, new String[]{solutionDao.getFileExtension()} , true));
        Collections.sort(fileList, FILE_COMPARATOR);
        return fileList;
    }

    public Solution getSolution() {
        return guiScoreDirector.getWorkingSolution();
    }

    public void setSolution(Solution solution) {
        guiScoreDirector.setWorkingSolution(solution);
    }

    public String getSolutionFileName() {
        return solutionFileName;
    }

    public Score getScore() {
        return guiScoreDirector.calculateScore();
    }

    public boolean isSolving() {
        return solver.isSolving();
    }

    public void registerForBestSolutionChanges(final SolverAndPersistenceFrame solverAndPersistenceFrame) {
        solver.addEventListener(new SolverEventListener<Solution>() {
            // Not called on the event thread
            public void bestSolutionChanged(BestSolutionChangedEvent<Solution> event) {
                // Avoid ConcurrentModificationException when there is an unprocessed ProblemFactChange
                // because the paint method uses the same problem facts instances as the Solver's workingSolution
                // unlike the planning entities of the bestSolution which are cloned from the Solver's workingSolution
                if (solver.isEveryProblemFactChangeProcessed()) {
                    // final is also needed for thread visibility
                    final Solution latestBestSolution = event.getNewBestSolution();
                    // Migrate it to the event thread
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // TODO by the time we process this event, a newer bestSolution might already be queued
                            guiScoreDirector.setWorkingSolution(latestBestSolution);
                            solverAndPersistenceFrame.bestSolutionChanged();
                        }
                    });
                }
            }
        });
    }

    public boolean isConstraintMatchEnabled() {
        return guiScoreDirector.isConstraintMatchEnabled();
    }

    public List<ConstraintMatchTotal> getConstraintMatchTotalList() {
        List<ConstraintMatchTotal> constraintMatchTotalList = new ArrayList<ConstraintMatchTotal>(
                guiScoreDirector.getConstraintMatchTotals());
        Collections.sort(constraintMatchTotalList);
        return constraintMatchTotalList;
    }

    public void importSolution(File file) {
        Solution solution = importer.readSolution(file);
        solutionFileName = file.getName();
        guiScoreDirector.setWorkingSolution(solution);
    }

    public void openSolution(File file) {
        Solution solution = solutionDao.readSolution(file);
        solutionFileName = file.getName();
        guiScoreDirector.setWorkingSolution(solution);
        /*
            The following code is to generate the JSOHP2
            problem parts.
        */
        if(!(solution instanceof VehicleRoutingSolution)){
            return;
            
        }

        VehicleRoutingSolution routingSolution = (VehicleRoutingSolution) solution;
        try {
            generateStartPart(routingSolution);
            generateUavsPart(routingSolution);
            generateObjectPart(routingSolution);
            generateLocationsPart(routingSolution);
            generateAssemblyLocationsPart(routingSolution);
            generateObjectStatePart(routingSolution);
            generateAssemblyPlannerPart(routingSolution);
            generateEndPart(routingSolution);
            
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(SolutionBusiness.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    private void generateStartPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "1_start_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("(defproblem problem quadrotor\n\n\t; FACTS\n\t(\n");
        out.close();
    }
    
    private void generateUavsPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "2_uav_defs_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; UAV defs");
        List<Vehicle> vehicleList = solution.getVehicleList();
        for(Vehicle vehicle : vehicleList){
            out.println("\t\t(quadrotor uav" + vehicle.getId() + ")");
            
        }
        out.println("\n");
        out.close();
    }
    
    
    private void generateObjectPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "3_object_defs_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; object defs");
        List<Task> taskList = solution.getTaskList();
        Set<String> set = new TreeSet();
        for(Task task : taskList){
            String objectName = task.getObjectName();
            if(!set.contains(objectName)){ // Some objects are repeated because the task was discretized
                set.add(objectName);
                out.println("\t\t(object " + objectName + ")");
                
            }            
            
        }
        out.println("\n");
        out.close();
        
    }
    
    private void generateLocationsPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "4_location_defs_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; location defs");
        List<Location> locationList = solution.getLocationList();
        for(Location location : locationList){
            Long id = location.getId();
            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();
            out.println("\t\t(location " + id + " " + x + " " + y + " " + z + ")");
            
        }
        out.println("\n");
        out.close();
    }
    
    private void generateAssemblyLocationsPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "5_assembly_location_defs_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; assembly location defs");
        List<Task> taskList = solution.getTaskList();
        Set<String> set = new TreeSet();
        for(Task task : taskList){
            String objectName = task.getObjectName();            
            if(!set.contains(objectName)){ // Some objects are repeated because the task was discretized
                set.add(objectName);
                Long assemblyLocationId = task.getLocation().getId();
                out.println("\t\t(assembly_location " + objectName + " " + assemblyLocationId + ")");
                
            }            
            
        }
        out.println("\n");
        out.close();
    }
    
    private void generateObjectStatePart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "6_object_state_defs_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; object state defs");
        List<Task> taskList = solution.getTaskList();
        Set<String> set = new TreeSet();
        for(Task task : taskList){
            String objectName = task.getObjectName();            
            if(!set.contains(objectName)){ // Some objects are repeated because the task was discretized
                set.add(objectName);
                Long locationId = task.getObjectLocation().getId();
                out.println("\t\t(at " + objectName + " " + locationId + ")");
                
            }            
            
        }
        out.println("\n");
        out.close();
    }
    
    private void generateAssemblyPlannerPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "7_assembly_planner_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        out.println("\t\t; ObjectDependencies");
        List<Task> taskList = solution.getTaskList();
        Set<String> set = new TreeSet();
        Map<Long, String> taskMap = new TreeMap();
        for(Task task : taskList){ // fill the map->for efficiency purposes
            if(!set.contains(task.getObjectName())){
                set.add(task.getObjectName());
                taskMap.put(task.getId(), task.getObjectName());
            }
            
        }
        set.clear();
        for(Task task : taskList){
            String objectName = task.getObjectName();            
            if(!set.contains(objectName)){ // Some objects are repeated because the task was discretized
                set.add(objectName);
                List<TaskDependency> list = task.getPreconditionList();
                if(list == null || list.isEmpty()){
                    out.println("\t\t(depends " + objectName + " ())");
                    
                } else{
                    String outStr = "\t\t(depends " + objectName + " (";
                    Iterator it = list.iterator();
                    while(it.hasNext()){
                        TaskDependency dep = (TaskDependency) it.next();
                        String depName = taskMap.get(dep.getId());
                        if(!it.hasNext()){
                            outStr += depName + "))";
                            break;
                            
                        } else{
                            outStr += depName + " ";
                            
                        }
                                                
                    }
                    out.println(outStr);
                    
                }                
                
            }            
            
        }
        out.println("\n");
        out.close();
    }
    
    private void generateEndPart(VehicleRoutingSolution solution) throws FileNotFoundException {
        // Generate plan file
        String path = "/home/jorge/git_projects/doctorado/JSHOP2/examples/quadrotor_arcas/problem_parts/";
        File file = new File(path + "9_end_part.txt");
        file.delete();
        PrintWriter out = new PrintWriter(file);
        List<Vehicle> vehicleList = solution.getVehicleList();
        String times = "";
        for(Vehicle vehicle : vehicleList){
            Long id = vehicle.getId();
            Long locId = vehicle.getLocation().getId();
            out.println("\t\t; UAV" + id + " state defs");
            out.println("\t\t(battery uav" + id + " 1200)");
            out.println("\t\t(at uav" + id + " " + locId + ")");
            out.println("\t\t(landed uav" + id + ")");
            out.println();
            times += "\t\t; read/write times UAV" + id + "\n";
            times += "\t\t(write-time at uav" + id + " 0)\n";
            times += "\t\t(read-time at uav" + id + " 0)\n";
            times += "\t\t(write-time battery uav" + id + " 0)\n";
            times += "\t\t(read-time battery uav" + id + " 0)\n\n";
            
        }
        
        out.println(times);
        
        List<Task> taskList = solution.getTaskList();
        Set<String> set = new TreeSet();
        for(Task task : taskList){
            String objectName = task.getObjectName();            
            if(!set.contains(objectName)){ // Some objects are repeated because the task was discretized
                set.add(objectName);
                out.println("\t\t; read/write times " + objectName);
                out.println("\t\t(write-time at " + objectName + " 0)");
                out.println("\t\t(read-time at " + objectName + " 0)\n");
                
            }            
            
        }
        out.println("\t\t; remaining tasks");
        out.println("\t\t(remaining_tasks " + set.size() + ")");
        
        String ending = "\t)\n\n\t; GOALS\n\t(\n\t\t(mission assemble)\n\t)\n)";
        out.println(ending);
        
        out.close();
    }
    
    public void saveSolution(File file) {
        Solution solution = guiScoreDirector.getWorkingSolution();
        solutionDao.writeSolution(solution, file);
    }

    public void exportSolution(File file) {
        Solution solution = guiScoreDirector.getWorkingSolution();
        exporter.writeSolution(solution, file);
    }

    public void doMove(Move move) {
        if (solver.isSolving()) {
            logger.error("Not doing user move ({}) because the solver is solving.", move);
            return;
        }
        if (!move.isMoveDoable(guiScoreDirector)) {
            logger.warn("Not doing user move ({}) because it is not doable.", move);
            return;
        }
        logger.info("Doing user move ({}).", move);
        move.doMove(guiScoreDirector);
        guiScoreDirector.calculateScore();
    }

    public void doProblemFactChange(ProblemFactChange problemFactChange) {
        if (solver.isSolving()) {
            solver.addProblemFactChange(problemFactChange);
        } else {
            problemFactChange.doChange(guiScoreDirector);
            guiScoreDirector.calculateScore();
        }
    }

    /**
     * Can be called on any thread.
     * <p/>
     * Note: This method does not change the guiScoreDirector because that can only be changed on the event thread.
     * @param planningProblem never null
     * @return never null
     */
    public Solution solve(Solution planningProblem) {
        solver.solve(planningProblem);
        return solver.getBestSolution();
    }

    public void terminateSolvingEarly() {
        solver.terminateEarly();
    }

    public ChangeMove createChangeMove(Object entity, String variableName, Object toPlanningValue) {
        SolutionDescriptor solutionDescriptor = ((InnerScoreDirector) guiScoreDirector).getSolutionDescriptor();
        GenuineVariableDescriptor variableDescriptor = solutionDescriptor.findGenuineVariableDescriptorOrFail(
                entity, variableName);
        if (variableDescriptor.isChained()) {
            return new ChainedChangeMove(entity, variableDescriptor, toPlanningValue);
        } else {
            return new ChangeMove(entity, variableDescriptor, toPlanningValue);
        }
    }

    public void doChangeMove(Object entity, String variableName, Object toPlanningValue) {
        ChangeMove move = createChangeMove(entity, variableName, toPlanningValue);
        doMove(move);
    }

    public SwapMove createSwapMove(Object leftEntity, Object rightEntity) {
        SolutionDescriptor solutionDescriptor = ((InnerScoreDirector) guiScoreDirector).getSolutionDescriptor();
        EntityDescriptor entityDescriptor = solutionDescriptor.findEntityDescriptor(leftEntity.getClass());
        Collection<GenuineVariableDescriptor> variableDescriptors = entityDescriptor.getGenuineVariableDescriptors();
        if (entityDescriptor.hasAnyChainedGenuineVariables()) {
            return new ChainedSwapMove(variableDescriptors, leftEntity, rightEntity);
        } else {
            return new SwapMove(variableDescriptors, leftEntity, rightEntity);
        }
    }

    public void doSwapMove(Object leftEntity, Object rightEntity) {
        SwapMove move = createSwapMove(leftEntity, rightEntity);
        doMove(move);
    }

}
