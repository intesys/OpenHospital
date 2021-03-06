package org.isf.medicals.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.isf.supplier.model.Supplier;
import org.isf.supplier.test.TestSupplier;
import org.isf.supplier.test.TestSupplierContext;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.ward.model.Ward;
import org.isf.ward.test.TestWard;
import org.isf.ward.test.TestWardContext;
import org.isf.medicals.model.Medical;
import org.isf.medstockmovtype.model.MovementType;
import org.isf.medstockmovtype.test.TestMovementType;
import org.isf.medstockmovtype.test.TestMovementTypeContext;
import org.isf.medtype.model.MedicalType;
import org.isf.medtype.test.TestMedicalType;
import org.isf.medtype.test.TestMedicalTypeContext;
import org.isf.medicals.service.MedicalsIoOperations;
import org.isf.medicals.test.TestMedical;
import org.isf.medicalstock.model.Lot;
import org.isf.medicalstock.model.Movement;
import org.isf.medicalstock.test.TestLot;
import org.isf.medicalstock.test.TestLotContext;
import org.isf.medicalstock.test.TestMovement;
import org.isf.medicalstock.test.TestMovementContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestMedical testMedical;
	private static TestMedicalContext testMedicalContext;
	private static TestMedicalType testMedicalType;
	private static TestMedicalTypeContext testMedicalTypeContext;
	private static TestMovement testMovement;
	private static TestMovementContext testMovementContext;
	private static TestMovementType testMovementType;
	private static TestMovementTypeContext testMovementTypeContext;
	private static TestWard testWard;
	private static TestWardContext testWardContext;
	private static TestLot testLot;
	private static TestLotContext testLotContext;
	private static TestSupplier testSupplier;
	private static TestSupplierContext testSupplierContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testMedical = new TestMedical();
    	testMedicalContext = new TestMedicalContext();
    	testMedicalType = new TestMedicalType();
    	testMedicalTypeContext = new TestMedicalTypeContext();
    	testMovement = new TestMovement();
    	testMovementContext = new TestMovementContext();
    	testMovementType = new TestMovementType();
    	testMovementTypeContext = new TestMovementTypeContext();
    	testWard = new TestWard();
    	testWardContext = new TestWardContext();
    	testLot = new TestLot();
    	testLotContext = new TestLotContext();
    	testSupplier = new TestSupplier();
    	testSupplierContext = new TestSupplierContext();
    	
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
    	testMedical = null;
    	testMedicalContext = null;
    	testMedicalType = null;
    	testMedicalTypeContext = null;
    	testMovement = null;
    	testMovementContext = null;
    	testMovementType = null;
    	testMovementTypeContext = null;
    	testWard = null;
    	testWardContext = null;
    	testLot = null;
    	testLotContext = null;
    	testSupplier = null;
    	testSupplierContext = null;

    	return;
    }
	
	
	@Test
	public void testMedicalGets() 
	{
		int code = 0;
			
		
		try 
		{		
			code = _setupTestMedical(false);
			_checkMedicalIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testMedicalSets() 
	{
		int code = 0;
			

		try 
		{		
			code = _setupTestMedical(true);
			_checkMedicalIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetMedical() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			Medical medical = ioOperations.getMedical(code);

			assertEquals(foundMedical.getCode(), medical.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetMedicals() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			ArrayList<Medical> medicals = ioOperations.getMedicals(String.valueOf(foundMedical.getDescription()));
			
			assertEquals(code, medicals.get(medicals.size()-1).getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoGetMedicalsType() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			ArrayList<Medical> medicals = ioOperations.getMedicals(foundMedical.getDescription(), foundMedical.getType().getCode(), false);
			
			assertEquals(code, medicals.get(medicals.size()-1).getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoMedicalExists() 
	{
		int code = 0;
		boolean result = false;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			result = ioOperations.medicalExists(foundMedical);
			
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
	public void testIoUpdateMedical() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		boolean result = false;
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			foundMedical.setDescription("Update");
			result = ioOperations.updateMedical(foundMedical);
			Medical updateMedical = (Medical)jpa.find(Medical.class, code); 
			
			assertEquals(true, result);
			assertEquals("Update", updateMedical.getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoNewMedical() 
	{
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		boolean result = false;
		
		
		try 
		{				
			jpa.beginTransaction();	
			MedicalType medicalType = testMedicalType.setup(false);
			jpa.persist(medicalType);
			jpa.commitTransaction();			
			Medical movementType = testMedical.setup(medicalType, true);
			result = ioOperations.newMedical(movementType);
			
			assertEquals(true, result);
			_checkMedicalIntoDb(movementType.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoDeleteMedical() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		boolean result = false;
		

		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			result = ioOperations.deleteMedical(foundMedical);
			
			assertEquals(true, result);
			Medical deletedMedical = (Medical)jpa.find(Medical.class, code); 
			assertEquals(null, deletedMedical);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetMedicalLock() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		
		
		try 
		{		
			code = _setupTestMedical(false);
			Medical foundMedical = (Medical)jpa.find(Medical.class, code); 
			int lock = ioOperations.getMedicalLock(code);
			
			assertEquals(foundMedical.getLock(), lock);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIsMedicalReferencedInStockMovement() 
	{
		int code = 0;
		MedicalsIoOperations ioOperations = new MedicalsIoOperations();
		boolean result = false;
		
		
		try 
		{			
			code = _setupTestMovement(false);
			Movement foundMovement = (Movement)jpa.find(Movement.class, code); 
			result = ioOperations.isMedicalReferencedInStockMovement(foundMovement.getMedical().getCode());
			
			assertEquals(true, result);
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
		testMedicalContext.saveAll(jpa);
		testMedicalTypeContext.saveAll(jpa);
		testMovementContext.saveAll(jpa);
		testMovementTypeContext.saveAll(jpa);
		testWardContext.saveAll(jpa);
		testLotContext.saveAll(jpa);
		testSupplierContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testMedicalContext.getAllSaved());
		testMovementContext.deleteNews(jpa);
		testMedicalContext.deleteNews(jpa);
		testMedicalTypeContext.deleteNews(jpa);
		testMovementTypeContext.deleteNews(jpa);
		testWardContext.deleteNews(jpa);
		testLotContext.deleteNews(jpa);
		testSupplierContext.deleteNews(jpa);
        
        return;
    }
    
	private int _setupTestMedical(
			boolean usingSet) throws OHException 
	{
		Medical medical;
		MedicalType medicalType = testMedicalType.setup(false);
		
	
		jpa.beginTransaction();	
		medical = testMedical.setup(medicalType, usingSet);
		jpa.persist(medicalType);
		jpa.persist(medical);
		jpa.commitTransaction();
		
		return medical.getCode();
	}
		
	private void  _checkMedicalIntoDb(
			int code) throws OHException 
	{
		Medical foundMedical;
		
	
		foundMedical = (Medical)jpa.find(Medical.class, code); 
		testMedical.check(foundMedical);
		
		return;
	}	

    private int _setupTestMovement(
			boolean usingSet) throws OHException 
	{
    	Movement movement;
		MedicalType medicalType = testMedicalType.setup(false);
		Medical medical =  testMedical.setup(medicalType, false);
		MovementType movementType = testMovementType.setup(false);
		Ward ward = testWard.setup(false);
		Lot lot = testLot.setup(false);
		Supplier supplier = testSupplier.setup(false);
		
	
		jpa.beginTransaction();	
		movement = testMovement.setup(medical, movementType, ward, lot, supplier, usingSet);
		jpa.persist(medicalType);
		jpa.persist(medical);
		jpa.persist(movementType);
		jpa.persist(ward);
		jpa.persist(lot);
		jpa.persist(supplier);
		jpa.persist(movement);
		jpa.commitTransaction();
		
		return movement.getCode();
	}
}