package com.pubg.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pubg.dto.ErrorResponseDTO;
import com.pubg.exception.PUBGBusinessException;



/**
 * ExceptionControllerAdvice is the intercepter which gets triggered
 * when any Exception is raised from an Controller in the Project.
 * 
 * @author Prolifics.
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	/**
	 * The System Error Code.
	 */
	private static final String SYSTEM_ERROR_CODE = "SYS_000";
		
	/**
	 * messageSource - ResourceBundleMessageSource instance to transform
	 * error codes to meaningful messages.
	 */
	@Autowired
	protected ResourceBundleMessageSource messageSource;
	
	/**
	 * The specific Exception Handler method which intercepts any raised Exception.
	 * 
	 * @param ex
	 * 	<p>The Exception raised.</p>
	 * @return
	 * 	<p>Meaningful message wrapped with an Error Code.</p>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> exceptionHandler(Exception ex) {
		ErrorResponseDTO error = new ErrorResponseDTO();
		if(ex instanceof PUBGBusinessException ){
			PUBGBusinessException be = (PUBGBusinessException)ex;
			error.setErrorCode(be.getErrorCode());
			error.setMessage( messageSource.getMessage(be.getErrorCode(), null, be.getMessage(), Locale.ENGLISH));			
		}else{
			ex.printStackTrace();
			error.setErrorCode(SYSTEM_ERROR_CODE);			
			error.setMessage( messageSource.getMessage(SYSTEM_ERROR_CODE, null, Locale.ENGLISH));
			error.setCause(ex.getMessage());
		}		
		return new ResponseEntity<ErrorResponseDTO>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
