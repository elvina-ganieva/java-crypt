package org.example.dto;

import org.example.enums.KeyStoreType;

import java.io.Serializable;
import java.security.PublicKey;

public record Dto(
        byte[] cipheredData,
        String keyStoreType,
        String keyStoreFileName,
        char[] password,
        PublicKey publicKey,
        String privateKeyAlias,
        byte[] signature
) implements Serializable {
}
