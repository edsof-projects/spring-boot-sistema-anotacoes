package com.edsof.anotacoes.infrastructure.dto;

public record UsuarioEntradaDTO(
        String nome,
        String email,
        String senha,
        Long nivelAcessoId
) {}

