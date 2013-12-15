package com.mrcrayfish.textcraft;

import java.security.Key;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;
//
// encrypt and decrypt using the DES private key algorithm
public class Encrypter 
{
	private static String algorithm = "AES";
	private static String encryptionKey = "I like birds too";

	@SuppressWarnings("restriction")
	public static String encryptString(String plainText) throws Exception 
    {
            Key key = new SecretKeySpec(encryptionKey.getBytes(), algorithm);
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = chiper.doFinal(plainText.getBytes());
            String encryptedValue = new BASE64Encoder().encode(encVal);
            return encryptedValue;
    }

    // Performs decryption
    @SuppressWarnings("restriction")
	public static String decryptString(String encryptedText) throws Exception 
    {
            // generate key 
    		Key key = new SecretKeySpec(encryptionKey.getBytes(), algorithm);
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedText);
            byte[] decValue = chiper.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
    }
}