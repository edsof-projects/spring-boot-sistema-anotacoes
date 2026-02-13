package com.edsof.anotacoes.infrastructure.repository;

import com.edsof.anotacoes.infrastructure.dto.UsuarioSaidaDTO;
import com.edsof.anotacoes.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    @Query("""
        SELECT new com.edsof.anotacoes.infrastructure.dto.UsuarioSaidaDTO(
            u.id,
            u.nome,
            u.email,
            n.tipo,
            n.id,
            u.urlfoto
        )
        FROM Usuario u
        JOIN u.nivelAcesso n
        ORDER BY UPPER(TRIM(u.nome))
    """)
    List<UsuarioSaidaDTO> listarUsuarios();

}
