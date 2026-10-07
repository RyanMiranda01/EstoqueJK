package br.com.upa.estoque.dto.movimentacao;

import br.com.upa.estoque.domain.TipoMovimentacao;
import java.math.BigDecimal;

import br.com.upa.estoque.dto.materiais.CadastroMaterialDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

public record MovimentacaDTO(
        @NotNull @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 10, fraction = 2)
        BigDecimal quantidade,
        @NotBlank
        String motivo,
        @NotNull
        TipoMovimentacao tipoMovimentacao,
        @NotNull Long materialId

) {

}
