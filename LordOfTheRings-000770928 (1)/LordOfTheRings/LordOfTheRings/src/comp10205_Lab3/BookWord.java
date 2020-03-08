/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp10205_Lab3;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * I, Shamik Bhesaniya - 000770928, certify that this is my original work and no other person's work is used 
 * or given without due acknowledgement.
 * @author Shamik Bhesaniya
 */
public class BookWord {

    private final String text;

    private int count;

    public BookWord(String wordText) {
        this.text = wordText;
    }

    public String getText() {
        return text;
    }

    public int getCount() {
        return count;
    }

    public void incrementalCount() {
        count = count + 1;
    }

    @Override
    public boolean equals(Object wordToCompare) {
        if (wordToCompare == null) {
            return false;
        }
        if (getClass() != wordToCompare.getClass()) {
            return false;
        }
        final BookWord other = (BookWord) wordToCompare;
        return Objects.equals(this.text, other.text);
    }

    // https://cp-algorithms.com/string/string-hashing.html.
    // polynomial rolling hash function.
    @Override
    public int hashCode() {
        int p = 31;
        int m = (int) (1e9 + 9);
        int hash_value = 0;
        int p_pow = 1;
        char[] textArray = text.toCharArray();

        for (char c : textArray) {
            hash_value = (hash_value + (c - 'a' + 1) * p_pow) % m;
            p_pow = (p_pow * p) % m;
        }
        return hash_value;
    }

    @Override
    public String toString() {
        String space = "";
        String space2 = "";
        int wordLength = this.text.length();
        int countLength = String.valueOf(this.count).length();
        for (int i = 0; i < 40 - wordLength; i++) {
            space += " ";
        }
        for (int i = 0; i < 6 - countLength; i++) {
            space2 += " ";
        }
        String bookWord = "[ Text=" + this.text + space + ", Count=" + this.count + space2 + "]";
        return bookWord;
    }

}
