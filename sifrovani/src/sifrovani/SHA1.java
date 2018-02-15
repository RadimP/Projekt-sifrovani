/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Student
 */
public class SHA1 extends CipherAlgorithm implements Serializable {
 private String decipheredtext;
 
 public SHA1() {
    }

    public SHA1(String texttocipher) {
                this.texttocipher = Sifrovani.Helper.adjustStringToLettersAndUpperCases(texttocipher);
    }
    
    

    
    @Override
    public void cipher(String string) {
        this.texttocipher=Sifrovani.Helper.adjustStringToLettersAndUpperCases(string);
        

		String sha1 = "";
		
		// With the java libraries
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(this.texttocipher.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

		System.out.println( "The sha1 of \""+ this.texttocipher + "\" is:");
		System.out.println( sha1 );
		System.out.println();
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
