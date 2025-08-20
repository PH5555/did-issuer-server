package org.omnione.did.issuer.v1.agent.service;

import com.zkrypto.domain.KeyPair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KeyRedisService {
    private final RedisTemplate<String, KeyPair> redisTemplate;

    public void setKeyPair(String key, KeyPair keyPair) {
        redisTemplate.opsForValue().set(key, keyPair);
    }

    public Optional<KeyPair> getKeyPair(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

}