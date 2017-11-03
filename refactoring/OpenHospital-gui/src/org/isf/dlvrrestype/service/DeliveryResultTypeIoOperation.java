package org.isf.dlvrrestype.service;

import java.util.ArrayList;
import java.util.List;

import org.isf.dlvrrestype.model.DeliveryResultType;
import org.isf.utils.db.DbJpaUtil;
import org.isf.utils.exception.OHException;
import org.springframework.stereotype.Component;

/**
 * Persistence class for DeliveryResultType module.
 */
@Component
public class DeliveryResultTypeIoOperation {

	/**
	 * Returns all stored {@link DeliveryResultType}s.
	 * @return the stored {@link DeliveryResultType}s.
	 * @throws OHException if an error occurs retrieving the stored delivery result types.
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<DeliveryResultType> getDeliveryResultType() throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		ArrayList<DeliveryResultType> deliveryresultTypes = null;
				
		try {
			jpa.beginTransaction();

			String query = "SELECT * FROM DELIVERYRESULTTYPE ORDER BY DRT_DESC";
			jpa.createQuery(query, DeliveryResultType.class, false);
			List<DeliveryResultType> deliveryResultList = (List<DeliveryResultType>)jpa.getList();
			deliveryresultTypes = new ArrayList<DeliveryResultType>(deliveryResultList);			

			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return deliveryresultTypes;
	}

	/**
	 * Updates the specified {@link DeliveryResultType}.
	 * @param deliveryresultType the delivery result type to update.
	 * @return <code>true</code> if the delivery result type has been updated, <code>false</code> otherwise.
	 * @throws OHException if an error occurs during the update.
	 */
	public boolean updateDeliveryResultType(
			DeliveryResultType deliveryResultType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;

		try{
			jpa.beginTransaction();	
			jpa.merge(deliveryResultType);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;	
	}

	/**
	 * Stores the specified {@link DeliveryResultType}.
	 * @param deliveryresultType the delivery result type to store.
	 * @return <code>true</code> if the delivery result type has been stored. 
	 * @throws OHException if an error occurs during the store operation.
	 */
	public boolean newDeliveryResultType(
			DeliveryResultType deliveryResultType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try{
			jpa.beginTransaction();	
			jpa.persist(deliveryResultType);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;
	}

	/**
	 * Deletes the specified {@link DeliveryResultType}.
	 * @param deliveryresultType the delivery result type to delete.
	 * @return <code>true</code> if the delivery result type has been deleted, <code>false</code> otherwise.
	 * @throws OHException if an error occurs during the delete operation.
	 */
	public boolean deleteDeliveryResultType(
			DeliveryResultType deliveryResultType) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		boolean result = true;
		
		try{
			jpa.beginTransaction();	
			DeliveryResultType objToRemove = (DeliveryResultType) jpa.find(DeliveryResultType.class, deliveryResultType.getCode());
			jpa.remove(objToRemove);
			jpa.commitTransaction();
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
		return result;	
	}

	/**
	 * Checks if the specified code is already used by others {@link DeliveryResultType}s.
	 * @param code the code to check.
	 * @return <code>true</code> if the code is used, <code>false</code> otherwise.
	 * @throws OHException if an error occurs during the check.
	 */
	public boolean isCodePresent(
			String code) throws OHException 
	{
		DbJpaUtil jpa = new DbJpaUtil(); 
		DeliveryResultType deliveryResultType;
		boolean result = false;

		try{
			jpa.beginTransaction();	
			deliveryResultType = (DeliveryResultType)jpa.find(DeliveryResultType.class, code);
			jpa.commitTransaction();
			if (deliveryResultType != null)
			{
				result = true;
			}
		}  catch (OHException e) {
			//DbJpaUtil managed exception
			jpa.rollbackTransaction();
			throw e;
		} 
    	
		return result;	
	}
}
