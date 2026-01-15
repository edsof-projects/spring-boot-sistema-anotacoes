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

    public List<NivelAcessoSaidaDTO> listarTodos(){
        return nivelAcessoRepository.findAll()
                .stream()
                .map(this::toSaidaDTO)
                .toList();
    }

    public NivelAcessoSaidaDTO buscarPorId(Long id){
        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel de Acesso não encontrado!"));
        return toSaidaDTO(nivelAcesso);
    }

    public NivelAcessoSaidaDTO cadastrar(NivelAcessoEntradaDTO dto){
        NivelAcesso nivelAcesso = new NivelAcesso();
        nivelAcesso.setTipo(dto.tipo());
        return toSaidaDTO(nivelAcessoRepository.save(nivelAcesso));
    }

    public NivelAcessoSaidaDTO editar(NivelAcessoSaidaDTO dto, Long id){

        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nível de acesso não encontrado"));

        // atualiza os campos editáveis
        nivelAcesso.setTipo(dto.tipo());

        NivelAcesso nivelAcessoAtualizado = nivelAcessoRepository.save(nivelAcesso);

        return toSaidaDTO(nivelAcessoRepository.save(nivelAcessoAtualizado));
    }

    public void excluir(Long id){
        nivelAcessoRepository.deleteById(id);
    }
}
