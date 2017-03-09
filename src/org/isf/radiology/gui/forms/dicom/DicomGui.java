//----------------------------------------------------------------------------------------------------------

package org.isf.radiology.gui.dicom;



//----------------------------------------------------------------------------------------------------------

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import org.isf.utils.services.TaskProgress;
import org.isf.generaldata.MessageBundle;
import org.isf.admission.gui.PatientFolderBrowser;
import org.isf.patient.model.Patient;
import org.isf.dicom.gui.FileDicomFilter;
import org.isf.dicom.manager.SourceFiles;
import org.isf.dicom.manager.SqlDicomManager;
import org.isf.dicom.model.FileDicom;



//-----------------------------------------------------------------------------------------------------------


public final class DicomGui extends org.isf.dicom.gui.DicomGui {
    
    
//-----------------------------------------------------------------------------------------------------------
    
   
    public static interface DicomGuiExitListener {
            
            public void onExit(java.util.ArrayList<FileDicom> dicomFiles);
        
    }
    
    public DicomGui(Patient paziente, PatientFolderBrowser owner, DicomGui.DicomGuiExitListener guiExitListener) {


                    
        super(paziente, owner);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon("rsc/icons/oh.png").getImage());
        windowEventsHandler = this.new WindowEventHandler();
        
        this.guiExitListener = guiExitListener;
                       


              
    }
    
    public DicomGui(Patient paziente, PatientFolderBrowser owner, DicomGui.DicomGuiExitListener guiExitListener, 
            ArrayList<FileDicom> dicomFiles, boolean hideButtons) {


                    
        super(paziente, owner);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon("rsc/icons/oh.png").getImage());
        windowEventsHandler = this.new WindowEventHandler();
        
        this.guiExitListener = guiExitListener;
        
        
        for(FileDicom dicomFile: dicomFiles) {
            
            examFiles.add(dicomFile);
            fdb.add(dicomFile);
            thumbnail.initialize();
            
            
        }
        
        
        if(hideButtons) {
            
            jButtonLoadDicom.setVisible(false);
            jButtonDeleteDicom.setVisible(false);
            jButtonDoneDicom.setVisible(false);
        }
        
        
                       


              
    }
    
    //--------------------------------------------------------------------------------------------------------------
    @Override
    protected void setThumbnailViewGui() {


                    
        thumbnail = new ThumbnailViewGui(patient, this);

              
    }
    
    @Override
    protected void initComponents() {
                  
                  
                  
        super.initComponents();
        
        jPanelDetail = new DicomViewGui(null, null, this);
        jPanelDetail.setName("jPanelDetail");
        jSplitPane1.setRightComponent(jPanelDetail);
                  
        jButtonDoneDicom = new JButton();
        jButtonDoneDicom.setText(MessageBundle.getMessage("angal.radiology.dicom.done"));
        jPanelButton.add(jButtonDoneDicom);
        buttonEventsHandler = this.new ButtonEventHandler();
        
        
                  
                  
                  
    }
        
    
    
  //--------------------------------------------------------------------------------------------------------------------------------
    
    ArrayList<FileDicom> getDicomFiles() {
        
        ArrayList<FileDicom> dicomBaseFiles = new ArrayList<FileDicom>();
        
        for(FileDicom dicomFile: fdb) {
            
            FileDicom dicomBaseFile = new FileDicom();
            
            dicomBaseFile.setDicomAccessionNumber(dicomFile.getDicomAccessionNumber());
            dicomBaseFile.setDicomInstanceUID(dicomFile.getDicomInstanceUID());
            
            dicomBaseFile.setDicomInstitutionName(dicomFile.getDicomInstitutionName());
            
            dicomBaseFile.setDicomPatientAddress(dicomFile.getDicomPatientAddress());
            dicomBaseFile.setDicomPatientAge(dicomFile.getDicomPatientAge());
            dicomBaseFile.setDicomPatientBirthDate(dicomFile.getDicomPatientBirthDate());
            dicomBaseFile.setDicomPatientID(dicomFile.getDicomPatientID());
            dicomBaseFile.setDicomPatientName(dicomFile.getDicomPatientName());
            dicomBaseFile.setDicomPatientSex(dicomFile.getDicomPatientSex());
            
            dicomBaseFile.setDicomSeriesDate(dicomFile.getDicomSeriesDate());
            dicomBaseFile.setDicomSeriesDescription(dicomFile.getDicomSeriesDescription());
            dicomBaseFile.setDicomSeriesDescriptionCodeSequence(dicomFile.getDicomSeriesDescriptionCodeSequence());
            dicomBaseFile.setDicomSeriesInstanceUID(dicomFile.getDicomSeriesInstanceUID());
            dicomBaseFile.setDicomSeriesNumber(dicomFile.getDicomSeriesNumber());
            dicomBaseFile.setDicomSeriesUID(dicomFile.getDicomSeriesUID());
            
            dicomBaseFile.setDicomStudyDate(dicomFile.getDicomStudyDate());
            dicomBaseFile.setDicomStudyDescription(dicomFile.getDicomStudyDescription());
            dicomBaseFile.setDicomStudyId(dicomFile.getDicomStudyId());
            
            
            
            dicomBaseFile.setFileName(dicomFile.getFileName());
            dicomBaseFile.setIdFile(dicomFile.getIdFile());
            
            dicomBaseFile.setDicomThumbnail(dicomFile.getDicomThumbnail());
            dicomBaseFile.setDicomThumbnail(dicomFile.getDicomThumbnailAsImage());
            dicomBaseFile.setFrameCount(1);
            dicomBaseFile.setModality(dicomFile.getModality());
            
            dicomBaseFile.setPatId(dicomFile.getPatId());
            
            dicomBaseFiles.add(dicomBaseFile);
            
            
        }
        return dicomBaseFiles;
        
    }
    
    
    
    FileDicom getSelectedInstance() {
        
        FileDicom selectedDicomFileBase = thumbnail.getSelectedInstance();
        FileDicom selectedDicomFile = null;
        
        for(FileDicom dicomFile: fdb) {
            
            if(dicomFile.getDicomInstanceUID().equals(selectedDicomFileBase.getDicomInstanceUID()))
                selectedDicomFile = dicomFile;
        }
        
        return selectedDicomFile;
        
    }
  //------------------------------------------------------------------------------------------
    
    private static final SqlDicomManager dbManager = new SqlDicomManager();
    private WindowEventHandler windowEventsHandler;
    private ButtonEventHandler buttonEventsHandler;
    private JButton jButtonDoneDicom;
    private ArrayList<FileDicom> fdb = new ArrayList<FileDicom>();
    private ArrayList<FileDicom> examFiles = new ArrayList<FileDicom>();
    private DicomGuiExitListener guiExitListener;
    
    
    
    
    private final class ButtonEventHandler implements ActionListener {
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(e.getSource() == jButtonLoadDicom)
                
                handleLoadButtonEvent();
            
            else if(e.getSource() == jButtonDeleteDicom)
                
                handleDeleteButtonEvent();
            
            
            else
                
                handleDoneButtonEvent();
                
                
            
            
        }
        
        
        
        ButtonEventHandler() {
            
            setButtonHandler(jButtonLoadDicom);
            setButtonHandler(jButtonDeleteDicom);
            setButtonHandler(jButtonDoneDicom);
            
        }
        
         private void setButtonHandler(JButton button) {
            
            ActionListener[] actionListeners = button.getActionListeners();
                  
            for(ActionListener actionListener: actionListeners)
                      button.removeActionListener(actionListener);
                  
                  
            button.addActionListener(this);
            
            
        }
       
        private void handleLoadButtonEvent() {
            
            JFileChooser jfc = new javax.swing.JFileChooser(new File(lastDir));
            jfc.setFileFilter(new FileDicomFilter());
	    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //--------------------------------------------------------------------------------------------------------------------------
            if(jfc.showDialog(new JLabel(""),
                                MessageBundle.getMessage("angal.radiology.dicom.load")
                   ) == JFileChooser.APPROVE_OPTION) {
                
                
                
                File selectedFile = jfc.getSelectedFile();
                File directory = null;
                try {
		   directory = selectedFile.getParentFile();
		} 
                catch (Exception exception) {}
                if (directory != null) {			
                    lastDir = directory.getAbsolutePath();
                }
                ForkJoinPool pool = TaskProgress.newTaskProgress();
                FileDicom dicomFile = SourceFiles.constructDicom(selectedFile, patient);
                //---------------------------------------------------------------------------
                if(dicomFile != null) {
                    
                    int loadImage = 0;
                    String studyUniqueID = dicomFile.getDicomStudyId();
                    String seriesUniqueID = dicomFile.getDicomSeriesUID();
                    String instanceUniqueID = dicomFile.getDicomInstanceUID();          
                    //----------------------------------------------------------------------------------------------------------------------
                    if(dbManager.existByUID(dicomFile)) {
                        
                        loadImage = -1;
                        //--------------------------------------------------------------------------------------------------
                        for(FileDicom loadedDicomFile: examFiles) {
                            //------------------------------------------------------------------------------------------------
                            if(loadedDicomFile.getDicomStudyId().equals(studyUniqueID)
                                     && loadedDicomFile.getDicomSeriesUID().equals(seriesUniqueID)
                                     && loadedDicomFile.getDicomInstanceUID().equals(instanceUniqueID)
                               ) {
                                
                                     
                                           loadImage = 1;
                                           //-----------------------------------------------------------------------------
                                           for(FileDicom existingDicomFile: fdb) {
                                    
                                               //--------------------------------------------------------------------------
                                               if(existingDicomFile.getDicomStudyId().equals(studyUniqueID)
                                                  && existingDicomFile.getDicomSeriesUID().equals(seriesUniqueID)
                                                  && existingDicomFile.getDicomInstanceUID().equals(instanceUniqueID)
                                                   ) {
                                        
                                        
                                                          loadImage = 0;
                                                          break;
                                               
                                                }
                                    
                                             }
                                
                                             break;
                                
                                    }
                            
                            
                         }
                        
                        
                   }
                    
                    //--------------------------------------------------------------------
                    else {
                        
                        loadImage = 1;
                        
                        
                    }
                    
                    //---------------------------------------------------------------
                    if(loadImage != 1) {
                        
                        //-------------------------------------------------------
                        if(loadImage == -1) {
                            
                            JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.dicom.associationerror"));
                        }
                    }
                    
                    //------------------------------------------------------------
                    else {
                        
                        fdb.add(dicomFile);
                        thumbnail.initialize();
                    }
                    
                }
                //-------------------------------------------------------------------
                else {
                     
                     JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.dicom.imageprocesserror"));
                }
                
                TaskProgress.endTaskProgress(pool);
                
            }
        
        
        }//End of Method
        
        private void handleDeleteButtonEvent() {
            
            Object[] options = { MessageBundle.getMessage("angal.radiology.dicom.delete.yes"), MessageBundle.getMessage("angal.radiology.dicom.delete.no") };
            int n = JOptionPane.showOptionDialog(DicomGui.this, MessageBundle.getMessage("angal.radiology.dicom.delete.request"), MessageBundle.getMessage("angal.radiology.dicom.delete.title"),
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "");

				
            if (n == 0) {
			
              
                FileDicom selectedDicomFile = thumbnail.getSelectedInstance();
               
                
                String instanceUID = selectedDicomFile.getDicomInstanceUID();
                
                

                int noDicomFiles = fdb.size();
                for(int i = 0; i < noDicomFiles; i++) {
                    if(fdb.get(i).getDicomInstanceUID().equals(instanceUID)) {
                        fdb.remove(i);
                        break;
                        
                    }
                }
                
            }
            
            thumbnail.initialize();
            
            ((DicomViewGui)DicomGui.this.jPanelDetail).reset();
            
            
            
        }
        
        private void handleDoneButtonEvent() {
            
            
            
            int noDicomFiles = fdb.size();
            
            ArrayList<FileDicom> fdb_out = new ArrayList<FileDicom>();
            for(int i = noDicomFiles - 1; i>=0; i--)
                fdb_out.add(fdb.get(i));
            
            DicomGui.this.guiExitListener.onExit(fdb_out);
            DicomGui.this.setVisible(false);
            DicomGui.this.dispose();
            
            
        
            
        }
        
        
    }
    
    
    
    private final class WindowEventHandler implements WindowListener, WindowStateListener  {
        
        @Override
        public void windowStateChanged(WindowEvent e) {
            
            
            
            
            if(e.getNewState() == Frame.NORMAL)
                
                setExtendedState(Frame.MAXIMIZED_BOTH);
            
        }
        
        @Override
        public void windowDeactivated(WindowEvent e) {
            
            
        }
        
        @Override
        public void windowActivated(WindowEvent e) {
            
            
        }
        @Override
        public void windowOpened(WindowEvent e) {
            
            
        }
        @Override
        public void windowClosing(WindowEvent e) {
		
           
        }
              
        @Override   
        public void windowClosed(WindowEvent e) {
		
                
                  
              
              
        }
              
        @Override      
        public void windowIconified(WindowEvent e) {
		
                  
	             
              
              
        }
         
        @Override
        public void windowDeiconified(WindowEvent e) {

	          
              
              
        }
              
              
        WindowEventHandler() {
            
            setWindowListenerHandler();
            setWindowStateListenerHandler();
            
        }
        
        
        private void setWindowListenerHandler() {
            
           WindowListener[] windowListeners = DicomGui.this.getWindowListeners();
                  
            for(WindowListener windowListener: windowListeners)
                      DicomGui.this.removeWindowListener(windowListener);
                  
                  
            DicomGui.this.addWindowListener(this);
            
            
        }
        
        private void setWindowStateListenerHandler() {
            
           WindowStateListener[] windowStateListeners = DicomGui.this.getWindowStateListeners();
                  
            for(WindowStateListener windowStateListener: windowStateListeners)
                      DicomGui.this.removeWindowStateListener(windowStateListener);
                  
                  
            DicomGui.this.addWindowStateListener(this);
            
            
        }
        
        
        
    }
    
    
    //----------------------------------------------------------------------------------------------------
      
}


