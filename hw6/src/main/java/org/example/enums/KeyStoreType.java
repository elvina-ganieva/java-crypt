package org.example.enums;

public enum KeyStoreType {
    JKS("keystore.jks"), JCEKS("keystore.jceks"), PKCS12("keystore.p12");

    private final String fileName;

    KeyStoreType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
