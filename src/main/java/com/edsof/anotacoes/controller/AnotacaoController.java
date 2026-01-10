package com.edsof.anotacoes.controller;

import com.edsof.anotacoes.business.service.AnotacaoService;
import com.edsof.anotacoes.infrastructure.dto.AnotacaoDTO;
import com.edsof.anotacoes.infrastructure.entity.Anotacao;
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
    public List<AnotacaoDTO> ListarTodas(){
        return anotacaoService.listarTodas();
    }

    @PostMapping
    public AnotacaoDTO cadastrar(@RequestBody Anotacao anotacao){
        return anotacaoService.cadastrar(anotacao);
    }

    @PutMapping("/{id}")
    public AnotacaoDTO editar(
            @PathVariable Long id,
            @RequestBody Anotacao anotacao) {
        return anotacaoService.editar(id, anotacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        anotacaoService.excluir(id);
        return ResponseEntity.ok().build();
    }

}
