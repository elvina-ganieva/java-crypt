package org.example.common;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

public class KeyHandler {
    private static final String keyStoreFileName = "myKeyStore.jks";
    private static final String keyAlias = "my-key";
    private static final String keyStoreType = "JCEKS";
    private static final String keyStorePassEnv = "KEYSTORE_PASS";

    public Key getKey() {
        try {
            var password = System.getenv(keyStorePassEnv).toCharArray();

            var keyStore = KeyStore.getInstance(keyStoreType);
            try (var fis = new FileInputStream(keyStoreFileName)) {
                keyStore.load(fis, password);
            }
            return keyStore.getKey(keyAlias, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey initializeKey() {
        var key = generateKey();
        saveKeyToStore(key);
        return key;
    }


    private SecretKey generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private void saveKeyToStore(SecretKey key) {
        try {
            var password = System.getenv(keyStorePassEnv).toCharArray();

            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            try (var fis = new FileInputStream(keyStoreFileName)) {
                keyStore.load(fis, password);
            }

            var secretKeyEntry = new KeyStore.SecretKeyEntry(key);
            keyStore.setEntry(
                    keyAlias,
                    secretKeyEntry,
                    new KeyStore.PasswordProtection(password)
            );

            try (var fos = new FileOutputStream(keyStoreFileName)) {
                keyStore.store(fos, password);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
