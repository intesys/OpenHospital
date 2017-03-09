

package org.isf.utils.services;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class RemoteServices {
    
    
    public static final String getServiceURL(String serviceName) {
        
        Properties p = new Properties();
        String serviceURL = null;
        
        try {
        
            p.load(new FileInputStream("rsc" + File.separator + "remoteservices.properties"));
            serviceURL = p.getProperty(serviceName);
            
        }
        
        catch(Exception exception) {}
                   
      
        return serviceURL;
        
        
    }
    
}
