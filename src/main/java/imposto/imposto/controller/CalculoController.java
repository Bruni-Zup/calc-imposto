package imposto.imposto.controller;

import imposto.imposto.dto.CalculoImpostoDTO;
import imposto.imposto.dto.CalculoImpostoRequestDTO;
import imposto.imposto.model.Imposto;
import imposto.imposto.service.ImpostoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculo")
public class CalculoController {

    private final ImpostoService impostoService;

    public CalculoController(ImpostoService impostoService) {
        this.impostoService = impostoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CalculoImpostoDTO calcularImposto(@RequestBody CalculoImpostoRequestDTO request) {
        Imposto imposto = impostoService.obterImpostoPorId(request.getTipoImpostoId());
        double valorImposto = impostoService.calcularImposto(request.getTipoImpostoId(), request.getValorBase());
        return new CalculoImpostoDTO(
                imposto.getNome(),
                request.getValorBase(),
                imposto.getAliquota(),
                valorImposto
        );
    }
}
