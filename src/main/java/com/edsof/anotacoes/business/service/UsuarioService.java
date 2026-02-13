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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository     usuarioRepository;
    private final NivelAcessoRepository nivelAcessoRepository;
    private final PasswordEncoder       passwordEncoder;
    private static final String FOTO_PADRAO = "default.png";

    // Entity → DTO de SAÍDA
    private UsuarioSaidaDTO toSaidaDTO(Usuario usuario) {
        String nomeFoto =
                (usuario.getUrlfoto() == null || usuario.getUrlfoto().isBlank())
                        ? "default-user.png"
                        : usuario.getUrlfoto();

        String urlFoto = "http://localhost:8080/uploads/usuarios/" + nomeFoto;

        return new UsuarioSaidaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getNivelAcesso().getTipo(),
                usuario.getNivelAcesso().getId(),
                urlFoto
        );
    }

    @Value("${app.upload.dir}")
    private String uploadDir;

    public Usuario salvarFoto(Long id, MultipartFile file) throws IOException {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir + "/usuarios");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        usuario.setUrlfoto(fileName);

        return usuarioRepository.save(usuario);
    }

    // DTO de ENTRADA → Entity
    private Usuario toEntity(UsuarioEntradaDTO dto) {

        if (dto.nivelAcessoId() == null) {
            throw new RuntimeException("nivelAcessoId é obrigatório");
        }

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
        return usuarioRepository.listarUsuarios();
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
