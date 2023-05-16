package org.example;

import org.example.common.DataTransferService;
import org.example.dto.Dto;
import org.example.service.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Необходимо передать пароль и salt в качестве параметров.");
            return;
        }
        var password = args[0];
        var salt = args[1];

        var keyStoreService = new KeyStoreService();
        var keyGenerator = new KeyGenerator(keyStoreService);
        var key = keyGenerator.generateKey(password, salt.getBytes());

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, key, "Java".getBytes(StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData));
    }
}
