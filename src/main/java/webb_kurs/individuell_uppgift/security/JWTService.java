package webb_kurs.individuell_uppgift.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

//Original comments have been kept for easier comprehension

// Service för att hantera JWT (JSON Web Tokens)
// JWT är tokens som används för att identifiera inloggade användare
@Service
public class JWTService {

    // Algoritmen som används för att signera tokens (HMAC256 med en hemlig nyckel)
    //The key is environmental variable
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JWTService(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).withIssuer("individuell_uppgift").build();
    }

    // Skapar en ny JWT-token för en användare
    public String generateToken(UUID userId) {
        return JWT.create()
                .withIssuer("individuell_uppgift")                                           // Vem som skapat token
                .withIssuedAt(Instant.now())                                     // När token skapades
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))       // Token gäller i 30 minuter
                .withSubject(userId.toString())                                  // Användarens ID
                .sign(algorithm);                                                // Signera med vår hemliga nyckel
    }

    // Validerar en JWT-token och returnerar användarens ID
    // Kastar ett exception om token är ogiltig eller utgången
    public UUID validateToken(String token) {
        // Verifiera att token är giltig och inte manipulerad
        DecodedJWT jwt = verifier.verify(token);
        // Hämta och returnera användarens ID från token
        return UUID.fromString(jwt.getSubject());
    }
}
