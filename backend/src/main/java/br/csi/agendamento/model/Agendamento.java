package br.csi.agendamento.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Agendamento")
@Table(name = "agendamento")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Agendamentos feitos")
public class Agendamento {
    public enum Status {PENDENTE, CONFIRMADO, CANCELADO, CONCLUIDO}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do agendamento", example = "1")
    private Long id;

    @NonNull
    @Column(nullable = false)
    @Schema(description = "Dia da colsulta agendada")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status = Status.PENDENTE;

    @Schema(description = "Se o cliente precisar adicionar alguma observação")
    private String observacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servico_id")
    private Servico servico;
}
