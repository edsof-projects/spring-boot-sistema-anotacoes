package com.edsof.anotacoes.infrastructure.dto;

import java.time.LocalDate;

public record AnotacaoDTO(
        Long id,
        String titulo,
        String descricao,
        Long usuarioId,
        LocalDate datacad
) {}

