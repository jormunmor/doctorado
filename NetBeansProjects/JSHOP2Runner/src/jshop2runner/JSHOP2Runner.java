/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jshop2runner;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class JSHOP2Runner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String execString = "bash /home/jorge/pruebas_software/JSHOP2/executeJSHOP2.sh";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(execString);
            process.waitFor();
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(JSHOP2Runner.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
}
