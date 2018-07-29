package com.cenfotec.encrypt.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

public class SymetricManager extends Cryptographer<SecretKeySpec, byte[]> {
	private final String KEY_EXTENSION = ".key";
	private final int KEYSIZE = 8;

	public SymetricManager(Algorithms pType) {
		super(pType);
	}

	@Override
	public byte[] ReadKeyFromFile(String name, String type) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(PATH + name + KEY_EXTENSION));
		String everything = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			everything = sb.toString();
		} finally {
			br.close();
		}
		return everything.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public void createKey(String keyName) throws Exception {
		try {
			byte[] key = generatedSequenceOfBytes();
			WriteBytesFile(keyName, key, KEY_EXTENSION);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public SecretKeySpec getKey(String keyName, String type) throws Exception {
		return new SecretKeySpec(ReadKeyFromFile(keyName, type), this.TYPE.name());

	}

	private byte[] generatedSequenceOfBytes() throws Exception {
		StringBuilder randomkey = new StringBuilder();
		for (int i = 0; i < KEYSIZE; i++) {
			randomkey.append(Integer.parseInt(Double.toString((Math.random() + 0.1) * 1000).substring(0, 2)));
		}
		return randomkey.toString().getBytes("UTF-8");
	}
}
