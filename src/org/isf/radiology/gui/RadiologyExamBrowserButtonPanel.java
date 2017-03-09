//------------------------------------------------------------------------------------------------------

package org.isf.radiology.gui;

//-----------------------------------------------------------------------------------------------------


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import imysqlds.ISqlDate;

import org.isf.radiology.gui.forms.RadiologyExamEditForm;
import org.isf.radiology.gui.forms.RadiologyExamNewForm;
import org.isf.radiology.gui.dicom.DicomGui;
import org.isf.radiology.manager.RadiologyExamDicomManager;
import org.isf.radiology.manager.RadiologyExamManager;
import org.isf.radiology.manager.RadiologyTableManager;
import org.isf.radiology.model.RadiologyExam;
import org.isf.radiology.model.RadiologyExamDicom;
import org.isf.utils.services.DbUpdateSubscriber;
import org.isf.utils.services.NetworkService;
import org.isf.utils.services.ReportService;
import org.isf.utils.services.TaskProgress;
import org.isf.dicom.manager.SqlDicomManager;
import org.isf.dicom.model.FileDicom;
import org.isf.generaldata.MessageBundle;
import org.isf.menu.gui.MainMenu;
import org.isf.patient.model.Patient;


//---------------------------------------------------------------------------------------------------------------
class RadiologyExamBrowserButtonPanel implements ActionListener {
        
        //-----------------------------------------------------------------------------------------
    
