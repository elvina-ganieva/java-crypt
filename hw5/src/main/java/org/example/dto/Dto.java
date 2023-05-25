package org.example.dto;

import java.io.Serializable;

public record Dto(byte[] cipheredData, String keyStoreFileName, String keyAlias) implements Serializable {
}
