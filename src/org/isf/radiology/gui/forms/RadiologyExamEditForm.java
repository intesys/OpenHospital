//////////////////////////////////////////////////////////////////////
package org.isf.radiology.gui.forms;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import org.isf.dicom.manager.SqlDicomManager;
import org.isf.dicom.model.FileDicom;

import org.isf.generaldata.MessageBundle;
import org.isf.patient.model.Patient;
import org.isf.radiology.gui.RadiologyExamBrowser;

import org.isf.radiology.gui.dicom.DicomGui;
import org.isf.radiology.manager.RadiologyExamDicomManager;
import org.isf.radiology.manager.RadiologyExamManager;
import org.isf.radiology.model.Radiology;
import org.isf.radiology.model.RadiologyExam;
import org.isf.radiology.model.RadiologyExamDicom;

/////////////////////////////////////////////////////////////////////////////////////////////////////
public class RadiologyExamEditForm extends RadiologyExamForm implements WindowListener {

    /////////////////////////////////////////////Frame///////////////////////////////////////////////////////
    private final int ownerState;
    private final ArrayList<FileDicom> persistentDicomFiles = new ArrayList<FileDicom>();

    public RadiologyExamEditForm(RadiologyExamBrowser owner, ArrayList<Radiology> radiologies, RadiologyExam radiologyExam) {

        super(owner, radiologies, radiologyExam);
        patientSelected = radiologyExam.getPatient();
        ownerState = owner.getState();
        for(FileDicom dicomFile: dicomFiles)
            persistentDicomFiles.add(dicomFile);

    }

    
    @Override
    protected String getWindowTitle() {

        return MessageBundle.getMessage("angal.radiology.edit");

    }
    
    ////////////////////////////////////////////Radiology////////////////////////////////////////////////////////////////////
    
    @Override
    protected Radiology getDefaultRadiology() {

        int noRadiologies = comboRadiologies.getItemCount();
        String examRadiologyCode = radiologyExam.getRadiology().getCode();
        Radiology currentRadiology = null;

        for (int i = 0; i < noRadiologies; i++) {

            Radiology loopRadiology = (Radiology) comboRadiologies.getItemAt(i);

            if (loopRadiology.getCode().equals(examRadiologyCode)) {

                currentRadiology = (Radiology) comboRadiologies.getItemAt(i);

            }

        }

        return currentRadiology;
    }

    ///////////////////////////////////////////Patient///////////////////////////////////////////////////////////////
    
    @Override
    protected String getPatientButtonLabel() {

        return MessageBundle.getMessage("angal.radiology.openpatientprofile");
    }

    @Override
    protected String getPatientButtonToolTipText() {

        return MessageBundle.getMessage("angal.radiology.openpatientprofile");
    }

    @Override
    protected String getPatientName() {

        return radiologyExam.getPatient().getName();

    }

    @Override
    public void patientSelected(Patient patient) {

        if (patient.getCode() != patientSelected.getCode()) {

            JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.cannotchangepatient"));

        }

    }

    /////////////////////////////////////////////////////////Date/////////////////////////////////////////////////////////
    @Override
    protected Date getDefaultDate() {

        return new Date(radiologyExam.getDate().getTime().getTime());
    }

     //////////////////////////////////////////////////////Clinical History////////////////////////////////////////
    @Override
    protected String getClinicalHistory() {

        return radiologyExam.getClinicalHistory();
    }

    //////////////////////////////////////////////////////Comparison//////////////////////////////////////////////////
    
    @Override
    protected String getComparison() {

        return radiologyExam.getComparison();
    }
    
    
    ///////////////////////////////////////////////////////Technique////////////////////////////////////////
    
    @Override
    protected String getTechnique() {

        return radiologyExam.getTechnique();
    }

    
   
    /////////////////////////////////////////////////////////DICOM////////////////////////////////////////////////////
    
    @Override
    protected String getDicomButtonToolTipText() {
        return MessageBundle.getMessage("angal.radiology.tooltip.patientdicomprofile");
    }
    
    @Override
    protected void openDicomGui() {
        new DicomGui(patientSelected, null, RadiologyExamEditForm.this, persistentDicomFiles, false);
    }
    
    ///////////////////////////////////////////////////Finding/////////////////////////////////////////////
    @Override
    protected String getFinding() {

        return radiologyExam.getFinding();
    }
    
    //////////////////////////////////////////////////Impression//////////////////////////////////////////////////////
    @Override
    protected String getImpression() {

        return radiologyExam.getImpression();
    }
    ////////////////////////////////////////////////////Commit Button/////////////////////////////////////////////////
    
    
    @Override
    protected String getCommitButtonText() {
        return MessageBundle.getMessage("angal.radiology.update");
    }
    
    
    @Override
    protected int commit(String radiologyID, GregorianCalendar newDate, int patientID, String clinicalHistory, String comparison, String technique, String finding, String impression) {

        int radiologyExamID = radiologyExam.getCode();
       
        new RadiologyExamManager().updateRadiologyExam(radiologyExamID, radiologyID, newDate, patientID, clinicalHistory, comparison, technique, finding, impression);

        RadiologyExamDicomManager radiologyExamDicomManager = new RadiologyExamDicomManager();
        
        ArrayList<RadiologyExamDicom> radiologyExamDicomFiles = radiologyExamDicomManager.getRadiologyExamDicoms(radiologyExamID); 
        SqlDicomManager dicomManager = new SqlDicomManager();
        try {
        
            for (RadiologyExamDicom radiologyExamDicomFile : radiologyExamDicomFiles) {
              dicomManager.deleteFile(radiologyExamDicomFile.getDicomFile().getIdFile());
        
            }
            
            radiologyExamDicomManager.deleteRadiologyExamDicoms(radiologyExamID);

        }
        
        catch(Exception e) {
            
            radiologyExamID = -1;
        }
        

        return radiologyExamID;
    }

    
    @Override
    protected String getSuccessCommitMessage() {

        return MessageBundle.getMessage("angal.radiology.successexamedit");
    }

   

    

   
    
    

    

    ////////////////////////////////////////////////////Rollback Button/////////////////////////////////////////////////

    
    @Override
    protected String getRollbackButtonText() {
        return MessageBundle.getMessage("angal.radiology.close");
    }

    
    

    
    
    //////////////////////////////////////////////Window Events Handling/////////////////////////
    
    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {

        owner.setEnabled(true);
        owner.setState(ownerState);

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    ////////////////
    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    
    
    /////////////////////////////////////////////////End//////////////////////////////////////////////////////////////////////

}
