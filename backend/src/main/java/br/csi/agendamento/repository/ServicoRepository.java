package br.csi.agendamento.repository;

import br.csi.agendamento.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    // todos os serviços oferecidos por um profissional
    // de preferencia não usar consulta aqui
    @Query(""" 
    select s
    from Profissional p 
    join p.servicos s 
    where p.id = :profissionalId 
""")
    List<Servico> findAllByProfissionalId(Long profissionalId);
}
