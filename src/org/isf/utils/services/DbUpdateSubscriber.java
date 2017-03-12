//-------------------------------------------------------------------------------------------------------------------------

package org.isf.utils.services;

//------------------------------------------------------------------------------------------------------------------------
import java.util.HashMap;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import javax.swing.JOptionPane;
import java.util.concurrent.ForkJoinPool;


import idbupdates.*;





//------------------------------------------------------------------------------------------------------------------------
public final class DbUpdateSubscriber extends UnicastRemoteObject implements IDbUpdateSubscriber{
    
    
    //-----------------------------------------------Interface------------------------------------------------------------------
    
    public static final void subscribe(TableUpdateSubscriber subscriber, String[] tableNames) {
        
        subscribersMap.put(subscriber, tableNames);
        
    }
   
    public static final void notifyAll(String tableName) {
        
        try {
        
            dbUpdateNotifier.notifyAll(tableName);
                
        }
        
        catch(RemoteException exception) {
        
        
            JOptionPane.showMessageDialog(null, "DbUpdateSubscriber Error: " + exception.getMessage());
        
        }
        
        
    }
    
    @Override
    public void newUpdate(final String tableName) throws RemoteException {
            
            
       
        TableUpdateSubscriber[] subscribers = subscribersMap.keySet().
                                              <TableUpdateSubscriber>toArray(
                                                      new TableUpdateSubscriber[subscribersMap.size()]
                                              );
        for(final TableUpdateSubscriber subscriber: subscribers) {
            String[] values = subscribersMap.get(subscriber);
            for(String value: values) {
                if(value.equals(tableName)) {
                    
                    final ForkJoinPool pool = new ForkJoinPool();
                    pool.execute(new Runnable() { 
                        
                                                 
                                                 public void run() {
                        
                                                           try {
                                                           
                                                               Thread.sleep(1000);
                                                               
                                                               
                                                               
                                                           }
                                                           
                                                           catch(InterruptedException exception) {
                                                               
                                                               
                                                           }
                                                           
                                                           subscriber.newUpdate(tableName);
                                                           pool.shutdown();
                       
                                                 }
                        
                                 });
                        
                }
                    
            }
            
        }
        
    }
        
    
    
    public static interface TableUpdateSubscriber {
        
        
        public void newUpdate(final String tableName);
        
        
    }
    
    
    public static final void unsubscribe(TableUpdateSubscriber subscriber) {
        
        subscribersMap.remove(subscriber);
        
    }
    
    
    
    public static final void end() {
        
       
        try {
       
            dbUpdateNotifier.unsubscribe(dbUpdateSubscriberRemoteAddress);
            dbUpdateNotifier = null;
            Naming.unbind(dbUpdateSubscriberLocalAddress);
           
            
       }
        
        
        
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "DbUpdateSubscriber Error: " + exception.getMessage());
        
        }
        
        subscribersMap.clear();
        subscribersMap = null;
        
    }
    
    
    //---------------------------------------------------------Private----------------------
    private static String dbUpdateSubscriberLocalAddress;
    private static String dbUpdateSubscriberRemoteAddress;
    private static IDbUpdateNotifer dbUpdateNotifier;
    private static HashMap<TableUpdateSubscriber, String[]> subscribersMap;
    
    static {
        
        
        
        try {
            dbUpdateSubscriberLocalAddress = "//localhost:2022/DbUpdateSubscriber";
            LocateRegistry.createRegistry(2022);
            Naming.rebind(dbUpdateSubscriberLocalAddress, new DbUpdateSubscriber());
            
            dbUpdateSubscriberRemoteAddress = "//" + NetworkService.getIPAddress() + ":2022/DbUpdateSubscriber";
            dbUpdateNotifier = (IDbUpdateNotifer)Naming.lookup(RemoteServices.getServiceURL("DbUpdateNotifier"));
            dbUpdateNotifier.subscribe(dbUpdateSubscriberRemoteAddress);
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "DbUpdateSubscriber Error: " + exception.getMessage());
        
        }
        
        subscribersMap = new HashMap<TableUpdateSubscriber, String[]>();
    
    }
    
    private DbUpdateSubscriber() throws RemoteException {}

    
    
    
    
    
    
    
    
    
    //---------------------------------------------------------------------------------------------------------------------------
    
    
    
    
}
