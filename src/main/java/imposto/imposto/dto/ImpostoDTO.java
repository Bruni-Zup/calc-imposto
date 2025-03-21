package imposto.imposto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImpostoDTO {
    private Long id;

    @NotBlank(message = "O nome do imposto é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição do imposto é obrigatória")
    private String descricao;

    @NotNull(message = "A alíquota é obrigatória")
    @PositiveOrZero(message = "A alíquota deve ser zero ou positiva")
    private Double aliquota;
}
