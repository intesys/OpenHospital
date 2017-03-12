

package org.isf.utils.services;

import java.io.File;
import iireports.IIReports;
import java.rmi.Naming;

import org.isf.hospital.manager.HospitalBrowsingManager;
import org.isf.hospital.model.Hospital;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;

/////////////////////////////////////////////////////////////////////////////////
public final class ReportService {
    
    ////////////////////////////////////////////////////////////////////////
    private static Process process;
    
    /////////////////////////////////////////////////////////////////////////////////
    
    public static final java.util.Map<String, Object> getHospitalHeader() {
    
    
        Map<String,Object> hospitalHeader = new HashMap<String,Object>();
        Hospital hospital = new HospitalBrowsingManager().getHospital(); 
                                                       
        hospitalHeader.put("hospitalDescription", hospital.getDescription());
        hospitalHeader.put("hospitalCity", hospital.getCity());
        hospitalHeader.put("hospitalAddress", hospital.getAddress());
        hospitalHeader.put("hospitalTelephone", hospital.getTelephone());
        hospitalHeader.put("hospitalFax", hospital.getFax());
        hospitalHeader.put("hospitalEmail", hospital.getEmail());

        return hospitalHeader;
    

    }
    public static final void doStart() {
        
        String[] params = {
                  
            System.getProperty("user.dir")+  File.separator + "local_svcs" + File.separator + "reports" + File.separator + "startup.cmd"
                
        };
                    

        
        process = null;

   
        try {
            
             process = Runtime.getRuntime().exec(params);
    
           //  process = new ProcessBuilder(params).start();
             
             
    
        }

       catch (Exception e) {

            System.out.println("Error executing Process: " + e.getMessage());
    
        }

             
       return;


  }
        
  public static final void doStop() {


      
      
      if(process!=null) {

           try {
      
          
               IIReports service = (IIReports)Naming.lookup("//localhost:2020/IReports");
               service.stopService();
          
           }
      
      
           catch(Exception e) {}
           
           process = null;
      }
      
   
      return;

 }  
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////
    
  public static final boolean viewReport(boolean simpleReport, final String reportFileName, final Map<String, Object> parameters) {
      
      boolean result;
      
      try {
          IIReports service = (IIReports)Naming.lookup("//localhost:2020/IReports");
          if(simpleReport) {
              service.viewSimpleReport(reportFileName, parameters);
          }
          else {
              service.viewComplexReport(reportFileName, parameters);
          }
          result = true;
      }
      catch(Exception exception) {
          JOptionPane.showMessageDialog(null, "Report Service Error: " + exception.getMessage());
          result = false;
      }
      
      return result;
      
  }
    
}
