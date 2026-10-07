package br.com.upa.estoque.dto.materiais;

import br.com.upa.estoque.domain.UnidadeMedida;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CadastroMaterialDTO(
        @NotBlank(message = "O nome é obrigatório no produto")
        @Size(max=150, message = "O nome dever ter até 150 caracteres")
        String nome,

        @NotBlank
        @Size(max=150)
        String modelo,

        @NotNull
        UnidadeMedida unidadeMedida,

        @NotNull
        @PositiveOrZero
        @Digits(integer = 10, fraction = 2)
        BigDecimal quantidade,

        @NotNull(message = "O estoque mínimo é obrigatório")
        @DecimalMin(value = "0.0", message = "O estoque mínimo não pode ser negativo")
        BigDecimal estoqueMinimo,

        @NotBlank
        String patrimonio

) {
}
