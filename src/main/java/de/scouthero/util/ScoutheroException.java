package de.scouthero.util;

import javax.persistence.NoResultException;

/**
 * 
 * @author Selle
 *
 */
public class ScoutheroException extends Exception {
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 6695269530517890046L;
	private ERROR errorCode; 
	
	public enum ERROR {
		ENTITY_NOT_FOUND,
	}
	

	public ScoutheroException(String message) {
		super(message);
	}
	
	public ScoutheroException(Exception e) {
		super(e);
		if (e instanceof NoResultException) {
			errorCode = ERROR.ENTITY_NOT_FOUND;
		}
	}
	
	public ERROR getErrorCode() {
		return errorCode;
	}
}
