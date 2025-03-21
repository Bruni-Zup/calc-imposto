package imposto.imposto.controller;

import imposto.imposto.dto.ImpostoDTO;
import imposto.imposto.mapper.ImpostoMapper;
import imposto.imposto.model.Imposto;
import imposto.imposto.service.ImpostoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipos")
public class ImpostoController {

    private final ImpostoService impostoService;
    private final ImpostoMapper impostoMapper;

    public ImpostoController(ImpostoService impostoService, ImpostoMapper impostoMapper) {
        this.impostoService = impostoService;
        this.impostoMapper = impostoMapper;
    }

    @GetMapping
    public List<ImpostoDTO> listarImpostos() {
        List<Imposto> impostos = impostoService.listarTodos();
        return impostos.stream()
                .map(impostoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ImpostoDTO obterImpostoPorId(@PathVariable Long id) {
        Imposto imposto = impostoService.obterImpostoPorId(id);
        return impostoMapper.toDTO(imposto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImpostoDTO criarImposto(@RequestBody @Valid ImpostoDTO impostoDTO) {
        Imposto imposto = impostoMapper.toEntity(impostoDTO);
        Imposto criado = impostoService.criarImposto(imposto);
        return impostoMapper.toDTO(criado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirImposto(@PathVariable Long id) {
        impostoService.excluirImposto(id);
    }
}
