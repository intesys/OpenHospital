//----------------------------------------------------Package----------------------------------------------------------------------
package org.isf.radiology.gui.forms;

//----------------------------------------------------Imports------------------------------------------------------------------------

//****************************************************Platform*******************************************************
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ForkJoinPool;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

//***************************************************External Libraries********************************************************
                                                   
import com.toedter.calendar.JDateChooser;
import org.joda.time.LocalDateTime;

//***************************************************Internal Packages*********************************************************

import org.isf.dicom.service.DicomIoOperations;
import org.isf.dicom.model.FileDicom;
import org.isf.generaldata.GeneralData;
import org.isf.generaldata.MessageBundle;
import org.isf.patient.gui.SelectPatient;
import org.isf.patient.gui.SelectPatient.SelectionListener;
import org.isf.patient.model.Patient;
import org.isf.radiology.gui.RadiologyExamBrowser;
import org.isf.radiology.gui.dicom.DicomGui.DicomGuiExitListener;
import org.isf.radiology.manager.RadiologyExamDicomManager;
import org.isf.radiology.model.Radiology;
import org.isf.radiology.model.RadiologyExam;
import org.isf.radiology.model.RadiologyExamDicom;
import org.isf.utils.services.TaskProgress;



//----------------------------------------------------Class Declaration-----------------------------------------------------------
abstract class RadiologyExamForm extends JFrame implements SelectionListener, DicomGuiExitListener {

    //////////////////////////////////////////////////Frame////////////////////////////
    protected final RadiologyExamBrowser owner;
    protected final RadiologyExam radiologyExam;
    private static final Dimension LabelDimension = new Dimension(50, 20);
    
    private ArrayList<Radiology> radiologies;
    
    
   
    
    protected RadiologyExamForm(RadiologyExamBrowser owner, ArrayList<Radiology> radiologies, RadiologyExam radiologyExam) {

        super();
        ForkJoinPool pool = TaskProgress.newTaskProgress();
        this.owner = owner;
        this.radiologies = radiologies;
        this.radiologyExam = radiologyExam;
        initialize();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(getWindowTitle());
        this.setBounds(this.getBounds().x, this.getBounds().y, this.getBounds().width, this.getBounds().height - 50);
        this.setIconImage(new ImageIcon("rsc/icons/oh.png").getImage());
        TaskProgress.endTaskProgress(pool);

    }
    
    
     protected abstract String getWindowTitle();
     
     private void initialize() {

        add(getJPanelNorth(), BorderLayout.NORTH);
        add(getJPanelCenter(), BorderLayout.CENTER);
        add(getJPanelSouth(), BorderLayout.SOUTH);
        pack();

    }
    
    //////////////////////////////////////////////////North Panel//////////////////////////////////////////////////////
    
    private JPanel jPanelNorth;
    
    private JPanel getJPanelNorth() {
        if (jPanelNorth == null) {
            jPanelNorth = new JPanel();
            jPanelNorth.setLayout(new BoxLayout(jPanelNorth, BoxLayout.Y_AXIS));
            jPanelNorth.add(getJPanelRadiologies());
            jPanelNorth.add(getJPanelDate());
            jPanelNorth.add(getJPanelPatient());

        }
        return jPanelNorth;

    }

    

    

    //////////////////////////////////////////////////Radiologies/////////////////////////////////////////////////////////
    
    private JPanel jPanelRadiologies;
    private JLabel jLabelRadiologies;
    protected JComboBox comboRadiologies;
    
    
    private JPanel getJPanelRadiologies() {
        if (jPanelRadiologies == null) {

            jPanelRadiologies = new JPanel(new FlowLayout(FlowLayout.LEFT));
            jPanelRadiologies.add(getJLabelRadiologies());
            jPanelRadiologies.add(getComboRadiologies());

        }
        return jPanelRadiologies;

    }
    
    private JLabel getJLabelRadiologies() {
        if (jLabelRadiologies == null) {
            jLabelRadiologies = new JLabel();
            jLabelRadiologies.setText(MessageBundle.getMessage("angal.radiology.exam"));
            jLabelRadiologies.setPreferredSize(LabelDimension);
        }
        return jLabelRadiologies;

    }
    
