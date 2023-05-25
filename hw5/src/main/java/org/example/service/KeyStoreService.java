package org.example.service;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreService {

    private static final String KEYSTORE_PASS = "KEYSTORE_PASS";
    private static final String KEYSTORE_TYPE = "JKS";

    public void storePrivateKey(String fileName, CertAndKeyGen generator, String keyAlias) {
        var certificateChain = getCertificateChain(generator);
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        var keyStore = getKeyStore();
        loadNewKeyStore(keyStore);

        try {
            keyStore.setKeyEntry(keyAlias, generator.getPrivateKey(), password, certificateChain);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Не удалось создать сделать запись KeyEntry.", e);
        }
        storeKeyStore(keyStore, fileName);
    }

    public Key getPrivateKey(String fileName, String keyAlias) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        var keyStore = getKeyStore();
        loadKeyStoreByFileName(keyStore, fileName);

        try {
            return keyStore.getKey(keyAlias, password);
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    private KeyStore getKeyStore() {
        try {
            return KeyStore.getInstance(KEYSTORE_TYPE);
        } catch (KeyStoreException e) {
            throw new RuntimeException("KeyStore данного типа не найден: " + KEYSTORE_TYPE, e);
        }
    }

    private void loadNewKeyStore(KeyStore keyStore) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        try {
            keyStore.load(null, password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить keyStore.", e);
        }
    }

    private void loadKeyStoreByFileName(KeyStore keyStore, String fileName) {
        var password = System.getenv(KEYSTORE_PASS).toCharArray();
        try (var fis = new FileInputStream(fileName)) {
            keyStore.load(fis, password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить keyStore из файла: " + fileName, e);
        }
    }

    private void storeKeyStore(KeyStore keyStore, String fileName) {
        var password = new KeyStore.PasswordProtection(System.getenv(KEYSTORE_PASS).toCharArray());
        try (var fos = new FileOutputStream(fileName)) {
            keyStore.store(fos, password.getPassword());
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось сохранить keyStore в " + fileName, e);
        }
    }

    private X509Certificate[] getCertificateChain(CertAndKeyGen generator) {
        X509Certificate x509Certificate;
        try {
            x509Certificate = generator.getSelfCertificate(new X500Name("CN=ROOT"), (long) 365 * 24 * 3600);
        } catch (CertificateException | InvalidKeyException | SignatureException | NoSuchAlgorithmException |
                 NoSuchProviderException | IOException e) {
            throw new RuntimeException("Не удалось выпустить самоподписанный сертификат.", e);
        }

        var chain = new X509Certificate[1];
        chain[0] = x509Certificate;
        return chain;
    }
}
