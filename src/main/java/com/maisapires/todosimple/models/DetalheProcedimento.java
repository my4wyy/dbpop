package com.maisapires.todosimple.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalhes_procedimento")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalheProcedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "procedimento_id", nullable = false)
    @NotNull
    private Procedimento procedimento;

    @ManyToOne
    @JoinColumn(name = "prontuario_id", nullable = false)
    @JsonBackReference // Corrigido para evitar ciclo
    private Prontuario prontuario;

    @Column(name = "detalhes", length = 500)
    private String detalhes;
}
