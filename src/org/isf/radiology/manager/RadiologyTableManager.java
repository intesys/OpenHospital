////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.manager;


import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

import imysqlds.ServiceException;

import org.isf.generaldata.MessageBundle;

import org.isf.radiology.service.RadiologyTableIoOperations;


public class RadiologyTableManager {
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void createRadiologyTable(GregorianCalendar date, String patient, String scan, String macAddress) {
        
        
         try {
                        
                  
                         RadiologyTableIoOperations.createRadiologyTable(date, patient, scan, macAddress);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
   
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public final void deleteRadiologyTables(String macAddress) {
        
        
         try {
                        
                  
                         RadiologyTableIoOperations.deleteRadiologyTables(macAddress);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
   
    }
    
}
