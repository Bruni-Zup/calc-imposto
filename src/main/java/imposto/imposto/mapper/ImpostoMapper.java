package imposto.imposto.mapper;

import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.model.Imposto;
import org.springframework.stereotype.Component;

@Component
public class ImpostoMapper {

    public ImpostoDTO toDTO(Imposto imposto) {
        return new ImpostoDTO(
                imposto.getId(),
                imposto.getNome(),
                imposto.getDescricao(),
                imposto.getAliquota()
        );
    }

    public Imposto toEntity(ImpostoDTO dto) {
        Imposto imposto = new Imposto();
        imposto.setNome(dto.getNome());
        imposto.setDescricao(dto.getDescricao());
        imposto.setAliquota(dto.getAliquota());
        return imposto;
    }
}
