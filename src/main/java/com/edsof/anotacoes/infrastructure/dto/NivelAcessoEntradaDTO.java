package com.edsof.anotacoes.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NivelAcessoEntradaDTO(

        @JsonProperty("tipo")
        String tipo
){}
