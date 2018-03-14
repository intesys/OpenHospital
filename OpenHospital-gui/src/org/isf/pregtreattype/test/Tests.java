package org.isf.pregtreattype.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.pregtreattype.service.PregnantTreatmentTypeIoOperation;
import org.isf.pregtreattype.model.PregnantTreatmentType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestPregnantTreatmentType testPregnantTreatmentType;
	private static TestPregnantTreatmentTypeContext testPregnantTreatmentTypeContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testPregnantTreatmentType = new TestPregnantTreatmentType();
    	testPregnantTreatmentTypeContext = new TestPregnantTreatmentTypeContext();
    	
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
	public void testPregnantTreatmentTypeGets() 
	{
		String code = "";
			

		try 
		{		
			code = _setupTestPregnantTreatmentType(false);
			_checkPregnantTreatmentTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testPregnantTreatmentTypeSets() 
	{
		String code = "";
			

		try 
		{		
			code = _setupTestPregnantTreatmentType(true);
			_checkPregnantTreatmentTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoGetPregnantTreatmentType() 
	{
		String code = "";
		PregnantTreatmentTypeIoOperation ioOperations = new PregnantTreatmentTypeIoOperation();
		
		
		try 
		{		
			code = _setupTestPregnantTreatmentType(false);
			PregnantTreatmentType foundPregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
			ArrayList<PregnantTreatmentType> pregnantTreatmentTypes = ioOperations.getPregnantTreatmentType();
			
			for (int i=0; i<pregnantTreatmentTypes.size(); i++)
			{
				if (pregnantTreatmentTypes.get(i).getCode().equals(code))
				{
					assertEquals(foundPregnantTreatmentType.getDescription(), pregnantTreatmentTypes.get(i).getDescription());
				}
			}
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoUpdatePregnantTreatmentType() 
	{
		String code = "";
		PregnantTreatmentTypeIoOperation ioOperations = new PregnantTreatmentTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			code = _setupTestPregnantTreatmentType(false);
			PregnantTreatmentType foundPregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
			foundPregnantTreatmentType.setDescription("Update");
			result = ioOperations.updatePregnantTreatmentType(foundPregnantTreatmentType);
			PregnantTreatmentType updatePregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
			
			assertEquals(true, result);
			assertEquals("Update", updatePregnantTreatmentType.getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	
	@Test
	public void testIoNewPregnantTreatmentType() 
	{
		PregnantTreatmentTypeIoOperation ioOperations = new PregnantTreatmentTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			PregnantTreatmentType pregnantTreatmentType = testPregnantTreatmentType.setup(true);
			result = ioOperations.newPregnantTreatmentType(pregnantTreatmentType);
			
			assertEquals(true, result);
			_checkPregnantTreatmentTypeIntoDb(pregnantTreatmentType.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}

	@Test
	public void testIoDeletePregnantTreatmentType() 
	{
		String code = "";
		PregnantTreatmentTypeIoOperation ioOperations = new PregnantTreatmentTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestPregnantTreatmentType(false);
			PregnantTreatmentType foundPregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
			result = ioOperations.deletePregnantTreatmentType(foundPregnantTreatmentType);
			
			assertEquals(true, result);
			PregnantTreatmentType deletedPregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
			assertEquals(null, deletedPregnantTreatmentType);
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
		PregnantTreatmentTypeIoOperation ioOperations = new PregnantTreatmentTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestPregnantTreatmentType(false);
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
		testPregnantTreatmentTypeContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testPregnantTreatmentTypeContext.getAllSaved());
		testPregnantTreatmentTypeContext.deleteNews(jpa);
        
        return;
    }
        
	private String _setupTestPregnantTreatmentType(
			boolean usingSet) throws OHException 
	{
		PregnantTreatmentType pregnantTreatmentType = new PregnantTreatmentType();
		pregnantTreatmentType.setDescription("Test Description");
		

    	jpa.beginTransaction();	
    	pregnantTreatmentType = testPregnantTreatmentType.setup(usingSet);
		jpa.persist(pregnantTreatmentType);
    	jpa.commitTransaction();
    	
		return pregnantTreatmentType.getCode();
	}
		
	private void  _checkPregnantTreatmentTypeIntoDb(
			String code) throws OHException 
	{
		PregnantTreatmentType foundPregnantTreatmentType;
		

		foundPregnantTreatmentType = (PregnantTreatmentType)jpa.find(PregnantTreatmentType.class, code); 
		testPregnantTreatmentType.check(foundPregnantTreatmentType);
		
		return;
	}	
}