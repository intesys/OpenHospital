package idbupdates;

import java.rmi.*;


		
public interface IDbUpdateNotifer extends Remote {
    
    
    public void subscribe(final String subscriberAddress) throws RemoteException;
    public void notifyAll(final String tableName) throws RemoteException;
    public void unsubscribe(final String subscriberAddress) throws RemoteException;
    
  
}

