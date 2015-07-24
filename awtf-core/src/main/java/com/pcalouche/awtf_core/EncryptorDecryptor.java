package com.pcalouche.awtf_core;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptorDecryptor {

	public static String encryptBlowfish(String to_encrypt, String strkey) {
		try {
			SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] bytes = cipher.doFinal(to_encrypt.getBytes());
			byte[] encryptedBytes = Base64.encodeBase64(bytes);
			return org.apache.commons.codec.binary.StringUtils.newStringUtf8(encryptedBytes);
		} catch (Exception e) {
			return ("Unable to encrypt string: " + to_encrypt + " because " + e);
		}
	}

	public static String decryptBlowfish(String to_decrypt, String strkey) {
		try {
			SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptedBytes = Base64.decodeBase64(to_decrypt);
			return new String(cipher.doFinal(decryptedBytes));
		} catch (Exception e) {
			return ("Unable to decrypt string: " + to_decrypt + " because " + e);
		}
	}
}
