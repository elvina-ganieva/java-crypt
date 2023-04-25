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

    public Key retrieveExistingKey() {
        try {
            var keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(new FileInputStream(keyStoreFileName), new char[0]);
            return keyStore.getKey(keyAlias, new char[0]);
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
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);

            var secretKeyEntry = new KeyStore.SecretKeyEntry(key);
            keyStore.setEntry(keyAlias, secretKeyEntry, new KeyStore.PasswordProtection(new char[0]));

            try (var fos = new FileOutputStream(keyStoreFileName)) {
                keyStore.store(fos, new char[0]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