        @Override
        public void actionPerformed(ActionEvent event) {
            
            Object eventSource = event.getSource();
            
            if(eventSource == buttonClose) {
                DbUpdateSubscriber.unsubscribe(owner);
                owner.dispose();
                
            }
            
            else if(buttonNew != null && eventSource == buttonNew)
                new RadiologyExamNewForm(owner, owner.getSelectionPanel().getRadiologies()).setVisible(true);
            
            else if(buttonTableReport != null && eventSource == buttonTableReport) {
                
                
                if(owner.getTablePanel().getRowCount() > 0) {
                    
                    
                    
                    ForkJoinPool pool = TaskProgress.newTaskProgress();
                    String macAddress = NetworkService.getMacAddress();
                    RadiologyTableManager radiologyManager = new RadiologyTableManager();
                    ArrayList<RadiologyExam> radiologyExams = owner.getTablePanel().getLoadedRadiologyExams();
                    for(RadiologyExam radiologyExam: radiologyExams) {
                                                 
                           
                                                     radiologyManager.createRadiologyTable(radiologyExam.getDate(), radiologyExam.getPatient().getName(), 
                                                                 radiologyExam.getRadiology().getDescription(),
                                                                 macAddress
                                              
                                                     );

                         
                                                 
                    }
                    
                    IRadiologyReportFiles ireportFiles = new IRadiologyReportFiles(macAddress);
                    
                    Map<String, Object> parameters = ireportFiles.getParameters();
                    parameters.put("radiologist", MainMenu.getUserDesc());
                    ReportService.viewReport(false, ireportFiles.getTableReportFileName(), parameters);
                   
                    radiologyManager.deleteRadiologyTables(macAddress);
                        
                    TaskProgress.endTaskProgress(pool);
                    
                    
                    
                    
                    
                }
                
                else 
                    JOptionPane.showMessageDialog(null,
								MessageBundle.getMessage("angal.radiology.selectexam"), MessageBundle.getMessage("angal.radiology.hospital"),
								JOptionPane.PLAIN_MESSAGE);
                
            }
            
            else {
            
                if (owner.getTablePanel().getTable().getSelectedRow() < 0) {
						
                    
                    JOptionPane.showMessageDialog(null,
								
                                                  MessageBundle.getMessage("angal.radiology.selectexam"), MessageBundle.getMessage("angal.radiology.hospital"),
						
                                                  JOptionPane.PLAIN_MESSAGE);
						
					
                }
                
                else {
                
                    if(buttonEdit != null && eventSource == buttonEdit)
                
                        new RadiologyExamEditForm(owner, owner.getSelectionPanel().getRadiologies(), (RadiologyExam)owner.getTablePanel().getValueAt(owner.getTablePanel().getTable().getSelectedRow(), -1)).setVisible(true);
            
            
                   else if(buttonDelete != null && eventSource == buttonDelete) {
                
                
                      
						
                                          RadiologyExam radiologyExam = (RadiologyExam) (((RadiologyExamBrowserTablePanel) owner.getTablePanel()).getValueAt(owner.getTablePanel().getTable().getSelectedRow(), -1));
						

                                          int dialogButton = JOptionPane.YES_NO_OPTION;
                                          int dialogResult = JOptionPane.showConfirmDialog (null, MessageBundle.getMessage("angal.radiology.confirmdelete"),MessageBundle.getMessage("angal.radiology.warning"), dialogButton);
						
                                                
                                          if (dialogResult == JOptionPane.YES_OPTION) {
                                                      
                                                        
                                                   ForkJoinPool pool = TaskProgress.newTaskProgress();
                                                    
                                                     
                                                   synchronized(owner.getTablePanel()) {
                                                   
                                                       new RadiologyExamManager().deleteRadiologyExam(radiologyExam.getCode());
                                                       owner.getTablePanel().getTable().clearSelection();
                                                       
                                                       
                                                   }
                                                        
                                                        
                                        
                                                        
                                                   RadiologyExamDicomManager radiologyExamDicomManager = new RadiologyExamDicomManager();
                                        
                                        
                                                   ArrayList<RadiologyExamDicom> dicomRecords = radiologyExamDicomManager.getRadiologyExamDicoms(radiologyExam.getCode());
                                                   SqlDicomManager dicomManager = new SqlDicomManager();
                                                   try {
                                                   
                                                       for(RadiologyExamDicom dicomRecord: dicomRecords)
                                                         dicomManager.deleteFile(dicomRecord.getDicomFile().getIdFile());
                                                       
                                                       radiologyExamDicomManager.deleteRadiologyExamDicoms(radiologyExam.getCode());
                                                       
                                                   }
                                                   catch(Exception e) {}
                                        
                                        //-----------------------------------------------------------------------
                                        
                                                   
                                        
                                        
                                                   TaskProgress.endTaskProgress(pool);
						
                                          
                       
                                          
                                        
                                        
						
                                                
                                        
                                          }
                                                
					
                        }
                    
                   else if(buttonExamReport != null && eventSource == buttonExamReport) {
                       
                       
                        RadiologyExam  radiologyExam = (RadiologyExam) (((RadiologyExamBrowserTablePanel) owner.getTablePanel()).getValueAt(owner.getTablePanel().getTable().getSelectedRow(), -1));   
                        IRadiologyReportFiles iReportFile = new IRadiologyReportFiles(radiologyExam);
                        ForkJoinPool pool = TaskProgress.newTaskProgress();
                        Map<String, Object> parameters = iReportFile.getParameters();
                        parameters.put("radiologist", radiologyExam.getRadiologist());
                        ReportService.viewReport(true, iReportFile.getExamReportFileName(), parameters);
                        TaskProgress.endTaskProgress(pool);
                                            
        
            
                                            
                         
            
                                           
                                             
                                           
                        
                       
                   }
                    
                   else {
                       
                       RadiologyExam  radiologyExam = (RadiologyExam) (((RadiologyExamBrowserTablePanel) owner.getTablePanel()).getValueAt(owner.getTablePanel().getTable().getSelectedRow(), -1));
                       ArrayList<RadiologyExamDicom> radiologyExamDicoms = new RadiologyExamDicomManager().getRadiologyExamDicoms(radiologyExam.getCode());
                       ArrayList<FileDicom> dicomFiles = new ArrayList<FileDicom>();
                       for(RadiologyExamDicom radiologyExamDicom: radiologyExamDicoms) {
                            dicomFiles.add(radiologyExamDicom.getDicomFile());
                            
                       }
                       
                       new DicomGui(patient, null, null, dicomFiles, true);
                       
                   }
                    
                    
                  
                
                
            }//Not New, and There is an Exam
                
         }//Not New
        
        }//End of Method
        
        //--------------------------------------------------------------------------------------
        
