package com.pagasi.lostfound.user_service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SecretKeyBuilder;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;

public class JwtSecretKeyMakerTest {
    @Test
    public void generateSecretKey() {
        SecretKey key = (SecretKey)((SecretKeyBuilder) Jwts.SIG.HS512.key()).build();
        String encodedKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.printf("%n key = [%s] %n", encodedKey);
    }
}
