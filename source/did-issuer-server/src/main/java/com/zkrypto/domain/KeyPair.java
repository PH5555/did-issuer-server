package com.zkrypto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyPair {
    private String privateKey;
    private String publicKey;
}
