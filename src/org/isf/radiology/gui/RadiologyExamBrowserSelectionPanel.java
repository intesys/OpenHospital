
//-------------------------------------------------------------------------------
package org.isf.radiology.gui;
//---------------------------------------------------------------------------------
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ForkJoinPool;

import org.isf.radiology.manager.RadiologyManager;
import org.isf.radiology.model.Radiology;
import org.isf.radiology.model.RadiologyType;
import org.isf.utils.services.TaskProgress;
import org.isf.utils.jobjects.VoDateTextField;
import org.isf.generaldata.MessageBundle;
import org.isf.patient.gui.SelectPatient;
import org.isf.patient.model.Patient;



//-----------------------------------------------------------------------------------------------------

class RadiologyExamBrowserSelectionPanel implements ActionListener, SelectPatient.SelectionListener {
        
        
        //------------------------------------------------------------------------------------
        
        @Override
         public void actionPerformed(ActionEvent event) {
             
             Object eventItem = event.getSource();
             
             if(eventItem.equals(comboRadiologies)) {
                 
                 typeSelected = ((Radiology) comboRadiologies.getSelectedItem())
							.getCode();
					
                
                 
             }
             
             else if(eventItem.equals(jButtonPickPatient)) {
                 
                 if(patientSelected == null) {
					
                                            
                     ForkJoinPool pool = TaskProgress.newTaskProgress();
                                            
                     SelectPatient sp = new SelectPatient(owner, patientSelected);
                     sp.addSelectionListener(this);
                     sp.pack();
                     sp.setVisible(true);
                                    
                     TaskProgress.endTaskProgress(pool);
                                         
                                           
                                            
                                            
                                        
                 }
                                        
                                        
                 else {
                                            
                                            
                     jTextFieldPatient.setText("");
                     jButtonPickPatient.setText(MessageBundle.getMessage("angal.radiology.patientfilter"));
                     patientSelected = null;
                                            
                                        
                 }
                 
             }
             
             else {
                 
                          GregorianCalendar dDateTo = null, dDateFrom = null;
                          try {                  
                          
                              
                              dDateFrom = dateFrom.getDate();
                              dDateTo = dateTo.getDate();
                              
                          }
                          
                          catch(Exception exception) {}
                          
                          finally {
                              
                              if(dDateFrom != null && dDateTo != null) {
                                  
                                  Calendar today = GregorianCalendar.getInstance();
                                  
                                  if(! (dDateTo.after(today) || dDateFrom.after(today) || dDateFrom.after(dDateTo)) ) {
                              
                                      
                                      dDateFrom.set(Calendar.HOUR_OF_DAY, 0);
                                      dDateFrom.set(Calendar.MINUTE, 0);
                                      dDateFrom.set(Calendar.SECOND, 0);
                                      dDateFrom.set(Calendar.MILLISECOND, 0);
                                      owner.getTablePanel().updateTable(owner.getRadiologyExams(), jTextFieldPatient.getText(), typeSelected,
              
                                              dDateFrom, dDateTo);
                                  }
                                  else
                              
                                      JOptionPane.showMessageDialog(null, "Invalid Date Range");
                              }
                              
                          }
                              
                          
                          
                          
                          
                                        
					
                 
             }
             
             
             
         
     }
         
     @Override
     public void patientSelected(Patient patient) {
		patientSelected = patient;
		jTextFieldPatient.setText(patientSelected.getCode().toString());
		jTextFieldPatient.setEditable(false);
		jButtonPickPatient.setText(MessageBundle.getMessage("angal.radiology.removepatient"));
                jSelectionPanel.repaint();
                jSelectionPanel.revalidate();
     
         
     }
    
       
    //---------------------------------------------------------------------------------------------------------
     
