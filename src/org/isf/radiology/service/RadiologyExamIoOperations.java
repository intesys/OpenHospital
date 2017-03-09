/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.isf.radiology.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.sql.ResultSet;

import org.isf.patient.service.PatientIoOperations;

import imysqlds.*;

import org.isf.radiology.model.RadiologyExam;
import org.isf.utils.services.DbUpdateSubscriber;

public final class RadiologyExamIoOperations {
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final int createRadiologyExam(String radiologyID, GregorianCalendar date, int patientID, String clinicalHistory, 
            String comparison, String technique, 
            String finding, String impression, String radiologist) throws ServiceException {
        
        
        IDb idb = new IDb();
        
        final String QUERY = "INSERT INTO RADIOLOGYEXAM (RADEX_RAD_ID_A, RADEX_DATE, RADEX_PAT_ID, CLN_HST, CMP, TCN, FND, IMP, RADIOLOGIST) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, true);
        
        ipreparedStatement.setStringParameter(radiologyID, 1);
        
        ISqlDate isqlDate = ISqlDate.toISqlDate(date);
        ipreparedStatement.setDateParameter(isqlDate, 2);
        
        ipreparedStatement.setIntegerParameter(patientID, 3);
        
        ipreparedStatement.setStringParameter(clinicalHistory, 4);
        ipreparedStatement.setStringParameter(comparison, 5);
        ipreparedStatement.setStringParameter(technique, 6);
        ipreparedStatement.setStringParameter(finding, 7);
        ipreparedStatement.setStringParameter(impression, 8);
        ipreparedStatement.setStringParameter(radiologist, 9);
        
        int radiologyExamID = -1;
        ResultSet resultSet = idb.executeUpdate(ipreparedStatement);
        
        try {
        
            if(resultSet.next()) {
            
            
            
                radiologyExamID = resultSet.getInt(1);
                
                
            }
            
            
            
            
        }
        
        catch(Exception e) {}
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        
        
        
        
        DbUpdateSubscriber.notifyAll("radiologyexam");
        
        
        
        return radiologyExamID;
        
        
           
        
        
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final RadiologyExam getRadiologyExam(int radiologyExamID) throws ServiceException {
        
        IDb idb = new IDb();
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, "SELECT * FROM RADIOLOGYEXAM WHERE RADEX_ID = ?", false);
        ipreparedStatement.setIntegerParameter(radiologyExamID, 1);
        
        ArrayList<RadiologyExam> radiologyExams = RadiologyExamIoOperations.toRadiologyExams(idb.read(ipreparedStatement));
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return ( (! radiologyExams.isEmpty()) ? radiologyExams.get(0): null );
        
        
    }
    
    
    public static final ArrayList<RadiologyExam> getRadiologyExams(int patientID, String radiology, GregorianCalendar dateFrom, GregorianCalendar dateTo) throws ServiceException {
        
        
        
        IDb idb = new IDb();
        String query = "SELECT * FROM RADIOLOGYEXAM JOIN RADIOLOGY ON RADEX_RAD_ID_A = RAD_ID_A WHERE RADEX_DATE >= ? AND RADEX_DATE <= ?";
        IPreparedStatement ipreparedStatement;
        
        //////////////////////////////////
        if(patientID != -1) 
            query = query + " AND RADEX_PAT_ID = ?";
        
        if(radiology != null)
            query = query + " AND RAD_DESC = ?";
        
        query = query + " ORDER BY RADEX_DATE";
        
        /////////////////////////////////
        
        ipreparedStatement = new IPreparedStatement(idb, query,false);
        
        if(patientID != -1) {
            
            ipreparedStatement.setIntegerParameter(patientID, 3);
            
            if(radiology != null)
                 ipreparedStatement.setStringParameter(radiology, 4);
            
        }
           
        else if (radiology != null)
            ipreparedStatement.setStringParameter(radiology, 3);
            
           
       
                
        ISqlDate isqlDateFrom = ISqlDate.toISqlDate(dateFrom);
        ipreparedStatement.setDateParameter(isqlDateFrom, 1);
        ISqlDate isqlDateTo = ISqlDate.toISqlDate(dateTo);
       
        ipreparedStatement.setDateParameter(isqlDateTo, 2);
        
        ResultSet resultSet = idb.read(ipreparedStatement);
        ArrayList<RadiologyExam> radiologyExams = RadiologyExamIoOperations.toRadiologyExams(resultSet);
        
        
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        
        return radiologyExams;
        
    }
    
    public static final ArrayList<RadiologyExam> getRadiologyExams(int patientID) throws ServiceException{
        
        IDb idb = new IDb();
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, "SELECT * FROM RADIOLOGYEXAM WHERE RADEX_PAT_ID = ? ORDER BY RADEX_ID DESC", false);
        ipreparedStatement.setIntegerParameter(patientID, 1);
        
