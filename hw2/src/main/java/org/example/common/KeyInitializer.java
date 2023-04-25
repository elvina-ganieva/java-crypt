package org.example.common;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

public class KeyInitializer {
    private static final String ALGORITHM = "AES";

    public SecretKey initializeKey() {
        var key = generateKey();
        saveKeyToStore(key);
        return key;
    }

    public Key getKey() {
        try {
            var keyStore = KeyStore.getInstance("JCEKS");
            var passwordAsCharArray = "password".toCharArray();
            keyStore.load(new FileInputStream("myKeyStore.jks"), passwordAsCharArray);
            return keyStore.getKey("my-key", passwordAsCharArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    private void saveKeyToStore(SecretKey key) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            var passwordAsCharArray = "password".toCharArray();
            keyStore.load(null, passwordAsCharArray);

            var secretKeyEntry = new KeyStore.SecretKeyEntry(key);
            var password = new KeyStore.PasswordProtection(passwordAsCharArray);
            keyStore.setEntry("my-key", secretKeyEntry, password);
            try (var fos = new FileOutputStream("myKeyStore.jks")) {
                keyStore.store(fos, passwordAsCharArray);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
