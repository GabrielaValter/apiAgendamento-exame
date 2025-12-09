package br.csi.agendamento.dto;

import jakarta.validation.constraints.*;

public record CriarUsuarioDTO(
        @NotNull @Size(min=2, max=60) String nome,
        @NotNull @Email String email,
        @NotNull @Size(min=3, max=255) String senha,
        String telefone,
        @NotNull @Pattern(regexp="^[CP]$", message="tipo deve ser 'C' ou 'P'") String tipo
) {}
