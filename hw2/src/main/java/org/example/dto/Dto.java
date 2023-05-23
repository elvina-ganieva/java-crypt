package org.example.dto;

import java.io.Serializable;

public record Dto(byte[] cipheredData, byte[] dataDigest) implements Serializable {
}
