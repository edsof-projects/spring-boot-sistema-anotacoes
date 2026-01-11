package com.edsof.anotacoes.infrastructure.repository;

import com.edsof.anotacoes.infrastructure.entity.Nivelacesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NivelAcessoRepository extends JpaRepository<Nivelacesso, Long> {
}