    private JComboBox getComboRadiologies() {

        //RadiologyManager managerRadiologies = new RadiologyManager();

        if (comboRadiologies == null) {
            comboRadiologies = new JComboBox();
            comboRadiologies.setPreferredSize(new Dimension(300, 30));

            //ArrayList<Radiology> type = managerRadiologies.getRadiologies(); // for

            for (Radiology elem : radiologies) {
                comboRadiologies.addItem(elem);
            }

            Radiology defaultRadiology = getDefaultRadiology();

            if (defaultRadiology != null) {
                comboRadiologies.setSelectedItem(getDefaultRadiology());
            }

        }

        return comboRadiologies;
    }

    

    
    protected abstract Radiology getDefaultRadiology();
    
    
        
    
    
    
    //////////////////////////////////////////////Patient///////////////////////////////////////////
    
    private JPanel jPanelPatient;
    private JLabel jLabelPatient;
    private JTextField jTextFieldPatient;
    private static final Dimension PatientDimension = new Dimension(200, 20);
    private JButton jButtonPatient;
    protected Patient patientSelected = null;
    
    
    
    
    
    
    
     private JPanel getJPanelPatient() {
        if (jPanelPatient == null) {
            jPanelPatient = new JPanel(new FlowLayout(FlowLayout.LEFT));
            jPanelPatient.add(getJLabelPatient());
            jPanelPatient.add(getJTextFieldPatient());
            jPanelPatient.add(getJButtonPatient());

        }
        return jPanelPatient;

    }
     
      
     private JLabel getJLabelPatient() {
        if (jLabelPatient == null) {
            jLabelPatient = new JLabel();
            jLabelPatient.setText(MessageBundle.getMessage("angal.radiology.patient"));
            jLabelPatient.setPreferredSize(LabelDimension);
        }
        return jLabelPatient;
    }
     
      private JTextField getJTextFieldPatient() {
        if (jTextFieldPatient == null) {
            jTextFieldPatient = new JTextField();
            jTextFieldPatient.setPreferredSize(PatientDimension);
            jTextFieldPatient.setEditable(false);
            jTextFieldPatient.setText(getPatientName());
        }
        return jTextFieldPatient;

    }
      
    protected abstract String getPatientName();
    
