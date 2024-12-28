package com.easylocate.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for generating, parsing, and validating JSON Web Tokens (JWT).
 */
@Service
public class JWTService {

    public static final long TOKEN_VALIDITY = 10 * 60 * 60;

    Map<String, Object> claims = new HashMap<>();

    private String secretKey = "";

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey generatedKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a JWT for the given username.
     *
     * @param username the username for which the token is generated
     * @return a signed JWT as a String
     */
    public String generateJWToken(String username) {
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .and()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extracts the username from a given JWT.
     *
     * @param token the JWT from which the username is extracted
     * @return the username contained in the token
     */
    public String extractUsernameFromJWToken(String token) {
        return extractClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Validates the JWT against the given user details.
     *
     * @param token       the JWT to validate
     * @param userDetails the user details to compare against
     * @return true if the token is valid and matches the user details, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsernameFromJWToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT is expired.
     *
     * @param token the JWT to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpirationFromToken(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a given JWT.
     *
     * @param token the JWT from which the expiration date is extracted
     * @return the expiration date of the token
     */
    private Date extractExpirationFromToken(String token) {
        return extractClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT.
     *
     * @param token          the JWT from which the claim is extracted
     * @param claimsTFunction a function that specifies which claim to extract
     * @param <T>            the type of the claim to extract
     * @return the extracted claim
     */
    private <T> T extractClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        Claims allClaimsFromToken = extractAllClaimsFromToken(token);
        return claimsTFunction.apply(allClaimsFromToken);
    }

    /**
     * Extracts all claims from a JWT.
     *
     * @param token the JWT from which the claims are extracted
     * @return the claims contained in the token
     */
    private Claims extractAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
