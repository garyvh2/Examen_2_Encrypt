package com.cenfotec.encrypt.factory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;

public interface FileManager<T> {
	final String PATH = "/Users/gvalverde/Desktop/EncryptationManager/encrypt/";

	T ReadKeyFromFile(String name, String type) throws Exception;

	default byte[] ReadMessageFile(String messageName, String type) throws Exception {
		File file = new File(PATH + messageName + type);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		Decoder oneDecoder = Base64.getDecoder();
		return oneDecoder.decode(bytes);

	}

	default void WriteBytesFile(String name, byte[] content, String type) throws IOException {
		FileOutputStream fos = new FileOutputStream(PATH + name + type);
		fos.write(content);
		fos.close();
	}

	default <F> void WriteObjectFile(String name, String type, F... keys) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(
			    new BufferedOutputStream(new FileOutputStream(name + type)));
		try {
			for(F key : keys) {
				oout.writeObject(key);
			}
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
		    oout.close();
		}
	}
}
