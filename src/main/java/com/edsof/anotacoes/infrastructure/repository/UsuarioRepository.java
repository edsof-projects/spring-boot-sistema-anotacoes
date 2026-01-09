package com.edsof.anotacoes.infrastructure.repository;

import com.edsof.anotacoes.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
