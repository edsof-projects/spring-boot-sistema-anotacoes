package com.edsof.anotacoes.controller;

import com.edsof.anotacoes.business.service.AnotacaoService;
import com.edsof.anotacoes.infrastructure.dto.AnotacaoEntradaDTO;
import com.edsof.anotacoes.infrastructure.dto.AnotacaoSaidaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anotacao")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    @GetMapping
    public List<AnotacaoSaidaDTO> listarTodas() {
        return anotacaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public AnotacaoSaidaDTO buscarPorId(@PathVariable Long id) {
        return anotacaoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<AnotacaoSaidaDTO> cadastrar(@RequestBody AnotacaoEntradaDTO dto) {
        return ResponseEntity.ok(anotacaoService.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public AnotacaoSaidaDTO editar(@RequestBody AnotacaoSaidaDTO dto, @PathVariable Long id){
        return anotacaoService.editar(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        anotacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
