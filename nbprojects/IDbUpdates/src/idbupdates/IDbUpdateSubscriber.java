package idbupdates;

import java.rmi.*;


		
public interface IDbUpdateSubscriber extends Remote {
    
    
    public void newUpdate(final String tableName) throws RemoteException;
    
    
  
}
