package org.example.decryption;

import org.example.common.CipherService;
import org.example.common.DataAccessService;
import org.example.common.KeyHandler;

import javax.crypto.*;

public class Decryption {
    public static void main(String[] args) {
        var dataAccessService = new DataAccessService();
        var hashCode = dataAccessService.getHashCode();
        var cipheredWord = dataAccessService.getCipheredWord();

        var key = new KeyHandler().retrieveExistingKey();
        var decryptedWord = new CipherService().cipher(Cipher.DECRYPT_MODE, key, cipheredWord);

        var result = new String(decryptedWord);
        if (result.hashCode() == hashCode) {
            System.out.println("Переданный и вычисленный хэши равны.");
        } else {
            System.out.println("Переданный и вычисленный хэши не равны.");
        }
        System.out.println("Секретное слово: " + result);
    }
}
