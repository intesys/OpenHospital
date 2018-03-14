package org.isf.lab.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.isf.utils.exception.OHException;
import org.isf.exa.model.Exam;
import org.isf.lab.model.Laboratory;
import org.isf.patient.model.Patient;

import static org.junit.Assert.assertEquals;

public class TestLaboratory 
{	 
	private int code = 0;
	private String material = "TestMaterial";
	private GregorianCalendar now = new GregorianCalendar();
	private GregorianCalendar registrationDate = new GregorianCalendar(now.get(Calendar.YEAR), 1, 1);
	private GregorianCalendar examDate = new GregorianCalendar(now.get(Calendar.YEAR), 10, 11);
	private String result = "TestResult";
	private int lock = 1;
	private String note = "TestNote";
	private String patName = "TestPatientName";
	private String InOutPatient = "O";
	private int age = 37;
	private String sex = "F";
    
			
	public Laboratory setup(
			Exam exam,
			Patient patient,
			boolean usingSet) throws OHException 
	{
		Laboratory laboratory;
	
				
		if (usingSet == true)
		{
			laboratory = new Laboratory();
			_setParameters(laboratory, exam, patient);
		}
		else
		{
			// Create Laboratory with all parameters 
			laboratory = new Laboratory(code, exam, registrationDate, result, lock, note, patient, patName);
			laboratory.setAge(age);
			laboratory.setExamDate(examDate);
			laboratory.setInOutPatient(InOutPatient);
			laboratory.setMaterial(material);
			laboratory.setResult(result);
			laboratory.setSex(sex);
		}
				    	
		return laboratory;
	}
	
	public void _setParameters(
			Laboratory laboratory,
			Exam exam,
			Patient patient) 
	{	
		laboratory.setAge(age);
		laboratory.setDate(registrationDate);
		laboratory.setExam(exam);
		laboratory.setExamDate(examDate);
		laboratory.setInOutPatient(InOutPatient);
		laboratory.setMaterial(material);
		laboratory.setNote(note);
		laboratory.setPatId(patient);
		laboratory.setPatName(patName);
		laboratory.setResult(result);
		laboratory.setSex(sex);
		laboratory.setLock(lock);
		
		return;
	}
	
	public void check(
			Laboratory laboratory) 
	{		
    	System.out.println("Check Laboratory: " + laboratory.getCode());
    	assertEquals(age, laboratory.getAge());		
    	assertEquals(registrationDate, laboratory.getDate());	
    	assertEquals(examDate, laboratory.getExamDate());
    	assertEquals(InOutPatient, laboratory.getInOutPatient());
    	assertEquals(material, laboratory.getMaterial());		
    	assertEquals(note, laboratory.getNote());	
    	assertEquals(patName, laboratory.getPatName());		
    	assertEquals(result, laboratory.getResult());	
    	assertEquals(sex, laboratory.getSex());		
    	assertEquals(lock, laboratory.getLock());
		
		return;
	}
}
