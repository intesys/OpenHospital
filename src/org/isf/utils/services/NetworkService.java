

package org.isf.utils.services;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public final class NetworkService {
    
    
    public static final String getIPAddress() {
        
        
        String ipAddress = null;
        
        try {
            
            ipAddress = InetAddress.getLocalHost().getHostAddress();
            
            
        }
        
        catch(UnknownHostException exception) {
            
            
        }
        
        return ipAddress;
    }
    public static final String getMacAddress() {
        
        
        String macAddress = null;

	try {

		

		byte[] macAddressBytes = NetworkInterface.getByInetAddress(
                                                                             InetAddress.getLocalHost()

                                                          ).getHardwareAddress();

		

		StringBuilder sb = new StringBuilder();
                int macAddressBytesLength = macAddressBytes.length;
		for (int i = 0; i < macAddressBytesLength; i++) {
			sb.append(
                                 String.format(
                                                
                                              "%02X%s", 
                                               macAddressBytes[i], 
                                               (i < macAddressBytesLength - 1) ? "-" : ""

                                  )

                       );
		}
		
                 macAddress = sb.toString();

	} catch (UnknownHostException e) {

		e.printStackTrace();

	} catch (SocketException e){

		e.printStackTrace();

	}

        return macAddress;

   }

        
        
        
        
    
    
}
