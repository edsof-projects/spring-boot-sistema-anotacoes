package com.edsof.anotacoes.business.service;

import com.edsof.anotacoes.infrastructure.dto.NivelAcessoEntradaDTO;
import com.edsof.anotacoes.infrastructure.dto.NivelAcessoSaidaDTO;
import com.edsof.anotacoes.infrastructure.entity.NivelAcesso;
import com.edsof.anotacoes.infrastructure.repository.NivelAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelAcessoService {

    private final NivelAcessoRepository nivelAcessoRepository;

    // Entity → DTO de SAÍDA
    private NivelAcessoSaidaDTO toSaidaDTO(NivelAcesso nivelacesso) {
        return new NivelAcessoSaidaDTO(
                nivelacesso.getId(),
                nivelacesso.getTipo()
        );
    }

    // DTO de ENTRADA → Entity
    private NivelAcesso toEntity(NivelAcessoEntradaDTO dto) {

        NivelAcesso nivelAcesso = new NivelAcesso();
        nivelAcesso.setTipo(dto.tipo());
        return nivelAcesso;

    }

    public List<NivelAcessoSaidaDTO> listarTodos() {
        return nivelAcessoRepository.listarAcessos();
    }

    public NivelAcessoSaidaDTO buscarPorId(Long id){
        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel de Acesso não encontrado!"));
        return toSaidaDTO(nivelAcesso);
    }

    public NivelAcessoSaidaDTO cadastrar(NivelAcessoEntradaDTO dto){
        if (dto.tipo() == null || dto.tipo().isBlank()) {
            throw new RuntimeException("O tipo do nível de acesso é obrigatório");
        }

        String tipoNormalizado = dto.tipo().trim().toUpperCase();

        if (nivelAcessoRepository.existsByTipo(tipoNormalizado)) {
            throw new RuntimeException("Nível de acesso "+dto.tipo()+" já cadastrado");
        }

        NivelAcesso nivelAcesso = new NivelAcesso();
        nivelAcesso.setTipo(tipoNormalizado);

        return toSaidaDTO(nivelAcessoRepository.save(nivelAcesso));

    }

    public NivelAcessoSaidaDTO editar(NivelAcessoEntradaDTO dto, Long id) {

        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nível de acesso não encontrado"));

        String tipoNormalizado = dto.tipo().trim().toUpperCase();

        // 🔥 verifica duplicidade ignorando o próprio ID
        nivelAcessoRepository.findByTipo(tipoNormalizado)
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new RuntimeException("Tipo de acesso já existe");
                });

        nivelAcesso.setTipo(tipoNormalizado);

        return toSaidaDTO(nivelAcessoRepository.save(nivelAcesso));
    }


    public void excluir(Long id){
        nivelAcessoRepository.deleteById(id);
    }
}
