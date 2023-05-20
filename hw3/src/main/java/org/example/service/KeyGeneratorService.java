package org.example.service;

import java.security.*;

public class KeyGeneratorService {

    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;

    private final KeyStoreService keyStoreService;

    public KeyGeneratorService(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    public KeyPair generateKey() {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Данный алгоритм генерации ключей не найден: " + KEY_ALGORITHM, e);
        }
        generator.initialize(KEY_SIZE);

        var key = generator.generateKeyPair();
        keyStoreService.storePrivateKey(key.getPrivate());
        return key;
    }
}
