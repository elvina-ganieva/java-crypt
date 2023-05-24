package org.example;

import org.example.dto.Dto;
import org.example.service.*;

import java.nio.charset.StandardCharsets;

public class Encryption {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Необходимо передать 3 параметра: режим (basic/secure), пароль и слово для шифрования.");
            return;
        }
        var randomMode = args[0];
        var password = args[1].toCharArray();
        var data = args[2].getBytes(StandardCharsets.UTF_8);

        var randomService = new RandomService();
        var keyStoreType = randomService.getRandomKeyStoreType(randomMode);

        var keyStoreService = new KeyStoreService();
        keyStoreService.createNewKeyStore(keyStoreType, password);

        var keyAlias = "rsa-key";
        var keyGeneratorService = new KeyGeneratorService();
        var generator = keyGeneratorService.getCertAndKeyGenerator();
        keyStoreService.storePrivateKey(generator, keyAlias, keyStoreType, password);

        var cipherService = new CipherService();
        var cipheredData = cipherService.cipher(data, generator.getPublicKey());

        var signatureService = new SignatureService();
        var digitalSignature = signatureService.signData(cipheredData, generator.getPrivateKey());

        System.out.printf("Тип keystore: %s\nИмя ключа: %s\nЗашифрованное слово: %s\nПодпись: %s\n",
                keyStoreType.name(),
                keyAlias,
                new String(cipheredData, StandardCharsets.UTF_8),
                new String(digitalSignature, StandardCharsets.UTF_8));

        var dataTransferService = new DataTransferService();
        dataTransferService.writeObject(new Dto(
                cipheredData,
                keyStoreType,
                password,
                generator.getPublicKey(),
                keyAlias,
                digitalSignature
        ));
    }
}
