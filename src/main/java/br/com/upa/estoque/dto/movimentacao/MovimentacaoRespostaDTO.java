package br.com.upa.estoque.dto.movimentacao;

import br.com.upa.estoque.domain.MovimentacaoDosMateriais;
import br.com.upa.estoque.domain.TipoMovimentacao;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record MovimentacaoRespostaDTO(

        Long id,
        LocalDateTime dataHora,
        BigDecimal quantidade,
        String motivo,
        TipoMovimentacao tipoMovimentacao,
        Long materialId

) {
        public MovimentacaoRespostaDTO(MovimentacaoDosMateriais movimentacaoDosMateriais){
                this(
                        movimentacaoDosMateriais.getId(),
                        movimentacaoDosMateriais.getDataHora(),
                        movimentacaoDosMateriais.getQuantidade(),
                        movimentacaoDosMateriais.getMotivo(),
                        movimentacaoDosMateriais.getTipoMovimentacao(),
                        movimentacaoDosMateriais.getMaterial().getId()
                );
        }
}
