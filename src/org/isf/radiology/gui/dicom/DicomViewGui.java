//----------------------------------------------------------------------------------------------------------

package org.isf.radiology.gui.dicom;



//----------------------------------------------------------------------------------------------------------

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Date;
import java.text.DateFormat;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;
import org.dcm4che2.data.Tag;

import org.isf.generaldata.MessageBundle;
import org.isf.patient.model.Patient;








//-----------------------------------------------------------------------------------------------------------


final class DicomViewGui extends org.isf.dicom.gui.DicomViewGui {
   
    
 //-------------------------------------------------------------------------------------------------------
    
    @Override 
    public void notifyChanges(Patient patient, String serieNumber) {
            
            
                this.patID = patient.getCode().intValue();
		this.ohPatient = patient;
		this.serieNumber = serieNumber;
		this.frameIndex = 0;

                
		

		

		frames = new Long[]{1L};

		jSliderZoom.setValue(100);
                
                refreshFrame();

		reInitComponent();

            
            
        }
        
  
    
//----------------------------------------------------------------------------------------------------------------
    
    DicomViewGui(Patient patient, String serieNumber, DicomGui dicomGui) {
            
            super(patient, serieNumber);
            this.dicomGui = dicomGui;
          
            
            
     }
    
     void reset() {
        
        
        this.jPanelCenter.removeAll();
        this.jPanelCenter.revalidate();
        this.jPanelCenter.repaint();
    }
    
     
     //--------------------------------------------------------------------------------------------------------------
    
    
    @Override 
    protected void refreshFrame() {
		
                  getImageFromDicom(dicomGui.getSelectedInstance());
	
    }
    
    
    @Override
    protected void drawInfo(Graphics2D canvas, int width, int height) {
        
        
        drawImage(canvas, width, height);
        drawPatient(canvas, width, height);
        
        
    }
    
    //------------------------------------------------------------------------------------------------------------
    
    private DicomGui dicomGui;
    
    
    private void drawImage(Graphics2D canvas, int width, int height) {
        
        drawImageInstitution(canvas, width);
        drawImagePatient(canvas);
        drawImageSeries(canvas);
        drawInfoFrameBottomLeft(canvas, width, height);
    }
    
    private void drawPatient(Graphics2D canvas, int width, int height) {
        
        Color originalColor = canvas.getColor();
        canvas.setColor(colScr);
        
        
         int hi = 30;
	 String txt;
        
         
         
        
	canvas.drawString("      " + MessageBundle.getMessage("angal.radiology.dicom.exam.patient"), width-120, hi);
		
               
        
        width = width - 150;
        
        
        hi += VGAP;
        txt = ohPatient.getCode().toString();
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.patient.id") + " : " + txt, width, hi);
                
               
        hi += VGAP;
        txt = ohPatient.getName();
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.patient.name") + " : " + txt, width, hi);
		
                
        hi += VGAP;
        txt = "" + ohPatient.getSex();
	canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.patient.sex") + " : " + txt, width, hi);
                
                
                
        hi += VGAP;
        txt = "" + DateFormat.getDateInstance().format(ohPatient.getBirthDate());//.toString();
	canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.patient.birthdate") + " : " + txt, width, hi);
                
                
        hi += VGAP;
	txt = "" + ohPatient.getAge();
	canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.patient.age") + " : " + txt, width, hi);
		
        
        canvas.setColor(originalColor);
        
        
    }
    
     private void drawImageInstitution(Graphics2D canvas, int width) {
        
        Color originalColor = canvas.getColor();
        canvas.setColor(colScr);

        String text = tmpDicom.getString(Tag.InstitutionName);
        canvas.drawString(
             (text != null)? text : "", 
             23, 
             10
           
        );
        
        canvas.setColor(originalColor);
        
    
    }
    
