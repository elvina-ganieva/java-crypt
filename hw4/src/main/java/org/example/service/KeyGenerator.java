package org.example.service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class KeyGenerator {

    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private final KeyStoreService keyStoreService;

    public KeyGenerator(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    public SecretKey generateKey(String password) {
        var salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        var keySpec = new PBEKeySpec(password.toCharArray(), salt, Short.MAX_VALUE, KEY_SIZE);
        var keyFactory = getSecretKeyFactory();
        var secretKey = getSecretKey(keyFactory, keySpec);
        keyStoreService.storeSecretKey(secretKey);
        return secretKey;
    }

    private SecretKeyFactory getSecretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            throw new RuntimeException("Данный алгоритм генерации ключей не найден: " + KEY_ALGORITHM, e);
        }
    }

    private SecretKey getSecretKey(SecretKeyFactory keyFactory, PBEKeySpec keySpec) {
        SecretKey secretKey;
        try {
            secretKey = new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), ALGORITHM);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Невозможно создать ключ по данной спецификации.", e);
        }
        keySpec.clearPassword();
        return secretKey;
    }
}
