

package org.isf.radiology.model;




public class RadiologyType {
    
        ////////////////////////////////////////////////////////////////////
        private String code;
        private String description;
	
        /////////////////////////////////////////////////////////////////////////
	public RadiologyType(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

        //////////////////////////////////////////////////////////////////////////
	public String getCode() {
		return code;
	}
        
        public String getDescription() {
		return description;
	}
        
        @Override
	public boolean equals(Object anObject) {
		return (anObject == null) || !(anObject instanceof RadiologyType) ? false
				: (getCode().equals(((RadiologyType) anObject).getCode())
						&& getDescription().equalsIgnoreCase(
								((RadiologyType) anObject).getDescription()));
	}
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCode(String code) {
		this.code = code;
	}

	

	public void setDescription(String description) {
		this.description = description;
	}


	

	public String toString() {
		return getDescription();
	}
        
        ///////////////////////////////////////////////////////////////////////////////////////////

    
}
