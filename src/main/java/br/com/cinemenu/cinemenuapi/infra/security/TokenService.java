package br.com.cinemenu.cinemenuapi.infra.security;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.JWTCineMenuException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;

@Service
@Generated
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    private static final String ERROR_MESSAGE = "Error to generate JWT";
    private static final String INVALID_TOKEN = "Invalid token";
    private static final String API_ISSUER = "CineMenu API";

    public String generateToken(CineMenuUser cineMenuUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(API_ISSUER)
                    .withSubject(cineMenuUser.getUsername())
                    .withClaim("id", cineMenuUser.getId())
                    .withExpiresAt(expiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCineMenuException(ERROR_MESSAGE);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(API_ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new JWTCineMenuException(INVALID_TOKEN);
        }
    }

    Instant expiration() {
        return OffsetDateTime.now().plusDays(7).toInstant();
    }
}
