package br.com.upa.estoque.controller;

import br.com.upa.estoque.domain.TipoMovimentacao;
import br.com.upa.estoque.dto.movimentacao.MovimentacaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacaoRespostaDTO;
import br.com.upa.estoque.dto.movimentacao.MovimentacoAtualizarDTO;
import br.com.upa.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(@Valid MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoRespostaDTO>> listarMovimentacao(){
        List<MovimentacaoRespostaDTO> listarMovimentacoes = movimentacaoService.listarMovimentacao();

        return ResponseEntity
                .ok()
                .body(listarMovimentacoes);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<MovimentacaoRespostaDTO>> listarMovimentacaoData(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<MovimentacaoRespostaDTO> movimentacoes = movimentacaoService.listarMovimentacaoPorData(data);
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimentacaoRespostaDTO>> listarMovimentacaoTipo(@PathVariable TipoMovimentacao tipo) {
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = movimentacaoService.listarPorTipo(tipo);

        return ResponseEntity.ok(movimentacaoRespostaDTOList);
    }

    @GetMapping("/dataInicioFim/{dataInicio}/{dataFim}")
    public ResponseEntity<List<MovimentacaoRespostaDTO>> listarMovimentacaoDataInicioFim(@PathVariable("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio, @PathVariable("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = movimentacaoService.listarPorDataInicioFim(dataInicio, dataFim);

        return ResponseEntity.ok(movimentacaoRespostaDTOList);
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<List<MovimentacaoRespostaDTO>> listarMovimentacaoPorId(@PathVariable Long id) {
        List<MovimentacaoRespostaDTO> movimentacaoRespostaDTOList = movimentacaoService.listarMovimentacaoPorMaterial(id);

        return ResponseEntity.ok(movimentacaoRespostaDTOList);

    }

    @PostMapping
    public ResponseEntity<MovimentacaoRespostaDTO> registrarMovimentacao(@Valid @RequestBody MovimentacaDTO movimentacaoRespostaDTO){
        MovimentacaoRespostaDTO movimentacaoRespostaDTO1 = movimentacaoService.registrarMovimentacao(movimentacaoRespostaDTO);

       return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movimentacaoRespostaDTO1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoRespostaDTO> editarMovimentacao(@Valid @RequestBody MovimentacoAtualizarDTO movimentacaoRespostaDTO, @PathVariable Long id){
        MovimentacaoRespostaDTO movimentacaoRespostaDTO1 = movimentacaoService.editarMovimentcao(movimentacaoRespostaDTO, id);

        return ResponseEntity
                .ok()
                .body(movimentacaoRespostaDTO1);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMovimentacao(@Valid @PathVariable Long id){
            movimentacaoService.deletarMovimentacao(id);


        return ResponseEntity.noContent().build();
    }


}
