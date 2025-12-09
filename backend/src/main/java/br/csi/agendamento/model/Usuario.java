package br.csi.agendamento.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Usuario")
@Table(name = "usuario")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um usuario no sistema")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do usuário", example = "1")
    private Long id;

    @UuidGenerator // serve para id que são expostos publicamente
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @NonNull  // impede que o java aceite valores nulos
    @Column(nullable = false, length = 60 )  // impede que o banco aceita valores nulos
    @Schema(description = "Nome do usuário", example = "João")
    private String nome;

    @NonNull
    @Column(nullable = false, length = 120, unique = true )
    @Schema(description = "Email do usuário", example = "joao@gmail.com")
    private String email;

    @NonNull
    @Column(nullable = false, length = 255 )
    @Schema(description = "Senha para acessar a conta")
    private String senha;

    @Column(length = 20)
    @Schema(description = "Telefone do usuário", example = "(55)99999-9999")
    private String telefone;

    @Column(nullable = false, length = 1)
    @Schema(description = "Tipo do usuário: C = Cliente | P = Profissional")
    private String tipo;

    @PrePersist
    public void prePersist() {
        // se o objeto ainda não tem uuid, gera aqui
        if (uuid == null) uuid = java.util.UUID.randomUUID();
    }
}
