package com.edsof.anotacoes.business.service;

import com.edsof.anotacoes.infrastructure.dto.NivelAcessoDTO;
import com.edsof.anotacoes.infrastructure.entity.NivelAcesso;
import com.edsof.anotacoes.infrastructure.repository.NivelAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelAcessoService {

    private final NivelAcessoRepository nivelAcessoRepository;

    // 🔁 Conversão Entity → DTO
    public NivelAcessoDTO toDTO(NivelAcesso nivelAcesso) {
        return new NivelAcessoDTO(
                nivelAcesso.getId(),
                nivelAcesso.getTipo()
        );
    }

    public List<NivelAcessoDTO> listarTodos(){
        return nivelAcessoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public NivelAcessoDTO buscarPorId(Long id){
        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel de Acesso não encontrado!"));
        return toDTO(nivelAcesso);
    }

    public NivelAcessoDTO cadastrar(NivelAcessoDTO dto){
        NivelAcesso nivelAcesso = new NivelAcesso();
        nivelAcesso.setTipo(dto.tipo());
        return toDTO(nivelAcessoRepository.save(nivelAcesso));
    }

    public NivelAcessoDTO editar(Long id, NivelAcessoDTO dto){
        NivelAcesso nivelAcesso = new NivelAcesso();
        nivelAcesso.setTipo(dto.tipo());
        return toDTO(nivelAcessoRepository.save(nivelAcesso));
    }

    public void excluir(Long id){
        nivelAcessoRepository.deleteById(id);
    }
}
