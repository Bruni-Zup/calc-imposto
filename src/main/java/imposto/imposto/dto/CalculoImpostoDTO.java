package imposto.imposto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculoImpostoDTO {
    private String tipoImposto;
    private Double valorBase;
    private Double aliquota;
    private Double valorImposto;
}
