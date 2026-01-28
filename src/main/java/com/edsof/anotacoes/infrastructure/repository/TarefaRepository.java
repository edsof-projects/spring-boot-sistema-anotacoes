package com.edsof.anotacoes.infrastructure.repository;

import com.edsof.anotacoes.infrastructure.dto.TarefaSaidaDTO;
import com.edsof.anotacoes.infrastructure.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("""
    SELECT new com.edsof.anotacoes.infrastructure.dto.TarefaSaidaDTO(
        t.id,
        t.titulo,
        t.historico,
        t.usuario.id,
        t.usuario.nome,
        t.data_fechamento,
        t.status
    )
    FROM Tarefa t
    WHERE t.status = 'ABERTA'
    ORDER BY t.titulo
    """)
    List<TarefaSaidaDTO> listarTarefas();
}
