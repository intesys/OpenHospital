package org.isf.admission.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.isf.operation.model.Operation;
import org.isf.operation.test.TestOperation;
import org.isf.operation.test.TestOperationContext;
import org.isf.opetype.model.OperationType;
import org.isf.opetype.test.TestOperationType;
import org.isf.opetype.test.TestOperationTypeContext;
import org.isf.patient.model.Patient;
import org.isf.patient.test.TestPatient;
import org.isf.patient.test.TestPatientContext;
import org.isf.pregtreattype.model.PregnantTreatmentType;
import org.isf.pregtreattype.test.TestPregnantTreatmentType;
import org.isf.pregtreattype.test.TestPregnantTreatmentTypeContext;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.ward.model.Ward;
import org.isf.ward.test.TestWard;
import org.isf.ward.test.TestWardContext;
import org.isf.admission.model.Admission;
import org.isf.admission.model.AdmittedPatient;
import org.isf.admission.service.AdmissionIoOperations;
import org.isf.admtype.model.AdmissionType;
import org.isf.admtype.test.TestAdmissionType;
import org.isf.admtype.test.TestAdmissionTypeContext;
import org.isf.disctype.model.DischargeType;
import org.isf.disctype.test.TestDischargeType;
import org.isf.disctype.test.TestDischargeTypeContext;
import org.isf.disease.model.Disease;
import org.isf.disease.test.TestDisease;
import org.isf.disease.test.TestDiseaseContext;
import org.isf.distype.model.DiseaseType;
import org.isf.distype.test.TestDiseaseType;
import org.isf.distype.test.TestDiseaseTypeContext;
import org.isf.dlvrrestype.model.DeliveryResultType;
import org.isf.dlvrrestype.test.TestDeliveryResultType;
import org.isf.dlvrrestype.test.TestDeliveryResultTypeContext;
import org.isf.dlvrtype.model.DeliveryType;
import org.isf.dlvrtype.test.TestDeliveryType;
import org.isf.dlvrtype.test.TestDeliveryTypeContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestAdmission testAdmission;
	private static TestAdmissionContext testAdmissionContext;
	private static TestWard testWard;
	private static TestWardContext testWardContext;
	private static TestPatient testPatient;
	private static TestPatientContext testPatientContext;
	private static TestAdmissionType testAdmissionType;
	private static TestAdmissionTypeContext testAdmissionTypeContext;
	private static TestDiseaseType testDiseaseType;
	private static TestDiseaseTypeContext testDiseaseTypeContext;
	private static TestDisease testDisease;
	private static TestDiseaseContext testDiseaseContext;
	private static TestOperationType testOperationType;
	private static TestOperationTypeContext testOperationTypeContext;
	private static TestOperation testOperation;
	private static TestOperationContext testOperationContext;
	private static TestDischargeType testDischargeType;
	private static TestDischargeTypeContext testDischargeTypeContext;
	private static TestPregnantTreatmentType testPregnantTreatmentType;
	private static TestPregnantTreatmentTypeContext testPregnantTreatmentTypeContext;
	private static TestDeliveryType testDeliveryType;
	private static TestDeliveryTypeContext testDeliveryTypeContext;
	private static TestDeliveryResultType testDeliveryResultType;
	private static TestDeliveryResultTypeContext testDeliveryResultTypeContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testAdmission = new TestAdmission();
    	testAdmissionContext = new TestAdmissionContext();
    	testWard = new TestWard();
    	testWardContext = new TestWardContext();
    	testPatient = new TestPatient();
    	testPatientContext = new TestPatientContext();
    	testAdmissionType = new TestAdmissionType();
    	testAdmissionTypeContext = new TestAdmissionTypeContext();
    	testDiseaseType = new TestDiseaseType();
    	testDiseaseTypeContext = new TestDiseaseTypeContext();
    	testDisease = new TestDisease();
    	testDiseaseContext = new TestDiseaseContext();
    	testOperationType = new TestOperationType();
    	testOperationTypeContext = new TestOperationTypeContext();
    	testOperation = new TestOperation();
    	testOperationContext = new TestOperationContext();
    	testDischargeType = new TestDischargeType();
    	testDischargeTypeContext = new TestDischargeTypeContext();
    	testPregnantTreatmentType = new TestPregnantTreatmentType();
    	testPregnantTreatmentTypeContext = new TestPregnantTreatmentTypeContext();
    	testDeliveryType = new TestDeliveryType();
    	testDeliveryTypeContext = new TestDeliveryTypeContext();
    	testDeliveryResultType = new TestDeliveryResultType();
    	testDeliveryResultTypeContext = new TestDeliveryResultTypeContext();
    	
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
    	testAdmission = null;
    	testAdmissionContext = null;
    	testWard = null;
    	testWardContext = null;
    	testPatient = null;
    	testPatientContext = null;
    	testAdmissionType = null;
    	testAdmissionTypeContext = null;
    	testDiseaseType = null;
    	testDiseaseTypeContext = null;
    	testDisease = null;
    	testDiseaseContext = null;
    	testOperationType = null;
    	testOperationTypeContext = null;
    	testOperation = null;
    	testOperationContext = null;
    	testDischargeType = null;
    	testDischargeTypeContext = null;
    	testPregnantTreatmentType = null;
    	testPregnantTreatmentTypeContext = null;
    	testDeliveryType = null;
    	testDeliveryTypeContext = null;
    	testDeliveryResultType = null;
    	testDeliveryResultTypeContext = null;

    	return;
    }
	
		
	@Test
	public void testAdmissionGets()
	{
		int id = 0;
			
		
		try 
		{		
			id = _setupTestAdmission(false);
			_checkAdmissionIntoDb(id);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testAdmissionSets()
	{
		int id = 0;
			

		try 
		{		
			id = _setupTestAdmission(true);
			_checkAdmissionIntoDb(id);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetAdmittedPatients() 
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			ArrayList<AdmittedPatient> patients = ioOperations.getAdmittedPatients(null);
			
			assertEquals(foundAdmission.getId(), patients.get(0).getAdmission().getId());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetCurrentAdmission() 
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			jpa.beginTransaction();	
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			foundAdmission.setDisDate(null);
			jpa.merge(foundAdmission);
			jpa.commitTransaction();
			Admission ioAdmission = ioOperations.getCurrentAdmission(foundAdmission.getPatient());
			
			assertEquals(foundAdmission.getNote(), ioAdmission.getNote());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
	
		return;
	}
	
	@Test
	public void testIoGetAdmission()
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = ioOperations.getAdmission(id);
			
			testAdmission.check(foundAdmission);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
	
		return;
	}
	
	@Test
	public void testIoGetAdmissions() 
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			ArrayList<Admission> admissions = ioOperations.getAdmissions(foundAdmission.getPatient());
			
			assertEquals(foundAdmission.getId(), admissions.get(admissions.size()-1).getId());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
	
		return;
	}
	
	@Test
	public void testIoNewAdmission()
	{
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		boolean result = false;

		
		try 
		{		
			Ward ward = testWard.setup(false);
			Patient patient = testPatient.setup(true);
			AdmissionType admissionType = testAdmissionType.setup(false);
			DiseaseType diseaseType = testDiseaseType.setup(false);
			Disease diseaseIn = testDisease.setup(diseaseType, false);
			Disease diseaseOut1 = testDisease.setup(diseaseType, false);
			diseaseOut1.setCode("888");
			Disease diseaseOut2 = testDisease.setup(diseaseType, false);
			diseaseOut2.setCode("777");
			Disease diseaseOut3 = testDisease.setup(diseaseType, false); 
			diseaseOut3.setCode("666");
			OperationType operationType = testOperationType.setup(false);
			Operation operation = testOperation.setup(operationType, false);
			DischargeType dischargeType = testDischargeType.setup(false);
			PregnantTreatmentType pregTreatmentType = testPregnantTreatmentType.setup(false);
			DeliveryType deliveryType = testDeliveryType.setup(false);
			DeliveryResultType deliveryResult = testDeliveryResultType.setup(false);

			
			jpa.beginTransaction();	
			jpa.persist(ward);
			jpa.persist(patient);	
			jpa.persist(admissionType);
			jpa.persist(diseaseType);
			jpa.persist(diseaseIn);
			jpa.persist(diseaseOut1);
			jpa.persist(diseaseOut2);
			jpa.persist(diseaseOut3);
			jpa.persist(operationType);
			jpa.persist(operation);
			jpa.persist(dischargeType);
			jpa.persist(pregTreatmentType);
			jpa.persist(deliveryType);
			jpa.persist(deliveryResult);
			jpa.commitTransaction();
			
			Admission admission = testAdmission.setup(ward, patient, admissionType, diseaseIn, diseaseOut1, 
					diseaseOut2, diseaseOut3, operation, dischargeType, pregTreatmentType, 
					deliveryType, deliveryResult, true);;
					result = ioOperations.newAdmission(admission);
					
					assertEquals(true, result);
					_checkAdmissionIntoDb(admission.getId());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoNewAdmissionReturnKey() 
	{
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		int id = 0;
		
		
		try 
		{		
			Ward ward = testWard.setup(false);
			Patient patient = testPatient.setup(true);
			AdmissionType admissionType = testAdmissionType.setup(false);
			DiseaseType diseaseType = testDiseaseType.setup(false);
			Disease diseaseIn = testDisease.setup(diseaseType, false);
			Disease diseaseOut1 = testDisease.setup(diseaseType, false);
			diseaseOut1.setCode("888");
			Disease diseaseOut2 = testDisease.setup(diseaseType, false);
			diseaseOut2.setCode("777");
			Disease diseaseOut3 = testDisease.setup(diseaseType, false); 
			diseaseOut3.setCode("666");
			OperationType operationType = testOperationType.setup(false);
			Operation operation = testOperation.setup(operationType, false);
			DischargeType dischargeType = testDischargeType.setup(false);
			PregnantTreatmentType pregTreatmentType = testPregnantTreatmentType.setup(false);
			DeliveryType deliveryType = testDeliveryType.setup(false);
			DeliveryResultType deliveryResult = testDeliveryResultType.setup(false);
			
			
			jpa.beginTransaction();	
			jpa.persist(ward);
			jpa.persist(patient);	
			jpa.persist(admissionType);
			jpa.persist(diseaseType);
			jpa.persist(diseaseIn);
			jpa.persist(diseaseOut1);
			jpa.persist(diseaseOut2);
			jpa.persist(diseaseOut3);
			jpa.persist(operationType);
			jpa.persist(operation);
			jpa.persist(dischargeType);
			jpa.persist(pregTreatmentType);
			jpa.persist(deliveryType);
			jpa.persist(deliveryResult);
			jpa.commitTransaction();
						
			Admission admission = testAdmission.setup(ward, patient, admissionType, diseaseIn, diseaseOut1, 
					diseaseOut2, diseaseOut3, operation, dischargeType, pregTreatmentType, 
					deliveryType, deliveryResult, true);;
					id = ioOperations.newAdmissionReturnKey(admission);
					
					_checkAdmissionIntoDb(id);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoHasAdmissionModified()
	{
		int id = 0;
		boolean result = true;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			result = ioOperations.hasAdmissionModified(foundAdmission);
			
			assertEquals(false, result);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
	
		return;
	}

	@Test
	public void testIoUpdateAdmission() 
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		boolean result = false;
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			foundAdmission.setNote("Update");
			result = ioOperations.updateAdmission(foundAdmission);
			Admission updateAdmission = (Admission)jpa.find(Admission.class, id); 
			
			assertEquals(true, result);
			assertEquals("Update", updateAdmission.getNote());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetAdmissionType() 
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id); 
			ArrayList<AdmissionType> admissionTypes = ioOperations.getAdmissionType();
			
			assertEquals(foundAdmission.getAdmType().getDescription(), admissionTypes.get(admissionTypes.size()-1).getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetDischargeType()
	{
		int id = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id);  
			ArrayList<DischargeType> dischargeTypes = ioOperations.getDischargeType();
			
			assertEquals(foundAdmission.getDisType().getDescription(), dischargeTypes.get(dischargeTypes.size()-1).getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetNextYProg()
	{
		int id = 0;
		int next = 1;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id);  
			next = ioOperations.getNextYProg(foundAdmission.getWard().getCode());
			
			assertEquals(foundAdmission.getYProg() + 1, next);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
	
		return;
	}
	
	@Test
	public void testIoSetDeleted() 
	{
		int id = 0;
		boolean result = true;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id);  
			result = ioOperations.setDeleted(foundAdmission.getId());
			
			assertEquals(true, result);
			assertEquals("Y", foundAdmission.getDeleted());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}

		return;
	}
	
	@Test
	public void testIoGetUsedWardBed()
	{
		int id = 0;
		int result = 0;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id);  
			result = ioOperations.getUsedWardBed(foundAdmission.getWard().getCode());
			
			assertEquals(1, result);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}

		return;
	}
	
	@Test
	public void testIoDeletePatientPhoto()
	{
		int id = 0;
		boolean result = true;
		AdmissionIoOperations ioOperations = new AdmissionIoOperations();
		
		
		try 
		{		
			id = _setupTestAdmission(false);
			Admission foundAdmission = (Admission)jpa.find(Admission.class, id);  
			result = ioOperations.deletePatientPhoto(foundAdmission.getPatient().getCode());
			
			assertEquals(true, result);
			assertEquals(null, foundAdmission.getPatient().getPhoto());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}

		return;
	}
	
	
	private void _saveContext() throws OHException 
    {	
		testAdmissionContext.saveAll(jpa);
    	testWardContext.saveAll(jpa);
    	testPatientContext.saveAll(jpa);
    	testAdmissionTypeContext.saveAll(jpa);
    	testDiseaseTypeContext.saveAll(jpa);
    	testDiseaseContext.saveAll(jpa);
		testDiseaseContext.addMissingKey(jpa);
    	testOperationTypeContext.saveAll(jpa);
    	testOperationContext.saveAll(jpa);
    	testDischargeTypeContext.saveAll(jpa);
    	testPregnantTreatmentTypeContext.saveAll(jpa);
    	testDeliveryTypeContext.saveAll(jpa);
    	testDeliveryResultTypeContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testAdmissionContext.getAllSaved());
		testAdmissionContext.deleteNews(jpa);
    	testWardContext.deleteNews(jpa);
    	testPatientContext.deleteNews(jpa);
    	testAdmissionTypeContext.deleteNews(jpa);
    	testDiseaseContext.deleteNews(jpa);
    	testDiseaseTypeContext.deleteNews(jpa);
    	testOperationContext.deleteNews(jpa);
    	testOperationTypeContext.deleteNews(jpa);
    	testDischargeTypeContext.deleteNews(jpa);
    	testPregnantTreatmentTypeContext.deleteNews(jpa);
    	testDeliveryTypeContext.deleteNews(jpa);
    	testDeliveryResultTypeContext.deleteNews(jpa);
        
        return;
    }
        
	private int _setupTestAdmission(
			boolean usingSet) throws OHException, InterruptedException 
	{
		Admission admission;
		Ward ward = testWard.setup(false);
		Patient patient = testPatient.setup(true);
		AdmissionType admissionType = testAdmissionType.setup(false);
		DiseaseType diseaseType = testDiseaseType.setup(false);
		Disease diseaseIn = testDisease.setup(diseaseType, false);
		Disease diseaseOut1 = testDisease.setup(diseaseType, false);
		diseaseOut1.setCode("888");
		Disease diseaseOut2 = testDisease.setup(diseaseType, false);
		diseaseOut2.setCode("777");
		Disease diseaseOut3 = testDisease.setup(diseaseType, false); 
		diseaseOut3.setCode("666");
		OperationType operationType = testOperationType.setup(false);
		Operation operation = testOperation.setup(operationType, false);
		DischargeType dischargeType = testDischargeType.setup(false);
		PregnantTreatmentType pregTreatmentType = testPregnantTreatmentType.setup(false);
		DeliveryType deliveryType = testDeliveryType.setup(false);
		DeliveryResultType deliveryResult = testDeliveryResultType.setup(false);
		

    	jpa.beginTransaction();	
    	admission = testAdmission.setup(ward, patient, admissionType, diseaseIn, diseaseOut1, 
    			diseaseOut2, diseaseOut3, operation, dischargeType, pregTreatmentType, 
    			deliveryType, deliveryResult, usingSet);
    	jpa.persist(ward);
    	jpa.persist(patient);	
    	jpa.persist(admissionType);
    	jpa.persist(diseaseType);
    	jpa.persist(diseaseIn);
    	jpa.persist(diseaseOut1);
    	jpa.persist(diseaseOut2);
    	jpa.persist(diseaseOut3);
    	jpa.persist(operationType);
    	jpa.persist(operation);
    	jpa.persist(dischargeType);
    	jpa.persist(pregTreatmentType);
    	jpa.persist(deliveryType);
    	jpa.persist(deliveryResult);
    	jpa.persist(admission);
    	jpa.commitTransaction();
    	
		return admission.getId();
	}
		
	private void  _checkAdmissionIntoDb(
			int id) throws OHException 
	{
		Admission foundAdmission;
		

		foundAdmission = (Admission)jpa.find(Admission.class, id); 
		testAdmission.check(foundAdmission);
		
		return;
	}	
}