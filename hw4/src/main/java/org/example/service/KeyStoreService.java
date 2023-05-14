package org.example.service;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class KeyStoreService {

    private static final String KEYSTORE_PASS = "KEYSTORE_PASS";
    private static final String KEYSTORE_FILE_NAME = "keystore.jks";
    private static final String KEY_ALIAS = "pbe-key";
    private static final String keyStoreType = "JCEKS";

    public void storeSecretKey(SecretKey secretKey) {
        var keyStore = getKeyStore();
        loadKeyStore(keyStore);
        var entry = new KeyStore.SecretKeyEntry(secretKey);
        var password = new KeyStore.PasswordProtection(System.getenv(KEYSTORE_PASS).toCharArray());
        try {
            keyStore.setEntry(KEY_ALIAS, entry, password);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        storeKeyStore(keyStore);
    }

    public SecretKey getSecretKey() {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        var keyStore = getKeyStore();
        loadKeyStore(keyStore);
        try {
            return (SecretKey) keyStore.getKey(KEY_ALIAS, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new RuntimeException("Не удалось достать секретный ключ из KeyStore.", e);
        }
    }

    private KeyStore getKeyStore() {
        try {
            return KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            throw new RuntimeException("KeyStore не найден.", e);
        }
    }

    private void loadKeyStore(KeyStore keyStore) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        try (var fis = new FileInputStream(KEYSTORE_FILE_NAME)) {
            keyStore.load(fis, password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить keyStore из " + KEYSTORE_FILE_NAME, e);
        }
    }

    private void storeKeyStore(KeyStore keyStore) {
        var password = new KeyStore.PasswordProtection(System.getenv(KEYSTORE_PASS).toCharArray());
        try (var fos = new FileOutputStream(KEYSTORE_FILE_NAME)) {
            keyStore.store(fos, password.getPassword());
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось сохранить keyStore в " + KEYSTORE_FILE_NAME, e);
        }
    }
}
