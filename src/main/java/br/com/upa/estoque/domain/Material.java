package br.com.upa.estoque.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal estoqueMinimo;
    private boolean ativo;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidade;
    private String patrimonio;

    public Material(MovimentacaoDosMateriais movimentacaoDosMateriaisSalva) {


    }
}
