package com.cenfotec.encrypt.factory;

public enum Algorithms {
	RSA(Encryption.ASYMETRIC, 0),
	AES(Encryption.SYMETRIC, 8),
	DES(Encryption.SYMETRIC, 4);
	
	Encryption type;
	int keySize;
	Algorithms (Encryption pType, int pKeySize) {
		this.type = pType;
		this.keySize = pKeySize;
	}
	
	public Encryption getType () {
		return this.type;
	}
	public int getKeySize () {
		return this.keySize;
	}
	

}
