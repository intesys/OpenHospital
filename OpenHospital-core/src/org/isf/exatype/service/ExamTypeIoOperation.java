package org.isf.exatype.service;

import java.util.ArrayList;
import java.util.List;

import org.isf.exatype.model.ExamType;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.springframework.stereotype.Component;

@Component
public class ExamTypeIoOperation {
	
	/**
	 * Return the list of {@link ExamType}s.
	 * @return the list of {@link ExamType}s.
	 * @throws OHException
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<ExamType> getExamType() throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<ExamType> pexamtype = null;
				
		try {
			jpa.beginTransaction();
			
			String query = "SELECT * FROM EXAMTYPE ORDER BY EXC_DESC";
			jpa.createQuery(query, ExamType.class, false);
			List<ExamType> examTypeList = (List<ExamType>)jpa.getList();
			pexamtype = new ArrayList<ExamType>(examTypeList);			
			
			jpa.commitTransaction();
		} catch (OHException e) {
			// DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return pexamtype;
	}
	
	/**
	 * Update an already existing {@link ExamType}.
	 * @param examType - the {@link ExamType} to update
	 * @return <code>true</code> if the examType has been updated, <code>false</code> otherwise.
	 * @throws OHException
	 */
	public boolean updateExamType(
			ExamType examType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			jpa.merge(examType);
			jpa.commitTransaction();
		} catch (OHException e) {
			// DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
    	
		return result;	
	}
	
	/**
	 * Insert a new {@link ExamType} in the DB.
	 * @param examType - the {@link ExamType} to insert.
	 * @return <code>true</code> if the examType has been inserted, <code>false</code> otherwise.
	 * @throws OHException
	 */
	public boolean newExamType(
			ExamType examType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			jpa.persist(examType);
	    	jpa.commitTransaction();
		} catch (OHException e) {
			// DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
    	
		return result;	
	}
	
	/**
	 * Delete the passed {@link ExamType}.
	 * @param examType - the {@link ExamType} to delete.
	 * @return <code>true</code> if the examType has been deleted, <code>false</code> otherwise.
	 * @throws OHException
	 */
	public boolean deleteExamType(
			ExamType examType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			ExamType objToRemove = (ExamType) jpa.find(ExamType.class, examType.getCode());
			jpa.remove(objToRemove);
	    	jpa.commitTransaction();
		} catch (OHException e) {
			// DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
    	
		return result;	
	}
	
	/**
	 * This function controls the presence of a record with the same code as in
	 * the parameter.
	 * @param code - the code
	 * @return <code>true</code> if the code is present, <code>false</code> otherwise.
	 * @throws OHException
	 */
	public boolean isCodePresent(
			String code) throws OHException
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ExamType examType;
		boolean result = false;
		
		try {
			jpa.beginTransaction();	
			examType = (ExamType)jpa.find(ExamType.class, code);
			if (examType != null)
			{
				result = true;
			}
	    	jpa.commitTransaction();
		}  catch (OHException e) {
			// DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return result;	
	}
}
