package org.example;

import org.example.dto.Dto;
import org.example.service.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Необходимо секретное слово в параметрах.");
            return;
        }
        var secretWord = args[0];
        var secretWordInBytes = secretWord.getBytes(StandardCharsets.UTF_8);

        var keyStoreService = new KeyStoreService();
        var keyGeneratorService = new KeyGeneratorService(keyStoreService);
        var key = keyGeneratorService.initializeKey();

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, key, secretWordInBytes);

        var messageDigestService = new MessageDigestService();
        var secretWordDigest = messageDigestService.digestMessage(secretWordInBytes);

        System.out.println("Дайджест: " + new String(secretWordDigest, StandardCharsets.UTF_8));
        System.out.println("Зашифрованное слово: " + new String(cipheredData, StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData, secretWordDigest));
    }
}
