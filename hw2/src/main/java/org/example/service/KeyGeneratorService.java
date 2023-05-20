package org.example.service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;

public class KeyGeneratorService {

    private final KeyStoreService keyStoreService;
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;

    public KeyGeneratorService(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    public SecretKey initializeKey() {
        var key = generateKey();
        keyStoreService.saveKeyToStore(key);
        return key;
    }

    private SecretKey generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось получить генератор ключей для алгоритма: " + ALGORITHM, e);
        }
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }
}
