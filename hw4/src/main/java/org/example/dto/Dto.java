package org.example.dto;

import java.io.Serializable;

public record Dto(byte[] cipheredText) implements Serializable {
}
