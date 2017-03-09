//---------------------------------------------------------------------------------------------------------

package iireports;

//----------------------------------------------------------------------------

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.Map;

//------------------------------------------------------------------------------------------------------------------
		
public interface IIReports extends Remote {
    
    
    //----------------------------------------------------------------------------------------------------------------
    public void viewSimpleReport(final String reportFileName, final Map<String,Object> parameters) throws RemoteException;
    
    public void viewComplexReport(final String reportFileName, final Map<String,Object> parameters) throws RemoteException;
		
    public void stopService() throws RemoteException;
    
    //------------------------------------------------------------------------------------------------------------------
}
