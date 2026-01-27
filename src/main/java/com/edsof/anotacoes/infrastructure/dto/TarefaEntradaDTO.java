package com.edsof.anotacoes.infrastructure.dto;

import java.time.LocalDate;

public record TarefaEntradaDTO(
        String titulo,
        String historico,
        LocalDate data_fechamento,
        Long usuarioId

) {}
