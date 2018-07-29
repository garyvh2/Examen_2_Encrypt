package com.cenfotec.encrypt.test;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;

import org.junit.Test;

import com.cenfotec.encrypt.factory.Algorithms;
import com.cenfotec.encrypt.factory.Cryptographer;
import com.cenfotec.encrypt.factory.EncryptionFactory;

public class EncryptionManager_Test {	
	String KEY_NAME = "TEST_KEY";
	String MESSAGE = "Hola TESTING";
	String MESSAGE_NAME = "TEST_MESSAGE";
	
	@Test
	public void RSA() throws Exception {
		String SUFFIX = "_RSA";
		
		Cryptographer<?, ?> CRYPTO = EncryptionFactory.Create(Algorithms.RSA);
		CRYPTO.createKey(KEY_NAME + SUFFIX);
		CRYPTO.encrypt(MESSAGE_NAME + SUFFIX, MESSAGE, KEY_NAME + SUFFIX);
		assertEquals(MESSAGE, CRYPTO.decrypt(MESSAGE_NAME + SUFFIX, KEY_NAME + SUFFIX));
	}
	
	@Test
	public void AES() throws Exception {
		String SUFFIX = "_AES";
		
		Cryptographer<?, ?> CRYPTO = EncryptionFactory.Create(Algorithms.AES);
		CRYPTO.createKey(KEY_NAME + SUFFIX);
		CRYPTO.encrypt(MESSAGE_NAME + SUFFIX, MESSAGE, KEY_NAME + SUFFIX);
		assertEquals(MESSAGE, CRYPTO.decrypt(MESSAGE_NAME + SUFFIX, KEY_NAME + SUFFIX));
	}

	@Test
	public void DES() throws Exception {
		String SUFFIX = "_DES";
		
		Cryptographer<?, ?> CRYPTO = EncryptionFactory.Create(Algorithms.DES);
		CRYPTO.createKey(KEY_NAME + SUFFIX);
		CRYPTO.encrypt(MESSAGE_NAME + SUFFIX, MESSAGE, KEY_NAME + SUFFIX);
		assertEquals(MESSAGE, CRYPTO.decrypt(MESSAGE_NAME + SUFFIX, KEY_NAME + SUFFIX));
	}
	
	@Test(expected = InvalidKeyException.class)
	public void OTHER_KEY() throws Exception {
		String SUFFIX = "_DES";
		
		Cryptographer<?, ?> CRYPTO = EncryptionFactory.Create(Algorithms.DES);
		CRYPTO.decrypt(MESSAGE_NAME + SUFFIX, KEY_NAME + "_AES");
	}
}
