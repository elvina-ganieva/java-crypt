package org.example.enums;

public enum KeyStoreType {
    JKS(".jks"), JCEKS(".jceks"), PKCS12(".p12");

    private final String fileExtension;

    KeyStoreType(String fileExtention) {
        this.fileExtension = fileExtention;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
