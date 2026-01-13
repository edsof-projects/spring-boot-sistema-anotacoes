package com.edsof.anotacoes.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioEntradaDTO(
        String nome,
        String email,
        String senha,
        @JsonProperty("nivelacessoid")
        Long nivelAcessoId
) {}

