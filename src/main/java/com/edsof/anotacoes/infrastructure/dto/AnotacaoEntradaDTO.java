package com.edsof.anotacoes.infrastructure.dto;

public record AnotacaoEntradaDTO(
        String titulo,
        String descricao,
        Long usuarioId
) {}
