///////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.model;

import java.util.GregorianCalendar;

import org.isf.patient.model.Patient;


////////////////////////////////////////////////////////////////////////////////////////////////////

public class RadiologyExam {
    
        //////////////////////////////////////////////////////////////////////////////////////
        private int code;
	private Radiology radiology;
	private GregorianCalendar date;
        private Patient patient;
        private String clinicalHistory;
        private String comparison;
        private String technique;
        private String finding;
        private String impression;
        private String radiologist;
	
        ////////////////////////////////////////////////////////////////////////////////////////////////
	public RadiologyExam() {}
        
        public RadiologyExam(int code, Radiology radiology, GregorianCalendar date, Patient patient,
                String clinicalHistory, String comparison, String technique, 
                String finding, String impression, String radiologist) {
            
            
            this.code = code;
            this.radiology = radiology;
            this.date = date;
            this.patient= patient;
            this.clinicalHistory = clinicalHistory;
            this.comparison = comparison;
            this.technique = technique;
            this.finding = finding;
            this.impression = impression;
            this.radiologist = radiologist;
            
            
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public int getCode() {
            
            return code;
        }
        
        public GregorianCalendar getDate() {
		return date;
	}
        
        public Patient getPatient() {
		return patient;
	}
        
        public Radiology getRadiology() {
            
                return radiology;
            
        }
        
        public String getClinicalHistory() {
            
            return clinicalHistory;
        }
        
        public String getComparison() {
            
            
            return comparison;
        }
        
        public String getTechnique() {
            
            return technique;
        }
    
        
        public String getFinding() {
            
            return finding;
        }
        
        public String getImpression() {
            
            return impression;
        }
        
        public String getRadiologist() {
            return radiologist;
        }
}
