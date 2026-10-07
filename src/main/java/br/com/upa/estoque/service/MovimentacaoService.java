package br.com.upa.estoque.service;


import br.com.upa.estoque.domain.Material;
import br.com.upa.estoque.domain.MovimentacaoDosMateriais;
import br.com.upa.estoque.domain.TipoMovimentacao;
import br.com.upa.estoque.dto.materiais.MaterialRespostaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacaoRespostaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacoAtualizarDTO;
import br.com.upa.estoque.exception.MaterialNaoEncontradoException;
import br.com.upa.estoque.repository.MateriaRepository;
import br.com.upa.estoque.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final MateriaRepository materiaRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, MateriaRepository materiaRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<MovimentacaoRespostaDTO> listarMovimentacao() {
        List<MovimentacaoDosMateriais> listaMovimentacao = movimentacaoRepository.findAll();
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = new ArrayList<>();

        for (MovimentacaoDosMateriais movimentacao : listaMovimentacao) {
            MovimentacaoRespostaDTO movimentacaoDosMateriais = new MovimentacaoRespostaDTO(movimentacao);
            movimentacaoRespostaDTOList.add(movimentacaoDosMateriais);
        }

        return movimentacaoRespostaDTOList;
    }

    public List<MovimentacaoRespostaDTO> listarMovimentacaoPorData(LocalDate data) {
        List<MovimentacaoDosMateriais> movimentacoes =
                movimentacaoRepository.findByDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDescIdDesc(
                        data.atStartOfDay(), data.plusDays(1).atStartOfDay());

        return movimentacoes.stream()
                .map(MovimentacaoRespostaDTO::new)
                .toList();
    }

    public MovimentacaoRespostaDTO registrarMovimentacao(MovimentacaDTO movimentacaoRespostaDTO) {

        MovimentacaoDosMateriais movimentacaoDosMateriais = new MovimentacaoDosMateriais();
        Material material = materiaRepository.findById(movimentacaoRespostaDTO.materialId()).orElseThrow(() -> new MaterialNaoEncontradoException(movimentacaoRespostaDTO.materialId()));
        movimentacaoDosMateriais.setMaterial(material);
        movimentacaoDosMateriais.setMotivo(movimentacaoRespostaDTO.motivo());
        movimentacaoDosMateriais.setTipoMovimentacao(movimentacaoRespostaDTO.tipoMovimentacao());
        movimentacaoDosMateriais.setQuantidade(movimentacaoRespostaDTO.quantidade());
        TipoMovimentacao tipoMovimentacao = movimentacaoRespostaDTO.tipoMovimentacao();

        switch (tipoMovimentacao) {
            case ENTRADA ->
                    material.setQuantidade(
                            material.getQuantidade().add(movimentacaoRespostaDTO.quantidade()));

            case SAIDA -> {
                if (material.getQuantidade().compareTo(movimentacaoRespostaDTO.quantidade()) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente");
                }
                material.setQuantidade(
                        material.getQuantidade().subtract(movimentacaoRespostaDTO.quantidade()));
            }

            case ATUALIZACAO ->
                    throw new IllegalArgumentException("Tipo ATUALIZACAO ainda não implementado");
        }

        materiaRepository.save(material);
        MovimentacaoDosMateriais movimentacaoDosMateriaisSalva = movimentacaoRepository.save(movimentacaoDosMateriais);
        MovimentacaoRespostaDTO movimentacaoRespostaDTOSalvo = new MovimentacaoRespostaDTO(movimentacaoDosMateriaisSalva);

        return movimentacaoRespostaDTOSalvo;
    }


    public MovimentacaoRespostaDTO editarMovimentcao(MovimentacoAtualizarDTO movimentacaoRespostaDTO, Long id) {

        MovimentacaoDosMateriais movimentacaoDosMateriais = movimentacaoRepository.findById(id).orElseThrow(() -> new MaterialNaoEncontradoException(id));

        movimentacaoDosMateriais.setMotivo(movimentacaoRespostaDTO.motivo());
        MovimentacaoDosMateriais movimentacaoDosMateriaisSalva = movimentacaoRepository.save(movimentacaoDosMateriais);
        MovimentacaoRespostaDTO movimentacaoRespostaDTOSalvo = new MovimentacaoRespostaDTO(movimentacaoDosMateriaisSalva);

        return movimentacaoRespostaDTOSalvo;
    }


    public void deletarMovimentacao(Long id) {
        movimentacaoRepository.deleteById(id);
    }

    public List<MovimentacaoRespostaDTO> listarPorTipo(TipoMovimentacao tipoMovimentacao) {

        List<MovimentacaoDosMateriais> list = movimentacaoRepository.findByTipoMovimentacaoOrderByDataHoraDesc(tipoMovimentacao);
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = new ArrayList<>();
        for (MovimentacaoDosMateriais material : list) {
            MovimentacaoRespostaDTO movimentacaoRespostaDTO = new MovimentacaoRespostaDTO(material);
            movimentacaoRespostaDTOList.add(movimentacaoRespostaDTO);
        }

        return movimentacaoRespostaDTOList;
    }

    public List<MovimentacaoRespostaDTO> listarPorDataInicioFim(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.plusDays(1).atStartOfDay();

        List<MovimentacaoDosMateriais> movimentacaoDosMateriais = movimentacaoRepository.findByDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDescIdDesc(inicio, fim);
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = new ArrayList<>();
        for (MovimentacaoDosMateriais material : movimentacaoDosMateriais) {
            MovimentacaoRespostaDTO movimentacaoRespostaDTO = new MovimentacaoRespostaDTO(material);
            movimentacaoRespostaDTOList.add(movimentacaoRespostaDTO);
        }

        return movimentacaoRespostaDTOList;
    }

    public List<MovimentacaoRespostaDTO> listarMovimentacaoPorMaterial(Long id) {
        List<MovimentacaoDosMateriais> movimentacaoDosMateriais = movimentacaoRepository.findByMaterial_IdOrderByDataHoraDescIdDesc(id);
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = new ArrayList<>();

        for (MovimentacaoDosMateriais material : movimentacaoDosMateriais) {
            MovimentacaoRespostaDTO movimentacaoRespostaDTO = new MovimentacaoRespostaDTO(material);
            movimentacaoRespostaDTOList.add(movimentacaoRespostaDTO);
        }

        return movimentacaoRespostaDTOList;

    }
}
