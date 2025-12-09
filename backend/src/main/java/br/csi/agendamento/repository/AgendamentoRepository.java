package br.csi.agendamento.repository;

import br.csi.agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    // listar os clientes
    List<Agendamento> findByClienteId(Long clienteId);
    // listar profissionais
    List<Agendamento> findByProfissionalId(Long profissionalId);
    // filtrar pelo status do agendamento
    List<Agendamento> findByStatus(Agendamento.Status status);
    // verificar conflitos por data para um profissional, futuramente checar por hora
    List<Agendamento> findByProfissionalIdAndData(Long profissionalId, LocalDate data);
    List<Agendamento> findByClienteIdAndData(Long clienteId, LocalDate data);
}

