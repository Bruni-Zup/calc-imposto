package imposto.imposto.service;

import imposto.imposto.exception.BadRequestException;
import imposto.imposto.model.Role;
import imposto.imposto.model.User;
import imposto.imposto.repository.RoleRepository;
import imposto.imposto.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpassword");

        Set<String> roleNames = Set.of("ADMIN", "USER");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedPassword");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(new Role(1L, "ADMIN")));
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role(2L, "USER")));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = userService.registerUser(user, roleNames);
        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());
        assertEquals(2, registeredUser.getRoles().size());
        assertTrue(registeredUser.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));
        assertTrue(registeredUser.getRoles().stream().anyMatch(r -> r.getName().equals("USER")));
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpassword");

        when(userRepository.findByUsername("testuser")).thenReturn(new User());
        Set<String> roleNames = Set.of("ADMIN");

        assertThrows(BadRequestException.class, () -> userService.registerUser(user, roleNames));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }
}
