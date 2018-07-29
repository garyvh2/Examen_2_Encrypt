package com.cenfotec.encrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.cenfotec.encrypt.factory.Algorithms;
import com.cenfotec.encrypt.factory.AsymetricManager;
import com.cenfotec.encrypt.factory.Cryptographer;
import com.cenfotec.encrypt.factory.Encryption;
import com.cenfotec.encrypt.factory.EncryptionManager;

public class MainClass {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
	
	public static void main(String[] args) throws Exception {
		Algorithms TYPE = null;
		do {
    		System.out.println("Select An Algorithm");
			for(Algorithms type : Algorithms.values()) {
				System.out.println(type.name() + " (" + type.getType().name() + ")");
			}
    		System.out.println("-1. Para Salir");
			try {
				String input = br.readLine();
				
				if (input.equals("-1")) break;
				
				TYPE = Algorithms.valueOf(input);
				ShowMenu(TYPE);
				TYPE = null;
			} catch (IllegalArgumentException e) {
	    		System.out.println("Invalid Option!");
				TYPE = null;
			}
    	} while (TYPE == null);
    	
    }
	
	public static void ShowMenu (Algorithms TYPE) throws Exception {
		int option = 0;
		do {
    		System.out.println("1.Create key");
        	System.out.println("2.Encript Message");
        	System.out.println("3.Decrypt Message");
        	System.out.println("4.Exit ");
        	option = Integer.parseInt(br.readLine());

        	if (option >= 1 && option <= 3){
        		executeAction(option, TYPE);
        	}
    	} while (option != 4);
	}

	private static void executeAction(int option, Algorithms TYPE) throws Exception {
		Cryptographer<?, ?> CRYTO = EncryptionManager.Create(TYPE);
		if (option == 1){ 
			System.out.println("Key name: ");
			String name = br.readLine();
			CRYTO.createKey(name);
		}
		if (option == 2){
			System.out.println("Key name: ");
			String name = br.readLine();
			System.out.println("Message name: ");
			String messageName = br.readLine();
			System.out.println("Message: ");
			String message = br.readLine();
			CRYTO.encrypt(messageName,message,name);		
		}
		if (option == 3){
			System.out.println("Key name: ");
			String keyName = br.readLine();
			System.out.println("Message name: ");
			String messageName = br.readLine();
			String message = CRYTO.decrypt(messageName, keyName);	
			System.out.println(message);
		}
	}

}