    private void drawImagePatient(Graphics2D canvas) {
        
        Color originalColor = canvas.getColor();
        canvas.setColor(colScr);
        //////
        String txt="";      
	int hi = 40;	
        
	canvas.drawString("    " + MessageBundle.getMessage("angal.radiology.dicom.image.patient"), 14, hi);
                
                
                
        hi += VGAP;
	txt = tmpDicom.getString(Tag.PatientID);
	if (txt == null)
		txt = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
		
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.exam.patient.id") + " : " + txt, 10, hi);
               
		
        hi += VGAP;
	txt = tmpDicom.getString(Tag.PatientName);
	if (txt == null)
		txt = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
		
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.exam.patient.name") + " : " + txt, 10, hi);
		
                
        hi += VGAP;
	txt = tmpDicom.getString(Tag.PatientSex);
	if (txt == null)
		txt = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
		
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.exam.patient.sex") + " : " + txt, 10, hi);
                
                
                
        hi += VGAP;
        Date date = tmpDicom.getDate(Tag.PatientBirthDate);
        DateFormat dateFormat = DateFormat.getDateInstance();
	if (date != null)
	    txt = dateFormat.format(date);
        else
            txt=MessageBundle.getMessage("angal.radiology.dicom.notavailable");
	
		
        canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.exam.patient.birthdate") + " : " + txt, 10, hi);
                
               
	hi += VGAP;
	txt = tmpDicom.getString(Tag.PatientAge);
	if (txt == null)
		txt = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
	canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.exam.patient.age") + " : " + txt, 10, hi);
                
                

	if (ohPatient.getPhoto() != null) {
			hi += VGAP;
			BufferedImage bi = new BufferedImage(ohPatient.getPhoto().getWidth(this), ohPatient.getPhoto().getHeight(this), BufferedImage.TYPE_INT_ARGB);
			bi.getGraphics().drawImage(ohPatient.getPhoto(), 0, 0, this);
			drawQuadrant(bi.getGraphics(), ohPatient.getPhoto().getHeight(this), ohPatient.getPhoto().getWidth(this), Color.WHITE);
			canvas.drawImage(Scalr.resize(bi, 100), 10, hi, this);
	}

		
	canvas.setColor(originalColor);
        
    }
    
    
    private void drawImageSeries(Graphics2D canvas) {
        
        
        Color originalColor = canvas.getColor();
	String txt;
	canvas.setColor(colScr);
        
	int height = 150;
	int width = 10;
        
        canvas.drawString("    " + MessageBundle.getMessage("angal.radiology.dicom.image.dicomexaminfo"), 14, height);
        
        
        height+=VGAP;
	txt = tmpDicom.getString(Tag.Modality);
	if (txt == null)
		txt = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
	canvas.drawString(MessageBundle.getMessage("angal.radiology.dicom.image.modality") + " : " + txt, width, height);
        
        
        
      
        height += VGAP;
        
        txt = MessageBundle.getMessage("angal.radiology.dicom.examdescription");
        
        String description = tmpDicom.getString(Tag.SeriesDescription);
        
        if(description == null) 
            
                description = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
        
        
        txt = txt + ": " + description;
       
        
	canvas.drawString(txt, width, height);
        
        
        
        height += VGAP;
        height = height + 15;
        canvas.drawString("    " + MessageBundle.getMessage("angal.radiology.dicom.image.instanceinfo"), 14, height);
        
        
        
        height += VGAP;
        txt = MessageBundle.getMessage("angal.radiology.dicom.image.instanceno");
        String instanceNo= tmpDicom.getString(Tag.InstanceNumber);
        if (instanceNo == null)
		instanceNo = MessageBundle.getMessage("angal.radiology.dicom.notavailable");
        txt = txt + ": " + instanceNo;
        
        canvas.drawString(txt, width, height);
        
        
        height += VGAP;
        Date date = tmpDicom.getDate(Tag.InstanceCreationDate);
        DateFormat dateFormat = DateFormat.getDateInstance();
        String sDate = MessageBundle.getMessage("angal.radiology.dicom.image.instancedate");
	if (date != null)
	    sDate = sDate + ": " + dateFormat.format(date);
        else
            sDate=sDate + ": " + MessageBundle.getMessage("angal.radiology.dicom.notavailable");
        
        
	canvas.drawString(sDate, width, height);
        
        
        
        canvas.setColor(originalColor);
        
        
        
        
    }
    
    

    
//-------------------------------------------------------------------------------
  
    
   
    
    
   
    
}

