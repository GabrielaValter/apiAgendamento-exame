package br.csi.agendamento.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CriarAgendamentoDTO (
        @NotNull Long clienteId,
        @NotNull Long profissionalId,
        @NotNull Long servicoId,
        @NotNull @FutureOrPresent LocalDate data,
        String observacao
) {}

