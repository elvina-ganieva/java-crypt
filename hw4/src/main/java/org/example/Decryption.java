package org.example;

import org.example.service.DataTransferService;
import org.example.service.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Decryption {

    public static void main(String[] args) {
        var dataTransferService = new DataTransferService();
        var dto = dataTransferService.readObject();

        var keyStoreService = new KeyStoreService();
        var key = keyStoreService.getSecretKey();

        var cipherService = new CipherService();
        var decipheredData = cipherService.cipher(Cipher.DECRYPT_MODE, key, dto.cipheredText());

        System.out.println("Расшифрованный текст: " + new String(decipheredData, StandardCharsets.UTF_8));
    }
}
