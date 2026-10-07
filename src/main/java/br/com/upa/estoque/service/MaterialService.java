package br.com.upa.estoque.service;


import br.com.upa.estoque.domain.Material;
import br.com.upa.estoque.domain.MovimentacaoDosMateriais;
import br.com.upa.estoque.domain.TipoMovimentacao;
import br.com.upa.estoque.dto.materiais.AtualizarMateriaisDTO;
import br.com.upa.estoque.dto.materiais.CadastroMaterialDTO;
import br.com.upa.estoque.dto.materiais.MaterialRespostaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacoAtualizarDTO;
import br.com.upa.estoque.exception.MaterialNaoEncontradoException;
import br.com.upa.estoque.repository.MateriaRepository;
import br.com.upa.estoque.repository.MovimentacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    private final MateriaRepository materiaRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public MaterialService(MateriaRepository materiaRepository, MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.materiaRepository = materiaRepository;
    }


    public List<MaterialRespostaDTO> listarMateriais() {
        List<MaterialRespostaDTO> materiais = new ArrayList<>();
        for (Material material : materiaRepository.findAll()) {
            MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);
            materiais.add(materialRespostaDTO);
        }

        return materiais;
    }

    public MaterialRespostaDTO salvarMaterial(CadastroMaterialDTO dados) {

        Material material = new Material();

        material.setNome(dados.nome());
        material.setModelo(dados.modelo());
        material.setEstoqueMinimo(dados.estoqueMinimo());
        material.setQuantidade(dados.quantidade());
        material.setUnidadeMedida(dados.unidadeMedida());
        material.setPatrimonio(dados.patrimonio());
        material.setAtivo(true);

        Material materialSalvo = materiaRepository.save(material);
        MovimentacaDTO movimentacaDTO = new MovimentacaDTO(materialSalvo.getQuantidade(), "Entrada de estoque", TipoMovimentacao.ENTRADA, materialSalvo.getId());
        MovimentacaoDosMateriais movimentacaoDosMateriais = new MovimentacaoDosMateriais(movimentacaDTO, materialSalvo);
        movimentacaoRepository.save(movimentacaoDosMateriais);
        MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);


        return materialRespostaDTO;
    }

    public MaterialRespostaDTO buscarMateriaId(Long id) {

        return new MaterialRespostaDTO(buscarPorId(id));
    }

    public void desativarMaterial(Long id) {
        Material material = buscarPorId(id);

        material.setAtivo(false);
        materiaRepository.save(material);
    }


    public MaterialRespostaDTO editarMaterial(Long id, AtualizarMateriaisDTO materialAtualizado) {

        Material material = buscarPorId(id);


        if (!material.isAtivo()) {
            throw new IllegalArgumentException("Não é permitido atualizar um materia inativo.");
        }

        material.setNome(materialAtualizado.nome());
        material.setModelo(materialAtualizado.modelo());
        material.setEstoqueMinimo(materialAtualizado.estoqueMinimo());
        material.setPatrimonio(materialAtualizado.patrimonio());
        material.setQuantidade(materialAtualizado.quantidade());
        Material materialSalvo = materiaRepository.save(material);
        MovimentacaDTO movimentacaDTO = new MovimentacaDTO(materialAtualizado.quantidade(), materialAtualizado.motivo(), TipoMovimentacao.ATUALIZACAO, material.getId());
        MovimentacaoDosMateriais movimentacaoDosMateriais = new MovimentacaoDosMateriais(movimentacaDTO, materialSalvo);
        movimentacaoRepository.save(movimentacaoDosMateriais);


        MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);

        return materialRespostaDTO;

    }

    public void ativarMaterial(Long id) {
        Material material = buscarPorId(id);

        material.setAtivo(true);
        materiaRepository.save(material);

    }

    public List<MaterialRespostaDTO> listarMaterialNome(String nome) {
        List<Material> listaMateriaisNomes = materiaRepository.findByNomeContainingIgnoreCase(nome);

        List<MaterialRespostaDTO> materiais = new ArrayList<>();
        for (Material material : listaMateriaisNomes) {
            MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);
            materiais.add(materialRespostaDTO);
        }

        return materiais;
    }

    public List<MaterialRespostaDTO> listarMenorMaiorQuantidade() {
        List<Material> maiorQuantidade = materiaRepository.findAllByOrderByQuantidadeAsc();
        List<MaterialRespostaDTO> materiais = new ArrayList<>();


        for (Material material : maiorQuantidade) {
            MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);
            materiais.add(materialRespostaDTO);
        }

        return materiais;
    }

    public List<MaterialRespostaDTO> listarMaiorMenorQuantidade() {
        List<Material> maiorMenorQuantidade = materiaRepository.findAllByOrderByQuantidadeDesc();
        List<MaterialRespostaDTO> materiais = new ArrayList<>();
        for (Material material : maiorMenorQuantidade) {
            MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(material);
            materiais.add(materialRespostaDTO);
        }

        return materiais;

    }

    @Transactional
    public MaterialRespostaDTO acrescentarQuantidadeId(Long id, BigDecimal qtd) {

        Material material = materiaRepository.findByIdForUpdate(id).orElseThrow(() -> new MaterialNaoEncontradoException(id));
        if (qtd == null || qtd.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        material.setQuantidade(material.getQuantidade().add(qtd));
        Material salvo = materiaRepository.save(material);
        MaterialRespostaDTO materialRespostaDTO = new MaterialRespostaDTO(salvo);
        MovimentacaoDosMateriais movimentacaoDosMateriais = new MovimentacaoDosMateriais(salvo, qtd, TipoMovimentacao.ENTRADA, "Entrada de estoque");
        movimentacaoRepository.save(movimentacaoDosMateriais);


        return materialRespostaDTO;

    }

    @Transactional
    public MaterialRespostaDTO removerQuantidadePorId(Long id, BigDecimal qtd) {

        Material material = materiaRepository.findByIdForUpdate(id).orElseThrow(() -> new MaterialNaoEncontradoException(id));
        if (qtd == null || qtd.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        if (qtd.compareTo(material.getQuantidade()) > 0) {
            throw new IllegalArgumentException("A quantidade deve ser menor que no estoque");
        }

        material.setQuantidade(material.getQuantidade().subtract(qtd));
        Material salvo = materiaRepository.save(material);
        MaterialRespostaDTO materialDto = new MaterialRespostaDTO(salvo);
        MovimentacaoDosMateriais movimentacaoDosMateriais = new MovimentacaoDosMateriais(salvo, qtd, TipoMovimentacao.SAIDA, "Saída de estoque");
        movimentacaoRepository.save(movimentacaoDosMateriais);

        return materialDto;
    }


    public List<MaterialRespostaDTO> listarMateriaisAbaixo() {

        List<Material> listaDeMaterais = materiaRepository.findAll();
        List<MaterialRespostaDTO> listaDeMateraisAbaixo = new ArrayList<>();

        for (Material material : listaDeMaterais) {
            if (material.isAtivo() && material.getQuantidade().compareTo(material.getEstoqueMinimo()) <= 0) {
                MaterialRespostaDTO materialDto = new MaterialRespostaDTO(material);
                listaDeMateraisAbaixo.add(materialDto);
            }

        }
        return listaDeMateraisAbaixo;
    }


    public Material buscarPorId(Long id) {

        Material material = materiaRepository.findById(id)
                .orElseThrow(() -> new MaterialNaoEncontradoException(id));

        return material;
    }
}
