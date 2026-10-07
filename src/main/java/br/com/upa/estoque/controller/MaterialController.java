package br.com.upa.estoque.controller;


import br.com.upa.estoque.dto.materiais.AtualizarMateriaisDTO;
import br.com.upa.estoque.dto.materiais.CadastroMaterialDTO;
import br.com.upa.estoque.dto.materiais.MaterialRespostaDTO;
import br.com.upa.estoque.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialRespostaDTO>> listarMateriais() {
        List<MaterialRespostaDTO> materiais = materialService.listarMateriais();

        return ResponseEntity
                .ok()
                .body(materiais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialRespostaDTO> buscarMateriaisPorId(@PathVariable Long id) {

        MaterialRespostaDTO material = materialService.buscarMateriaId(id);

        return ResponseEntity
                .ok()
                .body(material);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<MaterialRespostaDTO>> buscarMateriaisPorNome(@PathVariable String nome) {
        List<MaterialRespostaDTO> materialList = materialService.listarMaterialNome(nome);

        return ResponseEntity
                .ok()
                .body(materialList);
    }


    @GetMapping("/menorMaiorQuantidad/asc")
    public ResponseEntity<List<MaterialRespostaDTO>> menorMaiorQuantidad() {
        List<MaterialRespostaDTO> listaMaiorQuantidade = materialService.listarMenorMaiorQuantidade();

        return ResponseEntity
                .ok()
                .body(listaMaiorQuantidade);
    }



    @GetMapping("/maiorMenorQuantidad/desc")
    public ResponseEntity<List<MaterialRespostaDTO>> maiorMenorQuantidad() {
        List<MaterialRespostaDTO> listaMaiorMenorQuantidade = materialService.listarMaiorMenorQuantidade();

        return ResponseEntity
                .ok()
                .body(listaMaiorMenorQuantidade);
    }


    @PostMapping
    public ResponseEntity<MaterialRespostaDTO> salvarMateriais(@Valid @RequestBody CadastroMaterialDTO materials) {

        MaterialRespostaDTO material = materialService.salvarMaterial(materials);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(material);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MaterialRespostaDTO> atualizarMateriais(@PathVariable Long id, @Valid @RequestBody AtualizarMateriaisDTO material) {
        MaterialRespostaDTO materialAtualizado = materialService.editarMaterial(id, material);

        return ResponseEntity
                .ok()
                .body(materialAtualizado);
    }



    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<Void> desativarMateriaisPorId(@PathVariable Long id) {

        materialService.desativarMaterial(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



    @PutMapping("/ativar/{id}")
    public ResponseEntity<Void> ativarMateriaisPorId(@PathVariable Long id) {
        materialService.ativarMaterial(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



    @PutMapping("/acrescentarQuantidadePorId/{id}/{qtd}")
    public ResponseEntity<MaterialRespostaDTO> acrescentarQuantidadePorId(@PathVariable Long id, @PathVariable BigDecimal qtd) {
        MaterialRespostaDTO material = materialService.acrescentarQuantidadeId(id, qtd);
        return ResponseEntity
                .ok(material);
    }

    @PutMapping("/removerQuantidadePorId/{id}/{qtd}")
    public ResponseEntity<MaterialRespostaDTO> removerQuantidadePorId(@PathVariable Long id, @PathVariable BigDecimal qtd) {
        MaterialRespostaDTO material = materialService.removerQuantidadePorId(id, qtd);
        return ResponseEntity
                .ok(material);
    }

    @GetMapping("/estoque_baixo")
    public ResponseEntity<List<MaterialRespostaDTO>> estoqueBaixo() {
        List<MaterialRespostaDTO> materiais = materialService.listarMateriaisAbaixo();

        return ResponseEntity
                .ok()
                .body(materiais);
    }
}


