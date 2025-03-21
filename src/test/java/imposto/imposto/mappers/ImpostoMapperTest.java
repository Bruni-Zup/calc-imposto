package imposto.imposto.mapper;

import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.model.Imposto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImpostoMapperTest {

    private final ImpostoMapper mapper = new ImpostoMapper();

    @Test
    void testToDTO() {
        Imposto imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre circulação");
        imposto.setAliquota(18.0);

        ImpostoDTO dto = mapper.toDTO(imposto);
        assertEquals(1L, dto.getId());
        assertEquals("ICMS", dto.getNome());
        assertEquals("Imposto sobre circulação", dto.getDescricao());
        assertEquals(18.0, dto.getAliquota());
    }

    @Test
    void testToEntity() {
        ImpostoDTO dto = new ImpostoDTO(1L, "ICMS", "Imposto sobre circulação", 18.0);
        Imposto imposto = mapper.toEntity(dto);
        assertEquals("ICMS", imposto.getNome());
        assertEquals("Imposto sobre circulação", imposto.getDescricao());
        assertEquals(18.0, imposto.getAliquota());
        // O ID não é mapeado para a entidade (gerado pelo banco), então esperamos null
        assertNull(imposto.getId());
    }
}
