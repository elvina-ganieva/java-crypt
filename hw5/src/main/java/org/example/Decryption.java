package org.example;

import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyStoreService;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Decryption {

    public static void main(String[] args) {
        var dataTransferService = new DataTransferService();
        var dto = dataTransferService.readObject();

        var fileName = dto.keyStoreFileName();
        var keyAlias = dto.keyAlias();
        var cipheredData = dto.cipheredData();

        var keyStoreService = new KeyStoreService();
        var key = keyStoreService.getPrivateKey(fileName, keyAlias);

        var cipherService = new CipherService();
        var decipheredData = cipherService.cipher(Cipher.DECRYPT_MODE, key, cipheredData);

        System.out.println("Расшифрованный текст: " + new String(decipheredData, StandardCharsets.UTF_8));
    }
}
