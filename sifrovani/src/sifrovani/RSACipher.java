/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author RadimP
 */
public class RSACipher extends CipherAlgorithm {
    private KeyPair keypair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public Cipher RSAciph;
    RSACipher() {}
    RSACipher(String texttocipher) {
    this.texttocipher=texttocipher;
    }

    @Override
    public void cipher(String string) {
        this.generateKey();
        try {
            this.RSAciph = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(RSACipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.cipheredtext=this.encrypt(string);
        } catch (Exception ex) {
            Logger.getLogger(RSACipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String encrypt(String plaintext) throws Exception{
        this.texttocipher=Sifrovani.Helper.adjustStringToLettersAndUpperCases(plaintext);
	this.RSAciph.init(Cipher.ENCRYPT_MODE, this.keypair.getPublic());
	byte[] bytes = this.texttocipher.getBytes("UTF-8");

	byte[] encrypted = blockCipher(bytes,Cipher.ENCRYPT_MODE);

	char[] encryptedTranspherable = Hex.encodeHex(encrypted);
	return new String(encryptedTranspherable);
}
    private byte[] blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
	// string initialize 2 buffers.
	// scrambled will hold intermediate results
	byte[] scrambled = new byte[0];

	// toReturn will hold the total result
	byte[] toReturn = new byte[0];
	// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
	int length = (mode == Cipher.ENCRYPT_MODE)? 100 : 128;

	// another buffer. this one will hold the bytes that have to be modified in this step
	byte[] buffer = new byte[length];

	for (int i=0; i< bytes.length; i++){

		// if we filled our buffer array we have our block ready for de- or encryption
		if ((i > 0) && (i % length == 0)){
			//execute the operation
			scrambled = RSAciph.doFinal(buffer);
			// add the result to our total result.
			toReturn = append(toReturn,scrambled);
			// here we calculate the length of the next buffer required
			int newlength = length;

			// if newlength would be longer than remaining bytes in the bytes array we shorten it.
			if (i + length > bytes.length) {
				 newlength = bytes.length - i;
			}
			// clean the buffer array
			buffer = new byte[newlength];
		}
		// copy byte into our buffer.
		buffer[i%length] = bytes[i];
	}

	// this step is needed if we had a trailing buffer. should only happen when encrypting.
	// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
	scrambled = RSAciph.doFinal(buffer);

	// final step before we can return the modified data.
	toReturn = append(toReturn,scrambled);

	return toReturn;
}
    
    private byte[] append(byte[] prefix, byte[] suffix){
	byte[] toReturn = new byte[prefix.length + suffix.length];
	for (int i=0; i< prefix.length; i++){
		toReturn[i] = prefix[i];
	}
	for (int i=0; i< suffix.length; i++){
		toReturn[i+prefix.length] = suffix[i];
	}
	return toReturn;
}
    
 public void generateKey() {
 try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            this.keypair = kpg.generateKeyPair();
            this.publicKey = this.keypair.getPublic();
            this.privateKey = this.keypair.getPrivate();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSACipher.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    
    @Override
    public void cipher(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decipher(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decipher(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
