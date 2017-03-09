////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.service;


import java.util.ArrayList;

import java.sql.ResultSet;

import imysqlds.ServiceException;
import imysqlds.IPreparedStatement;
import imysqlds.IDb;

import org.isf.radiology.model.Radiology;


public final class RadiologyIoOperations {
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final Radiology getRadiology(String id) throws ServiceException {
        
        
        
        
        
        
        
        IDb idb = new IDb();
        
        final String QUERY = "SELECT * FROM RADIOLOGY WHERE RAD_ID_A = ?";
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        ipreparedStatement.setStringParameter(id, 1);
        ResultSet resultSet = idb.read(ipreparedStatement);
        ArrayList<Radiology> radiologies = RadiologyIoOperations.toRadiologies(resultSet);
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return radiologies.get(0);
        
    }
    
   
    public static final ArrayList<Radiology> getRadiologies() throws ServiceException {
        
        
        
        
        
        
        
        IDb idb = new IDb();
        
        final String QUERY = "SELECT * FROM RADIOLOGY";
        ResultSet resultSet = idb.read(QUERY);
        ArrayList<Radiology> radiologies = RadiologyIoOperations.toRadiologies(resultSet);
        
        try {
        
            idb.releaseConnection(resultSet.getStatement());
            
        }
        
        catch(Exception e){}
        
        return radiologies;
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    private static ArrayList<Radiology> toRadiologies(ResultSet resultSet) throws ServiceException{
        
           ArrayList<Radiology> pRadiologies = null;
           
           try {
           
               pRadiologies = new ArrayList<Radiology>(resultSet.getFetchSize());
           
               while (resultSet.next()) {
			pRadiologies.add(new Radiology(resultSet.getString("RAD_ID_A"),
						resultSet.getString("RAD_DESC"), 
                                                RadiologyTypeIoOperations.getRadiologyType(resultSet.getString("RAD_RADC_ID_A")))
                                );
	        }
               
           }
           
           catch(Exception e) {
               
               throw new ServiceException(e.getMessage());
               
           }
            
        
        return pRadiologies;
        
                
        
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    
    
}
    
    
 

    
    
    
    
    
