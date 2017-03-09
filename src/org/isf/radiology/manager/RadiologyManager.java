
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package org.isf.radiology.manager;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import imysqlds.ServiceException;

import org.isf.generaldata.MessageBundle;

import org.isf.radiology.model.Radiology;
import org.isf.radiology.service.RadiologyIoOperations;







public class RadiologyManager {
    
    
    
    
     
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Radiology> getRadiologies() {
		try {
			return RadiologyIoOperations.getRadiologies();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(null, MessageBundle.getMessage("angal.radiology.databaseerror"));
			return null;
		}
	}
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
     
     
    
}
