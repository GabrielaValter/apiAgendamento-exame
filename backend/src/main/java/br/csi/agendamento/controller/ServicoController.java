package br.csi.agendamento.controller;

import br.csi.agendamento.dto.CriarServicoDTO;
import br.csi.agendamento.model.Servico;
import br.csi.agendamento.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Cadastro e gerenciamento de serviços oferecidos pelos profissionais")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Criar serviço",
            description = "Cria um novo serviço com nome, descrição e valor."
    )
    public ResponseEntity<Servico> criar(@Valid @RequestBody CriarServicoDTO dto) {
        var s = service.criar(dto);
        return ResponseEntity.created(URI.create("/servicos/" + s.getId())).body(s);
    }

    @GetMapping
    @Operation(
            summary = "Listar serviços",
            description = "Retorna a lista de todos os serviços cadastrados."
    )
    public List<Servico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar serviço por ID",
            description = "Busca um serviço específico pelo seu identificador."
    )
    public Servico buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar serviço",
            description = "Atualiza os dados de um serviço existente."
    )
    public Servico atualizar(@PathVariable Long id, @Valid @RequestBody CriarServicoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover serviço",
            description = "Remove um serviço cadastrado pelo seu ID."
    )
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
