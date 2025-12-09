package br.csi.agendamento.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CriarServicoDTO(
        @NotNull @Size(min=2, max=60) String nome,
        @NotNull String descricao,
        @NotNull @PositiveOrZero BigDecimal valor
) {}