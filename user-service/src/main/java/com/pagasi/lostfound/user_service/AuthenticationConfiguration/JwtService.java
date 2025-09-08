package com.pagasi.lostfound.user_service.AuthenticationConfiguration;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.pagasi.lostfound.user_service.customization.CustomUserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String SECRET = "10A71F467C5EF9AC56654662F99CADE199B43DB8E5EE782264E172E228CA67F4B8578A2B36FF7A70F00082E270DB500272587A63CC30E0A1C42F2902C3E7965B";
    private static final Long VALIDITY;

    public String generateToken(CustomUserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap();
        claims.put("iss", "pagasicrop.com");
        claims.put("id", userDetails.getId());
        claims.put("name", userDetails.getName());
        claims.put("username",userDetails.getUniqueName());
        return Jwts.builder().claims(claims).subject(userDetails.getUsername()).issuedAt(Date.from(Instant.now())).expiration(Date.from(Instant.now().plusMillis(VALIDITY))).signWith(this.generateKey()).compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode("10A71F467C5EF9AC56654662F99CADE199B43DB8E5EE782264E172E228CA67F4B8578A2B36FF7A70F00082E270DB500272587A63CC30E0A1C42F2902C3E7965B");
        return Keys.hmacShaKeyFor(decodedKey);
    }

    static {
        VALIDITY = TimeUnit.MINUTES.toMillis(30L);
    }
}
