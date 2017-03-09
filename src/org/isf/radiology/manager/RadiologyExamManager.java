
/////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.manager;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

import imysqlds.ServiceException;

import org.isf.generaldata.MessageBundle;
import org.isf.menu.gui.MainMenu;

import org.isf.radiology.model.RadiologyExam;
import org.isf.radiology.service.RadiologyExamIoOperations;


public class RadiologyExamManager {
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final int createRadiologyExam(String radiologyID, GregorianCalendar date, int patientID, String clinicalHistory, 
            String comparison, String technique, 
            String finding, String impression) {
        
        
         int radiologyExamID = -1;
         try {
                        
                  
                         radiologyExamID = RadiologyExamIoOperations.createRadiologyExam(radiologyID, date, patientID, clinicalHistory, 
            comparison, technique,
            finding, impression, MainMenu.getUserDesc());
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
         
         
         return radiologyExamID;
        
   
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RadiologyExam getRadiologyExam(int radiologyExamID) {
		try {
			return RadiologyExamIoOperations.getRadiologyExam(radiologyExamID);
                        
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
			return null;
		}
	}
    
    public ArrayList<RadiologyExam> getRadiologyExams(int patientID, String radiology, GregorianCalendar dateFrom, GregorianCalendar dateTo) {
        
        
        try {
                        
                  
                         return RadiologyExamIoOperations.getRadiologyExams(patientID, radiology, dateFrom, dateTo);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
			return null;
		}
    }
    
     
    public ArrayList<RadiologyExam> getRadiologyExams(int patientID) {
		try {
			return RadiologyExamIoOperations.getRadiologyExams(patientID);
                        
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
			return null;
		}
	}
    
    
     
    public ArrayList<RadiologyExam> getRadiologyExams() {
		
        
        
        ArrayList<RadiologyExam> radiologyExams = null;
        boolean exception;
        int trialNo = 0;
        do {
            exception = false;
            trialNo++;
            try {
                 radiologyExams = RadiologyExamIoOperations.getRadiologyExams();
            }
            catch(ServiceException serviceException) {
            
               
                    radiologyExams = null;
                    exception = true;
                
                
           }
         } while(exception && (trialNo <= 3));
        
         if(exception)
             JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
         return radiologyExams;
}
   
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public final void updateRadiologyExam(int radiologyExamID, String radiologyID, GregorianCalendar date, int patientID, String clinicalHistory, 
            String comparison, String technique, //String dicomFile, 
            String finding, String impression) {
        
        
         try {
                        
                  
                         RadiologyExamIoOperations.updateRadiologyExam(radiologyExamID, radiologyID, date, patientID, clinicalHistory, 
            comparison, technique, 
            finding, impression, MainMenu.getUserDesc());
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		
        }
        
   
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void deleteRadiologyExam(int radiologyExamID) {
        
        
        try {
                        
                  
                         RadiologyExamIoOperations.deleteRadiologyExam(radiologyExamID);
		
        }
        
        catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
		
		}
        
        
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    
}
