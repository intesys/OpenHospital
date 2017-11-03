package org.isf.admtype.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.admtype.model.AdmissionType;
import org.isf.admtype.service.AdmissionTypeIoOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestAdmissionType testAdmissionType;
	private static TestAdmissionTypeContext testAdmissionTypeContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testAdmissionType = new TestAdmissionType();
    	testAdmissionTypeContext = new TestAdmissionTypeContext();
    	
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
    	testAdmissionType = null;
    	testAdmissionTypeContext = null;

    	return;
    }
	
		
	@Test
	public void testAdmissionTypeGets() 
	{
		String code = "";
			

		try 
		{		
			code = _setupTestAdmissionType(false);
			_checkAdmissionTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testAdmissionTypeSets()
	{
		String code = "";
			

		try 
		{		
			code = _setupTestAdmissionType(true);
			_checkAdmissionTypeIntoDb(code);
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
		String code = "";
		AdmissionTypeIoOperation ioOperations = new AdmissionTypeIoOperation();
		
		
		try 
		{		
			code = _setupTestAdmissionType(false);
			AdmissionType foundAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
			ArrayList<AdmissionType> admissionTypes = ioOperations.getAdmissionType();
			
			assertEquals(foundAdmissionType.getDescription(), admissionTypes.get(admissionTypes.size()-1).getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoUpdateAdmissionType() 
	{
		String code = "";
		AdmissionTypeIoOperation ioOperations = new AdmissionTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			code = _setupTestAdmissionType(false);
			AdmissionType foundAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
			foundAdmissionType.setDescription("Update");
			result = ioOperations.updateAdmissionType(foundAdmissionType);
			AdmissionType updateAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
			
			assertEquals(true, result);
			assertEquals("Update", updateAdmissionType.getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoNewAdmissionType() 
	{
		AdmissionTypeIoOperation ioOperations = new AdmissionTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			AdmissionType admissionType = testAdmissionType.setup(true);
			result = ioOperations.newAdmissionType(admissionType);
			
			assertEquals(true, result);
			_checkAdmissionTypeIntoDb(admissionType.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoDeleteAdmissionType() 
	{
		String code = "";
		AdmissionTypeIoOperation ioOperations = new AdmissionTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestAdmissionType(false);
			AdmissionType foundAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
			result = ioOperations.deleteAdmissionType(foundAdmissionType);
			
			assertEquals(true, result);
			AdmissionType deletedAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
			assertEquals(null, deletedAdmissionType);
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
		AdmissionTypeIoOperation ioOperations = new AdmissionTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestAdmissionType(false);
			result = ioOperations.isCodePresent(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		assertEquals(true, result);
		
		return;
	}
	
	
	private void _saveContext() throws OHException 
    {	
		testAdmissionTypeContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testAdmissionTypeContext.getAllSaved());
		testAdmissionTypeContext.deleteNews(jpa);
        
        return;
    }
        
	private String _setupTestAdmissionType(
			boolean usingSet) throws OHException 
	{
		AdmissionType admissionType;
		

    	jpa.beginTransaction();	
    	admissionType = testAdmissionType.setup(usingSet);
		jpa.persist(admissionType);
    	jpa.commitTransaction();
    	
		return admissionType.getCode();
	}
		
	private void  _checkAdmissionTypeIntoDb(
			String code) throws OHException 
	{
		AdmissionType foundAdmissionType;
		

		foundAdmissionType = (AdmissionType)jpa.find(AdmissionType.class, code); 
		testAdmissionType.check(foundAdmissionType);
		
		return;
	}	
}