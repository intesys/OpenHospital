//---------------------------------------------------------------------------

package imysqlds;

//------------------------------------------------------------------------------------
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.SQLException;

//------------------------------------------------------------------------------------
public class IPreparedStatement {
    
    
    
    
    //--------------------------------------------------------------------------------------------------
    public IPreparedStatement(IDb idb, String query, boolean generatedKeys) throws ServiceException {
        
       this.generatedKeys = generatedKeys;
       
       try {
      
           if(generatedKeys)
           
               preparedStatement = idb.connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
           
           else
               
               preparedStatement = idb.connection.prepareStatement(query);
           
           
       }
       
       catch(SQLException e) {
           
           throw new ServiceException(e.getMessage());
       }
        
    }
    
    
    public void setStringParameter(String parameter, int position) throws ServiceException {
        
        try {
        
            preparedStatement.setString(position, parameter);
            
        }
        
        catch(SQLException e) {
           
            throw new ServiceException(e.getMessage());
            
        }
        
        
    }
    
    public void setIntegerParameter(int parameter, int position) throws ServiceException {
        
        try {
        
            preparedStatement.setInt(position, parameter);
            
        }
        
        catch(SQLException e) {
           
            throw new ServiceException(e.getMessage());
            
        }
        
        
    }
    
    public void setLongParameter(long parameter, int position) throws ServiceException {
        
        
        try {
            
            preparedStatement.setLong(position, parameter);
            
            
        }
        
        catch(SQLException e) {
            
            throw new ServiceException(e.getMessage());
        }
    }
    
    public void setDateParameter(ISqlDate parameter, int position) throws ServiceException {
       
        try {
        
            preparedStatement.setTimestamp(position, new Timestamp(parameter.date.getTime()));
            
        }
        
        catch(SQLException e) {
           
            throw new ServiceException(e.getMessage());
            
        }
        
        
    }
    
    public PreparedStatement getPreparedStatement() {
        
        return preparedStatement;
    }
    
   //-----------------------------------------------------------------------------------------------------
    
    PreparedStatement preparedStatement;
    boolean generatedKeys;
    
    //---------------------------------------------------------------------------------------------
    
}

