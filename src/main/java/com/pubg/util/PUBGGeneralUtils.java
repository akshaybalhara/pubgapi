package com.pubg.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * OmifoGeneralUtils holds general util methods to be 
 * used by the Application.
 * 
 * @author Prolifics
 *
 */
public class PUBGGeneralUtils {
	
	/**
	 * getEncryptedText encrypts a specified plain text
	 * using SHA-256 Algorithm.
	 * 
	 * @param plainText
	 * 	<p>The plain text to be encrypted.</p>
	 * @return
	 * 	<p>The desired Encrypted text.
	 */
	public static String getEncryptedText(String plainText){
		String encryptedText = null;		
		 try {
			 	// Getting encoder  
		        Base64.Encoder encoder = Base64.getEncoder();  
	            // Create MessageDigest instance for MD5
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            //Add password bytes to digest
	            md.update(plainText.getBytes());
	            //Get the hash's bytes
	            byte[] bytes = md.digest();
	            
	            //Get complete hashed password in hex format
	            encryptedText = encoder.encodeToString(bytes); 
	        }
	        catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }		
		
		return encryptedText;
	}
	
	public static void main(String[] args) {
		System.out.println("J@ck$p@rr0w "+getEncryptedText("J@ck$p@rr0w"));
		System.out.println("password "+getEncryptedText("password"));
	}

}
