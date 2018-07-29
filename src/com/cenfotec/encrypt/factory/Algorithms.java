package com.cenfotec.encrypt.factory;

public enum Algorithms {
	RSA(Encryption.ASYMETRIC),
	AES(Encryption.SYMETRIC),
	DES(Encryption.SYMETRIC),
	ECIESwithAES(Encryption.ASYMETRIC);
	
	Encryption type;
	Algorithms (Encryption pType) {
		this.type = pType;
	}
	
	public Encryption getType () {
		return this.type;
	}
	

}
