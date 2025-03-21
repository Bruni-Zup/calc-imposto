package imposto.imposto.controller;

import static org.junit.jupiter.api.Assertions.*;

import imposto.imposto.model.Imposto;
import imposto.imposto.service.ImpostoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImpostoControllerTest {

    @InjectMocks
    private ImpostoController impostoController;

    @Mock
    private ImpostoService impostoService;

    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void testCriarImposto() {
        Imposto imposto = new Imposto();
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre Circulação de Mercadorias");
        imposto.setAliquota(18.0);

        when(impostoService.criarImposto(any(Imposto.class))).thenReturn(imposto);

        ResponseEntity<Imposto> response = restTemplate.postForEntity("/tipos", imposto, Imposto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testListarImpostos() {
        Imposto imposto = new Imposto();
        imposto.setNome("ICMS");
        imposto.setDescricao("Imposto sobre Circulação de Mercadorias");
        imposto.setAliquota(18.0);

        when(impostoService.listarTodos()).thenReturn(List.of(imposto));

        ResponseEntity<List> response = restTemplate.exchange("/tipos", HttpMethod.GET, null, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
}
