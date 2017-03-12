/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package org.isf.radiology.service;


import java.util.ArrayList;

import java.sql.ResultSet;

import org.isf.dicom.manager.SqlDicomManager;


import imysqlds.ServiceException;
import imysqlds.IPreparedStatement;
import imysqlds.IDb;



import org.isf.radiology.model.RadiologyExam;

import org.isf.radiology.manager.RadiologyExamManager;

import org.isf.radiology.model.RadiologyExamDicom;








public final class RadiologyExamDicomIoOperations {
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    
    
    public static final void createRadiologyExamDicom(int radiologyExamID, 
            long dicomFileID) throws ServiceException {
        
        
        IDb idb = new IDb();
        
        final String QUERY = "INSERT INTO RADIOLOGYEXAMDICOM (RADEXDCM_RADEX_ID, RADEXDCM_DM_FILE_ID) VALUES (?, ?)";
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        
        ipreparedStatement.setIntegerParameter(radiologyExamID, 1);
        ipreparedStatement.setLongParameter(dicomFileID, 2);
        
        
        
        idb.executeUpdate(ipreparedStatement);
        
        
       
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
       
        
        
           
        
        
    }
    
    //////////////////////////////////////
    
    public static final ArrayList<RadiologyExamDicom> getRadiologyExamDicoms(int radiologyExamID) throws ServiceException {
        
        ArrayList<RadiologyExamDicom> dicoms = null;
        
        IDb idb = new IDb();
        
        final String QUERY = "SELECT * FROM RADIOLOGYEXAMDICOM WHERE RADEXDCM_RADEX_ID = ?";
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        
        ipreparedStatement.setIntegerParameter(radiologyExamID, 1);
        
        
        dicoms = toRadiologyExamDicoms(idb.read(ipreparedStatement));
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
       
        
        return dicoms;
        
        
           
        
        
    }
    
 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final void deleteRadiologyExamDicoms(int radiologyExamID) throws ServiceException {
        
        
        IDb idb = new IDb();
        
        final String QUERY = "DELETE FROM RADIOLOGYEXAMDICOM WHERE RADEXDCM_RADEX_ID = ?";
                
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        ipreparedStatement.setIntegerParameter(radiologyExamID, 1);
        
        idb.executeUpdate(ipreparedStatement);
       
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        
        return ;
        
        
           
        
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
   
  private static ArrayList<RadiologyExamDicom> toRadiologyExamDicoms(ResultSet resultSet) throws ServiceException{
        
           ArrayList<RadiologyExamDicom> pDicoms = null;
           
           try {
           
               pDicoms = new ArrayList<RadiologyExamDicom>(resultSet.getFetchSize());
               RadiologyExamManager radiologyManager = new RadiologyExamManager();
               SqlDicomManager dbDicomManager = new SqlDicomManager();
               
               while (resultSet.next()) {
                   
                        
                        RadiologyExam radiologyExam = radiologyManager.getRadiologyExam(resultSet.getInt("RADEXDCM_RADEX_ID"));
			
                        pDicoms.add(
                                new RadiologyExamDicom(
                                        resultSet.getInt("RADEXDCM_ID"),
						
                                        
                                        radiologyExam, 
                                                
                                        
                                        dbDicomManager.loadFile(
                                                new Long(
                                                        resultSet.getLong("RADEXDCM_DM_FILE_ID")
                                                )
                                                
                                        )
                                                
                                                
                                   )
                        );
                                
	        }
               
           }
           
           catch(Exception e) {
               
               throw new ServiceException(e.getMessage());
               
           }
            
        
        return pDicoms;
        
                
        
    }
    
 
    
   
    
    
    
    
}
    
    
 

    
    
    
    
    


