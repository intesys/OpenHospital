
//////////////////////////////////////////////////////////////////////
package org.isf.radiology.gui.forms;


import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


import org.joda.time.LocalDateTime;

import org.isf.generaldata.MessageBundle;
import org.isf.radiology.gui.RadiologyExamBrowser;

import org.isf.radiology.gui.dicom.DicomGui;
import org.isf.radiology.manager.RadiologyExamManager;
import org.isf.radiology.model.Radiology;



/////////////////////////////////////////////////////////////////////////////////////////////////////
public class RadiologyExamNewForm extends RadiologyExamForm {
    
    
    ///////////////////////////////////////////////////////Frame//////////////////////////////////////////////////////
    public RadiologyExamNewForm(RadiologyExamBrowser owner, ArrayList<Radiology> radiologies) {
        
        super(owner, radiologies, null);
        
    }
    
    @Override
    protected String getWindowTitle() {
        
        
        return MessageBundle.getMessage("angal.radiology.new");
        
        
    }
    
    ////////////////////////////////////////////////////Radiology////////////////////////////////
    
    @Override
    protected Radiology getDefaultRadiology() {
        
        return null;
    }
    
    ///////////////////////////////////////////////////Patient////////////////////////////////////////////////////////////
    @Override
    protected String getPatientButtonLabel() {
        
        return MessageBundle.getMessage("angal.radiology.selectpatient");
    }
    @Override
    protected String getPatientButtonToolTipText() {
        
        return MessageBundle.getMessage("angal.radiology.tooltip.associatepatientexam");
    }
    
    @Override
    protected String getPatientName(){return "";}
    
    //////////////////////////////////////////////////Date////////////////////////////////////////////////////////////////////
    @Override
    protected Date getDefaultDate() {
        
        LocalDateTime now = LocalDateTime.now();
        return now.toDate();
    }
    
    //////////////////////////////////////////////////////////ClinicalHistory/////////////////////////////////////////////////
    @Override
    protected String getClinicalHistory() {return "";}
    
    //////////////////////////////////////////////////////////Comparison//////////////////////////////////////////////////////
    @Override
    protected String getComparison() { return "";}
    //////////////////////////////////////////////////////////Technique////////////////////////////////////////////////////////////
    @Override
    protected String getTechnique() { return "";}
    //////////////////////////////////////////////////////////DICOM/////////////////////////////////////////////////
    @Override
    protected String getDicomButtonToolTipText() { return MessageBundle.getMessage("angal.radiology.tooltip.associatedicomexam");}
    @Override
    protected void openDicomGui() { new DicomGui(patientSelected, null, RadiologyExamNewForm.this);}
    //////////////////////////////////////////////////////////Finding///////////////////////////////////////////////////////
    @Override
    protected String getFinding() { return "";}
    //////////////////////////////////////////////////////////Impression//////////////////////////////////////////////////////
    @Override
    protected String getImpression() { return "";}
    //////////////////////////////////////////////////////////Commit Button
    @Override
    protected String getCommitButtonText() {return MessageBundle.getMessage("angal.radiology.ok");}
    
    @Override
    protected int commit(String radiologyID, GregorianCalendar newDate, int patientID, String clinicalHistory, String comparison, String technique, String finding, String impression) {
        
        ////////////////////////////////////////////////////////////////////////////////////////
        
        return new RadiologyExamManager().createRadiologyExam(radiologyID, newDate, patientID, clinicalHistory, comparison, technique, finding, impression);
        
        
        
        
        
        
        
        
       
        
    }
    
    @Override
    protected String getSuccessCommitMessage() {
        
        return MessageBundle.getMessage("angal.radiology.successexam");
    }
    //////////////////////////////////////////////////////////Rollback Button
    @Override
    protected String getRollbackButtonText() {return MessageBundle.getMessage("angal.radiology.cancel");}
    
    
    
    
    
}
