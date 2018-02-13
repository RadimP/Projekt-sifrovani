/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;

/**
 * Class for coding text using Caesar sipher.
 *
 * @author zdenek
 */
public class CaesarCipher extends CipherAlgorithm {
        protected String code;
    /**
     * Constructor for Caesar ciphre using text on input
     *
     * @author zdenek
     * @param textToCipher text for coding
     */
    public CaesarCipher(String textToCipher) {

        this.texttocipher = textToCipher;

    }

    /**
     * this method prepare text for ciphre makes all letter uppercase ,delete
     * all non letters signs, remove diacritics from words
     *
     * @return text for code
     */
    @Override
    public String prepareText() {

        this.texttocipher = this.texttocipher.toUpperCase(); // change text to uppercase
        this.texttocipher = this.texttocipher.replaceAll("[^\\p{L} ]", "").replaceAll("\\s", "");// remove specials signs
        String normalized = java.text.Normalizer.normalize(this.texttocipher, java.text.Normalizer.Form.NFD);// change stanart czech diacritic to non diacritic
        this.texttocipher = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");// remove diacritical marks which left after normalizer

        return this.texttocipher; // ready text for coding
    }

    private String Code() {
        this.code = "123";

        return code;
    }

    /**
     * it encodes text to cipher
     *
     *
     */
    @Override
    public void cipher(String string) {

        this.texttocipher = string;
        this.prepareText();
        
        // main algorithm
        int j = 0, s = 1;
        for (int i = 0; i < this.texttocipher.length(); i++) {

        }

    }

    @Override
    public void cipher(File file) {
 /*String kod = "779";
        String veta = "AHOj JAVa!";
        int j = 0, s = 1;
        for (int i = 0; i < veta.length(); i++) {
            if (veta.charAt(i) >= 65 && veta.charAt(i) <= 90 || veta.charAt(i) >= 97 && veta.charAt(i) <= 122) {
                if (s == 1) {
                    if ((int) veta.charAt(i) <= 90 && ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)) > 90 || ((int) veta.charAt(i) >= 97 && ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)) > 122)) {
                        System.out.print(
                                (char) ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48) - 25)
                        );
                    } else {
                        System.out.print(
                                (char) ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)));
                    }
                } else if ((int) veta.charAt(i) <= 90 && ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)) < 65 || ((int) veta.charAt(i) >= 97 && ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)) < 97)) {
                    System.out.print(
                            (char) ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48) + 25)
                    );
                } else {
                    System.out.print(
                            (char) ((int) veta.charAt(i) + s * ((int) kod.charAt(j) - 48)));
                }
                if (j == kod.length() - 1) {
                    j = 0;
                    s = s * -1;

                } else {
                    j++;
                }
            } else {
                System.out.print(veta.charAt(i));
            }
        }
        
        Text_sifruj sifra=new Text_sifruj();*/
        
    }

    @Override
    public void decipher(String string) {

    }

    @Override
    public void decipher(File file) {
    }

}
