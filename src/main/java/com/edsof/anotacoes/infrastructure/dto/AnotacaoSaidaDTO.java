package com.edsof.anotacoes.infrastructure.dto;

public record AnotacaoSaidaDTO(
        Long   id,
        String titulo,
        String descricao,
        Long   usuarioId,
        String nomeUsuario
) {}
