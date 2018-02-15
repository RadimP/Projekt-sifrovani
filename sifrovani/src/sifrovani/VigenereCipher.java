/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RadimP
 */
public class VigenereCipher extends CipherAlgorithm implements Serializable {

    // http://javarevisited.blogspot.cz/2012/12/how-to-read-input-from-command-line-in-java.html interaktivní zadávání vstupu z commandline   
    private String key = "";
    private String decipheredtext;

    public VigenereCipher() {
    }

    public VigenereCipher(String texttocipher) {
                this.texttocipher = texttocipher;
    }

    
    

    

    @Override
    public void cipher(String string) {
        if (string.length() < 3) {
            throw new IllegalArgumentException("Text, který se má šifrovat, je kratší než tři znaky");
        }
        this.texttocipher = string;
        this.encipher();
        try {
            this.putDataIntoFile();
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cipher(String string, String key) {
        if (string.length() < 3) {
            throw new IllegalArgumentException("Text, který se má šifrovat, je kratší než tři znaky");
        }
        this.texttocipher = string;
        this.key = key;
        this.encipher(key);
        try {
            this.putDataIntoFile();
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String encipher() {
        StringBuilder builder = new StringBuilder();
        String s = Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher);
        generateKey();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 65 || s.charAt(i) > 90) { //znak v ASCII
                throw new IllegalArgumentException("Sifrovany retezec neobsahuje jen velka pismena");
            }
            //modularne pricteme shift
            char encyphered = s.charAt(i) + getShift(key, i) > 90 ? (char) ((s.charAt(i) + getShift(key, i)) - 26) : (char) (s.charAt(i) + getShift(key, i));
            builder.append(encyphered);
        }
        return this.cipheredtext = builder.toString();
    }

    private int getShift(String key, int i) {
        if (key.charAt(i % key.length()) < 65 || key.charAt(i % key.length()) > 90) { //znak v ASCII
            throw new IllegalArgumentException("Klic retezec neobsahuje jen velka pismena");
        }
        return ((int) key.charAt(i % key.length())) - 65;
    }

    public String encipher(String key) {
        StringBuilder builder = new StringBuilder();
        String s = Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher);
        key = Sifrovani.Helper.adjustStringToLettersAndUpperCases(key);
        this.key = key;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 65 || s.charAt(i) > 90) { //znak v ASCII
                throw new IllegalArgumentException("Sifrovany retezec neobsahuje jen velka pismena");
            }
            //modularne pricteme shift
            char encyphered = s.charAt(i) + getShift(key, i) > 90 ? (char) ((s.charAt(i) + getShift(key, i)) - 26) : (char) (s.charAt(i) + getShift(key, i));
            builder.append(encyphered);
        }
        return this.cipheredtext = builder.toString();
    }

    /**
     * Tato metoda nastaví délku klíče minimálně tři znaky a maximálně dvě
     * třetiny délky textu, který se má šifrovat. V rozmězí délky tři, čtyři a
     * pět znaků je minimální délka klíče 3 a maximální délka shodná s délkou
     * textu.
     *
     * @return délka klíče
     */
    private int setLengthOfKey() {
        Random rand = new Random();
        int lenghtofkey = 0;
        if (Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher).length() < 3) {
            throw new IllegalArgumentException("Text, který se má šifrovat, je kratší než tři znaky");
        }
        if (Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher).length() > 5) {
            lenghtofkey = rand.nextInt(2 * Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher).length() / 3 - 2) + 3;
        } else {
            lenghtofkey = rand.nextInt(Sifrovani.Helper.adjustStringToLettersAndUpperCases(this.texttocipher).length() - 2) + 3;
        }
        return lenghtofkey;
    }

    /**
     * Tato metoda vytvoří šifrovací klíč o délce minimálně tři znaky a
     * maximálně dvě třetiny délky textu, který se má šifrovat. Text, který se
     * má šifrovat, musí být dlouhý minimálně tři znaky. V rozmězí délky tři,
     * čtyři a pět znaků je minimální délka klíče 3 a maximální délka shodná s
     * délkou textu.
     *
     * @return šifrovací klíč
     */
    private String generateKey() {
        int lenghtofkey = this.setLengthOfKey();
        StringBuilder builder = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < lenghtofkey; i++) {
            int character = rand.nextInt(26) + 65;
            builder.append((char) character);
        }
        return this.key = builder.toString();
    }

    
    

    public String getKey() {
        return this.key;
    }

    public void putDataIntoFile() throws Exception {
        try {
            FileOutputStream fname = new FileOutputStream("vigenerecipher.dat");
            DataOutputStream out = new DataOutputStream(fname);

            out.writeUTF(key);
            out.writeUTF(this.cipheredtext);
            out.close();
        } catch (IOException e) {
            throw new Exception("Chyba při zápisu souboru : " + e);
        }

    }

    @Override
    public void cipher(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decipher(String string) {
        this.cipheredtext = string;
        decipherText();
    }

    public String decipherText() {
        StringBuilder builder = new StringBuilder();
        String s = this.cipheredtext;
        String key = getKey();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 65 || s.charAt(i) > 90) {
                throw new IllegalArgumentException(
                        "Desifrovany retezec neobsahuje jen velka pismena");
            }

            char decyphered = s.charAt(i) - getShift(key, i) < 65 ? (char) ((s.charAt(i) - getShift(key, i)) + 26) : (char) (s.charAt(i) - getShift(key, i));
            builder.append(decyphered);
        }
        return decipheredtext = builder.toString();
    }

    public String getDecipheredText() {
        return this.decipheredtext;
    }

    @Override
    public void decipher(File file) {

        try {
            /* System.out.println(this.getKey() +", šifrovaný text: " +this.cipheredtext);
             try {
            
             DataInputStream in = new DataInputStream(
             new FileInputStream(file));
             key = in.readUTF();
             cipheredtext = in.readUTF();
             in.close();
            
             } catch (IOException ex) {
             Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            Sifrovani.Helper.deserialize(file.getPath());
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            key = ((VigenereCipher) Sifrovani.Helper.deserialize(file.getPath())).getKey();
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            cipheredtext = ((VigenereCipher) Sifrovani.Helper.deserialize(file.getPath())).cipheredtext;
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.getKey() + ", šifrovaný text: " + this.cipheredtext);
        this.decipherText();
    }

    /*  public void serialize() throws Exception {
     try {
     // Serializace do souboru
     ObjectOutput out = new ObjectOutputStream(
     new FileOutputStream("vigenerecipher.dat"));
     // jméno souboru
     out.writeObject(this);
     out.close();
     } catch (IOException e) {
     throw new Exception("Chyba při zápisu souboru : " + e);
     }

     }*/
    /* public void deserialize() throws Exception {
     VigenereCipher vciph;   
     // Načtení ze souboru
     try {
     File file = new File("vigenerecipher.dat");
     ObjectInputStream in = new ObjectInputStream(
     new FileInputStream(file));
     // Deserializace objektu
     vciph = (VigenereCipher) in.readObject(); 
        
     in.close();
   
     } catch (IOException ex) {
     Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
     }
        
     }*/
}
