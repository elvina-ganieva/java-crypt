package org.example;

import org.example.dto.Dto;
import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyGeneratorService;
import org.example.service.KeyStoreService;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Необходимо секретное слово в параметрах.");
            return;
        }
        var secretWord = args[0];
        var hashOfSecretWord = secretWord.hashCode();

        var keyStoreService = new KeyStoreService();
        var keyGeneratorService = new KeyGeneratorService(keyStoreService);
        var key = keyGeneratorService.initializeKey();

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, key, secretWord.getBytes(StandardCharsets.UTF_8));

        System.out.printf("hash: %s%nciphered word: %s%n", hashOfSecretWord, new String(cipheredData, StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData, hashOfSecretWord));
    }
}
