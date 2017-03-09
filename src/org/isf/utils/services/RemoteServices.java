

package org.isf.utils.services;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class RemoteServices {
    
    
    public static final String getServiceURL(String serviceName) {
        
        Properties p = new Properties();
        String serviceURL = null;
        
        try {
        
            p.load(new FileInputStream("rsc" + File.separator + "remoteServices.properties"));
            serviceURL = p.getProperty(serviceName);
            
        }
        
        catch(Exception exception) {}
                   
      
        return serviceURL;
        
        
    }
    
    //-----------------------------------------------------------------------------------------------------------------------------
    
    public static final void doStart() {
        
        try {
        
            String[] params = {
                  
               System.getProperty("user.dir")+  File.separator + "remote_svcs" + File.separator + "DbUpdateNotifier" + File.separator + "startup.cmd"
                
            };
                    

             Runtime.getRuntime().exec(params);
    
           //  process = new ProcessBuilder(params).start();
             
             
    
       }

       catch (Exception e) {

            System.out.println("Error executing Process: " + e.getMessage());
    
        }

             
       return;


  }
    
    //--------------------------------------------------------------------------------------------------------------
    
}
