package org.example;

import org.example.service.DataTransferService;
import org.example.dto.Dto;
import org.example.service.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Необходимо передать пароль в качестве параметров.");
            return;
        }
        var password = args[0];

        var keyStoreService = new KeyStoreService();
        var keyGenerator = new KeyGenerator(keyStoreService);
        var key = keyGenerator.generateKey(password);

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, key, "Java".getBytes(StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData));
    }
}
