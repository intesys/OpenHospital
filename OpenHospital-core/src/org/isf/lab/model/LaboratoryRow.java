package org.isf.lab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="LABORATORYROW")
public class LaboratoryRow 
{
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LABR_ID")
	private int code;

	@NotNull
	@ManyToOne
	@JoinColumn(name="LABR_LAB_ID")
	private Laboratory laboratory;

	@NotNull
	@Column(name="LABR_DESC")
	private String description;
	
	@Transient
	private volatile int hashCode = 0;
		

	public LaboratoryRow() { }
	
	public LaboratoryRow(Integer aCode, Laboratory aLabId, String aDescription){
		code=aCode;
		laboratory = aLabId;
		description = aDescription;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Laboratory getLabId() {
		return laboratory;
	}
	public void setLabId(Laboratory laboratory) {
		this.laboratory = laboratory;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof LaboratoryRow)) {
			return false;
		}
		
		LaboratoryRow laboratoryRow = (LaboratoryRow)obj;
		return (this.getCode().equals(laboratoryRow.getCode()) );
	}
	
	@Override
	public int hashCode() {
	    if (this.hashCode == 0) {
	        final int m = 23;
	        int c = 133;
	        
	        c = m * c + code;
	        
	        this.hashCode = c;
	    }
	  
	    return this.hashCode;
	}	
}
