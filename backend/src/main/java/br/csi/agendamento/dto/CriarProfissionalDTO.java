package br.csi.agendamento.dto;

import jakarta.validation.constraints.*;

public record CriarProfissionalDTO(
        @NotNull Long usuarioId,
        @NotNull @Size(min=2, max=60) String area,
        @NotNull String descricao
) {}
