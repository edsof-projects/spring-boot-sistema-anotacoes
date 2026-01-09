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
@Table(name = "tblanotacoes")

public class Anotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", length = 100)
    private String titulo;
    @Column(name = "descricao", length = 100)
    private String descricao;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuarioid", referencedColumnName = "id")

}
