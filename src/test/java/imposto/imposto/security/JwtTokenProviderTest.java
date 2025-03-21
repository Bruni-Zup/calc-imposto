package imposto.imposto.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenProvider = new JwtTokenProvider();

        // Define a chave secreta e validade utilizando reflection
        Field secretField = jwtTokenProvider.getClass().getDeclaredField("secretKey");
        secretField.setAccessible(true);
        // A chave deve ter pelo menos 32 caracteres para HS256
        secretField.set(jwtTokenProvider, "testsecrettestsecrettestsecrettestsecr");

        Field validityField = jwtTokenProvider.getClass().getDeclaredField("validityInMilliseconds");
        validityField.setAccessible(true);
        validityField.set(jwtTokenProvider, 3600000L); // 1 hora

        // Invoca o método @PostConstruct para inicializar a chave
        jwtTokenProvider.init();
    }

    @Test
    void testCreateAndValidateToken() {
        String username = "testuser";
        List<String> roles = List.of("ADMIN");
        String token = jwtTokenProvider.createToken(username, roles);

        assertNotNull(token, "O token deve ser criado");
        assertTrue(jwtTokenProvider.validateToken(token), "O token gerado deve ser válido");

        Claims claims = jwtTokenProvider.getClaims(token);
        assertEquals(username, claims.getSubject(), "O campo 'sub' deve corresponder ao username");

        List<String> tokenRoles = claims.get("roles", List.class);
        assertNotNull(tokenRoles, "O token deve conter a claim 'roles'");
        assertTrue(tokenRoles.contains("ADMIN"), "O token deve conter a role ADMIN");
    }
}
