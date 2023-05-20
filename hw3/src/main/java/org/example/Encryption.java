package org.example;

import org.example.service.DataTransferService;
import org.example.dto.Dto;
import org.example.service.*;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

public class Encryption {

    public static void main(String[] args) {
        var keyStoreService = new KeyStoreService();
        var keyGeneratorService = new KeyGeneratorService(keyStoreService);
        var key = keyGeneratorService.generateKey();

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(Cipher.ENCRYPT_MODE, key.getPublic(), "Java".getBytes(StandardCharsets.UTF_8));

        var signatureService = new SignatureService();
        var signature = signatureService.signData(cipheredData, key.getPrivate());

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(cipheredData, key.getPublic(), signature));
    }
}
