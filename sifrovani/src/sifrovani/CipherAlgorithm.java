/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;

/**
 *
 * @author Student
 */
public abstract class CipherAlgorithm implements Cipher {

    protected String texttocipher;

    public CipherAlgorithm() {
    }

    public CipherAlgorithm(String texttocipher) {
        this.texttocipher = texttocipher;

    }
public abstract String prepareText();

//public abstract void cipherText();

//public abstract void decipherText();

    @Override
    public abstract void cipher(String string);

    @Override
    public abstract void cipher(File file);

    @Override
    public abstract void decipher(String string); 

    @Override
    public abstract void decipher(File file); 
}
