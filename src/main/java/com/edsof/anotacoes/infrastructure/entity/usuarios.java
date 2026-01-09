package com.edsof.anotacoes.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblusuarios")

public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha", length = 15)
    private String senha;
    @Column(name = "tipoacesso", length = 4)
    private String tipoacesso;
    @Column(name = "datacad",length = )
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")

}

