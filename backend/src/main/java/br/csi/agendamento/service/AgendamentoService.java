package br.csi.agendamento.service;

import br.csi.agendamento.model.Agendamento;
import br.csi.agendamento.model.Profissional;
import br.csi.agendamento.model.Servico;
import br.csi.agendamento.model.Usuario;
import br.csi.agendamento.repository.AgendamentoRepository;
import br.csi.agendamento.repository.ProfissionalRepository;
import br.csi.agendamento.repository.ServicoRepository;
import br.csi.agendamento.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <— use o Transactional do Spring

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              UsuarioRepository usuarioRepository,
                              ProfissionalRepository profissionalRepository,
                              ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.profissionalRepository = profissionalRepository;
        this.servicoRepository = servicoRepository;
    }

    // crud
    @Transactional
    public void salvar(Agendamento agendamento) {
        // busca os ids vindos do json
        Long clienteId = agendamento.getCliente().getId();
        Long profissionalId = agendamento.getProfissional().getId();
        Long servicoId = agendamento.getServico().getId();
        LocalDate data = agendamento.getData();

        validar(clienteId, profissionalId, servicoId, data);

        agendamento.setCliente(usuarioRepository.getReferenceById(clienteId));
        agendamento.setProfissional(profissionalRepository.getReferenceById(profissionalId));
        agendamento.setServico(servicoRepository.getReferenceById(servicoId));

        // Status inicial, caso não venha
        if (agendamento.getStatus() == null) {
            agendamento.setStatus(Agendamento.Status.PENDENTE);
        }

        agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> listar() {
        return agendamentoRepository.findAll();
    }

    public Agendamento getAgendamento(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento inexistente."));
    }

    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }

    @Transactional
    public void atualizarStatus(Long id, Agendamento.Status status) {
        Agendamento a = agendamentoRepository.getReferenceById(id);
        a.setStatus(status);
        agendamentoRepository.save(a);
    }

    public List<Agendamento> listarDoCliente(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId);
    }

    public List<Agendamento> listarDoProfissional(Long profissionalId) {
        return agendamentoRepository.findByProfissionalId(profissionalId);
    }

    // regras de negócio
    private void validar(Long clienteId, Long profissionalId, Long servicoId, LocalDate data) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente inexistente."));
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional inexistente."));
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço inexistente."));

        // cliente ou profissional
        if (!"C".equalsIgnoreCase(cliente.getTipo())) {
            throw new IllegalArgumentException("Usuário informado não é cliente (tipo 'C').");
        }
        if (!"P".equalsIgnoreCase(profissional.getUsuario().getTipo())) {
            throw new IllegalArgumentException("Usuário associado ao profissional não é do tipo 'P'.");
        }

        // não permite autoatendimento
        if (cliente.getId().equals(profissional.getId())) {
            throw new IllegalArgumentException("Cliente e profissional devem ser diferentes.");
        }

        // o profissional oferece o serviço
        boolean oferece = profissional.getServicos().stream()
                .anyMatch(s -> s.getId().equals(servico.getId()));
        if (!oferece) {
            throw new IllegalArgumentException("Profissional não oferece o serviço informado.");
        }

        // conflitos por dia, futuramente implementar horários de consultas
        if (!agendamentoRepository.findByProfissionalIdAndData(profissionalId, data).isEmpty()) {
            throw new IllegalArgumentException("O profissional já possui agendamento neste dia.");
        }
        if (!agendamentoRepository.findByClienteIdAndData(clienteId, data).isEmpty()) {
            throw new IllegalArgumentException("O cliente já possui agendamento neste dia.");
        }
    }
}
