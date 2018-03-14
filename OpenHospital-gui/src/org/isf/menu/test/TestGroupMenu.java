package org.isf.menu.test;


import org.isf.utils.exception.OHException;
import org.isf.menu.model.GroupMenu;

import static org.junit.Assert.assertEquals;

public class TestGroupMenu 
{	
    private Integer code = 999;
    private String userGroup = "TestDescription";
    private String menuItem = "TestDescription";
    private char active = 'Y';
    
			
	public GroupMenu setup(
			boolean usingSet) throws OHException 
	{
		GroupMenu groupMenu;
	
				
		if (usingSet == true)
		{
			groupMenu = new GroupMenu();
			_setParameters(groupMenu);
		}
		else
		{
			// Create GroupMenu with all parameters 
			groupMenu = new GroupMenu(code, userGroup, menuItem, active);
		}
				    	
		return groupMenu;
	}
	
	public void _setParameters(
			GroupMenu groupMenu) 
	{	
		groupMenu.setCode(code);
		groupMenu.setUserGroup(userGroup);
		groupMenu.setMenuItem(menuItem);
		groupMenu.setActive(active);
		
		return;
	}
	
	public void check(
			GroupMenu groupMenu) 
	{		
    	System.out.println("Check GroupMenu: " + groupMenu.getCode());
    	assertEquals(code, groupMenu.getCode());
    	assertEquals(userGroup, groupMenu.getUserGroup());
    	assertEquals(menuItem, groupMenu.getMenuItem());
    	assertEquals(active, groupMenu.getActive());
		
		return;
	}
}
