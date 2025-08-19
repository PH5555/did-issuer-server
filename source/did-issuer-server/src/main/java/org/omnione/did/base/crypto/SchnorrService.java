package org.omnione.did.base.crypto;

import com.zkrypto.signature.Schnorr;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SchnorrService {
    private String privateKey;
    private String publicKey;

    public SchnorrService() {
        System.load(System.getProperty("user.dir") + "/libs/libOpenDID_Hackathon.dylib");
    }

    public void generateKeys() {
        String[] keyPair = Schnorr.generateKeys();
        this.publicKey = keyPair[0];
        this.privateKey = keyPair[1];
    }
}
