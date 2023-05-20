package org.example;

import org.example.dto.Dto;
import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyGeneratorService;
import org.example.service.KeyStoreService;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {

    private static final String KEY_ALIAS = "rsa-key";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("В параметрах требуется передавать имя файла хранилища и длину ключа RSA.");
            return;
        }
        var keyStoreFileName = args[0] + ".jks";
        var keyLength = Integer.parseInt(args[1]);

        var keyGeneratorService = new KeyGeneratorService();
        var keyGenerator = keyGeneratorService.getCertAndKeyGenerator(keyLength);

        var keyStoreService = new KeyStoreService();
        keyStoreService.storePrivateKey(keyStoreFileName, keyGenerator, KEY_ALIAS);

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, keyGenerator.getPublicKey(), "Java".getBytes());

        System.out.println("Зашифрованное слово: " + new String(cipheredData, StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData, keyStoreFileName, KEY_ALIAS));
    }
}
