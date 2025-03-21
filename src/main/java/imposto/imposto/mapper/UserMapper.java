package imposto.imposto.mapper;

import imposto.imposto.dto.UserRegistrationDTO;
import imposto.imposto.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRegistrationDTO dto) {
        // As roles serão atribuídas na camada de serviço
        return new User(dto.getUsername(), dto.getPassword(), null);
    }
}
