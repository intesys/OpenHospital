package org.isf.opetype.test;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.opetype.model.OperationType;
import org.isf.opetype.service.OperationTypeIoOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestOperationType testOperationType;
	private static TestOperationTypeContext testOperationTypeContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testOperationType = new TestOperationType();
    	testOperationTypeContext = new TestOperationTypeContext();
    	
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
	public void testOperationTypeGets() throws OHException 
	{
		String code = "";
			

		try 
		{		
			code = _setupTestOperationType(false);
			_checkOperationTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testOperationTypeSets() 
	{
		String code = "";
			

		try 
		{		
			code = _setupTestOperationType(true);
			_checkOperationTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetOperationType() 
	{
		String code = "";
		OperationTypeIoOperation ioOperations = new OperationTypeIoOperation();
		
		
		try 
		{		
			code = _setupTestOperationType(false);
			OperationType foundOperationType = (OperationType)jpa.find(OperationType.class, code); 
			ArrayList<OperationType> operationTypes = ioOperations.getOperationType();
			
			assertEquals(foundOperationType.getDescription(), operationTypes.get(operationTypes.size() - 1).getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoUpdateOperationType() 
	{
		String code = "";
		OperationTypeIoOperation ioOperations = new OperationTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			code = _setupTestOperationType(false);
			OperationType foundOperationType = (OperationType)jpa.find(OperationType.class, code); 
			foundOperationType.setDescription("Update");
			result = ioOperations.updateOperationType(foundOperationType);
			OperationType updateOperationType = (OperationType)jpa.find(OperationType.class, code); 
			
			assertEquals(true, result);
			assertEquals("Update", updateOperationType.getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoNewOperationType() 
	{
		OperationTypeIoOperation ioOperations = new OperationTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			OperationType operationType = testOperationType.setup(true);
			result = ioOperations.newOperationType(operationType);
			
			assertEquals(true, result);
			_checkOperationTypeIntoDb(operationType.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoDeleteOperationType() 
	{
		String code = "";
		OperationTypeIoOperation ioOperations = new OperationTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestOperationType(false);
			OperationType foundOperationType = (OperationType)jpa.find(OperationType.class, code); 
			result = ioOperations.deleteOperationType(foundOperationType);
			
			assertEquals(true, result);
			OperationType deletedOperationType = (OperationType)jpa.find(OperationType.class, code); 
			assertEquals(null, deletedOperationType);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoIsCodePresent() 
	{
		String code = "";
		OperationTypeIoOperation ioOperations = new OperationTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestOperationType(false);
			result = ioOperations.isCodePresent(code);
			
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
		testOperationTypeContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testOperationTypeContext.getAllSaved());
		testOperationTypeContext.deleteNews(jpa);
        
        return;
    }
        
	private String _setupTestOperationType(
			boolean usingSet) throws OHException 
	{
		OperationType operationType;
		

    	jpa.beginTransaction();	
    	operationType = testOperationType.setup(usingSet);
		jpa.persist(operationType);
    	jpa.commitTransaction();
    	
		return operationType.getCode();
	}
		
	private void  _checkOperationTypeIntoDb(
			String code) throws OHException 
	{
		OperationType foundOperationType;
		

		foundOperationType = (OperationType)jpa.find(OperationType.class, code); 
		testOperationType.check(foundOperationType);
		
		return;
	}	
}