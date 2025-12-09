package br.csi.agendamento.repository;

import br.csi.agendamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // verifica se o email já existe
    boolean existsByEmail(String email);
    // buscar por email
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
