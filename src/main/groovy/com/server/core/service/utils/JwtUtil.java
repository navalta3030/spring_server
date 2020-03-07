package com.server.core.service.utils;

import com.server.core.model.Role;
import com.server.core.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtUtil {

    // encrypting password for jwt
    private String SECRET_KEY = "secret";

    // hour based
    private final Integer EXPIRATION = 6;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("ROLE_");
    }


    /**
     * Helper function to make dynamic Claims within the existing JWT token.
     * @param token - JWT token
     * @param claimsResolver - Claims object
     * @param <T> - What kind of transaction you want to perform? {@link Claims} for reference
     * @return Object based on the request of <T>
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all the existing claims
     * @param token - JWT token
     * @return decrypted jwt token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * After logging in, it generates a persistent token for the user.
     * @param user - {@link User}
     * @return the JWT token.
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("ROLE_", user.getRole().toString());
        return createToken(claims, user);
    }

    /**
     * Helper function for {@link #generateToken(User)} method
     * to modify expiration go to  {@link #EXPIRATION} variable
     */
    private String createToken(Map<String, Object> claims, User user) {

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * EXPIRATION)) // convert to hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Only verifies token expiration for now
     * @param token - JWT token
     */
    public Boolean validateToken(String token) {
        return extractExpiration(token).after(new Date());
    }
}
