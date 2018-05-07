package com.goomo.cardvault.utils;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class used for cyrpto algo's for encryption and decryption.
 * @author Manjunath Jakkandi
 *
 */
public class CryptoUtils {

	private static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);
	private static int iterations = 65536;
	private static int keySize = 256;

	public static String encrypt(String word,String aesKey) throws Exception {
		byte[] ivBytes;
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		byte[] saltBytes = bytes;
		// Derive the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
		PBEKeySpec spec = new PBEKeySpec(aesKey.toCharArray(), saltBytes, iterations, keySize);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		// encrypting the word
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] encryptedTextBytes = cipher.doFinal(word.getBytes("UTF-8"));
		// prepend salt and vi
		byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
		System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
		System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
		System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
		return new Base64().encodeToString(buffer);
	}

	public static String decrypt(String encryptedText,String aesKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// strip off the salt and iv
		ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(encryptedText));
		byte[] saltBytes = new byte[20];
		buffer.get(saltBytes, 0, saltBytes.length);
		byte[] ivBytes1 = new byte[cipher.getBlockSize()];
		buffer.get(ivBytes1, 0, ivBytes1.length);
		byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];

		buffer.get(encryptedTextBytes);
		// Deriving the key
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
		PBEKeySpec spec = new PBEKeySpec(aesKey.toCharArray(), saltBytes, 65556, 256);
		SecretKey secretKey = factory.generateSecret(spec);
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));
		byte[] decryptedTextBytes = null;
		try {
			decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
		} catch (IllegalBlockSizeException e) {
			log.error("IllegalBlockSizeException in CryptoUtils class decrypt method");
		} catch (BadPaddingException e) {
			log.error("BadPaddingException in CryptoUtils class decrypt method");
		}

		return new String(decryptedTextBytes);
	}

}
