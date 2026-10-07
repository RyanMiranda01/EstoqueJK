package br.com.upa.estoque.domain;


import br.com.upa.estoque.dto.movimentacao.MovimentacaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movimentacoes")
public class MovimentacaoDosMateriais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "tipo")
    private TipoMovimentacao tipoMovimentacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidade;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    @Column(nullable = false, length = 255)
    private String motivo;


    public MovimentacaoDosMateriais(Material material, BigDecimal quantidade, TipoMovimentacao tipoMovimentacao, String motivo) {
        this.material = material;
        this.quantidade = quantidade;
        this.tipoMovimentacao = tipoMovimentacao;
        this.motivo = motivo;

    }

    public MovimentacaoDosMateriais(MovimentacaDTO movimentacaDTO,  Material materialSalvo) {
        this.tipoMovimentacao = movimentacaDTO.tipoMovimentacao();
        this.quantidade = movimentacaDTO.quantidade();
        this.material = materialSalvo;
        this.dataHora = LocalDateTime.now();
        this.motivo = movimentacaDTO.motivo();
    }

}
