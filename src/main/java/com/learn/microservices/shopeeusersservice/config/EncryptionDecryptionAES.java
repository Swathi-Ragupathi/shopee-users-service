package com.learn.microservices.shopeeusersservice.config;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionDecryptionAES {
	@Value("${secret}")
	String secret;
	static Cipher cipher;

	static {
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
		}
	}

	public String encrypt(String plainText) throws Exception {
		byte[] keyByte = secret.getBytes(StandardCharsets.UTF_8.name());
		SecretKey secretKey = new SecretKeySpec(keyByte, "AES");
		return encrypt(plainText, secretKey);

	}

	public String decrypt(String encryptedText) throws Exception {
		byte[] keyByte = secret.getBytes(StandardCharsets.UTF_8.name());
		SecretKey secretKey = new SecretKeySpec(keyByte, "AES");
		return decrypt(encryptedText, secretKey);

	}

	public String encrypt(String plainText, SecretKey secretKey) throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}
}
