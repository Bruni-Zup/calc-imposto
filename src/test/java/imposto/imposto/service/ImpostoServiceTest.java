package imposto.imposto.service;

import imposto.imposto.exception.ResourceNotFoundException;
import imposto.imposto.model.Imposto;
import imposto.imposto.repository.ImpostoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImpostoServiceTest {

    private ImpostoRepository impostoRepository;
    private ImpostoService impostoService;

    @BeforeEach
    void setUp() {
        impostoRepository = mock(ImpostoRepository.class);
        impostoService = new ImpostoService(impostoRepository);
    }

    @Test
    void testListarTodos() {
        Imposto imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre circulação");
        imposto.setAliquota(18.0);

        when(impostoRepository.findAll()).thenReturn(List.of(imposto));
        List<Imposto> impostos = impostoService.listarTodos();

        assertNotNull(impostos);
        assertEquals(1, impostos.size());
        assertEquals("ICMS", impostos.get(0).getNome());
    }

    @Test
    void testObterImpostoPorIdSuccess() {
        Imposto imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre circulação");
        imposto.setAliquota(18.0);

        when(impostoRepository.findById(1L)).thenReturn(Optional.of(imposto));
        Imposto found = impostoService.obterImpostoPorId(1L);

        assertNotNull(found);
        assertEquals("ICMS", found.getNome());
    }

    @Test
    void testObterImpostoPorIdNotFound() {
        when(impostoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> impostoService.obterImpostoPorId(1L));
    }

    @Test
    void testCriarImposto() {
        Imposto imposto = new Imposto();
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre circulação");
        imposto.setAliquota(18.0);

        when(impostoRepository.save(any(Imposto.class))).thenAnswer(invocation -> {
            Imposto saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Imposto criado = impostoService.criarImposto(imposto);
        assertNotNull(criado.getId());
        assertEquals("ICMS", criado.getNome());
    }

    @Test
    void testExcluirImpostoSuccess() {
        when(impostoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(impostoRepository).deleteById(1L);

        assertDoesNotThrow(() -> impostoService.excluirImposto(1L));
        verify(impostoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExcluirImpostoNotFound() {
        when(impostoRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> impostoService.excluirImposto(1L));
        verify(impostoRepository, never()).deleteById(1L);
    }

    @Test
    void testCalcularImposto() {
        Imposto imposto = new Imposto();
        imposto.setId(1L);
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre circulação");
        imposto.setAliquota(18.0);

        when(impostoRepository.findById(1L)).thenReturn(Optional.of(imposto));
        double calculado = impostoService.calcularImposto(1L, 100.0);
        assertEquals(18.0, calculado);
    }
}
