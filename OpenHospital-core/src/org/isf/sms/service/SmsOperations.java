package org.isf.sms.service;

import java.sql.Timestamp;
import java.util.ArrayList;

// Generated 31-gen-2014 15.39.04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import org.isf.sms.model.Sms;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.springframework.stereotype.Component;

/**
 * @see org.isf.sms.model.Sms
 * @author Mwithi
 */
@Component
public class SmsOperations {

	/**
	 * 
	 */
	public SmsOperations() {}
	
	/**
	 * Save or Update a {@link Sms}
	 * @param supplier - the {@link Sms} to save or update
	 * return <code>true</code> if data has been saved, <code>false</code> otherwise. 
	 * @throws OHException 
	 */
	public boolean saveOrUpdate(
			Sms sms) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try{
			jpa.beginTransaction();
			if (sms.getSmsId() > 0)
			{
				jpa.merge(sms);			
			}
			else
			{			
				jpa.persist(sms);
			}
			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return result;	
	}
	
	/**
	 * Returns a {@link Sms} with specified ID
	 * @param ID - sms ID
	 * @return sms - the sms with specified ID
	 * @throws OHException 
	 */
	public Sms getByID(
			int ID) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		Sms foundSms = null;
		
		try{
			jpa.beginTransaction();	
			foundSms = (Sms)jpa.find(Sms.class, ID);
			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return foundSms;
	}
	
	/**
	 * Returns the list of all {@link Sms}s, sent and not sent, between the two dates
	 * @return smsList - the list of {@link Sms}s
	 * @throws OHException 
	 */
	@SuppressWarnings("unchecked")
	public List<Sms> getAll(
			Date dateFrom, 
			Date dateTo) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<Sms> sms = null;
		ArrayList<Object> params = new ArrayList<Object>();
				
		try{
			jpa.beginTransaction();

			String query = "SELECT * FROM SMS" +
					" WHERE DATE(SMS_DATE_SCHED) BETWEEN ? AND ?" +
					" ORDER BY SMS_DATE_SCHED ASC";
			jpa.createQuery(query, Sms.class, false);
			params.add(new Timestamp(dateFrom.getTime()));
			params.add(new Timestamp(dateTo.getTime()));
			jpa.setParameters(params, false);
			List<Sms> smsList = (List<Sms>)jpa.getList();
			sms = new ArrayList<Sms>(smsList);			

			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return sms;
	}
	
	/**
	 * Returns the list of not sent {@link Sms}s between the two dates
	 * @return smsList - the list of {@link Sms}s
	 * @throws OHException 
	 */
	@SuppressWarnings("unchecked")
	public List<Sms> getList(
			Date dateFrom, 
			Date dateTo) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<Sms> sms = null;
		ArrayList<Object> params = new ArrayList<Object>();

		try{
			jpa.beginTransaction();

			String query = "SELECT * FROM SMS" +
					" WHERE DATE(SMS_DATE_SCHED) BETWEEN ? AND ?" +
					" AND SMS_DATE_SENT IS NULL " +
					" ORDER BY SMS_DATE_SCHED ASC";
			jpa.createQuery(query, Sms.class, false);
			params.add(new Timestamp(dateFrom.getTime()));
			params.add(new Timestamp(dateTo.getTime()));
			jpa.setParameters(params, false);
			List<Sms> smsList = (List<Sms>)jpa.getList();
			sms = new ArrayList<Sms>(smsList);			

			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return sms;
	}
	
	/**
	 * Returns the list of not sent {@link Sms}s
	 * @return smsList - the list of {@link Sms}s
	 * @throws OHException 
	 */
	@SuppressWarnings("unchecked")
	public List<Sms> getList() throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<Sms> sms = null;
				
		try{
			jpa.beginTransaction();

			String query = "SELECT * FROM SMS" +
					" WHERE SMS_DATE_SENT IS NULL " +
					" ORDER BY SMS_DATE_SCHED ASC";
			jpa.createQuery(query, Sms.class, false);
			List<Sms> smsList = (List<Sms>)jpa.getList();
			sms = new ArrayList<Sms>(smsList);			

			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return sms;
	}

	/**
	 * Delete the specified {@link Sms}
	 * @param sms - the {@link Sms} to delete
	 * @throws OHException 
	 */
	public void delete(
			Sms sms) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		
		try{
			jpa.beginTransaction();
			Sms objToRemove = (Sms) jpa.find(Sms.class, sms.getSmsId());
			jpa.remove(objToRemove);
			jpa.commitTransaction();
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}
		return;	
	}

	/**
	 * Delete the specified {@link Sms}s if not already sent
	 * @param module - the module name which generated the {@link Sms}s
	 * @param moduleID - the module ID within its generated {@link Sms}s
	 * @throws OHException 
	 */
	public void deleteByModuleModuleID(
			String module, 
			String moduleID) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<Object> params = new ArrayList<Object>();

		try {
			jpa.beginTransaction();		

			String query = "DELETE FROM SMS" +
					" WHERE SMS_MOD = ? AND SMS_MOD_ID = ?" +
					" AND SMS_DATE_SENT IS NULL";
			jpa.createQuery(query, Sms.class, false);
			params.add(module);
			params.add(moduleID);
			jpa.setParameters(params, false);
			jpa.executeUpdate();

			jpa.commitTransaction();	
		}catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		}		
        return;
	}
}
