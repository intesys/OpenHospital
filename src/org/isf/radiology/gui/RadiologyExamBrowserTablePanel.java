//--------------------------------------------------------------------------------------------------------

package org.isf.radiology.gui;

//----------------------------------------------------------------------------------------------------------
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.isf.generaldata.MessageBundle;
import org.isf.radiology.model.RadiologyExam;

//-----------------------------------------------------------------------------------------------------------------
public class RadiologyExamBrowserTablePanel extends DefaultTableModel {
        
        //---------------------------------------------------------------
    
        public final JTable getTable() {
            
            return jTable;
        }
        
        @Override
        public int getRowCount() {
			if (tableRadiologyExams == null)
				return 0;
			return tableRadiologyExams.size();
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
            
                        if(tableRadiologyExams.size()==0)
                            return null;
                        
			if (c == -1) {
				return tableRadiologyExams.get(tableRadiologyExams.size() - r - 1);
			} else if (getNumber(c) == 0) {
				return  dateFormat.format(tableRadiologyExams.get(tableRadiologyExams.size() - r - 1).getDate().getTime());
			} else if (getNumber(c) == 1) {
				return tableRadiologyExams.get(tableRadiologyExams.size() - r - 1).getPatient().getName(); //Alex: added
			} else if (getNumber(c) == 2) {
				return tableRadiologyExams.get(tableRadiologyExams.size() - r - 1).getRadiology().getDescription();
			}
                        
			return null;
		
        }
        
        
        @Override
        public boolean isCellEditable(int arg0, int arg1) {
			// return super.isCellEditable(arg0, arg1);
			return false;
		
        }
        
       
      
       
        //----------------------------------------------------------
        protected final ArrayList<RadiologyExam> tableRadiologyExams = new ArrayList<RadiologyExam>();
        protected String[] pColumns; 
        protected boolean[] columnsVisible;
        protected int[] pColumwidth;
        protected int[] maxWidth;
        protected boolean[] columnsResizable;
        protected SimpleDateFormat dateFormat;
        protected int selectionMode;
        
        protected void setColumnsProperties() {
            
            pColumns = new String[]{ MessageBundle.getMessage("angal.radiology.datem"), MessageBundle.getMessage("angal.radiology.patient"), MessageBundle.getMessage("angal.radiology.scan") };
            columnsVisible = new boolean[] {true, true, true};
            pColumwidth = new int[] { 150, 150, 150};
            maxWidth = new int[] { 150, 150, 150};
            columnsResizable = new boolean[] {true, true, true};
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            selectionMode = ListSelectionModel.SINGLE_SELECTION;
            
        }
    
        protected RadiologyExamBrowserTablePanel(final ArrayList<RadiologyExam> radiologyExams) {
		
                        
                        setColumnsProperties();
			jTable = new JTable(this);
                        
                        
                        for(RadiologyExam radiologyExam: radiologyExams)
                           tableRadiologyExams.add(radiologyExam);
                        
			int columnLengh = pColumwidth.length;
			for (int i=0;i<columnLengh; i++){
				jTable.getColumnModel().getColumn(i).setMinWidth(pColumwidth[i]);
				if (!columnsResizable[i]) jTable.getColumnModel().getColumn(i).setMaxWidth(maxWidth[i]);
			}
                        
                        jTable.setSelectionMode(selectionMode);
                        
                        
                        
		
		
	}
        
        protected int getNumber(int col) {
	    	// right number to return
	        
                    int n = col;    
	            int i = 0;
	            do {
	            
                        if (!columnsVisible[i]) {
	            	
                            n++;
	                }
	            
                    i++;
	        
                    } while (i < n);
	        // If we are on an invisible column, 
	        // we have to go one step further
	        
                    while (!columnsVisible[n]) {
	        	n++;
	            }
	        
                    return n;
	    
                
       }
       
         
        //-----------------------------------------------------------------------------------------------------------------
        
        final ArrayList<RadiologyExam> getLoadedRadiologyExams() {
            
            return tableRadiologyExams;
        }
        
        void updateTable(final ArrayList<RadiologyExam> radiologyExams, final String patientID, final String radiology, final GregorianCalendar dateFrom, final GregorianCalendar dateTo) {
        
        
                abstract class Filter {
                  
                   public abstract boolean isSatisfied(RadiologyExam radiologyExam); 
                    
                }
                
                Filter filter;
                
                
                if(patientID.equals("") && radiology.equals("")) {
                    
                    
                    filter = new Filter() {
                        
                        @Override
                        public boolean isSatisfied(RadiologyExam radiologyExam) {
                            
                             GregorianCalendar radiologyExamDate = radiologyExam.getDate();
                             if(radiologyExamDate.compareTo(dateFrom) >= 0 &&
                                radiologyExamDate.compareTo(dateTo) <=0)
                                 
                                 return true;
                             
                             else
                                 
                                 return false;
                            
                            
                        }
                        
                    };
                    
                }
                
                else if(patientID.equals("")) {
                    
                    
                    filter = new Filter() {
                        
                       
                        @Override
                        public boolean isSatisfied(RadiologyExam radiologyExam) {
                            
                             GregorianCalendar radiologyExamDate = radiologyExam.getDate();
                             
                             if(radiologyExamDate.compareTo(dateFrom) >= 0 &&
                                radiologyExamDate.compareTo(dateTo) <=0 && 
                                radiology.equals(radiologyExam.getRadiology().getCode()))
                                     
                                 return true;
                             
                             else
                                 
                                 return false;
                                 
                                 
                            
                            
                        }
                        
                    };
                    
                }
                
                 else if(radiology.equals("")) {
                    
                    
                    filter = new Filter() {
                        
                       
                        @Override
                        public boolean isSatisfied(RadiologyExam radiologyExam) {
                            
                             GregorianCalendar radiologyExamDate = radiologyExam.getDate();
                             
                             if(radiologyExamDate.compareTo(dateFrom) >= 0 &&
                                radiologyExamDate.compareTo(dateTo) <=0 && 
                                Integer.parseInt(patientID)==radiologyExam.getPatient().getCode())
                                     
                                 return true;
                             
                             else
                                 
                                 return false;
                                 
                                 
                            
                            
                        }
                        
                    };
                    
                }
                     
                  
                else  {
                    
                    
                    filter = new Filter() {
                        
                        @Override
                        public boolean isSatisfied(RadiologyExam radiologyExam) {
                            
                             GregorianCalendar radiologyExamDate = radiologyExam.getDate();
                             
                             if(radiologyExamDate.compareTo(dateFrom) >= 0 &&
                                radiologyExamDate.compareTo(dateTo) <=0 && 
                                Integer.parseInt(patientID)==radiologyExam.getPatient().getCode() &&
                                radiology.equals(radiologyExam.getRadiology().getCode()))
                                     
                                 return true;
                             
                             else
                                 
                                 return false;
                                 
                                 
                            
                            
                        }
                        
                    };
                    
                }
                     
                     
                
                
                
                tableRadiologyExams.removeAll(tableRadiologyExams);
                for(RadiologyExam radiologyExam: radiologyExams) {
                    if(filter.isSatisfied(radiologyExam))
                        tableRadiologyExams.add(radiologyExam);
                    
                }
                
                
                
                    
                fireTableDataChanged();
               
                    
                    
               
                
        
                
                
        }
        
        //-----------------------------------------------------------------------------
        private final JTable jTable;
        
        
        
        
        
       
  
        
       
        
        
        
        //-------------------------------------------------------------------------------
        
        
    }
    
  
