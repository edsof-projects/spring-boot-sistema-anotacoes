package com.edsof.anotacoes.controller;

import com.edsof.anotacoes.business.service.UsuarioService;
import com.edsof.anotacoes.infrastructure.dto.UsuarioEntradaDTO;
import com.edsof.anotacoes.infrastructure.dto.UsuarioSaidaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioSaidaDTO>listarTodos(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioSaidaDTO buscarPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioSaidaDTO> cadastrar(@RequestBody UsuarioEntradaDTO dto) {
        return ResponseEntity.ok(usuarioService.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public UsuarioSaidaDTO editar(@RequestBody UsuarioSaidaDTO dto, @PathVariable Long id){
        return usuarioService.editar(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluir(@PathVariable Long id){
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
