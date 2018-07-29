package com.cenfotec.encrypt.factory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class AsymetricManager extends Cryptographer<Key, Key> {
	private final String KEY_EXTENSION = ".key";

	public AsymetricManager(Algorithms pType) {
		super(pType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Key ReadKeyFromFile(String name, String type) throws IOException {
		InputStream in = new FileInputStream(PATH + name + "_" + type + KEY_EXTENSION);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();

			KeyFactory fact = KeyFactory.getInstance(TYPE.name());
			
			if (type.equals("public")) {
				RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
				return fact.generatePublic(keySpec);
			} else {
				RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
				return fact.generatePrivate(keySpec);
			}
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();
		}
	}

	@Override
	public void createKey(String keyName) throws Exception {
		try {

			// TODO Auto-generated method stub
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(this.TYPE.name());
			KeyFactory fact = KeyFactory.getInstance(this.TYPE.name());
			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class);
			RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class);

			WriteObjectFile(PATH + keyName + "_" + "PUBLIC", KEY_EXTENSION, pub.getModulus(), pub.getPublicExponent());
			WriteObjectFile(PATH + keyName + "_" + "PRIVATE", KEY_EXTENSION, priv.getModulus(), priv.getPrivateExponent());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Key getKey(String keyName, String type) throws Exception {
		return ReadKeyFromFile(keyName, type);
	}
}
