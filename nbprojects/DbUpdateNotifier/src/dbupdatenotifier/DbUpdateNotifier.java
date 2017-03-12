//-----------------------------------------------------------------------------------------------------------

package dbupdatenotifier;


//---------------------------------------------------------------------------------------------------------------
import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

import javax.swing.JOptionPane;


import idbupdates.*;

//---------------------------------------------------------------------------------------------------------------

public class DbUpdateNotifier extends UnicastRemoteObject implements idbupdates.IDbUpdateNotifer {

    
    
    //-------------------------------------------------------------------------------------------
    
    public static void main(String[] args) {
        
        try {
            subscribersAddresses = new ArrayList<String>();
            LocateRegistry.createRegistry(2021);
            Naming.rebind("//localhost:2021/DbUpdateNotifier", new DbUpdateNotifier());
        }
        
        catch (MalformedURLException | RemoteException exception) {
            JOptionPane.showMessageDialog(null, "DbUpdateNotifier Error: " + exception.getMessage());
        }
        
        
        
    }
    
    
    
    @Override
    public synchronized void subscribe(final String subscriberAddress) {
        
        
        subscribersAddresses.add(subscriberAddress);
        
    }
    
    @Override
    public synchronized void notifyAll(final String tableName) throws RemoteException {
        
        
        
       
        
        
        for(String subscriberAddress: subscribersAddresses) {
        
        
             try {
        
            
                       ((IDbUpdateSubscriber)Naming.lookup(subscriberAddress)).newUpdate(tableName);
                       
            
            
              }
        
        
              catch (MalformedURLException | RemoteException | NotBoundException exception) {
            
                  JOptionPane.showMessageDialog(null, "DbUpdateNotifier Error: " + exception.getMessage());
        
              }
        
        
        
        }
        
       
        
    }
    
    
    @Override
    public synchronized void unsubscribe(String subscriberAddress) {
        
        subscribersAddresses.remove(subscriberAddress);
        
    }
    
    
    //-------------------------------------------------------------------------------------------------------------
    private static ArrayList<String> subscribersAddresses;
    
    private DbUpdateNotifier() throws RemoteException {
    
    
        
    
    
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    
}
