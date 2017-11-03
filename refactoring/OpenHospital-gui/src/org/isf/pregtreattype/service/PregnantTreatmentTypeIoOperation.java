package org.isf.pregtreattype.service;

import java.util.ArrayList;
import java.util.List;

import org.isf.pregtreattype.model.PregnantTreatmentType;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.springframework.stereotype.Component;

@Component
public class PregnantTreatmentTypeIoOperation {

	/**
	 * return the list of {@link PregnantTreatmentType}s
	 * 
	 * @return the list of {@link PregnantTreatmentType}s
	 * @throws OHException 
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<PregnantTreatmentType> getPregnantTreatmentType() throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<PregnantTreatmentType> pregnantTreatmentTypes = null;
				
		try{
			jpa.beginTransaction();

			String query = "SELECT * FROM PREGNANTTREATMENTTYPE ORDER BY PTT_DESC";
			jpa.createQuery(query, PregnantTreatmentType.class, false);
			List<PregnantTreatmentType> pregnantTreatmentList = (List<PregnantTreatmentType>)jpa.getList();
			pregnantTreatmentTypes = new ArrayList<PregnantTreatmentType>(pregnantTreatmentList);			

			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return pregnantTreatmentTypes;
	}
	
	/**
	 * insert a {@link PregnantTreatmentType} in the DB
	 * 
	 * @param pregnantTreatmentType - the {@link PregnantTreatmentType} to insert
	 * @return <code>true</code> if the item has been inserted, <code>false</code> otherwise
	 * @throws OHException 
	 */
	public boolean newPregnantTreatmentType(
			PregnantTreatmentType pregnantTreatmentType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try{
			jpa.beginTransaction();	
			jpa.persist(pregnantTreatmentType);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;
	}
	
	/**
	 * update a {@link PregnantTreatmentType} in the DB
	 * 
	 * @param pregnantTreatmentType - the {@link PregnantTreatmentType} to update
	 * @return <code>true</code> if the item has been updated, <code>false</code> otherwise
	 * @throws OHException 
	 */
	public boolean updatePregnantTreatmentType(
			PregnantTreatmentType pregnantTreatmentType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;

		try{
			jpa.beginTransaction();	
			jpa.merge(pregnantTreatmentType);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;	
	}
	
	/**
	 * delete a {@link PregnantTreatmentType} in the DB
	 * 
	 * @param pregnantTreatmentType - the {@link PregnantTreatmentType} to delete
	 * @return <code>true</code> if the item has been deleted, <code>false</code> otherwise
	 * @throws OHException 
	 */
	public boolean deletePregnantTreatmentType(
			PregnantTreatmentType pregnantTreatmentType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try{
			jpa.beginTransaction();
			PregnantTreatmentType objToRemove = (PregnantTreatmentType) jpa.find(PregnantTreatmentType.class, pregnantTreatmentType.getCode());
			jpa.remove(objToRemove);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;	
	}
	
	/**
	 * check if the code is already in use
	 * 
	 * @param code - the code
	 * @return <code>true</code> if the code is already in use, <code>false</code> otherwise
	 * @throws OHException 
	 */
	public boolean isCodePresent(
			String code) throws OHException
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		PregnantTreatmentType pregnantTreatmentTyp;
		boolean result = false;
		
		try{
			jpa.beginTransaction();	
			pregnantTreatmentTyp = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code);
			if (pregnantTreatmentTyp != null)
			{
				result = true;
			}
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;	
	}
}
