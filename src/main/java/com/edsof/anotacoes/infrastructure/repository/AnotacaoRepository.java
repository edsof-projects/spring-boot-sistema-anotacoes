package com.edsof.anotacoes.infrastructure.repository;

import com.edsof.anotacoes.infrastructure.entity.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
}
