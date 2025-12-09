package br.csi.agendamento.controller;

import br.csi.agendamento.dto.AtualizarStatusDTO;
import br.csi.agendamento.dto.CriarAgendamentoDTO;
import br.csi.agendamento.model.Agendamento;
import br.csi.agendamento.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Tag(name = "Agendamentos", description = "Agendamentos entre clientes, profissionais e serviços")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Criar agendamento",
            description = "Cria um novo agendamento informando cliente, profissional, serviço, data e observação."
    )
    public ResponseEntity<Agendamento> criar(@Valid @RequestBody CriarAgendamentoDTO dto) {
        var a = new Agendamento();
        a.setCliente(new br.csi.agendamento.model.Usuario());
        a.getCliente().setId(dto.clienteId());
        a.setProfissional(new br.csi.agendamento.model.Profissional());
        a.getProfissional().setId(dto.profissionalId());
        a.setServico(new br.csi.agendamento.model.Servico());
        a.getServico().setId(dto.servicoId());
        a.setData(dto.data());
        a.setObservacao(dto.observacao());
        service.salvar(a);
        return ResponseEntity.created(URI.create("/agendamentos/" + a.getId())).body(a);
    }

    @GetMapping
    @Operation(
            summary = "Listar agendamentos",
            description = "Retorna todos os agendamentos cadastrados no sistema."
    )
    public List<Agendamento> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar agendamento por ID",
            description = "Busca um agendamento específico pelo seu identificador."
    )
    public Agendamento buscar(@PathVariable Long id) {
        return service.getAgendamento(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir agendamento",
            description = "Remove um agendamento do sistema pelo seu ID."
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status do agendamento",
            description = "Atualiza apenas o status de um agendamento (PENDENTE, CONFIRMADO, CANCELADO, CONCLUIDO)."
    )
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @Valid @RequestBody AtualizarStatusDTO dto) {
        service.atualizarStatus(id, dto.status());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(
            summary = "Listar agendamentos de um cliente",
            description = "Retorna todos os agendamentos associados a um cliente específico."
    )
    public List<Agendamento> doCliente(@PathVariable Long clienteId) {
        return service.listarDoCliente(clienteId);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(
            summary = "Listar agendamentos de um profissional",
            description = "Retorna todos os agendamentos associados a um profissional específico."
    )
    public List<Agendamento> doProfissional(@PathVariable Long profissionalId) {
        return service.listarDoProfissional(profissionalId);
    }
}
