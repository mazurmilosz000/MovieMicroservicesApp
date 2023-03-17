package com.milosz000.model.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {


    @Value(value = "${SECRET_KEY}")
    private String SECRET_KEY;

    private final int JWT_EXP_TIME = 5 * 60 * 60;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_EXP_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    // method to generate token without extra claims
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));

    }

    // for retrieving any information from token I need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    private boolean isTokenExpired(String token) {
        final Date expirationDateFromToken = getExpirationDateFromToken(token);
        // check if expiration date is before current date
        return expirationDateFromToken.before(new Date(System.currentTimeMillis()));
    }

    private String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
}
