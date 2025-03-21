package imposto.imposto.config;

import imposto.imposto.security.JwtTokenProvider;
import imposto.imposto.service.UserService;
import imposto.imposto.mapper.UserMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
public class UserControllerTestConfig {

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return Mockito.mock(JwtTokenProvider.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return Mockito.mock(BCryptPasswordEncoder.class);
    }

    // Adicionando o UserMapper como bean real
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
