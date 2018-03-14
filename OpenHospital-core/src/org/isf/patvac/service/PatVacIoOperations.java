package org.isf.patvac.service;

/*------------------------------------------
 * IoOperations  - Patient Vaccine Io operations
 * -----------------------------------------
 * modification history
 * 25/08/2011 - claudia - first beta version
 * 20/10/2011 - insert vaccine type management
 * 14/11/2011 - claudia - inserted search condtion on date
 *------------------------------------------*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.isf.patvac.model.PatientVaccine;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.springframework.stereotype.Component;

@Component
public class PatVacIoOperations {

	/**
	 * returns all {@link PatientVaccine}s of today or one week ago
	 * 
	 * @param minusOneWeek - if <code>true</code> return the last week
	 * @return the list of {@link PatientVaccine}s
	 * @throws OHException 
	 */
	public ArrayList<PatientVaccine> getPatientVaccine(
			boolean minusOneWeek) throws OHException 
	{
		GregorianCalendar timeTo = new GregorianCalendar();
		GregorianCalendar timeFrom = new GregorianCalendar();
	
		
		if (minusOneWeek)
		{
			timeFrom.add(GregorianCalendar.WEEK_OF_YEAR, -1);			
		}
		
		return getPatientVaccine(null, null, timeFrom, timeTo, 'A', 0, 0);
	}

	/**
	 * returns all {@link PatientVaccine}s within <code>dateFrom</code> and
	 * <code>dateTo</code>
	 * 
	 * @param vaccineTypeCode
	 * @param vaccineCode
	 * @param dateFrom
	 * @param dateTo
	 * @param sex
	 * @param ageFrom
	 * @param ageTo
	 * @return the list of {@link PatientVaccine}s
	 * @throws OHException 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<PatientVaccine> getPatientVaccine(
			String vaccineTypeCode, 
			String vaccineCode, 
			GregorianCalendar dateFrom, 
			GregorianCalendar dateTo, 
			char sex, 
			int ageFrom, 
			int ageTo) throws OHException 
	{
		
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<PatientVaccine> patientVaccines = null;
		StringBuilder query = new StringBuilder();
		ArrayList<Object> params = new ArrayList<Object>();
				
		try {
			jpa.beginTransaction();
			String clause = " WHERE";
			query.append("SELECT *"
					+ " FROM PATIENTVACCINE JOIN VACCINE ON PAV_VAC_ID_A=VAC_ID_A"
					+ " JOIN VACCINETYPE ON VAC_VACT_ID_A = VACT_ID_A"
					+ " JOIN PATIENT ON PAV_PAT_ID = PAT_ID");
			if (dateFrom != null || dateTo != null) {
				if (dateFrom != null) {
					query.append(clause).append(" DATE_FORMAT(PAV_DATE,'%Y-%m-%d') >= ?");
					params.add(convertToSQLDateLimited(dateFrom));
					clause = " AND";
				}
				if (dateTo != null) {
					params.add(convertToSQLDateLimited(dateTo));
					query.append(clause).append(" DATE_FORMAT(PAV_DATE,'%Y-%m-%d') <= ?");
					clause = " AND";
				}
			}
			if (vaccineTypeCode != null) {
				query.append(clause).append(" VACT_ID_A = ?");
				params.add(vaccineTypeCode);
				clause = " AND";
			}
			if (vaccineCode != null) {
				query.append(clause).append(" VAC_ID_A = ?");
				params.add(vaccineCode);
				clause = " AND";
			}
			if ('A' != sex) {
				query.append(clause).append(" PAT_SEX = ?");
				params.add(sex);
				clause = " AND";
			}		
			if (ageFrom != 0 || ageTo != 0) {
				query.append(clause).append(" PAT_AGE BETWEEN ? AND ?");
				params.add(ageFrom);
				params.add(ageTo);
				clause = " AND";
			}		
			query.append(" ORDER BY PAV_DATE DESC, PAV_ID");
			
			jpa.createQuery(query.toString(), PatientVaccine.class, false);
			jpa.setParameters(params, false);
			List<PatientVaccine> patientVaccineList = (List<PatientVaccine>)jpa.getList();
			patientVaccines = new ArrayList<PatientVaccine>(patientVaccineList);		
			
			jpa.commitTransaction();
		} catch (OHException e) {
			jpa.rollbackTransaction();
			throw e;
		}
		return patientVaccines;
	}

	/**
	 * inserts a {@link PatientVaccine} in the DB
	 * 
	 * @param patVac - the {@link PatientVaccine} to insert
	 * @return <code>true</code> if the item has been inserted, <code>false</code> otherwise 
	 * @throws OHException 
	 */
	public boolean newPatientVaccine(
			PatientVaccine patVac) throws OHException 
	{

		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			jpa.persist(patVac);
	    	jpa.commitTransaction();
		} catch (OHException e) {
			jpa.rollbackTransaction();
			throw e;
		}
		
		return result;
	}

	/**
	 * updates a {@link PatientVaccine} 
	 * 
	 * @param patVac - the {@link PatientVaccine} to update
	 * @return <code>true</code> if the item has been updated, <code>false</code> otherwise 
	 * @throws OHException 
	 */
	public boolean updatePatientVaccine(
			PatientVaccine patVac) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			jpa.merge(patVac);
	    	jpa.commitTransaction();
		} catch (OHException e) {
			jpa.rollbackTransaction();
			throw e;
		}
		
		return result;
	}

	/**
	 * deletes a {@link PatientVaccine} 
	 * 
	 * @param patVac - the {@link PatientVaccine} to delete
	 * @return <code>true</code> if the item has been deleted, <code>false</code> otherwise 
	 * @throws OHException 
	 */
	public boolean deletePatientVaccine(
			PatientVaccine patVac) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try {
			jpa.beginTransaction();	
			PatientVaccine objToRemove = (PatientVaccine) jpa.find(PatientVaccine.class, patVac.getCode());
			jpa.remove(objToRemove);
	    	jpa.commitTransaction();
		} catch (OHException e) {
			jpa.rollbackTransaction();
			throw e;
		}
		return result;
	}

	/**
	 * Returns the max progressive number within specified year or within current year if <code>0</code>.
	 * 
	 * @param year
	 * @return <code>int</code> - the progressive number in the year
	 * @throws OHException 
	 */
	public int getProgYear(
			int year) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil();
		ArrayList<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		Integer progYear = 0;
		
		try {
			jpa.beginTransaction();
			
			query.append("SELECT MAX(PAV_YPROG) FROM PATIENTVACCINE");
			if (year != 0) {
				query.append(" WHERE YEAR(PAV_DATE) = ?");
				params.add(year);
			}
			jpa.createQuery(query.toString(), null, false);
			jpa.setParameters(params, false);	
			progYear = (Integer)jpa.getResult();
			
			jpa.commitTransaction();
		} catch (OHException e) {
			jpa.rollbackTransaction();
			throw e;
		}
		
		return progYear == null ? new Integer(0) : progYear;
	}
	
	/**
	 * return a String representing the date in format <code>yyyy-MM-dd</code>
	 * 
	 * @param date
	 * @return the date in format <code>yyyy-MM-dd</code>
	 */
	private String convertToSQLDateLimited(GregorianCalendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date.getTime());
	}
}
