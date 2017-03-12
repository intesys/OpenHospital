
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.manager;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import imysqlds.ServiceException;

import org.isf.generaldata.MessageBundle;

import org.isf.radiology.service.RadiologyExamDicomIoOperations;

import org.isf.radiology.model.RadiologyExamDicom;






public class RadiologyExamDicomManager {
    
    
    
    
     /////////////////////////////////////////////////////////////////////////////////////////////////////
    
     public final void createRadiologyExamDicom(int radiologyExamID, 
             long dicomFileID) {
        
        
         try {
                        
                  
                         RadiologyExamDicomIoOperations.createRadiologyExamDicom(radiologyExamID, dicomFileID);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
   
    }
    
    //////////////
    
     public final ArrayList<RadiologyExamDicom> getRadiologyExamDicoms(int radiologyExamID)
              
     {
        
         ArrayList<RadiologyExamDicom> dicoms = null;
         
         try {
                        
                  
                         dicoms = RadiologyExamDicomIoOperations.getRadiologyExamDicoms(radiologyExamID);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
         
         return dicoms;
   
    }
    
     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public final void deleteRadiologyExamDicoms(int radiologyExamID) {
        
        
         try {
                        
                  
                         RadiologyExamDicomIoOperations.deleteRadiologyExamDicoms(radiologyExamID);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
   
    }
     
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
}
