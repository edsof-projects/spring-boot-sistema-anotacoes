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

    public AnotacaoDTO buscarPorId(Long id) {
        Anotacao anotacao = anotacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));
        return toDTO(anotacao);
    }

    public AnotacaoDTO cadastrar(AnotacaoDTO dto) {

        if (dto.usuarioId() == null) {
            throw new IllegalArgumentException("usuarioId é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Anotacao anotacao = new Anotacao();
        anotacao.setTitulo(dto.titulo());
        anotacao.setDescricao(dto.descricao());
        anotacao.setDatacad(LocalDate.now());
        anotacao.setUsuario(usuario);

        return toDTO(anotacaoRepository.save(anotacao));
    }

    public AnotacaoDTO editar(Long id, AnotacaoDTO dto) {

        Anotacao anotacao = anotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anotação não encontrada"));

        anotacao.setTitulo(dto.titulo());
        anotacao.setDescricao(dto.descricao());

        return toDTO(anotacaoRepository.save(anotacao));
    }

    public void excluir(Long id) {
        anotacaoRepository.deleteById(id);
    }

    // 🔁 Conversão Entity → DTO
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
