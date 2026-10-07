package br.com.upa.estoque.dto.movimentacao;

import br.com.upa.estoque.domain.TipoMovimentacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovimentacoAtualizarDTO(
        @NotBlank
        String motivo
) {
}
