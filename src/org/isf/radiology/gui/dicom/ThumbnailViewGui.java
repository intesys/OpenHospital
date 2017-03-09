//----------------------------------------------------------------------------------------------------------

package org.isf.radiology.gui.dicom;



//----------------------------------------------------------------------------------------------------------


import java.util.ArrayList;

import org.isf.dicom.model.FileDicom;
import org.isf.generaldata.MessageBundle;



//-----------------------------------------------------------------------------------------------------------


final class ThumbnailViewGui extends org.isf.dicom.gui.ThumbnailViewGui {
    
    
//-----------------------------------------------------------------------------------------------------------
    
    @Override
    public void initialize() {
        
        
        if( ! loadInitComplete) {
                              
            prepare();
            loadInitComplete = true;
                          
        }
                          
                          
        else {
                              
                              
            loadDicom();
            prepare();
                          
        }

                
    }
    
    //---------------------------------------------------------------------------------------------------------
    
    @Override
    protected String getTooltipText(FileDicom dicomFile) {
        
        
          String separator = ": ";
	  String newline = " <br>";
	  StringBuilder rv = new StringBuilder("<html>");
          
          String institutionName = dicomFile.getDicomInstitutionName();
	  rv.append(MessageBundle.getMessage("angal.radiology.dicom.institution")).append(separator).append(
                  (!institutionName.equals("")) ? institutionName: MessageBundle.getMessage("angal.radiology.dicom.notavailable")
          );
	  rv.append(newline);
            
          //////////////
          String patientName = dicomFile.getDicomPatientName();
	  rv.append(MessageBundle.getMessage("angal.radiology.dicom.image.patient.name")).append(separator).append(
                  (!patientName.equals("")) ? patientName: MessageBundle.getMessage("angal.radiology.dicom.notavailable")
          );
	  rv.append(" <br>");
		
          ///////////
          String modality = dicomFile.getModality();
	  rv.append(MessageBundle.getMessage("angal.radiology.dicom.image.modality")).append(separator).append(
                  (!modality.equals("")) ? modality: MessageBundle.getMessage("angal.radiology.dicom.notavailable")
          );
	  rv.append(" <br>");
          ////////
          
          String uniqueID = dicomFile.getDicomInstanceUID();
	  rv.append(MessageBundle.getMessage("angal.radiology.dicom.image.instanceid")).append(separator).append(
                  (!uniqueID.equals("")) ? uniqueID: MessageBundle.getMessage("angal.radiology.dicom.notavailable")
          );
	  rv.append(" <br>");
          ////////
          
	  rv.append("</html>");
		
          return rv.toString();
          
                
        
        
    }
    
    //-----------------------------------------------------------------------------------------------------------
    
    ThumbnailViewGui(int patID, org.isf.dicom.gui.DicomGui owner) {
        
        
        super(patID, owner);

                

    }
    
    //--------------------------------------------------------------------------------------------------------------------
    
    private boolean loadInitComplete = false;
    
    private void loadDicom() {
                  
            
       
        
        ArrayList<FileDicom> fdb = ((DicomGui)this.dicomViewer).getDicomFiles();
        

		     
        dicomThumbsModel.clear();

	
        
        for (int i = 0; i < fdb.size(); i++) 
			
            dicomThumbsModel.addInstance(fdb.get(i));
            
            
            
        
		
                  
                
    }
    
    
    
    
    
    
   
    
   
    
    
    
   
    
    //-------------------------------------------------------------------------------------------------
    
}
