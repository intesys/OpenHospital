
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package org.isf.radiology.model;




public class Radiology {
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String code;
    private String description;
    private RadiologyType radiologytype;
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Radiology(String code, String description, RadiologyType radiologytype) {
		super();
		this.code = code;
		this.description = description;
		this.radiologytype = radiologytype;
		
	
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getCode() {
		return code;
	}
        
        public String getDescription() {
		return description;
	}
        
        public RadiologyType getRadiologytype() {
		return radiologytype;
	}
        
        public boolean equals(Object anObject) {
		return (anObject == null) || !(anObject instanceof Radiology) ? false
				: (getCode().equals(((Radiology) anObject).getCode())
						&& getDescription().equalsIgnoreCase(
								((Radiology) anObject).getDescription())
						&& getRadiologytype()
								.equals(((Radiology) anObject).getRadiologytype())
						);
	}
        
        ///////////////////////////////////////////////////////////////////////////////////////

	public void setCode(String code) {
		this.code = code;
	}

	

	public void setDescription(String description) {
		this.description = description;
	}

	

	public void setRadiologytype(RadiologyType radiologytype) {
		this.radiologytype = radiologytype;
	}

	
        

	public String toString() {
		return getDescription();
	}
        
        //////////////////////////////////////////////////////////////////////////////////////

	

    
}
