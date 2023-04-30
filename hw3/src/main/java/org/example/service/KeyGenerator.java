package org.example.service;

import java.security.*;

public class KeyGenerator {

    private static final String KEY_ALIAS = "rsa-key";
    private static final String KEY_ALGORITHM = "RSA";

    private final KeyStoreService keyStoreService;

    public KeyGenerator(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    public KeyPair generateKey() {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Данный алгоритм генерации ключей не найден: " + KEY_ALGORITHM, e);
        }
        generator.initialize(2048);

        var key = generator.generateKeyPair();
        keyStoreService.storePrivateKey(key.getPrivate(), KEY_ALIAS);
        return key;
    }
}
