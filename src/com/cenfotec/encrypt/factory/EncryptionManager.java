package com.cenfotec.encrypt.factory;

public class EncryptionManager {
	public static Cryptographer<?, ?> Create (Algorithms pType) {
		switch(pType.getType()) {
		case ASYMETRIC:
			return new AsymetricManager(pType);
		case SYMETRIC:
			return new SymetricManager(pType);
		default:
			return null;
		}
	}
}
