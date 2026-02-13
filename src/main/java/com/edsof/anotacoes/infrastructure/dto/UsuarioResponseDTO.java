package com.edsof.anotacoes.infrastructure.dto;

import com.edsof.anotacoes.infrastructure.entity.Usuario;

public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String fotoUrl;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id    = usuario.getId();
        this.nome  = usuario.getNome();
        this.email = usuario.getEmail();

        if (usuario.getUrlfoto() == null) {
            this.fotoUrl = "http://localhost:8080/img/default-user.png";
        } else {
            this.fotoUrl = "http://localhost:8080/uploads/usuarios/" + usuario.getUrlfoto();
        }
    }
}
