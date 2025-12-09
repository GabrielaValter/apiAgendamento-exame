package br.csi.agendamento.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Profissional")
@Table(name = "profissional")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informações específicas de um profissional")
public class Profissional {
    @Id
    private Long id;

    @OneToOne(optional = false) // o JPA não aceita null
    @MapsId // o JPA usar a coluna id tanto como PK do profissional quanto como FK para usuario(id)
    @JoinColumn(name = "id") // a coluna de join se chama exatamente "id" na tabela profissional
    @NonNull
    @Schema(description = "Usuário associado ao profissional")
    private Usuario usuario;

    @NonNull
    @Column(nullable = false, length = 60)
    @Schema(description = "Área de atuação do profissional", example = "Reumatologia")
    private String area;

    @NonNull
    @Column(nullable = false)
    @Schema(description = "Descrição do profissional", example = "Especialista em terapias musculares e reabilitação")
    private String descricao;

    // cumpre a regra de que um profissional ofereçe um serviço, mapeia a tabela de jução profissional_servico
    @ManyToMany
    @JoinTable(
            name = "profissional_servico",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private java.util.Set<Servico> servicos = new java.util.HashSet<>();

}
