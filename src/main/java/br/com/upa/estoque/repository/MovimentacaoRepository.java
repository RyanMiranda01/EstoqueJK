package br.com.upa.estoque.repository;

import br.com.upa.estoque.domain.MovimentacaoDosMateriais;
import br.com.upa.estoque.domain.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoDosMateriais, Long> {

    List<MovimentacaoDosMateriais> findByMaterial_IdOrderByDataHoraDescIdDesc(Long materialId);

    List<MovimentacaoDosMateriais> findByDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDescIdDesc(
            LocalDateTime inicio, LocalDateTime fim);

    List<MovimentacaoDosMateriais> findByTipoMovimentacaoOrderByDataHoraDesc(
            TipoMovimentacao tipoMovimentacao);

}
