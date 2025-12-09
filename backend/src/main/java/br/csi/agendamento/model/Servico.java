package br.csi.agendamento.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Servico")
@Table(name = "servico")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Serviço oferecido na clínica")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do serviço", example = "1")
    private Long id;

    @NonNull
    @Column(nullable = false, length = 60)
    @Schema(description = "Nome do serviço prestado")
    private String nome;

    @NonNull
    @Column(nullable = false)
    @Schema(description = "Descrição do atendimento")
    private String descricao;

    @NonNull
    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Valor cobrado pela consulta")
    private BigDecimal valor;

}
