package com.pubg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

/**
 * BaseService is the base class for all the Services that will be
 * will be used in application. It will have common functionalities which may be used in 
 * multiple Service classes.
 *  
 * @author Prolifics
 *
 */
@Service
public abstract class BaseService {

	/**
	 * messageSource - ResourceBundleMessageSource instance to transform
	 * error codes to meaningful messages.
	 */
	@Autowired
	protected ResourceBundleMessageSource messageSource;
}
