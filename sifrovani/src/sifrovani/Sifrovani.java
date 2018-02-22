/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sifrovani;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class Sifrovani {

    public static class Helper {

        public static void serialize(Object o, String name) throws Exception {
            try {
                // Serializace do souboru
                ObjectOutput out = new ObjectOutputStream(
                        new FileOutputStream(name));
                // jméno souboru
                out.writeObject(o);
                out.close();
            } catch (IOException e) {
                throw new Exception("Chyba při zápisu souboru : " + e);
            }

        }

        public static Object deserialize(String name) throws Exception {
            Object o = new Object();
            // Načtení ze souboru
            try {
                File file = new File(name);
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(file));
                // Deserializace objektu
                o = in.readObject();

                in.close();

            } catch (IOException ex) {
                Logger.getLogger(VigenereCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
            return o;
        }

        public static String adjustStringToLettersAndUpperCases(String string) {
            string = string.replaceAll("[^\\p{L} ]", "").replaceAll("\\s", "").toUpperCase();
            String normalized = java.text.Normalizer.normalize(string, java.text.Normalizer.Form.NFD);// change stanart czech diacritic to non diacritic
            string = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");// remove diacritical marks which left after normalizer

            return string;

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String s = "Žluťoučký kůň uháněl řeřichovým polem!";
        SHA1 sha1 = new SHA1();
        sha1.cipher(new File("sha1cipher.txt"));
        System.out.println(sha1.texttocipher);
        System.out.println(sha1.cipheredtext);


      
       

       
    }

}