        ArrayList<RadiologyExam> radiologyExams = RadiologyExamIoOperations.toRadiologyExams(idb.read(ipreparedStatement));
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        return ( (! radiologyExams.isEmpty()) ? radiologyExams: null );
        
        
    }
    
    public static final ArrayList<RadiologyExam> getRadiologyExams() throws ServiceException {
        
        
        
        
        
        
        
        IDb idb = new IDb();
        
        final String QUERY = "SELECT * FROM RADIOLOGYEXAM ORDER BY RADEX_DATE";
        ResultSet resultSet = idb.read(QUERY);
        ArrayList<RadiologyExam> radiologyExams = RadiologyExamIoOperations.toRadiologyExams(resultSet);
        
        try {
        
            idb.releaseConnection(resultSet.getStatement());
            
            
        }
        
        catch(Exception e){}
        
        return radiologyExams;
        
    }
    
    
    
    
    
    
    
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final void updateRadiologyExam(int radiologyExamID, String radiologyID, GregorianCalendar date, int patientID, String clinicalHistory, 
            String comparison, String technique,
                    String finding, String impression, String radiologist) throws ServiceException {
        
        
        IDb idb = new IDb();
        
        final String QUERY = "UPDATE RADIOLOGYEXAM SET RADEX_RAD_ID_A = ?, RADEX_DATE = ?, RADEX_PAT_ID = ?, CLN_HST = ?, CMP = ?, TCN = ?, FND = ?, IMP = ?, RADIOLOGIST = ? WHERE RADEX_ID = ?";
                
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY,false);
        
        ipreparedStatement.setStringParameter(radiologyID, 1);
        
        ISqlDate isqlDate = ISqlDate.toISqlDate(date);
        ipreparedStatement.setDateParameter(isqlDate, 2);
        
        ipreparedStatement.setIntegerParameter(patientID, 3);
        
        ipreparedStatement.setStringParameter(clinicalHistory, 4);
        ipreparedStatement.setStringParameter(comparison, 5);
        ipreparedStatement.setStringParameter(technique, 6);
        
        ipreparedStatement.setStringParameter(finding, 7);
        ipreparedStatement.setStringParameter(impression, 8);
        
        ipreparedStatement.setStringParameter(radiologist, 9);
        
        ipreparedStatement.setIntegerParameter(radiologyExamID, 10);
        
        idb.executeUpdate(ipreparedStatement);
       
        
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        
        
        
        
        DbUpdateSubscriber.notifyAll("radiologyexam");
        
        
        
        return ;
        
        
           
        
        
    }
    
    /////////////////////////////////////////////////////////////////////
    
     public static final void deleteRadiologyExam(int radiologyExamID) throws ServiceException {
        
         IDb idb = new IDb();
        
        final String QUERY = "DELETE FROM RADIOLOGYEXAM WHERE RADEX_ID = ?";
                
           
        
        IPreparedStatement ipreparedStatement = new IPreparedStatement(idb, QUERY, false);
        ipreparedStatement.setIntegerParameter(radiologyExamID, 1);
        
        idb.executeUpdate(ipreparedStatement);
       
        idb.releaseConnection(ipreparedStatement.getPreparedStatement());
        
        
        
        
        
        DbUpdateSubscriber.notifyAll("radiologyexam");
        
        
        
        return ;
        
        
        
        
    }
    
     /////////////////////////////////////////////////////////////////////////////////////////////////
     
     
    private static ArrayList<RadiologyExam> toRadiologyExams(ResultSet resultSet) throws ServiceException{
        
           ArrayList<RadiologyExam> pRadiologyExams = null;
           
           try {
           
               pRadiologyExams = new ArrayList<RadiologyExam>(resultSet.getFetchSize());
               PatientIoOperations patientIoOperations = new PatientIoOperations();
               while (resultSet.next()) {
                   
                        GregorianCalendar date = new GregorianCalendar();
                        date.setTime(resultSet.getTimestamp("RADEX_DATE"));
                        
			pRadiologyExams.add(new RadiologyExam(resultSet.getInt("RADEX_ID"),
						RadiologyIoOperations.getRadiology(resultSet.getString("RADEX_RAD_ID_A")), 
                                                date,
                                                patientIoOperations.getPatient(resultSet.getInt("RADEX_PAT_ID")),
                                                resultSet.getString("CLN_HST"),
                                                resultSet.getString("CMP"),
                                                resultSet.getString("TCN"),
                                                //resultSet.getString("DICOM"),
                                                resultSet.getString("FND"),
                                                resultSet.getString("IMP"),
                                                resultSet.getString("RADIOLOGIST")
                                ));
                                
	        }
               
           }
           
           catch(Exception e) {
               
               throw new ServiceException(e.getMessage());
               
           }
            
        
        return pRadiologyExams;
        
                
        
    }
    
 
    //////////////////////////////////////////////////////////////////////////////////////////////
    
   
    
    
    
    
}
    
    
 

    
    
    
    
    

