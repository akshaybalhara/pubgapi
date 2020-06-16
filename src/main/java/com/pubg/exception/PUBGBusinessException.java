package com.pubg.exception;
/**
 * IFFCOBusinessException is the class which wraps
 * a Business Exception in the System.
 * 
 * @author Prolifics.
 *
 */
public class PUBGBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * Specific Code of the Business Error.
	 */
	private String errorCode;
	
	/**
	 * IFFCOBusinessException constructor.
	 * @param errorCode
	 * 	<p>The errorCode to set.</p>
	 * @param errorMessage
	 * 	<p>The errorMessage to set.</p>
	 */
	public PUBGBusinessException(String errorCode, String errorMessage){
		super(errorMessage);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
