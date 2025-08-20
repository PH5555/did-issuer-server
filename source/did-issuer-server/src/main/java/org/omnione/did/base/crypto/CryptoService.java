package org.omnione.did.base.crypto;

import com.zkrypto.domain.KeyPair;
import com.zkrypto.signature.Schnorr;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class CryptoService {
    private final Map<String, KeyPair> keys = new HashMap<>();

    public CryptoService() {
        System.load(System.getProperty("user.dir") + "/libs/libOpenDID_Hackathon.dylib");
    }

    public void generateKey(String keyId) {
        String[] keyPair = Schnorr.generateKeys();
        keys.put(keyId, new KeyPair(keyPair[0], keyPair[1]));
    }
}
