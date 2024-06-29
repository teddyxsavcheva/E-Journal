package com.nbu.ejournalgroupproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "c8b34f63cca185f8d5d7c3b931ffaadd1f3b6aa4fc830bced04b91dd692d271d";

    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);

    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails) {

        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUserName(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);

    }



}
