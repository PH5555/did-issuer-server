package org.omnione.did.base.crypto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zkrypto.domain.IssueType;
import com.zkrypto.domain.KeyPair;
import com.zkrypto.signature.Schnorr;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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

    public String sign(String keyId, String data, String issueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        KeyPair keyPair = keys.get(keyId);

        String signature = "";
        if(issueType.equals(IssueType.EDUCATION.name())) {
            signature = Schnorr.generateGraduationCredential(
                    keyPair.getPrivateKey(),
                    jsonNode.get("ci").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("univ").asText(),
                    jsonNode.get("univType").asText(),
                    jsonNode.get("maj").asText(),
                    jsonNode.get("degree").asText(),
                    jsonNode.get("registerNumber").asText());
        }
        else if(issueType.equals(IssueType.LICENSE .name())) {
            signature = Schnorr.generateLicenseCredential(
                    keyPair.getPrivateKey(),
                    jsonNode.get("ci").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("pid").asText(),
                    jsonNode.get("license").asText(),
                    jsonNode.get("expired").asText());
        }
        else if(issueType.equals(IssueType.EMPLOYMENT .name())) {
            signature = Schnorr.generateEmploymentCredential(
                    keyPair.getPrivateKey(),
                    jsonNode.get("ci").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("startDate").asText(),
                    jsonNode.get("expDate").asText(),
                    jsonNode.get("company").asText(),
                    jsonNode.get("department").asText(),
                    jsonNode.get("position").asText());
        }

        return signature;
    }
}
