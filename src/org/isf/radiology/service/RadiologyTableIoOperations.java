////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.service;

import java.util.GregorianCalendar;

import imysqlds.*;





public class RadiologyTableIoOperations {
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final void createRadiologyTable(GregorianCalendar date, String patient, String scan, 
            String macAddress) throws ServiceException {
        
        IDb idb = new IDb();
        
        final String QUERY = "INSERT INTO RADIOLOGYTABLE (DATE, PATIENT, SCAN, MAC_ADDRESS) VALUES (?, ?, ?, ?)";
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        
        ISqlDate isqlDate = ISqlDate.toISqlDate(date);
        ipreparedStatement.setStringParameter(isqlDate.toString(), 1);
 
        ipreparedStatement.setStringParameter(patient, 2);
        ipreparedStatement.setStringParameter(scan, 3);
        ipreparedStatement.setStringParameter(macAddress, 4);
        
        
        idb.executeUpdate(ipreparedStatement);
       
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return ;
        
        
           
        
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final void deleteRadiologyTables(String macAddress) throws ServiceException {
        
        IDb idb = new IDb();
        
        final String QUERY = "DELETE FROM RADIOLOGYTABLE WHERE MAC_ADDRESS = ?";
                
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        
        ipreparedStatement.setStringParameter(macAddress, 1);
        
        idb.executeUpdate(ipreparedStatement);
       
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return ;
        
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
}