         RadiologyExamBrowserButtonPanel(RadiologyExamBrowser owner, Patient patient) {
		
            this.owner = owner;
            this.patient = patient;
            
            jButtonPanel = new JPanel();
            jButtonPanel.setLayout(new GridLayout());
            
            buttonExamReport = new JButton(MessageBundle.getMessage("angal.radiology.report.exam"));
            buttonExamReport.setMnemonic(KeyEvent.VK_R);
	    buttonExamReport.addActionListener(this);
            
            buttonClose = new JButton(MessageBundle.getMessage("angal.radiology.close"));
            buttonClose.setMnemonic(KeyEvent.VK_C);
	    buttonClose.addActionListener(this);
            
            if(patient!=null) {
                
                buttonDicom = new JButton(MessageBundle.getMessage("angal.radiology.dicom.profile"));  //$NON-NLS-1$
                buttonDicom.setMnemonic(KeyEvent.VK_D);
                buttonDicom.setIcon(new ImageIcon("rsc/icons/radiology_dicom_button.png")); //$NON-NLS-1$
                jButtonPanel.add(buttonDicom, null);
                buttonDicom.addActionListener(this);
                
                buttonNew = null;
                buttonEdit = null;
                buttonDelete = null;
                buttonTableReport = null;
                
            }
            else {
            
                buttonNew = new JButton(MessageBundle.getMessage("angal.radiology.new"));
                buttonNew.setMnemonic(KeyEvent.VK_N);
	        buttonNew.addActionListener(this);
	        jButtonPanel.add(buttonNew, null);
            
                buttonEdit = new JButton(MessageBundle.getMessage("angal.radiology.edit"));
                buttonEdit.setMnemonic(KeyEvent.VK_E);
	        buttonEdit.addActionListener(this);
	        jButtonPanel.add(buttonEdit, null);
            
                buttonDelete = new JButton(MessageBundle.getMessage("angal.radiology.delete"));
                buttonDelete.setMnemonic(KeyEvent.VK_D);
	        buttonDelete.addActionListener(this);
	        jButtonPanel.add(buttonDelete, null);
                
                buttonTableReport = new JButton(MessageBundle.getMessage("angal.radiology.report.exams"));
                buttonTableReport.setMnemonic(KeyEvent.VK_R);
	        buttonTableReport.addActionListener(this);
	        jButtonPanel.add(buttonTableReport, null);
                
                buttonDicom = null;
            
            }
               
	       
            jButtonPanel.add(buttonExamReport, null);
	    jButtonPanel.add(buttonClose, null);
                        
			
	
		
	
  
        }
         
        JPanel getJButtonPanel() {
           return jButtonPanel;
        }
        
        //-----------------------------------------------------------------------------------------------
        private RadiologyExamBrowser owner;
        private JPanel jButtonPanel;
        private JButton buttonNew;
        private JButton buttonEdit;
        private JButton buttonDelete;
        private JButton buttonExamReport;
        private JButton buttonTableReport;
        private JButton buttonClose;
        private JButton buttonDicom;
        private Patient patient;
        
       
        //-----------------------------------------------------------------------------------------------------------
        private class IRadiologyReportFiles {
        
        
            IRadiologyReportFiles(String macAddress) {
        
        
            
                parameters.put("macAddress", macAddress);
        
            }
        
            IRadiologyReportFiles(final RadiologyExam radiologyExam) {
            
          
            
            
               parameters.put("patientName", radiologyExam.getPatient().getName());
               parameters.put("examType", radiologyExam.getRadiology().getDescription());
               parameters.put("examDate", ISqlDate.toISqlDate(radiologyExam.getDate()).toString());
               parameters.put("clinicalHistory", radiologyExam.getClinicalHistory());
               parameters.put("pastExams", radiologyExam.getComparison());
               parameters.put("technique", radiologyExam.getTechnique());
               parameters.put("finding", radiologyExam.getFinding());
               parameters.put("impression", radiologyExam.getImpression());
            
          }
        
        
        
           Map<String,Object> getParameters() {
            
            
               return parameters;
        
           }
        
        
        
           String getExamReportFileName() {
            
            
               return "RadiologyExamReport";
           
        
           }
        
        
        
        
           String getTableReportFileName() {
            
            
               return "RadiologyTableReport";
           
        
           }
        
           //------------------------------------------------------------------------------------
            private final Map<String,Object> parameters;
        
        
            {
            
            
                parameters = ReportService.getHospitalHeader();
                //parameters.put("radiologist", MainMenu.getUserDesc());
            
            
           }
        
        
        //--------------------------------------------------------------------------------------------------
        
        
    }
    
       
   //----------------------------------------------------------------------------------------------------------
    
}
    
