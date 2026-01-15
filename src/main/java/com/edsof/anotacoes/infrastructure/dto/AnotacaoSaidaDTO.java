package com.edsof.anotacoes.infrastructure.dto;

import java.time.LocalDate;

public record AnotacaoSaidaDTO(
        Long id,
        String titulo,
        String descricao,
        Long usuarioId,
        LocalDate datacad
) {}

