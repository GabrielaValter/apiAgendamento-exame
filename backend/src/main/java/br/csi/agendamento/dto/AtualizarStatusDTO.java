package br.csi.agendamento.dto;

import br.csi.agendamento.model.Agendamento;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
        @NotNull Agendamento.Status status
) {}

