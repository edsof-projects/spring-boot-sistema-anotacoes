package com.edsof.anotacoes.controller;

import com.edsof.anotacoes.business.service.AnotacaoService;
import com.edsof.anotacoes.infrastructure.dto.AnotacaoDTO;
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
    public List<AnotacaoDTO> listarTodas() {
        return anotacaoService.listarTodas();
    }

    @PostMapping
    public AnotacaoDTO cadastrar(@RequestBody AnotacaoDTO dto) {
        return anotacaoService.cadastrar(dto);
    }

    @GetMapping("/{id}")
    public AnotacaoDTO buscarPorId(@PathVariable Long id) {
        return anotacaoService.buscarPorId(id);
    }


    @PutMapping("/{id}")
    public AnotacaoDTO editar(
            @PathVariable Long id,
            @RequestBody AnotacaoDTO dto) {
        return anotacaoService.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        anotacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
