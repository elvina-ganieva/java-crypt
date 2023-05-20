package org.example;

import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyStoreService;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;

public class Decryption {
    public static void main(String[] args) {
        var dataTransferService = new DataTransferService();
        var dto = dataTransferService.readObject();
        var hashCode = dto.hashCodeOfOriginalData();
        var cipheredWord = dto.cipheredData();

        var keyStoreService = new KeyStoreService();
        var key = keyStoreService.getKeyFromStore();

        var cipherService = new CipherService();
        var decryptedWord = cipherService.cipher(Cipher.DECRYPT_MODE, key, cipheredWord);

        var originalWord = new String(decryptedWord, StandardCharsets.UTF_8);

        if (originalWord.hashCode() == hashCode) {
            System.out.println("Переданный и вычисленный хэши равны.");
        } else {
            System.out.println("Переданный и вычисленный хэши не равны.");
        }
        System.out.println("Секретное слово: " + originalWord);
    }
}
