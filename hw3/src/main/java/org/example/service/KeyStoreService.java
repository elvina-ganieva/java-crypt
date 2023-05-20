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
        var keyStore = getKeyStoreInstance();
        loadExistingKeyStore(keyStore);

        try {
            return (PrivateKey) keyStore.getKey(KEY_ALIAS, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new RuntimeException("Не удалось достать приватный ключ из KeyStore по alias: " + KEY_ALIAS, e);
        }
    }

    public void storePrivateKey(PrivateKey privateKey) {
        var keyStore = getKeyStoreInstance();
        loadExistingKeyStore(keyStore);

        var certificateChain = getCertificateChain(keyStore);
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        var entry = new KeyStore.PrivateKeyEntry(privateKey, certificateChain);

        try {
            keyStore.setEntry(KEY_ALIAS, entry, new KeyStore.PasswordProtection(password));
        } catch (KeyStoreException | NullPointerException e) {
            throw new RuntimeException("Не удалось добавить запись в KeyStore.", e);
        }
        storeKeyStoreInFile(keyStore);
    }

    private KeyStore getKeyStoreInstance() {
        try {
            return KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new RuntimeException("Не удалось создать экземпляр KeyStore с дефолтным типом: " + KeyStore.getDefaultType(), e);
        }
    }

    private void loadExistingKeyStore(KeyStore keyStore) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        try (var fis = new FileInputStream(KEYSTORE_FILE_NAME)) {
            keyStore.load(fis, password);
        } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось загрузить KeyStore из файла: " + KEYSTORE_FILE_NAME, e);
        }
    }

    private void storeKeyStoreInFile(KeyStore keyStore) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        try (var fos = new FileOutputStream(KEYSTORE_FILE_NAME)) {
            keyStore.store(fos, password);
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось сохранить KeyStore в файл: " + KEYSTORE_FILE_NAME, e);
        }
    }

    private X509Certificate[] getCertificateChain(KeyStore keyStore) {
        Certificate certificate;
        try {
            certificate = keyStore.getCertificate(CERTIFICATE_ALIAS);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Данный сертификат не найден: " + CERTIFICATE_ALIAS, e);
        }
        return new X509Certificate[]{(X509Certificate) certificate};
    }
}
