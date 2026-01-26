package com.edsof.anotacoes.infrastructure.dto;

import com.edsof.anotacoes.infrastructure.enums.StatusTarefa;

public record TarefaEntradaDTO(
        String titulo,
        String historico,
        Long usuarioId,
        StatusTarefa status
) {}
