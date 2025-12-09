package br.csi.agendamento.controller;

import br.csi.agendamento.dto.CriarUsuarioDTO;
import br.csi.agendamento.model.Usuario;
import br.csi.agendamento.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações de cadastro, consulta, atualização e exclusão de usuários")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário (cliente ou profissional) com nome, e-mail, senha, telefone e tipo."
    )
    public ResponseEntity<Usuario> criar(@Valid @RequestBody CriarUsuarioDTO dto) {
        var u = new Usuario();
        u.setNome(dto.nome());
        u.setEmail(dto.email());
        u.setSenha(dto.senha());
        u.setTelefone(dto.telefone());
        u.setTipo(dto.tipo());
        service.salvar(u);
        return ResponseEntity.created(URI.create("/usuarios/" + u.getId())).body(u);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna a lista completa de usuários cadastrados."
    )
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Busca um usuário específico pelo seu identificador."
    )
    public Usuario buscar(@PathVariable Long id) {
        return service.getUsuario(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza todos os dados de um usuário existente."
    )
    public Usuario atualizar(@PathVariable Long id, @Valid @RequestBody CriarUsuarioDTO dto) {
        var u = new Usuario();
        u.setId(id);
        u.setNome(dto.nome());
        u.setEmail(dto.email());
        u.setSenha(dto.senha());
        u.setTelefone(dto.telefone());
        u.setTipo(dto.tipo());
        service.atualizar(u);
        return service.getUsuario(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir usuário",
            description = "Remove um usuário do sistema pelo seu ID."
    )
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
