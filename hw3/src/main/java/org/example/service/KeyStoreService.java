package org.example.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreService {

    private static final String KEYSTORE_PASS = "KEYSTORE_PASS";
    private static final String CERTIFICATE_ALIAS = "selfsigned";
    private static final String KEYSTORE_FILE_NAME = "keystore.jks";
    private static final String KEY_ALIAS = "rsa-key";

    public PrivateKey getPrivateKey() {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        var keyStore = loadKeyStore();

        try {
            return (PrivateKey) keyStore.getKey(KEY_ALIAS, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new RuntimeException("Не удалось достать приватный ключ из KeyStore.", e);
        }
    }

    public void storePrivateKey(PrivateKey privateKey, String keyAlias) {
        var keyStore = loadKeyStore();
        storePrivateKey(keyStore, privateKey, keyAlias);
    }

    private KeyStore loadKeyStore() {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();

        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new RuntimeException("KeyStore не найден.", e);
        }

        try (var fis = new FileInputStream(KEYSTORE_FILE_NAME)) {
            keyStore.load(fis, password);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new RuntimeException("Не удалось загрузить KeyStore из файла: " + KEYSTORE_FILE_NAME, e);
        }
        return keyStore;
    }

    private void storePrivateKey(KeyStore keyStore, PrivateKey privateKey, String alias) {
        Certificate certificate;
        try {
            certificate = keyStore.getCertificate(CERTIFICATE_ALIAS);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Данный сертификат не найден: " + CERTIFICATE_ALIAS, e);
        }

        var entry = new KeyStore.PrivateKeyEntry(privateKey, new X509Certificate[]{(X509Certificate) certificate});
        var password = System.getenv(KEYSTORE_PASS).toCharArray();

        try {
            keyStore.setEntry(alias, entry, new KeyStore.PasswordProtection(password));
        } catch (KeyStoreException | NullPointerException e) {
            throw new RuntimeException("Не удалось добавить запись в KeyStore.", e);
        }

        try (var fos = new FileOutputStream(KEYSTORE_FILE_NAME)) {
            keyStore.store(fos, password);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось добавить запись в KeyStore.", e);
        }
    }
}
