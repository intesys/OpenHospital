
//---------------------------------------------------------------------------------------------------
package org.isf.radiology.gui;

//----------------------------------------------------------------------------------------------------------------
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.isf.radiology.manager.RadiologyExamManager;
import org.isf.radiology.model.RadiologyExam;
import org.isf.utils.services.DbUpdateSubscriber;
import org.isf.utils.services.TaskProgress;
import org.isf.generaldata.MessageBundle;
import org.isf.patient.model.Patient;




//-------------------------------------------------------------------------------------------------------------

public class RadiologyExamBrowser extends JFrame implements WindowListener, DbUpdateSubscriber.TableUpdateSubscriber {
    
    
    //-----------------------------------------------------------------------------------------------------------
    
    public RadiologyExamBrowser() {
        
        ForkJoinPool pool = TaskProgress.newTaskProgress();
        radiologyExams = manager.getRadiologyExams();
        initialize(null);
        addWindowListener(this);
        DbUpdateSubscriber.subscribe(this, new String[] {"radiologyexam"});
        setVisible(true);
        TaskProgress.endTaskProgress(pool);
        
    }
     
    
    public RadiologyExamBrowser(Patient patient, ArrayList<RadiologyExam> radiologyExams) {
        
        this.radiologyExams = radiologyExams;
        initialize(patient);
        setVisible(true);
        
        
    }
     
     @Override 
     public void windowClosing(WindowEvent event) {
       
       DbUpdateSubscriber.unsubscribe(this);
       
     }
     @Override
     public void windowClosed(WindowEvent event){}
     @Override
     public void windowOpened(WindowEvent event){}
     @Override
     public void windowDeactivated(WindowEvent event){}
     @Override
     public void windowActivated(WindowEvent event){}
     @Override
     public void windowIconified(WindowEvent event){}
     @Override
     public void windowDeiconified(WindowEvent event){}
     @Override
     public synchronized void newUpdate(String tableName) {
       
       radiologyExams.removeAll(radiologyExams);
       radiologyExams = manager.getRadiologyExams();
       tablePanel.updateTable(radiologyExams, selectionPanel.getJTextFieldPatient().getText(),selectionPanel.getTypeSelected(), selectionPanel.getDateFrom().getDate(), selectionPanel.getDateTo().getDate());
       
       JOptionPane.showMessageDialog(null, "A Change in the Exam Records has been detected");
   
     }
   
    

     //-------------------------------------------------------------------------------------------------------------------
    
    ArrayList<RadiologyExam> getRadiologyExams() {
        return radiologyExams;
    }
    
    RadiologyExamBrowserSelectionPanel getSelectionPanel() {
        return selectionPanel;
    }
    
    RadiologyExamBrowserTablePanel getTablePanel() {
        return tablePanel;
    }
    
    //-------------------------------------------------------------------------------------------------------
    private static final RadiologyExamManager manager = new RadiologyExamManager();
    private ArrayList<RadiologyExam> radiologyExams;
    private final int pfrmHeight = 12;
    private RadiologyExamBrowserSelectionPanel selectionPanel;
    private RadiologyExamBrowserTablePanel tablePanel;
    private RadiologyExamBrowserButtonPanel buttonPanel;
    
    private void constructFrame() {
        
        final int pfrmBase = 20, pfrmWidth = 14;
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screensize.width - screensize.width * pfrmWidth / pfrmBase) / 2;
        int y = (screensize.height - screensize.height * pfrmHeight / pfrmBase) / 2;
        int width = screensize.width * pfrmWidth / pfrmBase;
        int height = screensize.height * pfrmHeight / pfrmBase;
        this.setBounds(x, y, width, height);
        setResizable(false);
        setIconImage(new ImageIcon("rsc/icons/oh.png").getImage());
        setTitle(MessageBundle.getMessage("angal.radiology.browsing"));
        
    }
   
    private void initialize(Patient patient) {
    
        constructFrame();
        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
   
        selectionPanel = null;
        JPanel jCenterPanel = new JPanel();
        jCenterPanel.setLayout(new BorderLayout());
        tablePanel = new RadiologyExamBrowserTablePanel(radiologyExams);
        jCenterPanel.add(
                new JScrollPane(tablePanel.getTable()), 
                java.awt.BorderLayout.CENTER
        );
        
        if(patient==null) {
        
            selectionPanel = new RadiologyExamBrowserSelectionPanel(this, pfrmHeight);
            jContentPane.add(selectionPanel.getJSelectionPanel(), java.awt.BorderLayout.WEST);
            
        }
        
        
        buttonPanel = new RadiologyExamBrowserButtonPanel(this, patient);
        jCenterPanel.add(buttonPanel.getJButtonPanel(), java.awt.BorderLayout.SOUTH);
        jContentPane.add(jCenterPanel, java.awt.BorderLayout.CENTER);
        
        
        setContentPane(jContentPane);
        
        
        validate();
        
        
    }
   
    
   //-------------------------------------------------------------------------------------------------------------- 
    
    
    
    
    
    
     
     
     
    
    
    
    
    
    

}//End of Top Class
