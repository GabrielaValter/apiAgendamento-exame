package br.csi.agendamento.controller;

import br.csi.agendamento.dto.CriarProfissionalDTO;
import br.csi.agendamento.model.Profissional;
import br.csi.agendamento.model.Servico;
import br.csi.agendamento.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/profissionais")
@Tag(name = "Profissionais", description = "Cadastro de profissionais e vinculação com serviços")
public class ProfissionalController {

    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Criar profissional",
            description = "Cria um profissional a partir de um usuário já existente."
    )
    public ResponseEntity<Profissional> criar(@Valid @RequestBody CriarProfissionalDTO dto) {
        var p = new Profissional();
        p.setUsuario(new br.csi.agendamento.model.Usuario());
        p.getUsuario().setId(dto.usuarioId());
        p.setArea(dto.area());
        p.setDescricao(dto.descricao());
        service.salvar(p);
        return ResponseEntity.created(URI.create("/profissionais/" + p.getId())).body(p);
    }

    @GetMapping
    @Operation(
            summary = "Listar profissionais",
            description = "Retorna a lista de todos os profissionais cadastrados."
    )
    public List<Profissional> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar profissional por ID",
            description = "Busca um profissional específico pelo seu identificador."
    )
    public Profissional buscar(@PathVariable Long id) {
        return service.getProfissional(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar profissional",
            description = "Atualiza as informações de área e descrição de um profissional."
    )
    public Profissional atualizar(@PathVariable Long id, @Valid @RequestBody CriarProfissionalDTO dto) {
        var p = new Profissional();
        p.setId(id);
        p.setArea(dto.area());
        p.setDescricao(dto.descricao());
        service.atualizar(p);
        return service.getProfissional(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir profissional",
            description = "Remove um profissional do sistema."
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{profissionalId}/servicos/{servicoId}")
    @Operation(
            summary = "Vincular serviço a profissional",
            description = "Associa um serviço a um profissional."
    )
    public ResponseEntity<Void> vincular(@PathVariable Long profissionalId, @PathVariable Long servicoId) {
        service.vincularServico(profissionalId, servicoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{profissionalId}/servicos/{servicoId}")
    @Operation(
            summary = "Desvincular serviço de profissional",
            description = "Remove a associação de um serviço com um profissional."
    )
    public ResponseEntity<Void> desvincular(@PathVariable Long profissionalId, @PathVariable Long servicoId) {
        service.desvincularServico(profissionalId, servicoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profissionalId}/servicos")
    @Operation(
            summary = "Listar serviços de um profissional",
            description = "Retorna a lista de serviços vinculados a um profissional específico."
    )
    public List<Servico> listarServicos(@PathVariable Long profissionalId) {
        return service.listarServicosDoProfissional(profissionalId);
    }
}
