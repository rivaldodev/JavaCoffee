package com.coffee.tracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key; private final long expirationMs;
    public JwtTokenProvider(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration}") long expirationMs){
        // HS256 needs >= 256 bits => 32 bytes. If secret not Base64 or too short, derive a proper key.
        byte[] keyBytes;
        try {
            if(secret.matches("^[A-Za-z0-9+/=]{43,}$")) { // looks like base64 of sufficient length
                keyBytes = Decoders.BASE64.decode(secret);
            } else {
                keyBytes = secret.getBytes();
            }
        } catch (Exception e){
            keyBytes = secret.getBytes();
        }
        if(keyBytes.length < 32){
            // pad/expand deterministically (simple) – for production prefer a proper generated key stored securely
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 32));
            for(int i=keyBytes.length;i<32;i++){ padded[i] = (byte)(i * 31); }
            keyBytes = padded;
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }
    public String generateToken(Long userId, String email){ Date now=new Date(); Date exp=new Date(now.getTime()+expirationMs); return Jwts.builder().setSubject(String.valueOf(userId)).claim("email", email).setIssuedAt(now).setExpiration(exp).signWith(key, SignatureAlgorithm.HS256).compact(); }
    public Long getUserId(String token){ return Long.valueOf(parse(token).getSubject()); }
    public boolean isValid(String token){ try { parse(token); return true;} catch(Exception e){ return false;} }
    private Claims parse(String token){ return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody(); }
}
