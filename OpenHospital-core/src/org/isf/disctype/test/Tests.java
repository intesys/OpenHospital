package org.isf.disctype.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.isf.disctype.service.DischargeTypeIoOperation;
import org.isf.disctype.model.DischargeType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tests  
{
	private static DbJpaUtil jpa;
	private static TestDischargeType testDischargeType;
	private static TestDischargeTypeContext testDischargeTypeContext;
		
	
	@BeforeClass
    public static void setUpClass()  
    {
    	jpa = new DbJpaUtil();
    	testDischargeType = new TestDischargeType();
    	testDischargeTypeContext = new TestDischargeTypeContext();
    	
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
	public void testDischargeTypeGets()
	{
		String code = "";

		
		try 
		{		
			code = _setupTestDischargeType(false);
			_checkDischargeTypeIntoDb(code);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
				
		return;
	}
	
	@Test
	public void testDischargeTypeSets()
	{
		String code = "";
			

		try 
		{		
			code = _setupTestDischargeType(true);
			_checkDischargeTypeIntoDb(code);
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
		String code = "";
		DischargeTypeIoOperation ioOperations = new DischargeTypeIoOperation();
		

		try 
		{		
			code = _setupTestDischargeType(false);
			DischargeType foundDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
			ArrayList<DischargeType> dischargeTypes = ioOperations.getDischargeType();
			
			assertEquals(foundDischargeType.getDescription(), dischargeTypes.get(dischargeTypes.size()-1).getDescription());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
    
    @Test
	public void testIoNewDischargeType()  
	{
		DischargeTypeIoOperation ioOperations = new DischargeTypeIoOperation();
		boolean result = false;
		
		
		try 
		{		
			DischargeType dischargeType = testDischargeType.setup(true);
			result = ioOperations.newDischargeType(dischargeType);
			
			assertEquals(true, result);
			_checkDischargeTypeIntoDb(dischargeType.getCode());
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
   
	@Test
	public void testIoDeleteDischargeType()
	{
		String code = "";
		DischargeTypeIoOperation ioOperations = new DischargeTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestDischargeType(false);
			DischargeType foundDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
			result = ioOperations.deleteDischargeType(foundDischargeType);
			
			assertEquals(true, result);
			DischargeType deletedDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
			assertEquals(null, deletedDischargeType);
		} 
		catch (Exception e) 
		{
			System.out.println("==> Test Exception: " + e);		
			assertEquals(true, false);
		}
		
		return;
	}
	 
	@Test
	public void testIoUpdateDischargeType()
	{
		String code = "";
		DischargeTypeIoOperation ioOperations = new DischargeTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestDischargeType(false);
			DischargeType foundDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
			foundDischargeType.setDescription("Update");
			result = ioOperations.updateDischargeType(foundDischargeType);
			DischargeType updateDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
			
			assertEquals(true, result);
			assertEquals("Update", updateDischargeType.getDescription());
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
		DischargeTypeIoOperation ioOperations = new DischargeTypeIoOperation();
		boolean result = false;
		

		try 
		{		
			code = _setupTestDischargeType(false);
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
		testDischargeTypeContext.saveAll(jpa);
        		
        return;
    }
	
    private void _restoreContext() throws OHException 
    {
		System.out.println(testDischargeTypeContext.getAllSaved());
		testDischargeTypeContext.deleteNews(jpa);
        
        return;
    }
        
	private String _setupTestDischargeType(
			boolean usingSet) throws OHException 
	{
		DischargeType dischargeType;
		

    	jpa.beginTransaction();	
    	dischargeType = testDischargeType.setup(usingSet);
		jpa.persist(dischargeType);
    	jpa.commitTransaction();
    	
		return dischargeType.getCode();
	}
		
	private void  _checkDischargeTypeIntoDb(
			String code) throws OHException 
	{
		DischargeType foundDischargeType;
		

		foundDischargeType = (DischargeType)jpa.find(DischargeType.class, code); 
		testDischargeType.check(foundDischargeType);
		
		return;
	}	
}