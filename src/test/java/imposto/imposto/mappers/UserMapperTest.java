package imposto.imposto.mapper;

import imposto.imposto.dto.UserRegistrationDTO;
import imposto.imposto.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testToEntity() {
        UserRegistrationDTO dto = new UserRegistrationDTO("testuser", "password", Set.of("ADMIN"));
        User user = userMapper.toEntity(dto);
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        // Como as roles serão setadas no serviço, o mapper não as atribui (esperado null)
        assertNull(user.getRoles());
    }
}
