//------------------------------------------------------------------------------------------

package imysqlds;

//---------------------------------------------------------------------------------------
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.InputStream;
import java.io.FileInputStream;

import java.util.Properties;

import javax.swing.JOptionPane;

//-----------------------------------------------------------------------------------------------------------------------

public class IDb {
    
    
    //-----------------------------------------------------------------------------------------------------
      public IDb() throws ServiceException {
         
         
         try {
         
             connection = IDb.createConnection();
             
             
         }
         
         catch(Exception e) {
             
             JOptionPane.showMessageDialog(null, "Exception Connecting to Db: " + e.getMessage());
             throw new ServiceException(e.getMessage());
         }
         
     }
    
      
     public Connection getConnection() {
         
         return connection;
    }
    
      public void releaseConnection(Statement statement) throws ServiceException {
        
        
        try {
                
                
                     if(statement != null) {
                    
                         ResultSet resultSet = statement.getResultSet();
                     
                         if(resultSet !=null) {
                         
                             resultSet.close();
                         
                             resultSet = null;
                     
                         }
                     
                     
                         statement.close();
                         statement = null;
                         
                     }
                     
                     
                
                
		    connection.close();
		    connection = null;
                
                
        }
        
        catch(SQLException e) {
            
            throw new ServiceException(e.getMessage());
        }
        
        
	
    
    }
    
    
   
      public ResultSet executeUpdate(IPreparedStatement ipreparedStatement) throws ServiceException {
        
        ResultSet resultSet = null;
        
        try {
            
           
            ipreparedStatement.preparedStatement.executeUpdate();
            if(ipreparedStatement.generatedKeys) {
                resultSet = ipreparedStatement.preparedStatement.getGeneratedKeys();
            }
            
        }
        
        catch(SQLException e) {
            
            throw new ServiceException(e.getMessage());
            
        }
        
        return resultSet;
    }
    
    
   
    
      public final ResultSet read(String query) throws ServiceException {
        
        try {
        
            return connection.createStatement().executeQuery(query);        
        }
        
        catch(SQLException e) {
            
            throw new ServiceException(e.getMessage());
        }
        
           
    }
    
    
    
    public ResultSet read(IPreparedStatement ipreparedStatement) throws ServiceException {
        
        try {
            
           
            return ipreparedStatement.preparedStatement.executeQuery();
            
        }
        
        catch(SQLException e) {
            
            throw new ServiceException(e.getMessage());
            
        }
    }
    
    
    
   
    
     
    //---------------------------------------------------------------------------------------------------------
     
    
    
    
    
    Connection connection;
    
    //----------------------------------------------------------------------------------------------------------
    
    private final static int MYSQL_DEFAULT_PORT = 3306;
    
    private static Connection createConnection() throws Exception {
        
                Properties props = new Properties();
		InputStream is = IDb.class.getClassLoader().getResourceAsStream("database.properties");
		if (is == null) {
			FileInputStream in = new FileInputStream("rsc/database.properties");
			props.load(in);
			in.close();
		} else {
			props.load(is);
			is.close();
		}
		
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null)
			System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String server = props.getProperty("jdbc.server");
		String db = props.getProperty("jdbc.db");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		String port = props.getProperty("jdbc.port");
		if (port == null) {
			port = String.valueOf(MYSQL_DEFAULT_PORT);
		}
		
		StringBuilder sbURL = new StringBuilder();
		sbURL.append(url);
		sbURL.append("//");
		sbURL.append(server);
		sbURL.append(":");
		sbURL.append(port);
		sbURL.append("/");
		sbURL.append(db);
		sbURL.append("?useUnicode=true&characterEncoding=UTF-8");
		sbURL.append("&user=");
		sbURL.append(username);
		sbURL.append("&password=");
		sbURL.append(password);
		

		return DriverManager.getConnection(sbURL.toString());
            
            
        
        
    }
    
     
    //-------------------------------------------------------------------------------------------------
    
     
     
   
    
    
}

