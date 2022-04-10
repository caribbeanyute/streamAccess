package me.cleon.streamaccess.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Date;

import static me.cleon.streamaccess.config.Constants.TOKEN_EXPIRATION;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    private final Long expiresAtAccessToken = 600L;

    private final String issuerString = "STREAMACCESS/PROJECT/CARIBBEANYUTE";

    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expiration = jwtVerifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        byte[] encodedByte = Base64.getEncoder().encode(secret.getBytes());
        long unixTime = System.currentTimeMillis() / 1000L;
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuerString)
                .withExpiresAt(
                        Date.from(
                                OffsetDateTime.now().plusSeconds(expiresAtAccessToken.intValue()).toInstant()))
                .sign(Algorithm.HMAC256(new String(encodedByte)));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        byte[] encodedByte = Base64.getEncoder().encode(secret.getBytes());
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(new String(encodedByte)))
                .withSubject("User Details")
                .withIssuer(issuerString)
                .acceptExpiresAt(expiresAtAccessToken)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

}