package imposto.imposto.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Removidos os espaços em branco no placeholder
    @Value("${jwt.secret}")
    private String secretKey;

    // Alinhado com a chave configurada em application.properties (jwt.expiration)
    @Value("${jwt.expiration:3600000}")
    private long validityInMilliseconds;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        logger.info("Chave JWT inicializada com sucesso.");
    }

    public String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        // Log de debug
        long issuedAtSeconds = now.getTime() / 1000;
        long expirySeconds = expiry.getTime() / 1000;
        logger.debug("Criando token para '{}'. IssuedAt (iat): {} exp: {} (validity: {} ms)",
                username, issuedAtSeconds, expirySeconds, validityInMilliseconds);

        // Verifica se o cálculo da validade está correto
        if (expiry.getTime() - now.getTime() != validityInMilliseconds) {
            logger.warn("A diferença entre exp e iat ({} ms) não corresponde à validade configurada ({} ms)",
                    expiry.getTime() - now.getTime(), validityInMilliseconds);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Token inválido ou expirado: {}", e.getMessage());
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
