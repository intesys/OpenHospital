package org.isf.sms.manager;

import org.isf.generaldata.MessageBundle;
import org.isf.menu.manager.MainApplicationManager;
import org.isf.sms.model.Sms;
import org.isf.sms.service.SmsOperations;
import org.isf.utils.exception.OHException;
import org.isf.utils.exception.OHServiceException;
import org.isf.utils.exception.model.OHExceptionMessage;
import org.isf.utils.exception.model.OHSeverityLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class SmsManager {

	private final Logger logger = LoggerFactory.getLogger(SmsManager.class);
	
	private SmsOperations smsOperations = MainApplicationManager.getApplicationContext().getBean(SmsOperations.class);

	public List<Sms> getAll(Date from, Date to) throws OHServiceException {
		try {
			return smsOperations.getAll(from, to);
		} catch (OHException e) {
			/*Already cached exception with OH specific error message - 
			 * create ready to return OHServiceException and keep existing error message
			 */
			logger.error("", e);
			throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"), 
					e.getMessage(), OHSeverityLevel.ERROR));
		}catch(Exception e){
			//Any exception
			logger.error("", e);
			throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"), 
					MessageBundle.getMessage("angal.sql.problemsoccurredwiththesqlistruction"), OHSeverityLevel.ERROR));
		}
	}

	public void saveOrUpdate(Sms smsToSend) throws OHServiceException {
		try {
			smsOperations.saveOrUpdate(smsToSend);
		} catch (OHException e) {
			/*Already cached exception with OH specific error message - 
			 * create ready to return OHServiceException and keep existing error message
			 */
			logger.error("", e);
			throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"), 
					e.getMessage(), OHSeverityLevel.ERROR));
		}catch(Exception e){
			//Any exception
			logger.error("", e);
			throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"), 
					MessageBundle.getMessage("angal.sql.problemsoccurredwiththesqlistruction"), OHSeverityLevel.ERROR));
		}
	}

    public void delete(Sms sms) throws OHServiceException {
        try {
            smsOperations.delete(sms);
        } catch (OHException e) {
			/*Already cached exception with OH specific error message -
			 * create ready to return OHServiceException and keep existing error message
			 */
            logger.error("", e);
            throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"),
                    e.getMessage(), OHSeverityLevel.ERROR));
        }catch(Exception e){
            //Any exception
            logger.error("", e);
            throw new OHServiceException(e, new OHExceptionMessage(MessageBundle.getMessage("angal.hospital"),
                    MessageBundle.getMessage("angal.sql.thedatacouldnotbesaved"), OHSeverityLevel.ERROR));
        }
    }
	
}
