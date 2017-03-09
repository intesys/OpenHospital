package org.isf.radiology.service;


import java.util.ArrayList;

import java.sql.ResultSet;

import imysqlds.ServiceException;
import imysqlds.IPreparedStatement;
import imysqlds.IDb;

import org.isf.radiology.model.RadiologyType;


public final class RadiologyTypeIoOperations {
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public static final RadiologyType getRadiologyType(String id) throws ServiceException {
        
        
        
        
        
        
        
        IDb idb = new IDb();
        
        final String QUERY = "SELECT * FROM RADIOLOGYTYPE WHERE RADC_ID_A = ?";
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        ipreparedStatement.setStringParameter(id, 1);
        ResultSet resultSet = idb.read(ipreparedStatement);
        ArrayList<RadiologyType> radiologyTypes = RadiologyTypeIoOperations.toRadiologyTypes(resultSet);
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return radiologyTypes.get(0);
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    private static ArrayList<RadiologyType> toRadiologyTypes(ResultSet resultSet) throws ServiceException{
        
           ArrayList<RadiologyType> pRadiologyTypes = null;
           
           try {
           
               pRadiologyTypes = new ArrayList<RadiologyType>(resultSet.getFetchSize());
           
               while (resultSet.next()) {
			pRadiologyTypes.add(new RadiologyType(resultSet.getString("RADC_ID_A"),
						resultSet.getString("RADC_DESC"))
                                );
	        }
               
           }
           
           catch(Exception e) {
               
               throw new ServiceException(e.getMessage());
               
           }
            
        
        return pRadiologyTypes;
        
                
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
}
    
    
 

    
    
    
    
    

