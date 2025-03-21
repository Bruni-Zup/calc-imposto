package imposto.imposto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculoImpostoRequestDTO {
    private Long tipoImpostoId;
    private Double valorBase;
}
