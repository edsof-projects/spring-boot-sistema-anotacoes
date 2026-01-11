package com.edsof.anotacoes.controller;

import com.edsof.anotacoes.business.service.NivelAcessoService;
import com.edsof.anotacoes.infrastructure.dto.NivelAcessoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nivelacesso")
public class NivelAcessoController {

    private final NivelAcessoService nivelAcessoService;

    @GetMapping
    public List<NivelAcessoDTO>listarTodos(){
        return nivelAcessoService.listarTodos();
    }

    @GetMapping("/{id}")
    public NivelAcessoDTO buscarPorId(@PathVariable Long id){
        return nivelAcessoService.buscarPorId(id);
    }

    @PostMapping
    public NivelAcessoDTO cadastrar(@RequestBody NivelAcessoDTO dto){
        return nivelAcessoService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public NivelAcessoDTO editar(@RequestBody NivelAcessoDTO dto, @PathVariable Long id){
        return nivelAcessoService.editar(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir (@PathVariable Long id){
        nivelAcessoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
