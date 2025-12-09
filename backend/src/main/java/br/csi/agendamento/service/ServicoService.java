package br.csi.agendamento.service;

import br.csi.agendamento.dto.CriarServicoDTO;
import br.csi.agendamento.model.Servico;
import br.csi.agendamento.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicoService {
    private final ServicoRepository repo;
    public ServicoService(ServicoRepository repo){ this.repo=repo; }

    @Transactional
    public Servico criar(CriarServicoDTO dto){
        var s = new Servico();
        s.setNome(dto.nome());
        s.setDescricao(dto.descricao());
        s.setValor(dto.valor());
        return repo.save(s);
    }

    public List<Servico> listar(){ return repo.findAll(); }

    public Servico buscar(Long id){
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado"));
    }

    @Transactional
    public Servico atualizar(Long id, CriarServicoDTO dto){
        var s = buscar(id);
        s.setNome(dto.nome());
        s.setDescricao(dto.descricao());
        s.setValor(dto.valor());
        return repo.save(s);
    }

    @Transactional
    public void remover(Long id){
        if (!repo.existsById(id)) throw new EntityNotFoundException("Serviço não encontrado");
        repo.deleteById(id);
    }
}
