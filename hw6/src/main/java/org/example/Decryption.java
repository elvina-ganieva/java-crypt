package org.example;

import org.example.service.CipherService;
import org.example.service.DataTransferService;
import org.example.service.KeyStoreService;
import org.example.service.SignatureService;

import java.nio.charset.StandardCharsets;

public class Decryption {

    public static void main(String[] args) {
        var dataTransferService = new DataTransferService();
        var dto = dataTransferService.readObject();

        var keyStoreType = dto.keyStoreType();
        var password = dto.password();
        var cipheredData = dto.cipheredData();
        var digitalSignature = dto.signature();
        var privateKeyAlias = dto.privateKeyAlias();
        var publicKey = dto.publicKey();

        var signatureService = new SignatureService();
        var isCorrect = signatureService.verifySignature(digitalSignature, cipheredData, publicKey);
        if (!isCorrect) {
            System.out.println("Подпись неверная.");
            return;
        }

        var keyStoreService = new KeyStoreService();
        var privateKey = keyStoreService.getPrivateKey(privateKeyAlias, keyStoreType, password);

        var cipherService = new CipherService();
        var decipheredData = cipherService.decipher(cipheredData, privateKey);

        System.out.println("Зашифрованное слово: " + new String(decipheredData, StandardCharsets.UTF_8));
    }
}
