package imposto.imposto.controller;

import imposto.imposto.dto.AuthResponseDTO;
import imposto.imposto.dto.UserRegistrationDTO;
import imposto.imposto.exception.InvalidCredentialsException;
import imposto.imposto.mapper.UserMapper;
import imposto.imposto.model.User;
import imposto.imposto.security.JwtTokenProvider;
import imposto.imposto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Valid UserRegistrationDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userService.registerUser(user, userDTO.getRoles());
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser == null || !passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new InvalidCredentialsException("Usuário ou senha incorretos");
        }
        if (foundUser.getRoles() == null || foundUser.getRoles().isEmpty()) {
            throw new InvalidCredentialsException("Roles do usuário não definidas");
        }
        String token = jwtTokenProvider.createToken(foundUser.getUsername(),
                foundUser.getRoles().stream().map(role -> role.getName()).toList());
        return new AuthResponseDTO(token);
    }
}
