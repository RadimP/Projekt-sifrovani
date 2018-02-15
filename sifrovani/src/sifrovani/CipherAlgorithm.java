/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Student
 */
public abstract class CipherAlgorithm implements Cipher, Serializable {

    protected String texttocipher;
public String cipheredtext;
    public CipherAlgorithm() {
    }

    public CipherAlgorithm(String texttocipher) {
        this.texttocipher = texttocipher;

    }


    @Override
    public abstract void cipher(String string);

    @Override
    public abstract void cipher(File file);

    @Override
    public abstract void decipher(String string); 

    @Override
    public abstract void decipher(File file); 
}
