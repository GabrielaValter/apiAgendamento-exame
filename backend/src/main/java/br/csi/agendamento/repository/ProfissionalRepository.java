package br.csi.agendamento.repository;

import br.csi.agendamento.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository  extends JpaRepository<Profissional, Long> {
    // id do profissional = id do usuario
    Optional<Profissional> findByUsuarioId(Long usuarioId);
    // listar todos os profissionais que oferem um serviço
    List<Profissional> findAllByServicos_Id(Long servicoId);
}
