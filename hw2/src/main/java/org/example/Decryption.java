package org.example;

import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyStoreService;
import org.example.service.MessageDigestService;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Decryption {
    public static void main(String[] args) {
        var dataTransferService = new DataTransferService();
        var dto = dataTransferService.readObject();
        var dataDigest = dto.dataDigest();
        var cipheredWord = dto.cipheredData();

        var keyStoreService = new KeyStoreService();
        var key = keyStoreService.getKeyFromStore();

        var cipherService = new CipherService();
        var decryptedWord = cipherService.cipher(Cipher.DECRYPT_MODE, key, cipheredWord);

        var originalWord = new String(decryptedWord, StandardCharsets.UTF_8);

        var messageDigestService = new MessageDigestService();
        var decipheredDataDigest = messageDigestService.digestMessage(decryptedWord);

        if (Arrays.equals(dataDigest, decipheredDataDigest)) {
            System.out.println("Переданный и вычисленный дайджесты равны.");
        } else {
            System.out.println("Переданный и вычисленный дайджесты не равны.");
        }
        System.out.println("Секретное слово: " + originalWord);
    }
}
