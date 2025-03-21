package imposto.imposto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import imposto.imposto.config.UserControllerTestConfig;
import imposto.imposto.mapper.UserMapper;
import imposto.imposto.security.JwtTokenProvider;
import imposto.imposto.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserController.class)
@Import(UserControllerTestConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Estes já são gerados via UserControllerTestConfig,
    // mas se precisar adicionalmente pode mockar aqui também
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Mock do UserMapper, que não estava sendo injetado:
    @MockBean
    private UserMapper userMapper;

    @Test
    public void testRegisterUser() throws Exception {
        // ...
    }

    @Test
    public void testLogin() throws Exception {
        // ...
    }
}
