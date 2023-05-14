package org.example.dto;

import java.io.Serializable;
import java.security.PublicKey;

public record Dto(byte[] cipheredText, PublicKey publicKey, byte[] signature) implements Serializable {
}
