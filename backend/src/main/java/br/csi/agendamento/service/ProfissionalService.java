package br.csi.agendamento.service;

import br.csi.agendamento.model.Profissional;
import br.csi.agendamento.model.Servico;
import br.csi.agendamento.model.Usuario;
import br.csi.agendamento.repository.ProfissionalRepository;
import br.csi.agendamento.repository.ServicoRepository;
import br.csi.agendamento.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ServicoRepository servicoRepository;

    public ProfissionalService(ProfissionalRepository repository,
                               UsuarioRepository usuarioRepository,
                               ServicoRepository servicoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.servicoRepository = servicoRepository;
    }

    @Transactional
    public void salvar(Profissional profissional) {
        if (profissional.getUsuario() == null || profissional.getUsuario().getId() == null) {
            throw new IllegalArgumentException("É obrigatório informar o usuário (id) do profissional.");
        }

        // carrega o usuário e valida o tipo = 'P'
        Long usuarioId = profissional.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inexistente."));

        if (!"P".equalsIgnoreCase(usuario.getTipo())) {
            throw new IllegalArgumentException("O usuário informado não é do tipo 'P' (Profissional).");
        }

        // id do profissional = id do usuario
        profissional.setId(usuario.getId());
        profissional.setUsuario(usuarioRepository.getReferenceById(usuario.getId()));

        repository.save(profissional);
    }

    public List<Profissional> listar() {
        return repository.findAll();
    }

    public Profissional getProfissional(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void atualizar(Profissional novosDados) {
        if (novosDados.getId() == null) {
            throw new IllegalArgumentException("Id do profissional é obrigatório para atualizar.");
        }

        Profissional p = repository.getReferenceById(novosDados.getId());

        // atualiza campos simples
        p.setArea(novosDados.getArea());
        p.setDescricao(novosDados.getDescricao());

        repository.save(p);
    }

    // vincula um serviço ao profissional
    @Transactional
    public void vincularServico(Long profissionalId, Long servicoId) {
        Profissional p = repository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));
        Servico s = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));

        p.getServicos().add(s);
        repository.save(p);
    }

    @Transactional
    public void desvincularServico(Long profissionalId, Long servicoId) {
        Profissional p = repository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado."));
        Servico s = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado."));

        p.getServicos().remove(s);
        repository.save(p);
    }

    // lista os serviços oferecidos por um profissional
    public List<Servico> listarServicosDoProfissional(Long profissionalId) {
        // usa o repositório de serviço com a JPQL que consulta p.servicos
        return servicoRepository.findAllByProfissionalId(profissionalId);
    }
}
