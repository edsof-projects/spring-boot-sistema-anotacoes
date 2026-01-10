package com.edsof.anotacoes.business.service;

import com.edsof.anotacoes.infrastructure.dto.AnotacaoDTO;
import com.edsof.anotacoes.infrastructure.entity.Anotacao;
import com.edsof.anotacoes.infrastructure.entity.Usuario;
import com.edsof.anotacoes.infrastructure.repository.AnotacaoRepository;
import com.edsof.anotacoes.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnotacaoService {

    private final AnotacaoRepository anotacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<AnotacaoDTO> listarTodas() {
        return anotacaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public AnotacaoDTO cadastrar(Anotacao dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Anotacao anotacao = new Anotacao();
        anotacao.setTitulo(dto.getTitulo());
        anotacao.setDescricao(dto.getDescricao());
        anotacao.setDatacad(LocalDate.now());
        anotacao.setUsuario(usuario);

        return toDTO(anotacaoRepository.save(anotacao));
    }

    public AnotacaoDTO editar(Long id, Anotacao dto) {

        Anotacao anotacao = anotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));

        anotacao.setTitulo(dto.getTitulo());
        anotacao.setDescricao(dto.getDescricao());

        return toDTO(anotacaoRepository.save(anotacao));
    }

    public void excluir(Long id) {
        anotacaoRepository.deleteById(id);
    }

    // 🔁 Conversões
    private AnotacaoDTO toDTO(Anotacao anotacao) {
        return new AnotacaoDTO(
                anotacao.getId(),
                anotacao.getTitulo(),
                anotacao.getDescricao(),
                anotacao.getUsuario().getId(),
                anotacao.getDatacad()
        );
    }
}

