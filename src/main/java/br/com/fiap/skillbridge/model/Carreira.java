package br.com.fiap.skillbridge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_CARREIRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carreira {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carreira")
    @SequenceGenerator(name = "seq_carreira", sequenceName = "SEQ_CARREIRA", allocationSize = 1)
    @Column(name = "ID_CARREIRA")
    private Long id;

    @NotBlank(message = "Título da carreira é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    @Column(name = "TITULO_CARREIRA", nullable = false, length = 100)
    private String tituloCarreira;

    @Lob
    @Column(name = "DESCRICAO")
    private String descricao;

    @Size(max = 50, message = "Área deve ter no máximo 50 caracteres")
    @Column(name = "AREA", length = 50)
    private String area;

    @Size(max = 20, message = "Nível de entrada deve ter no máximo 20 caracteres")
    @Column(name = "NIVEL_ENTRADA", length = 20, insertable = false)
    private String nivelEntrada;

    @PrePersist
    protected void onCreate() {
        if (nivelEntrada == null) {
            nivelEntrada = "BASICO";
        }
    }
}
