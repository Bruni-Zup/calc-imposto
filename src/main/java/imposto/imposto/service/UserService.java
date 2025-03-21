package imposto.imposto.service;

import imposto.imposto.exception.BadRequestException;
import imposto.imposto.model.Role;
import imposto.imposto.model.User;
import imposto.imposto.repository.RoleRepository;
import imposto.imposto.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user, Set<String> roleNames) {
        logger.info("Registrando usuário: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BadRequestException("Username já existe");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName))))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        logger.info("Buscando usuário por username: {}", username);
        return userRepository.findByUsername(username);
    }
}
