package com.dewitt.configuration;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationHelper {
	
	private static Key aesKey;
	private static AuthenticationHelper singleton = null;

	private AuthenticationHelper(){
		
		String key = "12345ThisIsKey";
		aesKey = new SecretKeySpec(key.getBytes(), "AES");
	}
	
	public String encrypt(String toEncrypt){
		
		try{
			Cipher cipher = Cipher.getInstance("AES");
			
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			
			byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
			
			return new String(encrypted);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String decrypt(String toDecrypt){
		try{
			Cipher cipher = Cipher.getInstance("AES");
			
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			
			byte[] decrypted = cipher.doFinal(toDecrypt.getBytes());
			
			return new String(decrypted);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static AuthenticationHelper getAuthenticationHelper(){
		if(singleton == null){
			singleton = new AuthenticationHelper();
		}
		
		return singleton;
	}
}
