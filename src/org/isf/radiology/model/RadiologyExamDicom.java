/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.isf.radiology.model;


import org.isf.dicom.model.FileDicom;





public class RadiologyExamDicom {
    
    
        ////////////////////////////////////////////////////////////////////////////////////////////////
        private int code;
        private FileDicom dicomFile;
	private RadiologyExam radiologyExam;
        
	
        ////////////////////////////////////////////////////////////////////////////////////////////////
	public RadiologyExamDicom() {}
        
        
        public RadiologyExamDicom(int code, RadiologyExam radiologyExam, FileDicom dicomFile) {
            
            
            this.code = code;
            this.radiologyExam = radiologyExam;
            this.dicomFile = dicomFile;
            
            
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public int getCode() {
            
            return code;
        }
        
        public RadiologyExam getRadiologyExam() {
		return radiologyExam;
	}
        
        public FileDicom getDicomFile() {
		return dicomFile;
	}
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
}

    

