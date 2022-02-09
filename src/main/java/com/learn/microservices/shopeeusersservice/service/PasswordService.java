package com.learn.microservices.shopeeusersservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.microservices.shopeeusersservice.config.EncryptionDecryptionAES;

@Service
public class PasswordService {
	

	@Autowired
	private EncryptionDecryptionAES encryptionDecryption;
	
	public String passwordEncryption(String plainPassword) {
		try {
			return encryptionDecryption.encrypt(plainPassword);
		} catch (Exception e) {
			System.err.println("Error in password encode");
		}
		return null;
	}

}
