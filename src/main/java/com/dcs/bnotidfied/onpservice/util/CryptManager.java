package com.dcs.bnotidfied.onpservice.util;

import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
import sun.misc.CharacterEncoder;

public class CryptManager
{

    public CryptManager()
    {
    }

    public static void main(String args[])
    {
    	
    	try {
			System.out.println(new CryptManager().encrypt("SU@123"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	
    }
    
    
    public synchronized static String encrypt(String plaintext) throws Exception
    {
    	 MessageDigest md = null;
    	 md = MessageDigest.getInstance("SHA"); //step 2
    	 md.update(plaintext.getBytes("UTF-8")); //step 3
    	 byte raw[] = md.digest(); //step 4
    	 @SuppressWarnings("restriction")
		String hash = (new BASE64Encoder()).encode(raw); //step 5
    	 return hash; //step 6
    	    	 
    }
    
    
}