    private JButton getJButtonPatient() {
        if (jButtonPatient == null) {
            jButtonPatient = new JButton();
            jButtonPatient.setText(getPatientButtonLabel());
            jButtonPatient.setMnemonic(KeyEvent.VK_P);
            jButtonPatient.setIcon(new ImageIcon("rsc/icons/pick_patient_button.png"));
            jButtonPatient.setToolTipText(getPatientButtonToolTipText());
            jButtonPatient.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SelectPatient sp = new SelectPatient(RadiologyExamForm.this, patientSelected);
                    sp.addSelectionListener(RadiologyExamForm.this);
                    sp.pack();
                    sp.setVisible(true);
                }
            });
        }
        return jButtonPatient;
    }
    
    protected abstract String getPatientButtonLabel();
    protected abstract String getPatientButtonToolTipText();
   
    
    @Override
    public void patientSelected(Patient patient) {

        patientSelected = patient;

        jTextFieldPatient.setText(patientSelected.getName());
        jTextFieldPatient.setEditable(false);
        jButtonPatient.setText(getPatientButtonLabel());
        jButtonPatient.setToolTipText(getPatientButtonToolTipText());

    }
    
    

    
   
    
   
    
    /////////////////////////////////////////////////////////Date//////////////////////////////////////////////////////
    
    private JPanel jPanelDate;
    private JLabel jLabelDate;
    private JDateChooser jCalendarDate;
    
    
    
    
    

    
    private JPanel getJPanelDate() {
        if (jPanelDate == null) {
            jPanelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
            jPanelDate.add(getJLabelDate());
            jPanelDate.add(getJCalendarDate());
        }
        return jPanelDate;

    }
    
    
    private JLabel getJLabelDate() {
        if (jLabelDate == null) {
            jLabelDate = new JLabel();
            jLabelDate.setText(MessageBundle.getMessage("angal.radiology.datem"));
            jLabelDate.setPreferredSize(LabelDimension);
        }
        return jLabelDate;

    }

    
    private JDateChooser getJCalendarDate() {

        if (jCalendarDate == null) {
            jCalendarDate = new JDateChooser();//RememberDates.getLastLabExamDateGregorian().getTime()); //To remind last used
            jCalendarDate.setLocale(new Locale(GeneralData.LANGUAGE));
            final String DATE_FORMAT = "dd/MM/yy";
            jCalendarDate.setDateFormatString(DATE_FORMAT);
            jCalendarDate.setDate(getDefaultDate());
        }
        return jCalendarDate;

    }
    
    protected abstract Date getDefaultDate();
    
    ///////////////////////////////////////////////////////Center Panel//////////////////////////
    
    private JPanel jPanelCenter;
    
    
    private JPanel getJPanelCenter() {
        if (jPanelCenter == null) {
            jPanelCenter = new JPanel();
            jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.Y_AXIS));
            jPanelCenter.add(getJPanelClinicalHistory());
            jPanelCenter.add(getJPanelComparison());
            jPanelCenter.add(getJPanelTechnique());
            jPanelCenter.add(getJPanelDiCom());
            jPanelCenter.add(getJPanelFinding());
            jPanelCenter.add(getJPanelImpression());

        }
        return jPanelCenter;
    }
    
    //////////////////////////////////////////////////////Clinical History///////////////////////////////////
    
    private JPanel jPanelClinicalHistory;
    private JScrollPane jScrollPaneClinicalHistory;
    private JTextArea jTextAreaClinicalHistory;
   
    
    
     private JPanel getJPanelClinicalHistory() {
        if (jPanelClinicalHistory == null) {
            jPanelClinicalHistory = new JPanel();
            jPanelClinicalHistory.setLayout(new BoxLayout(jPanelClinicalHistory, BoxLayout.Y_AXIS));
            jPanelClinicalHistory.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.clinicalhistory")));
            jPanelClinicalHistory.add(getJScrollPaneClinicalHistory());
        }
        return jPanelClinicalHistory;
    }
     
     private JScrollPane getJScrollPaneClinicalHistory() {
        if (jScrollPaneClinicalHistory == null) {
            jScrollPaneClinicalHistory = new JScrollPane();
            jScrollPaneClinicalHistory.setViewportView(getJTextAreaClinicalHistory());
        }
        return jScrollPaneClinicalHistory;
    }
     
     private JTextArea getJTextAreaClinicalHistory() {
        if (jTextAreaClinicalHistory == null) {
            jTextAreaClinicalHistory = new JTextArea(3, 50);
            jTextAreaClinicalHistory.setText(getClinicalHistory());

        }
        return jTextAreaClinicalHistory;
    }
     
     protected abstract String getClinicalHistory();

    
    //////////////////////////////////////////////////////Comparison/////////////////////////////
   
    private JPanel jPanelComparison;
    private JScrollPane jScrollPaneComparison;
    private JTextArea jTextAreaComparison;
    
    private JPanel getJPanelComparison() {
        if (jPanelComparison == null) {
            jPanelComparison = new JPanel();
            jPanelComparison.setLayout(new BoxLayout(jPanelComparison, BoxLayout.Y_AXIS));
            jPanelComparison.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.comparison")));
            jPanelComparison.add(getJScrollPaneComparison());
        }
        return jPanelComparison;
    }
    
    
    
    
    
    private JScrollPane getJScrollPaneComparison() {
        if (jScrollPaneComparison == null) {
            jScrollPaneComparison = new JScrollPane();
            jScrollPaneComparison.setViewportView(getJTextAreaComparison());
        }
        return jScrollPaneComparison;
    }

    private JTextArea getJTextAreaComparison() {
        if (jTextAreaComparison == null) {
            jTextAreaComparison = new JTextArea(3, 50);
            jTextAreaComparison.setText(getComparison());

        }
        return jTextAreaComparison;
    }

    
    protected abstract String getComparison();
   
    ///////////////////////////////////////////////////////Technique///////////////////////////////////////
     
     private JPanel jPanelTechnique;
     private JScrollPane jScrollPaneTechnique; 
     private JTextArea jTextAreaTechnique;
     
    
     
     private JPanel getJPanelTechnique() {
        if (jPanelTechnique == null) {
            jPanelTechnique = new JPanel();
            jPanelTechnique.setLayout(new BoxLayout(jPanelTechnique, BoxLayout.Y_AXIS));
            jPanelTechnique.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.technique")));
            jPanelTechnique.add(getJScrollPaneTechnique());
        }
        return jPanelTechnique;
    }
     
     
     
     private JScrollPane getJScrollPaneTechnique() {
        if (jScrollPaneTechnique == null) {
            jScrollPaneTechnique = new JScrollPane();
            jScrollPaneTechnique.setViewportView(getJTextAreaTechnique());
        }
        return jScrollPaneTechnique;
    }
     
    private JTextArea getJTextAreaTechnique() {
        if (jTextAreaTechnique == null) {
            jTextAreaTechnique = new JTextArea(3, 50);
            jTextAreaTechnique.setText(getTechnique());

        }
        return jTextAreaTechnique;
    }
     
      protected abstract String getTechnique();
    //////////////////////////////////////////////////////DICOM////////////////////////////////////////////////////////////////////////////
    
     
    private JPanel jPanelDiCom;
    private JTable jTable = null;
    private final int[] pColumwidth = {190};
    private final boolean[] columnsResizable = {true};
    private final int[] maxWidth = {190};
    private JButton jButtonSetDiCom;
    protected final ArrayList<FileDicom> dicomFiles = new ArrayList<FileDicom>();
    private final String[] pColumns = {MessageBundle.getMessage("angal.radiology.dicom.id")};
    private final boolean[] columnsVisible = {true};
    
    
    
    
   
  
    private JPanel getJPanelDiCom() {

        if (jPanelDiCom == null) {
            jPanelDiCom = new JPanel();
            jPanelDiCom.setLayout(new BoxLayout(jPanelDiCom, BoxLayout.Y_AXIS));

            jPanelDiCom.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.dicom")));

            jPanelDiCom.add(new JScrollPane(getJTable()));
            //java.awt.BorderLayout.CENTER);

            JPanel dicomButtonJPanel = new JPanel();
            dicomButtonJPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            dicomButtonJPanel.add(getJButtonSetDiCom());
            jPanelDiCom.add(dicomButtonJPanel);

        }
        return jPanelDiCom;

    }
    
    private JTable getJTable() {
        if (jTable == null) {
            RadiologyExamForm.DicomFilesBrowsingModel model = new DicomFilesBrowsingModel();
            jTable = new JTable(model);
            int columnLengh = pColumwidth.length;

            for (int i = 0; i < columnLengh; i++) {
                jTable.getColumnModel().getColumn(i).setMinWidth(pColumwidth[i]);
                if (!columnsResizable[i]) {
                    jTable.getColumnModel().getColumn(i).setMaxWidth(maxWidth[i]);
                }
            }

            jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            if(radiologyExam != null) {
                
                ArrayList<RadiologyExamDicom> radiologyExamDicoms = new RadiologyExamDicomManager().getRadiologyExamDicoms(radiologyExam.getCode());
                for(RadiologyExamDicom radiologyExamDicom: radiologyExamDicoms) {
                            dicomFiles.add(radiologyExamDicom.getDicomFile());
                            
                }
                
                updateJTable();
                
            }
        }
        return jTable;

    }

    
    private JButton getJButtonSetDiCom() {

        if (jButtonSetDiCom == null) {
            jButtonSetDiCom = new JButton();
            jButtonSetDiCom.setText(MessageBundle.getMessage("angal.radiology.dicom.profile"));  //$NON-NLS-1$
            jButtonSetDiCom.setMnemonic(KeyEvent.VK_D);
            jButtonSetDiCom.setIcon(new ImageIcon("rsc/icons/radiology_dicom_button.png")); //$NON-NLS-1$
            jButtonSetDiCom.setToolTipText(getDicomButtonToolTipText());  //$NON-NLS-1$

            jButtonSetDiCom.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    /////////////////////**************////////////////////
                    if (patientSelected == null) {
                        JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.selectpatient"));
                    } else {
                        openDicomGui();
                    }

                    /////////////////////*********////////////////////////////
                }

            });
        }
        return jButtonSetDiCom;

    }
    
    protected abstract String getDicomButtonToolTipText();
    protected abstract void openDicomGui();
     

    
    
    
    
    
    
    
    
    
   
    
    
    
    @Override
    public void onExit(java.util.ArrayList<FileDicom> dicomFiles) {

        this.dicomFiles.removeAll(this.dicomFiles);
        this.dicomFiles.addAll(dicomFiles);

        updateJTable();

    }

     
      private void updateJTable() {

        RadiologyExamForm.DicomFilesBrowsingModel model = new DicomFilesBrowsingModel();
        model.fireTableDataChanged();
        jTable.updateUI();

    }


    private class DicomFilesBrowsingModel extends DefaultTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            if (RadiologyExamForm.this.dicomFiles == null) {
                return 0;
            }
            return RadiologyExamForm.this.dicomFiles.size();
        }

        @Override
        public String getColumnName(int c) {
            return pColumns[getNumber(c)];
        }

        @Override
        public int getColumnCount() {
            int c = 0;
            for (int i = 0; i < columnsVisible.length; i++) {
                if (columnsVisible[i]) {
                    c++;
                }
            }
            return c;
        }

        @Override
        public Object getValueAt(int r, int c) {

            if (c == -1) {
                return dicomFiles.get(dicomFiles.size() - r - 1);

            } else if (getNumber(c) == 0) {
                return RadiologyExamForm.this.dicomFiles.get(RadiologyExamForm.this.dicomFiles.size() - r - 1).getDicomInstanceUID();

            } else {
                return null;
            }
        }

        @Override
        public boolean isCellEditable(int arg0, int arg1) {

            return false;
        }

        protected int getNumber(int col) {

            int n = col;
            int i = 0;
            do {

                if (!columnsVisible[i]) {

                    n++;
                }

                i++;

            } while (i < n);

            while (!columnsVisible[n]) {
                n++;
            }

            return n;

        }

        ///////////////////////////////////////////////////////////////////////////
    }

    
    ////////////////////////////////////////////////////////Finding//////////////////////////////////////////////////////
    
    private JPanel jPanelFinding;
    private JScrollPane jScrollPaneFinding;
    private JTextArea jTextAreaFinding;
    
    
    
    
    private JPanel getJPanelFinding() {
        if (jPanelFinding == null) {
            jPanelFinding = new JPanel();
            jPanelFinding.setLayout(new BoxLayout(jPanelFinding, BoxLayout.Y_AXIS));
            jPanelFinding.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.finding")));
            jPanelFinding.add(getJScrollPaneFinding());
        }
        return jPanelFinding;
    }

    private JScrollPane getJScrollPaneFinding() {
        if (jScrollPaneFinding == null) {
            jScrollPaneFinding = new JScrollPane();
            jScrollPaneFinding.setViewportView(getJTextAreaFinding());
        }
        return jScrollPaneFinding;
    }

     private JTextArea getJTextAreaFinding() {
        if (jTextAreaFinding == null) {
            jTextAreaFinding = new JTextArea(3, 50);
            jTextAreaFinding.setText(getFinding());

        }
        return jTextAreaFinding;
    }

    protected abstract String getFinding();
    
    ////////////////////////////////////////////////////////Impression////////////////////////////////////////////////////

    private JPanel jPanelImpression;
    private JScrollPane jScrollPaneImpression;
    private JTextArea jTextAreaImpression;
    
    
    
    
    
    private JPanel getJPanelImpression() {
        if (jPanelImpression == null) {
            jPanelImpression = new JPanel();
            jPanelImpression.setLayout(new BoxLayout(jPanelImpression, BoxLayout.Y_AXIS));
            jPanelImpression.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY), MessageBundle.getMessage("angal.radiology.impression")));
            jPanelImpression.add(getJScrollPaneImpression());
        }
        return jPanelImpression;
    }

    
    private JScrollPane getJScrollPaneImpression() {
        if (jScrollPaneImpression == null) {
            jScrollPaneImpression = new JScrollPane();
            jScrollPaneImpression.setViewportView(getJTextAreaImpression());
        }
        return jScrollPaneImpression;
    }
    
     private JTextArea getJTextAreaImpression() {
        if (jTextAreaImpression == null) {
            jTextAreaImpression = new JTextArea(3, 50);
            jTextAreaImpression.setText(getImpression());

        }
        return jTextAreaImpression;
    }
     
   protected abstract String getImpression();
    
    ///////////////////////////////////////////////////////South Panel[Buttons]/////////////////////////
    
    private JPanel jPanelSouth;
    private JPanel jPanelButtons;

    
      private JPanel getJPanelSouth() {
        if (jPanelSouth == null) {
            jPanelSouth = new JPanel();
            jPanelSouth.setLayout(new BoxLayout(jPanelSouth, BoxLayout.Y_AXIS));
            jPanelSouth.add(getJPanelButtons());
        }
        return jPanelSouth;
    }
     private JPanel getJPanelButtons() {
        if (jPanelButtons == null) {
            jPanelButtons = new JPanel();
            jPanelButtons.add(getJButtonCommit());
            jPanelButtons.add(getJButtonClose());
        }
        return jPanelButtons;
    }
 //////////////////////////////////////////////////////////Commit Button////////////////////////////////////////////////////////////
    
    private JButton jButtonCommit;
    
    
    
    private JButton getJButtonCommit() {
        if (jButtonCommit == null) {
            jButtonCommit = new JButton();
            jButtonCommit.setText(getCommitButtonText());
            jButtonCommit.setMnemonic(KeyEvent.VK_C);
            jButtonCommit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) { //Start of Method

                    if (patientSelected != null) {

                        String radiologyID = ((Radiology) comboRadiologies.getSelectedItem()).getCode();

                        GregorianCalendar newDate = new GregorianCalendar();
                        
                        Date date = jCalendarDate.getDate();
                        
                        if(date.after(LocalDateTime.now().toDate())) {
                            JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.dateerror"));
                            
                        }
                        
                        else {
                            
                        newDate.setTimeInMillis(jCalendarDate.getDate().getTime());

                        int patientID = patientSelected.getCode().intValue();

                        String clinicalHistory = jTextAreaClinicalHistory.getText();

                        String comparison = jTextAreaComparison.getText();

                        String technique = jTextAreaTechnique.getText();

                        String finding = jTextAreaFinding.getText();

                        String impression = jTextAreaImpression.getText();

                        
                        ForkJoinPool pool = TaskProgress.newTaskProgress();
                        
                        int radiologyExamID = commit(radiologyID, newDate, patientID, clinicalHistory, comparison, technique, finding, impression);

                        
                        DicomIoOperations dicomManager = new DicomIoOperations();
                        RadiologyExamDicomManager radExamDicomManager = new RadiologyExamDicomManager();

                        try {
                        
                            for (FileDicom dicomFile : dicomFiles) {

                               dicomFile = dicomManager.saveFile(dicomFile);
                               radExamDicomManager.createRadiologyExamDicom(radiologyExamID, dicomFile.getIdFile());

                            }
                            
                        }
                        
                        catch(Exception exception) {}

                        TaskProgress.endTaskProgress(pool);
                       // JOptionPane.showMessageDialog(null, getSuccessCommitMessage());
                        //owner.updateTable();
                        dispose();
                        
                    }

                    } else {
                        JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.selectpatient"));
                    }

                } //End of Method

            }
            );
        }

        return jButtonCommit;

    }
    
    protected abstract String getCommitButtonText();
    protected abstract int commit(String radiologyID, GregorianCalendar newDate, int patientID, String clinicalHistory, String comparison, String technique, String finding, String impression);
    protected abstract String getSuccessCommitMessage();

    
    
    
    
    ////////////////////////////////////////////////////Close Button/////////////////////////////////////////////////
    
    private JButton jButtonClose;
    
    
    
    private JButton getJButtonClose() {
        if (jButtonClose == null) {
            jButtonClose = new JButton();
            jButtonClose.setText(getRollbackButtonText());
            jButtonClose.setMnemonic(KeyEvent.VK_C);
            jButtonClose.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return jButtonClose;
    }
    
    protected abstract String getRollbackButtonText();
    
   

    
    //--------------------------------------------End-------------------------------------------------------------------------------
    

    

    
    
   
}
