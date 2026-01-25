package com.edsof.anotacoes.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblanotacoes")
public class Anotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT",nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuarioid", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Column(name = "datacad", nullable = false)
    private LocalDate datacad;

}

