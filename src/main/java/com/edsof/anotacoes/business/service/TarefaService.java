package com.edsof.anotacoes.business.service;

import com.edsof.anotacoes.infrastructure.dto.TarefaEntradaDTO;
import com.edsof.anotacoes.infrastructure.dto.TarefaSaidaDTO;
import com.edsof.anotacoes.infrastructure.entity.Tarefa;
import com.edsof.anotacoes.infrastructure.entity.Usuario;
import com.edsof.anotacoes.infrastructure.enums.StatusTarefa;
import com.edsof.anotacoes.infrastructure.repository.TarefaRepository;
import com.edsof.anotacoes.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    // Entity → DTO de SAÍDA
    private TarefaSaidaDTO toSaidaDTO(Tarefa tarefa) {
        return new TarefaSaidaDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getHistorico(),
                tarefa.getUsuario().getId(),
                tarefa.getUsuario().getNome(),
                tarefa.getData_fechamento(),
                tarefa.getStatus()
        );
    }

    // DTO de ENTRADA → Entity
    private Tarefa toEntity(TarefaEntradaDTO dto) {

        if (dto.usuarioId() == null) {
            throw new RuntimeException("usuarioId é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Id do usuário não encontrado"));

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setHistorico(dto.historico());
        tarefa.setUsuario(usuario);
        tarefa.setData_abertura(LocalDate.now());

        return tarefa;
    }

    public List<TarefaSaidaDTO> listarTodas() {
        return tarefaRepository.listarTarefas();
    }

    public TarefaSaidaDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        return toSaidaDTO(tarefa);
    }

    // CREATE
    public Tarefa cadastrar(TarefaEntradaDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String historicoInicial =
                LocalDate.now().format(formatter) + " : Tarefa criada.";

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setHistorico(historicoInicial);
        tarefa.setUsuario(usuario);
        tarefa.setData_abertura(LocalDate.now());
        tarefa.setStatus(StatusTarefa.ABERTA);
        return tarefaRepository.save(tarefa);
    }

    private String novaLinhaHistorico(String mensagem) {
        String data = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return data + " : " + mensagem;
    }

    // UPDATE
    public TarefaSaidaDTO editar(TarefaSaidaDTO dto, Long id) {

        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setTitulo(dto.titulo());

        // HISTÓRICO NAO PRECISA COLOCAR A DATA ENTRE SOMENTE COM A STRING
        String historicoAtual   = tarefa.getHistorico();
        StringBuilder historico = new StringBuilder(
                historicoAtual != null ? historicoAtual : ""
        );

        historico.append("\n")
                .append(novaLinhaHistorico(dto.historico()));

        tarefa.setHistorico(historico.toString());

        // STATUS
        if (dto.status() != null) {
            tarefa.setStatus(dto.status());
            historico.append("\n")
                    .append(novaLinhaHistorico(
                            "Status alterado para " + dto.status()
                    ));
        }

        // DATA DE FECHAMENTO
        if (dto.data_fechamento() != null) {
            tarefa.setData_fechamento(dto.data_fechamento());
            historico.append("\n")
                    .append(novaLinhaHistorico("Tarefa finalizada."));
        }

        return toSaidaDTO(tarefaRepository.save(tarefa));
    }

    public void excluir(Long id) {
        tarefaRepository.deleteById(id);
    }

}

