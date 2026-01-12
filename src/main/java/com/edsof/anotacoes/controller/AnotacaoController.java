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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<AnotacaoDTO> listarTodas() {
        return anotacaoService.listarTodas();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public AnotacaoDTO buscarPorId(@PathVariable Long id) {
        return anotacaoService.buscarPorId(id);
    }

    @PostMapping
    public AnotacaoDTO cadastrar(@RequestBody AnotacaoDTO dto) {
        return anotacaoService.cadastrar(dto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public AnotacaoDTO editar(
            @PathVariable Long id,
            @RequestBody AnotacaoDTO dto) {
        return anotacaoService.editar(id, dto);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        anotacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
