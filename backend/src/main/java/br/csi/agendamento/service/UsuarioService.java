package br.csi.agendamento.service;

import br.csi.agendamento.model.Usuario;
import br.csi.agendamento.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.passwordEncoder = encoder;
    }

    // cria um novo usuário
    @Transactional
    public void salvar(Usuario usuario) {

        // tipo de usuário
        if (!"C".equalsIgnoreCase(usuario.getTipo()) &&
                !"P".equalsIgnoreCase(usuario.getTipo())) {
            throw new IllegalArgumentException("Tipo inválido! Use 'C' para Cliente ou 'P' para Profissional.");
        }

        // email único
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    // buscar usuário pelo id
    public Usuario getUsuario(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void atualizar(Usuario novosDados) {

        Usuario u = repository.getReferenceById(novosDados.getId());

        if (!"C".equalsIgnoreCase(novosDados.getTipo()) &&
                !"P".equalsIgnoreCase(novosDados.getTipo())) {
            throw new IllegalArgumentException("Tipo inválido! Use 'C' ou 'P'");
        }

        if (!u.getEmail().equals(novosDados.getEmail()) &&
                repository.existsByEmail(novosDados.getEmail())) {
            throw new IllegalArgumentException("E-mail já está cadastrado!");
        }

        u.setNome(novosDados.getNome());
        u.setEmail(novosDados.getEmail());
        u.setSenha(passwordEncoder.encode(novosDados.getSenha()));
        u.setTelefone(novosDados.getTelefone());
        u.setTipo(novosDados.getTipo());

        repository.save(u);
    }

    // uuid
    public Usuario getUsuarioUUID(String uuid) {
        UUID u = UUID.fromString(uuid);
        return repository.findByUuid(u)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado pelo UUID."));
    }

    @Transactional
    public void atualizarUUID(Usuario novosDados) {
        if (novosDados.getUuid() == null) {
            throw new IllegalArgumentException("UUID é obrigatório para atualizar por UUID.");
        }
        Usuario u = repository.findByUuid(novosDados.getUuid())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado pelo UUID."));

        if (!"C".equalsIgnoreCase(novosDados.getTipo()) &&
                !"P".equalsIgnoreCase(novosDados.getTipo())) {
            throw new IllegalArgumentException("Tipo inválido! Use 'C' ou 'P'.");
        }
        if (!u.getEmail().equals(novosDados.getEmail()) &&
                repository.existsByEmail(novosDados.getEmail())) {
            throw new IllegalArgumentException("E-mail já está cadastrado!");
        }

        u.setNome(novosDados.getNome());
        u.setEmail(novosDados.getEmail());
        u.setSenha(passwordEncoder.encode(novosDados.getSenha()));        u.setTelefone(novosDados.getTelefone());
        u.setTipo(novosDados.getTipo());

        repository.save(u);
    }

    @Transactional
    public void deletarUUID(String uuid) {
        UUID u = UUID.fromString(uuid);
        repository.deleteByUuid(u);
    }
}
