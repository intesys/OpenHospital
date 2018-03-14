package org.isf.patient.test;


import org.isf.patient.model.Patient;
import org.isf.patient.service.PatientIoOperations;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List; 

import static org.junit.Assert.assertEquals;

public class Tests 
{	
	private static DbJpaUtil jpa;	
	private static TestPatient testPatient;
	private static TestPatientContext testPatientContext;
	

    @BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testPatient = new TestPatient();
    	testPatientContext = new TestPatientContext();

        return;
    }

    @Before
    public void setUp() throws OHException
    {
        jpa.open();
        
        _saveContext();
		
		return;
    }
        
    @After
    public void tearDown() throws Exception 
    {
        _restoreContext();   
        
        jpa.flush();
        jpa.close();
                
        return;
    }
    
    @AfterClass
    public static void tearDownClass() throws OHException 
    {
    	//jpa.destroy();

    	return;
    }
    
	
	@Test
	public void testPatientGets() throws OHException 
	{
		Integer code = 0;
				

		try 
		{		
			code = _setupTestPatient(false);
			_checkPatientIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testPatientSets() 
	{
		Integer code = 0;
		
				
		try 
		{		
			code = _setupTestPatient(true);	
			_checkPatientIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testIoGetPatients() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();

		
		try 
		{		
			_setupTestPatient(false);
			ArrayList<Patient> patients = ioOperations.getPatients();
			
			testPatient.check( patients.get(patients.size()-1));
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetPatientsWithHeightAndWeight() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{		
			_setupTestPatient(false);
			// Pay attention that query return with PAT_ID descendant
			ArrayList<Patient> patients = ioOperations.getPatientsWithHeightAndWeight(null);

			testPatient.check(patients.get(0));
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetPatientsWithHeightAndWeightRegEx() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{	
			Integer code = _setupTestPatient(false);
			Patient foundPatient = (Patient)jpa.find(Patient.class, code); 
			ArrayList<Patient> patients = ioOperations.getPatientsWithHeightAndWeight(foundPatient.getFirstName());
			
			testPatient.check(patients.get(0));
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetPatientFromName() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{	
			Integer code = _setupTestPatient(false);
			Patient foundPatient = (Patient)jpa.find(Patient.class, code); 
			Patient patient = ioOperations.getPatient(foundPatient.getName());
			
			assertEquals(foundPatient.getName(), patient.getName());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetPatientFromCode() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{		
			Integer code = _setupTestPatient(false);
			Patient foundPatient = (Patient)jpa.find(Patient.class, code); 
			Patient patient = ioOperations.getPatient(code);

			assertEquals(foundPatient.getName(), patient.getName());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetPatientAll() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{		
			Patient patient = ioOperations.getPatientAll(testPatientContext.getAllSaved().get(0).getCode());
			
			
			assertEquals(testPatientContext.getAllSaved().get(0).getName(), patient.getName());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testNewPatient() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();

		
		try 
		{		
			Patient patient = testPatient.setup(true);;
			boolean result = ioOperations.newPatient(patient);
			
			assertEquals(true, result);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
		
	@Test
	public void testUpdatePatientFalse() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		int lock = 0;
		
		
		try 
		{		
			Integer code = _setupTestPatient(false);
			Patient patient = (Patient)jpa.find(Patient.class, code); 
			lock = patient.getLock();
			boolean result = ioOperations.updatePatient(patient, false);
			
			assertEquals(true, result);
			assertEquals(lock, patient.getLock());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
		
	@Test
	public void testUpdatePatientTrue() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		int lock = 0;
		
		
		try 
		{		
			Integer code = _setupTestPatient(false);
			Patient patient = (Patient)jpa.find(Patient.class, code); 
			lock = patient.getLock();
			boolean result = ioOperations.updatePatient(patient, true);
			
			assertEquals(true, result);
			assertEquals((lock + 1), patient.getLock());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testDeletePatient() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{		
			Integer code = _setupTestPatient(false);
			Patient patient = (Patient)jpa.find(Patient.class, code); 
			boolean result = ioOperations.deletePatient(patient);
			Patient deletedPatient = _getDeletedPatient(code);
			
			assertEquals(true, result);
			assertEquals(code, deletedPatient.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}	
	
	@Test
	public void testIsPatientPresent() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		
		
		try 
		{		
			Integer code = _setupTestPatient(false);
			Patient foundPatient = (Patient)jpa.find(Patient.class, code); 
			boolean result = ioOperations.isPatientPresent(foundPatient.getName());
			
			assertEquals(true, result);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}	
	
	@Test
	public void testGetNextPatientCode() 
	{
		PatientIoOperations ioOperations = new PatientIoOperations();
		Integer code = 0;
		Integer max = 0;
		
		
		try 
		{		
			code = ioOperations.getNextPatientCode();
			max = testPatientContext.getMaxCode();
			assertEquals(max+1, code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testMergePatientHistory()
	{		
		//TODO: function not yet ported to JPA
		assertEquals(1, 1);
		
		return;
	}	
	
	
	private void _saveContext() throws OHException 
    {	
		testPatientContext.saveAll(jpa);
        		
        return;
    }
		
    private void _restoreContext() throws OHException 
    {
		testPatientContext.deleteNews(jpa);
        
        return;
    }
    	
	private Integer _setupTestPatient(
			boolean usingSet) throws OHException 
	{
		Patient patient;
	
		
    	jpa.beginTransaction();	
    	patient = testPatient.setup(usingSet);
		jpa.persist(patient);
    	jpa.commitTransaction();
				    	
		return patient.getCode();
	}
		
	private void _checkPatientIntoDb(
			Integer code) throws OHException 
	{
		Patient foundPatient; 
			

		foundPatient = (Patient)jpa.find(Patient.class, code); 
		testPatient.check(foundPatient);
		
		return;
	}
		
	@SuppressWarnings("unchecked")
	private Patient _getDeletedPatient(
			Integer Code) throws OHException 
	{	
		ArrayList<Object> params = new ArrayList<Object>();
		
		
		jpa.beginTransaction();			
		jpa.createQuery("SELECT * FROM PATIENT WHERE PAT_DELETED = 'Y' AND PAT_ID = ?", Patient.class, false);
		params.add(Code);
		jpa.setParameters(params, false);
		List<Patient> patients = (List<Patient>)jpa.getList();
		jpa.commitTransaction();
		
		return patients.get(0);
	}
}