    RadiologyExamBrowserSelectionPanel(RadiologyExamBrowser owner, int frameHeight) {
            
                        this.owner = owner;
                        
			jSelectionPanel = new JPanel();
			jSelectionPanel.setPreferredSize(new Dimension(210, frameHeight));
			jSelectionPanel.add(new JLabel(MessageBundle.getMessage("angal.radiology.selectaradiologytype")), null);
                        jSelectionPanel.add(getComboRadiologies(), null);
                        
                        jSelectionPanel.add(Box.createHorizontalStrut(6));
                        jSelectionPanel.add(new JLabel(MessageBundle.getMessage("angal.common.datem") +": "+ MessageBundle.getMessage("angal.radiology.from")), null);
                        jSelectionPanel.add(Box.createHorizontalStrut(10));
                        dateFrom = getDatePanel(false);
                        jSelectionPanel.add(dateFrom);
                        jSelectionPanel.add(Box.createHorizontalStrut(6));
                        jSelectionPanel.add(new JLabel(MessageBundle.getMessage("angal.common.datem") +": "+MessageBundle.getMessage("angal.radiology.to") +"     "), null);
			jSelectionPanel.add(Box.createHorizontalStrut(12));
                        dateTo = getDatePanel(true);
                        jSelectionPanel.add(dateTo);
                        
                        jSelectionPanel.add(getJPanelPatient());
                        
                        
			jSelectionPanel.add(getSearchButton());
			
			
		
		
            
        
    }
        
        
    JPanel getJSelectionPanel() {
          return jSelectionPanel;
        
    }
    
    ArrayList<Radiology> getRadiologies() {
            return radiologies;
        
    }
    
    String getTypeSelected() {
          return typeSelected;
        
    }
    
    VoDateTextField getDateFrom() {
            return dateFrom;
        
    }
    
    VoDateTextField getDateTo() {
            return dateTo;
        
    }
    
    JTextField getJTextFieldPatient() {
            return jTextFieldPatient;
        
    }
    
    
    
    //-----------------------------------------------------------------------------------------------------------
     
    private RadiologyExamBrowser owner;
    private JPanel jSelectionPanel;
    private ArrayList<Radiology> radiologies;
    private JComboBox comboRadiologies;
    private String typeSelected = "";
    private VoDateTextField dateFrom = null;
    private VoDateTextField dateTo = null;
    private JButton jButtonPickPatient;
    private JTextField jTextFieldPatient;
    private Patient patientSelected = null;
    private JButton jButtonSearch;

    private static VoDateTextField getDatePanel(boolean setNow) {
            
		        VoDateTextField date= null;
    
			GregorianCalendar now = new GregorianCalendar();
                        if(!setNow)
                            now.add(GregorianCalendar.WEEK_OF_YEAR, -1);
                        
			date = new VoDateTextField("dd/mm/yyyy", now, 10);
                        if(setNow)
			   date.setDate(now);
                        
                            
		
		        return date;
	
    }
        
        
        
    private JComboBox getComboRadiologies() {
            
                
		RadiologyManager managerRadiologies = new RadiologyManager();
	        comboRadiologies = new JComboBox();
		comboRadiologies.setPreferredSize(new Dimension(210, 30));
		comboRadiologies.addItem(new Radiology("", MessageBundle.getMessage("angal.radiology.all"), new RadiologyType("", "")));
		radiologies = managerRadiologies.getRadiologies(); 
			for (Radiology elem : radiologies) {
				comboRadiologies.addItem(elem);
			}
                comboRadiologies.addActionListener(this);
                return comboRadiologies;
        
    }
        
        
    
    private JPanel getJPanelPatient() {
		
                JPanel jPanelPatient = new JPanel(new FlowLayout(FlowLayout.LEFT));
                
                jTextFieldPatient = new JTextField();
		jTextFieldPatient.setText(""); //$NON-NLS-1$
		jTextFieldPatient.setPreferredSize(new Dimension(50,20));
		jTextFieldPatient.setEditable(false);
		
                
                jButtonPickPatient = new JButton();
		jButtonPickPatient.setText(MessageBundle.getMessage("angal.radiology.patientfilter"));  //$NON-NLS-1$
		jButtonPickPatient.setMnemonic(KeyEvent.VK_P);
		jButtonPickPatient.setIcon(new ImageIcon("rsc/icons/radiology_pick_patient_button.png")); //$NON-NLS-1$
                jButtonPickPatient.addActionListener(this);
                
                jPanelPatient.add(jButtonPickPatient);
                jPanelPatient.add(jTextFieldPatient);
                
		
			
			
		
		return jPanelPatient;
	
    }
        
         
    private JButton getSearchButton() {
		jButtonSearch = new JButton(MessageBundle.getMessage("angal.radiology.search"));
		jButtonSearch.setMnemonic(KeyEvent.VK_S);
                jButtonSearch.addActionListener(this);
                return jButtonSearch;
    
    
  
         
    }
    
    //-------------------------------------------------------------------------------------------------------------
         
   }
    
    //-----------------------------------------------------------------------------
    
    
