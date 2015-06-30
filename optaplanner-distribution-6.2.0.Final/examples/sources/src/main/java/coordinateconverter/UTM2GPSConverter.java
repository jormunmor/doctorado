/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package coordinateconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import static optaplannersolutionparser.OptaPlannerSolutionParser.polygonsPath;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jorge
 */
public class UTM2GPSConverter {
    public static void main(String[] args) throws IOException {
        String routePath = "/home/jorge/Escritorio/SystemPlanner/Routes";
        String gpsRoutePath = "/home/jorge/Escritorio/SystemPlanner/GPS_Routes/";
        UTM2LL utm2ll = new UTM2LL(Ellipsoid.WGS_84);
        String utmZone = "29N";
        FileUtils.cleanDirectory(new File(gpsRoutePath));
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(routePath));
            for (Path path : directoryStream) {
                String utmFile = path.toString();
                //System.out.println(utmFile);
                File gpsFile = new File (gpsRoutePath + path.getFileName().toString());
                //System.out.println(gpsRoutePath + path.getFileName().toString());
                PrintWriter printWriter = new PrintWriter (gpsFile);
                BufferedReader br = new BufferedReader(new FileReader(utmFile));
                try {
                    String line = br.readLine();

                    while (line != null) {
                        //System.out.println(line);
                        String[] coordinates = line.split(" ");
                        utm2ll.convertUTMCoordinates(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]), utmZone);
                        printWriter.println(utm2ll.getLatitude() + " " + utm2ll.getLongitude());
                        //printWriter.println(line);
                        line = br.readLine();
                        
                    }
                    
                } finally {
                    br.close();
                    printWriter.close();
                    
                }
          
                
                
                
            }
        } catch (IOException ex) {
            System.out.println("IOException");
            
        }
        
    }
    
}
