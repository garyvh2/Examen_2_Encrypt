package com.cenfotec.encrypt.factory;

public enum Algorithms {
	RSA(Encryption.ASYMETRIC),
	AES(Encryption.SYMETRIC);
	
	Encryption type;
	Algorithms (Encryption pType) {
		this.type = pType;
	}
	
	public Encryption getType () {
		return this.type;
	}
	

}
