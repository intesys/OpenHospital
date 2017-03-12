

package org.isf.utils.services;

/////////////////////////////////////////////////////


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;


import java.util.concurrent.ForkJoinPool;
import javax.swing.SwingUtilities;


//////////////////////////////////



public class TaskProgress extends Thread {
    
    /////////////////////////////////////////////////////Creation//////////////////////////////////////////////////////////
    private static ArrayList<TaskProgress> tasks = new ArrayList<TaskProgress>();
    
    private TaskProgressBar taskProgressBar;
    private ForkJoinPool pool;
    
    public static final ForkJoinPool newTaskProgress() {
        
        ForkJoinPool pool = new ForkJoinPool();
        
        pool.execute(new TaskProgress(pool));
        
        return pool;
        
        
    }
    
    
    
    
    

    private TaskProgress(ForkJoinPool pool){
     
         this.pool = pool;
         initialize();
        
         

      }


    private void initialize() {

          taskProgressBar = this.new TaskProgressBar();
    }
    
    private final class TaskProgressBar extends JFrame {
    
       private static final int pfrmBase   = 20;
       private static final int pfrmWidth  = 14;
       private static final int pfrmHeight = 12;
       JProgressBar progressBar;
    
       TaskProgressBar(){
     
         initialize();
         setResizable(false);
         this.setIconImage(new ImageIcon("rsc/icons/oh.png").getImage());
         

      }
    
    
      private void initialize() {

          Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
          int x = ((screensize.width - screensize.width * pfrmWidth / pfrmBase) / 2) + 510;
          int y = ((screensize.height - screensize.height * pfrmHeight / pfrmBase) / 2) + 200;
          int width = screensize.width * pfrmWidth / pfrmBase;
          int height = screensize.height * pfrmHeight / pfrmBase;
          this.setBounds(x+510, y, width, height);
          
          setLayout(new FlowLayout());
          setSize(300, 200);
          
          progressBar = new JProgressBar(0, 100);
          progressBar.setValue(0);
          progressBar.setStringPainted(true);
          
          getContentPane().add(progressBar);
          validate();
          
          
          

    }

    
    
    
}


    
  /////////////////////////////////////////////////////Running///////////////////////////////////////////////////////
    
    @Override
    public void run(){
         
       final Thread currentThread = Thread.currentThread();
       Thread progressBarUpdater = null;
       taskProgressBar.setVisible(true);
       
       //-----------------------------------------------Try---------------------------------------
       try {
       
           for(int i =1; i<= 100; i++) {
            
               final int progress = i;

               progressBarUpdater = new Thread () {

                                               @Override
                                               public void run() {

                                                        
                                                        synchronized(currentThread) {
                                                            
                                                        
                                                            taskProgressBar.progressBar.setValue(progress);
                                                            taskProgressBar.progressBar.updateUI();
                                                            taskProgressBar.progressBar.repaint();
                                                            taskProgressBar.progressBar.revalidate();
                                                        
                                                            taskProgressBar.repaint();
                                                            taskProgressBar.revalidate();
                                                            
                                                            currentThread.notify();
                                                        
                                                        }

                                               }

                                         
                                        


               };

              


            SwingUtilities.invokeLater(progressBarUpdater);
            Thread.sleep(1000);
            progressBarUpdater.join();
            

           
     
       }// end for
           
     }// end try
       
     catch (InterruptedException interruptedException) {
            
            if(progressBarUpdater != null && progressBarUpdater.isAlive()) {
               
                     try {
                        
                         progressBarUpdater.wait();
                        
                        
                     }
                     
                     catch(InterruptedException exception) {}
                     
                     
            }
            
            
              
            
     }
       
     onThreadExit();
    

            
     
       
      
      
  }//End of Run
    
    
    /////////////////////////////////////////////////////////Exit///////////////////////////////////////////////////////////////////
    
    public static final synchronized void endTaskProgress(ForkJoinPool pool) {
        
        
        int noTasks = tasks.size();
        for(int i = 0; i < noTasks; i++) {
            TaskProgress task = tasks.get(i);
            if(task.pool == pool) {
                task.interrupt();
                tasks.remove(task);
                noTasks--;
                i--;
            }
        }
        
        pool.shutdownNow();
        
       
       
    }
    public void onThreadExit() {
        
        taskProgressBar.progressBar.setValue(100);
        
        try {
        
            Thread.sleep(1000);
            
        }
        
        catch(InterruptedException exception) {}
        taskProgressBar.setVisible(false);
        taskProgressBar.dispose();
        
    }
    
    
    


}//End of Class
