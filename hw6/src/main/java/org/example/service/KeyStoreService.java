package org.example.service;

import org.example.enums.KeyStoreType;
import sun.security.tools.keytool.CertAndKeyGen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreService {

    public void createNewKeyStore(KeyStoreType keyStoreType, char[] password) {
        var keyStore = getKeyStore(keyStoreType.name());
        loadNewKeyStore(keyStore);
        storeKeyStore(keyStore, keyStoreType.getFileName(), password);
    }

    public PrivateKey getPrivateKey(String keyAlias, KeyStoreType keyStoreType, char[] password) {
        var keyStore = getKeyStore(keyStoreType.name());
        loadExistingKeyStore(keyStore, password, keyStoreType.getFileName());

        try {
            return (PrivateKey) keyStore.getKey(keyAlias, password);
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new RuntimeException("Не удалось достать приватный ключ из KeyStore по alias: " + keyAlias, e);
        }
    }

    public void storePrivateKey(CertAndKeyGen generator, String keyAlias, KeyStoreType keyStoreType, char[] password) {
        var keyStore = getKeyStore(keyStoreType.name());
        loadExistingKeyStore(keyStore, password, keyStoreType.getFileName());

        var certificateChain = getCertificateChain(generator);

        try {
            keyStore.setKeyEntry(keyAlias, generator.getPrivateKey(), password, certificateChain);
        } catch (KeyStoreException e) {
            throw new RuntimeException("Не удалось создать сделать запись KeyEntry.", e);
        }
        storeKeyStore(keyStore, keyStoreType.getFileName(), password);
    }

    private KeyStore getKeyStore(String keyStoreType) {
        try {
            return KeyStore.getInstance(keyStoreType);
        } catch (KeyStoreException e) {
            throw new RuntimeException("KeyStore не найден с данным типом: " + keyStoreType, e);
        }
    }

    private void loadNewKeyStore(KeyStore keyStore) {
        try {
            keyStore.load(null, null);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить пустой keyStore", e);
        }
    }

    private void loadExistingKeyStore(KeyStore keyStore, char[] password, String keyStoreFileName) {
        try (var fis = new FileInputStream(keyStoreFileName)) {
            keyStore.load(fis, password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException("Не удалось загрузить keyStore из " + keyStoreFileName, e);
        }
    }

    private void storeKeyStore(KeyStore keyStore, String keyStoreFileName, char[] password) {
        try (var fos = new FileOutputStream(keyStoreFileName)) {
            keyStore.store(fos, password);
        } catch (IOException | CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Не удалось сохранить keyStore в " + keyStoreFileName, e);
        }
    }

    private Certificate[] getCertificateChain(CertAndKeyGen generator) {
        X509Certificate x509Certificate;
        try {
            x509Certificate = generator.getSelfCertificate(new sun.security.x509.X500Name("CN=ROOT"), (long) 365 * 24 * 3600);
        } catch (CertificateException | InvalidKeyException | SignatureException | NoSuchAlgorithmException |
                 NoSuchProviderException | IOException e) {
            throw new RuntimeException("Не удалось выпустить самоподписанный сертификат.", e);
        }

        var chain = new X509Certificate[1];
        chain[0] = x509Certificate;
        return chain;
    }
}
