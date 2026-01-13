package com.edsof.anotacoes.business.service;

import com.edsof.anotacoes.infrastructure.dto.UsuarioEntradaDTO;
import com.edsof.anotacoes.infrastructure.dto.UsuarioSaidaDTO;
import com.edsof.anotacoes.infrastructure.entity.NivelAcesso;
import com.edsof.anotacoes.infrastructure.entity.Usuario;
import com.edsof.anotacoes.infrastructure.exceptions.ConflictException;
import com.edsof.anotacoes.infrastructure.repository.NivelAcessoRepository;
import com.edsof.anotacoes.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final NivelAcessoRepository nivelAcessoRepository;
    private final PasswordEncoder passwordEncoder;


    // Entity → DTO de SAÍDA
    private UsuarioSaidaDTO toSaidaDTO(Usuario usuario) {
        return new UsuarioSaidaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getNivelAcesso().getId(),
                usuario.getDatacad()
        );
    }

    // DTO de ENTRADA → Entity
    private Usuario toEntity(UsuarioEntradaDTO dto) {

        if (dto.nivelAcessoId() == null) {
            throw new RuntimeException("nivelAcessoId é obrigatório");
        }

//        if (usuarioRepository.existsByEmail(dto.email())) {
//            throw new RuntimeException("Duplicidade : o email "+dto.email()+" já esta cadastrado");
//        }

        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(dto.nivelAcessoId())
                .orElseThrow(() -> new RuntimeException("Nível de acesso não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setNivelAcesso(nivelAcesso);
        usuario.setDatacad(LocalDate.now());

        return usuario;
    }
    
    public List<UsuarioSaidaDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toSaidaDTO)
                .toList();
    }

    public UsuarioSaidaDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return toSaidaDTO(usuario);
    }

    // CREATE
    public UsuarioSaidaDTO cadastrar(UsuarioEntradaDTO dto) {
        try {
            emailExiste(dto.email());
            Usuario usuario = toEntity(dto);
            return toSaidaDTO(usuarioRepository.save(usuario));
        }catch (ConflictException e){
            throw  new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe){
                throw new ConflictException("Duplicidade : o email "+email+" já esta cadastrado");
            }
        }catch (ConflictException e){
            throw  new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    // UPDATE (sem senha)
    public UsuarioSaidaDTO editar(UsuarioSaidaDTO dto, Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(dto.nivelAcessoId())
                .orElseThrow(() -> new RuntimeException("Nível de acesso não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setNivelAcesso(nivelAcesso);

        return toSaidaDTO(usuarioRepository.save(usuario));
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
