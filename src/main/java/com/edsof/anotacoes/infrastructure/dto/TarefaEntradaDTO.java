package com.edsof.anotacoes.infrastructure.dto;

public record TarefaEntradaDTO(
        String titulo,
        String historico,
        Long usuarioId
) {}
