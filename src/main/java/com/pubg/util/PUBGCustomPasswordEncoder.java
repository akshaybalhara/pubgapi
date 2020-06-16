package com.pubg.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PUBGCustomPasswordEncoder is the Custom Password Encoder
 * which implements the Spring's PasswordEncoder contract
 * and provides an implementation of the callback methods.
 * 
 * @author Prolifics
 *
 */
public class PUBGCustomPasswordEncoder implements PasswordEncoder {

	/**
	 * encode method provides implementation of necessary
	 * Password Encoding.
	 */
	@Override
	public String encode(CharSequence plainText) {		
		if(plainText!=null){
			return PUBGGeneralUtils.getEncryptedText(plainText.toString());
		}
		return null;
	}

	/**
	 * matches method compares the password authenticity
	 * and returns the status.
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encoded = encode(rawPassword);
		return encoded.equals(encodedPassword);
	}
}
