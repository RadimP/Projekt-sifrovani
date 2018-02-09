/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;

/**
 *
 * @author RadimP
 */
public class VigenereCipher extends CipherAlgorithm {

    public VigenereCipher() {
    }

    public VigenereCipher(String texttocipher) {
        this.texttocipher = texttocipher;

    }
/** 
 * Tato metoda zbaví text všech znaků, jež nejsou písmena, ořeže diakritiku a převede text na velká písmena.
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
 * Tato metoda zbaví text všech znaků, jež nejsou písmena, a odstraní mezery.
 * @return vrátí String texttocipher, v němž zůstanou jen písmena (tedy bez mezer, čísel, interpunkce a jiných nepísmenných znaků
 */
      public String removeAllNonletterCharacters() {
        this.texttocipher = this.texttocipher.replaceAll("[^\\p{L} ]", "").replaceAll("\\s", "");
        return this.texttocipher;
    }

   /**
    * Tato metoda odstraní ze znaků diakritiku.
    * @return vrátí String texttocipher bez diakritiky
    */
    public String replaceDiacritics() {
        String[] diacritics = new String[]{"À", "Á", "Â", "Ã", "Ä", "Å", "Ç", "È", "É", "Ê", "Ë", "Ì", "Í", "Î", "Ï", "Ñ", "Ò", "Ó", "Ô", "Õ", "Ö", "Ù", "Ú", "Û", "Ü", "Ý", "ß", "à", "á", "â", "ã", "ä", "å", "ç", "è", "é", "ê", "ë", "ì", "í", "î", "ï", "ñ", "ò", "ó", "ô", "õ", "ö", "ù", "ú", "û", "ü", "ý", "ÿ", "Ā", "ā", "Ă", "ă", "Ą", "ą", "Ć", "ć", "Ĉ", "ĉ", "Ċ", "ċ", "Č", "č", "Ď", "ď", "Đ", "đ", "Ē", "ē", "Ĕ", "ĕ", "Ė", "ė", "Ę", "ę", "Ě", "ě", "Ĝ", "ĝ", "Ğ", "ğ", "Ġ", "ġ", "Ģ", "ģ", "Ĥ", "ĥ", "Ħ", "ħ", "Ĩ", "ĩ", "Ī", "ī", "Ĭ", "ĭ", "Į", "į", "İ", "ı", "Ķ", "ķ", "ĸ", "Ĺ", "ĺ", "Ļ", "ļ", "Ľ", "ľ", "Ŀ", "ŀ", "Ł", "ł", "Ń", "ń", "Ņ", "ņ", "Ň", "ň", "ŉ", "Ŋ", "ŋ", "Ō", "ō", "Ŏ", "ŏ", "Ő", "ő", "Ŕ", "ŕ", "Ŗ", "ŗ", "Ř", "ř", "Ś", "ś", "Ŝ", "ŝ", "Ş", "ş", "Š", "š", "Ţ", "ţ", "Ť", "ť", "Ŧ", "ŧ", "Ũ", "ũ", "Ū", "ū", "Ŭ", "ŭ", "Ů", "ů", "Ű", "ű", "Ų", "ų", "Ŵ", "ŵ", "Ŷ", "ŷ", "Ÿ", "Ź", "ź", "Ż", "ż", "Ž", "ž", "ſ"};
        String[] nondiacritics = new String[]{"A", "A", "A", "A", "A", "A", "C", "E", "E", "E", "E", "I", "I", "I", "I", "N", "O", "O", "O", "O", "O", "U", "U", "U", "U", "Y", "s", "a", "a", "a", "a", "a", "a", "c", "e", "e", "e", "e", "i", "i", "i", "i", "n", "o", "o", "o", "o", "o", "u", "u", "u", "u", "y", "y", "A", "a", "A", "a", "A", "a", "C", "c", "C", "c", "C", "c", "C", "c", "D", "d", "D", "d", "E", "e", "E", "e", "E", "e", "E", "e", "E", "e", "G", "g", "G", "g", "G", "g", "G", "g", "H", "h", "H", "h", "I", "i", "I", "i", "I", "i", "I", "i", "I", "i", "K", "k", "k", "L", "l", "L", "l", "L", "l", "L", "l", "L", "l", "N", "n", "N", "n", "N", "n", "N", "n", "N", "O", "o", "O", "o", "O", "o", "R", "r", "R", "r", "R", "r", "S", "s", "S", "s", "S", "s", "S", "s", "T", "t", "T", "t", "T", "t", "U", "u", "U", "u", "U", "u", "U", "u", "U", "u", "U", "u", "W", "w", "Y", "y", "Y", "Z", "z", "Z", "z", "Z", "z", "s"};
        for (int i = 0; i < diacritics.length; i++) {
            if (this.texttocipher.contains(diacritics[i])) {
                this.texttocipher = this.texttocipher.replace(diacritics[i], nondiacritics[i]);
            }
        }
        return this.texttocipher;
    }
    /**
     * tato metoda převede text na velká písmena (nepočítá již s diakritikou)
     * @return vrátí String texttocipher převedený na velká písmena
     */
    public String textToUpperCases() {
    this.texttocipher=this.texttocipher.toUpperCase();
    return this.texttocipher;
    }

    @Override
    public void cipher(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
