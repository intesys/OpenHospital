

package imysqlds;

//------------------------------------------------------------------------------
import java.util.GregorianCalendar;
import java.sql.Date;

//----------------------------------------------------------------------------------
public class ISqlDate {
    
    
    //----------------------------------------------------------------------------
    public static final ISqlDate toISqlDate(final GregorianCalendar parameter) {
        
         return new ISqlDate(new Date(parameter.getTime().getTime()));
       
   }
   
   @Override
   public String toString() {
       
       return date.toString();
       
   }
   
   //---------------------------------------------------------------------------------------------------------------
   
   Date date;
   
   //----------------------------------------------------------------------------------------------------------------
   
   private ISqlDate(Date date) {
       
       this.date = date;
   }
   
   //-----------------------------------------------------------------------------------------------------------------
    
}

