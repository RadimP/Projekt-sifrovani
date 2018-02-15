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
    private String key ="";
    private String decipheredtext;

    public VigenereCipher() {
    }

    public VigenereCipher(String texttocipher) {
        /* if(texttocipher.length()<3) { 
         throw new IllegalArgumentException("Text, který se má šifrovat, je kratší než tři znaky");} else
         {  this.texttocipher = texttocipher;
         if(this.removeAllNonletterCharacters().length()<3) { 
         throw new IllegalArgumentException("Text, který se má šifrovat, bude po úpravě kratší než tři znaky");}
         }*/
        this.texttocipher = texttocipher;
    }

    /**
     * Tato metoda zbaví text všech znaků, jež nejsou písmena, ořeže diakritiku
     * a převede text na velká písmena.
     *
     * @return vrátí očištěný String texttocipher
     */
    @Override
    public String prepareText() {
        this.removeAllNonletterCharacters();
        this.replaceDiacritics();
        this.textToUpperCases();
        return this.texttocipher;
    }

    /**
     * Tato metoda zbaví text všech znaků, jež nejsou písmena, a odstraní
     * mezery.
     *
     * @return vrátí String texttocipher, v němž zůstanou jen písmena (tedy bez
     * mezer, čísel, interpunkce a jiných nepísmenných znaků
     */
    public String removeAllNonletterCharacters() {
        this.texttocipher = this.texttocipher.replaceAll("[^\\p{L} ]", "").replaceAll("\\s", "");
        return this.texttocipher;
    }

    /**
     * Tato metoda odstraní ze znaků diakritiku.
     *
     * @return vrátí String texttocipher bez diakritiky
     */
    public String replaceDiacritics() {
        /*  String[] diacritics = new String[]{"À", "Á", "Â", "Ã", "Ä", "Å", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î", "Ï", "Ñ", "Ò", "Ó", "Ô", "Õ", "Ö", "Ù", "Ú", "Û", "Ü", "Ý", "ß", "à", "á", "â", "ã", "ä", "å", "ç", "è", "é", "ê", "ë", "ì", "í", "î", "ï", "ñ", "ò", "ó", "ô", "õ", "ö", "ù", "ú", "û", "ü", "ý", "ÿ", "Ā", "ā", "Ă", "ă", "Ą", "ą", "Ć", "ć", "Ĉ", "ĉ", "Ċ", "ċ", "Č", "č", "Ď", "ď", "Đ", "đ", "Ē", "ē", "Ĕ", "ĕ", "Ė", "ė", "Ę", "ę", "Ě", "ě", "Ĝ", "ĝ", "Ğ", "ğ", "Ġ", "ġ", "Ģ", "ģ", "Ĥ", "ĥ", "Ħ", "ħ", "Ĩ", "ĩ", "Ī", "ī", "Ĭ", "ĭ", "Į", "į", "İ", "ı", "Ķ", "ķ", "ĸ", "Ĺ", "ĺ", "Ļ", "ļ", "Ľ", "ľ", "Ŀ", "ŀ", "Ł", "ł", "Ń", "ń", "Ņ", "ņ", "Ň", "ň", "ŉ", "Ŋ", "ŋ", "Ō", "ō", "Ŏ", "ŏ", "Ő", "ő", "Ŕ", "ŕ", "Ŗ", "ŗ", "Ř", "ř", "Ś", "ś", "Ŝ", "ŝ", "Ş", "ş", "Š", "š", "Ţ", "ţ", "Ť", "ť", "Ŧ", "ŧ", "Ũ", "ũ", "Ū", "ū", "Ŭ", "ŭ", "Ů", "ů", "Ű", "ű", "Ų", "ų", "Ŵ", "ŵ", "Ŷ", "ŷ", "Ÿ", "Ź", "ź", "Ż", "ż", "Ž", "ž", "ſ"};
         String[] nondiacritics = new String[]{"A", "A", "A", "A", "A", "A", "C", "E", "E", "E", "E", "I", "I", "I", "I", "N", "O", "O", "O", "O", "O", "U", "U", "U", "U", "Y", "s", "a", "a", "a", "a", "a", "a", "c", "e", "e", "e", "e", "i", "i", "i", "i", "n", "o", "o", "o", "o", "o", "u", "u", "u", "u", "y", "y", "A", "a", "A", "a", "A", "a", "C", "c", "C", "c", "C", "c", "C", "c", "D", "d", "D", "d", "E", "e", "E", "e", "E", "e", "E", "e", "E", "e", "G", "g", "G", "g", "G", "g", "G", "g", "H", "h", "H", "h", "I", "i", "I", "i", "I", "i", "I", "i", "I", "i", "K", "k", "k", "L", "l", "L", "l", "L", "l", "L", "l", "L", "l", "N", "n", "N", "n", "N", "n", "N", "n", "N", "O", "o", "O", "o", "O", "o", "R", "r", "R", "r", "R", "r", "S", "s", "S", "s", "S", "s", "S", "s", "T", "t", "T", "t", "T", "t", "U", "u", "U", "u", "U", "u", "U", "u", "U", "u", "U", "u", "W", "w", "Y", "y", "Y", "Z", "z", "Z", "z", "Z", "z", "s"};
         for (int i = 0; i < diacritics.length; i++) {
         if (this.texttocipher.contains(diacritics[i])) {
         this.texttocipher = this.texttocipher.replace(diacritics[i], nondiacritics[i]);
         }
         }*/
        String normalized = java.text.Normalizer.normalize(this.texttocipher, java.text.Normalizer.Form.NFD);// change stanart czech diacritic to non diacritic
        this.texttocipher = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");// remove diacritical marks which left after normalizer
        return this.texttocipher;
    }

    /**
     * tato metoda převede text na velká písmena (nepočítá již s diakritikou)
     *
     * @return vrátí String texttocipher převedený na velká písmena
     */
    public String textToUpperCases() {
        this.texttocipher = this.texttocipher.toUpperCase();
        return this.texttocipher;
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
        this.key=key;
        this.encipher(key);
        try {
            this.putDataIntoFile();
        } catch (Exception ex) {
            Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String encipher() {
        StringBuilder builder = new StringBuilder();
        String s = this.prepareText();
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
        String s = this.prepareText();
        key=prepareKey(key);
this.key=key;
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
        if (this.prepareText().length() < 3) {
            throw new IllegalArgumentException("Text, který se má šifrovat, je kratší než tři znaky");
        }
        if (this.prepareText().length() > 5) {
            lenghtofkey = rand.nextInt(2 * this.prepareText().length() / 3 - 2) + 3;
        } else {
            lenghtofkey = rand.nextInt(this.prepareText().length() - 2) + 3;
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

    /**
     * Tato metoda upraví zadaný klíč tak, aby obsahoval jen velká písmena -
     * znaky anglické abecedy
     *
     * @param key zadaný klíč
     * @return upravený klíč key
     */
    public String prepareKey(String key) {
        key = key.replaceAll("[^\\p{L} ]", "").replaceAll("\\s", "").toUpperCase();
        key = java.text.Normalizer.normalize(key, java.text.Normalizer.Form.NFD);
        key = key.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return  key;
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
