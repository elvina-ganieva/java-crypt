package org.example.service;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class KeyStoreService {

    private static final String KEY_STORE_FILE_NAME = "myKeyStore.jks";
    private static final String KEY_ALIAS = "my-key";
    private static final String KEY_STORE_TYPE = "JCEKS";
    private static final String KEY_STORE_PASS_ENV = "KEYSTORE_PASS";

    public Key getKeyFromStore() {
        var password = System.getenv(KEY_STORE_PASS_ENV).toCharArray();
        var keyStore = getKeyStoreInstance();
        loadExistingKeyStore(keyStore);
        try {
            return keyStore.getKey(KEY_ALIAS, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RuntimeException("Не удалось достать ключ по alias: " + KEY_ALIAS, e);
        }
    }

    public void saveKeyToStore(SecretKey key) {
        var password = System.getenv(KEY_STORE_PASS_ENV).toCharArray();
        var keyStore = getKeyStoreInstance();
        loadNewKeyStore(keyStore);

        var secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        try {
            keyStore.setEntry(KEY_ALIAS, secretKeyEntry, new KeyStore.PasswordProtection(password));
        } catch (KeyStoreException e) {
            throw new RuntimeException("Не удалось установить Entry в KeyStore с alias: " + KEY_ALIAS, e);
        }

        storeKeyStoreInFile(keyStore);
    }

    private KeyStore getKeyStoreInstance() {
        try {
            return KeyStore.getInstance(KEY_STORE_TYPE);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Не удалось создать экземпляр KeyStore с типом: " + KEY_STORE_TYPE, e);
        }
    }

    private void loadNewKeyStore(KeyStore keyStore) {
        var password = System.getenv(KEY_STORE_PASS_ENV).toCharArray();
        try {
            keyStore.load(null, password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить пустой KeyStore.", e);
        }
    }

    private void loadExistingKeyStore(KeyStore keyStore) {
        var password = System.getenv(KEY_STORE_PASS_ENV).toCharArray();
        try (var fis = new FileInputStream(KEY_STORE_FILE_NAME)) {
            keyStore.load(fis, password);
        } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось загрузить KeyStore из файла: " + KEY_STORE_FILE_NAME, e);
        }
    }

    private void storeKeyStoreInFile(KeyStore keyStore) {
        var password = System.getenv(KEY_STORE_PASS_ENV).toCharArray();
        try (var fos = new FileOutputStream(KEY_STORE_FILE_NAME)) {
            keyStore.store(fos, password);
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось сохранить KeyStore в файл: " + KEY_STORE_FILE_NAME, e);
        }
    }
}
