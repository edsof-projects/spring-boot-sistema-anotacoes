package com.edsof.anotacoes.infrastructure.dto;

public record UsuarioSaidaDTO(
        Long id,
        String nome,
        String email,
        String acesso,
        Long nivelAcessoId
){}
