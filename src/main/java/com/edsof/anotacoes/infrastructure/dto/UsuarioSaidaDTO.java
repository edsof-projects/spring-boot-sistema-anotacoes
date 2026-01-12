package com.edsof.anotacoes.infrastructure.dto;

import java.time.LocalDate;

public record UsuarioSaidaDTO(
        Long id,
        String nome,
        String email,
        Long nivelAcessoId,
        LocalDate datacad
){}
