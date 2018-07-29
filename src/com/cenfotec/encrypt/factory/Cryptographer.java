package com.cenfotec.encrypt.factory;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class Cryptographer<T extends Key, F> implements FileManager<F> {
	protected Algorithms TYPE;
	private Cipher ENCRYPTER;
	
	private final String MESSAGE_ENCRYPT_EXTENSION = ".encript";
	private final String PUBLIC = "public";
	private final String PRIVATE = "private";

	public Cryptographer(Algorithms pType) {
		this.TYPE = pType;
	};

	public boolean encrypt(String messageName, String message, String keyName)
			throws NoSuchPaddingException, Exception {
		try {
			boolean ASYMETRIC = TYPE.getType().equals(Encryption.ASYMETRIC);
			
			String type = ASYMETRIC ? PUBLIC : null;
			// >> Get key
			T crypKey = this.getKey(keyName, type);

			// >> Initialize Encrypter
			ENCRYPTER = Cipher.getInstance(TYPE.name());
			ENCRYPTER.init(Cipher.ENCRYPT_MODE, ASYMETRIC ? (PublicKey)crypKey : crypKey);

			// >> Encrypt Message
			byte[] encryptedMessage = this.encryptMessage(message);

			// >> Save to file
			this.WriteBytesFile(messageName, encryptedMessage, MESSAGE_ENCRYPT_EXTENSION);

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	};

	public String decrypt(String messageName, String keyName) throws NoSuchPaddingException, Exception {
		try {
			boolean ASYMETRIC = TYPE.getType().equals(Encryption.ASYMETRIC);
			
			String type = ASYMETRIC ? PRIVATE : null;
			// >> Get key
			T crypKey = this.getKey(keyName, type);

			// >> Initialize Crypter
			ENCRYPTER = Cipher.getInstance(TYPE.name());
			ENCRYPTER.init(Cipher.DECRYPT_MODE, ASYMETRIC ? (PrivateKey)crypKey : crypKey);

			// >> Get Message
			byte[] encryptedMessage = ReadMessageFile(messageName, MESSAGE_ENCRYPT_EXTENSION);

			return this.decryptMessage(encryptedMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	};

	public byte[] encryptMessage(String message) throws IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedData = ENCRYPTER.doFinal(message.getBytes(StandardCharsets.UTF_8));
		Encoder oneEncoder = Base64.getEncoder();
		encryptedData = oneEncoder.encode(encryptedData);

		return encryptedData;
	};

	public String decryptMessage(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
		byte[] DecryptedData = ENCRYPTER.doFinal(message);
		return new String(DecryptedData, StandardCharsets.UTF_8);
	}

	public abstract void createKey(String keyName) throws Exception;

	public abstract T getKey(String keyName, String type) throws Exception;
}
