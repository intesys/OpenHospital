
package imysqlds;

//------------------------------------------------------------------------------------------------------
public final class ServiceException extends Exception {
   
    
    //-----------------------------------------------------------------------------------------
    
    public ServiceException(String description) {
        
        this.description = description;
    }
    
    public String getDescription() {
        
        return description;
    }
    
    //-------------------------------------------------------------------------
    private final String description;
    
    
    //-----------------------------------------------------------------------
    
}

