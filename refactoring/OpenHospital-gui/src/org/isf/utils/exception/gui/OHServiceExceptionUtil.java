package org.isf.utils.exception.gui;

import org.isf.utils.exception.OHServiceException;
import org.isf.utils.exception.model.OHExceptionMessage;

import javax.swing.JOptionPane;
import java.awt.Component;

public class OHServiceExceptionUtil {

	private OHServiceExceptionUtil() {}
	
	/**
	 * Iterate exception messages and show them in a JOptionPane with parentComponent = null
	 * @param e
	 */
	public static void showMessages(OHServiceException e) {
		showMessages(e, null);
	}
	
	/**
	 * Iterate exception messages and show them in a JOptionPane with parentComponent specified
	 * @param e
	 * @param parentComponent
	 */
	public static void showMessages(OHServiceException e, Component parentComponent) {
		if (null != e.getMessages()) {
			for(OHExceptionMessage msg : e.getMessages()){
				String message = msg.getMessage();				
				String title = msg.getTitle();
				if (null == title) {
					title = "";
				}
				
				int messageType = msg.getLevel().getSwingSeverity();
				JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
			}
		}
	}
}
