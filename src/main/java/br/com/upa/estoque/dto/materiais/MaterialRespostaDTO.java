package br.com.upa.estoque.dto.materiais;

import br.com.upa.estoque.domain.Material;
import br.com.upa.estoque.domain.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record MaterialRespostaDTO(
        Long id,
        String nome,
        String modelo,
        UnidadeMedida unidadeMedida,
        BigDecimal estoqueMinimo,
        boolean ativo,
        BigDecimal quantidade,
        String patrimonio

) {

    public MaterialRespostaDTO(Material material) {
        this(
                material.getId(),
                material.getNome(),
                material.getModelo(),
                material.getUnidadeMedida(),
                material.getEstoqueMinimo(),
                material.isAtivo(),
                material.getQuantidade(),
                material.getPatrimonio()
        );
    }
}
