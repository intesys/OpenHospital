//----------------------------------------------------------------------------------------------
package ireports;

//--------------------------------------------------------------------------------------------
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.util.Map;
import java.io.File;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JRException;

import iireports.IIReports;

import imysqlds.IDb;
import imysqlds.ServiceException;




//---------------------------------------------------------------------------------------------------------------------



public class IReports extends UnicastRemoteObject implements IIReports {
    
    
    
    //--------------------------------------------------------------------------------------------------------
    
    public static void main(String args[]) { 
        
        try {
            
            LocateRegistry.createRegistry(2020);
            
            IReports object = new IReports();
            Naming.rebind("//localhost:2020/IReports", object);
            
           
            
            
        } 
        
        catch (MalformedURLException | RemoteException e) {
            
            System.err.println("Server exception: " + e.toString());
            
            
        }
        
        
    }
    
    
    public IReports() throws RemoteException {}
    
  
    @Override
    public final void viewSimpleReport(final String reportFileName, final Map<String,Object> parameters) throws RemoteException {
        
        JasperReport jasperReport = loadJasperFile(reportFileName);
        
        if(jasperReport !=null) {
        
            try {
            
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
                
                JasperViewer.viewReport(jasperPrint, false);
              
            }
            
            catch(JRException e) {
            
                System.out.println("Exception Viewing Report: " + e.getMessage());
            
            }
                    
                    
         }
        
       
        
        
    }
    
    
    @Override
    public final void viewComplexReport(final String reportFileName, final Map<String,Object> parameters) throws RemoteException {
        
        
        
        JasperReport jasperReport = loadJasperFile(reportFileName);
        
        
        IDb idb = null;
        if(jasperReport !=null) {
        
            try {
            
                
                idb = new IDb();
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, idb.getConnection());
                
                JasperViewer.viewReport(jasperPrint, false);
                
                
                
                
                
                
            }
            
            catch(ServiceException | JRException e) {
            
                System.out.println("Exception Viewing Report: " + e.getMessage());
                
                
            
            }
            
            if(idb != null) {
                
                try {
                
                    idb.releaseConnection(null);
                   
                    
                }
            
                catch(ServiceException e) {
                
                    
                
                }
                
            }
              
                    
                    
         }
        
       
        
    }
    
    
    
    @Override
    public void stopService() throws RemoteException {
        
        try {
       
            Naming.unbind("//localhost:2020/IReports");
          
            
            
        }
        
        
        
        catch(MalformedURLException | NotBoundException | RemoteException e) {
            
          
        
        }
        
        System.exit(0);
        
        
    }
    
    //------------------------------------------------------------------------------------------------------------
    private static JasperReport loadJasperFile(final String reportFileName) {
        
        String sFilename = System.getProperty("user.dir");
        sFilename = sFilename + File.separator;
        
        sFilename = sFilename + "rpt";
        sFilename = sFilename + File.separator;
        
        
        sFilename = sFilename + reportFileName;
        sFilename = sFilename + ".jrxml";
        
        
        
        
        JasperReport jasperReport = null;
        
        try {
        
            jasperReport = JasperCompileManager.compileReport(sFilename);
          
            
        
        }
        
        catch(JRException e) {
        
            
            System.out.println("Exception Loading Report: " + e.getMessage());
            
            
        
        }
        
        return jasperReport;
        
    }
    
    //------------------------------------------------------------------------------------
    
}
