package com.maisapires.todosimple.models;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.maisapires.todosimple.models.enums.StatusConsulta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consulta")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cliente", nullable = false)
    private String cliente;

    @NotBlank
    @Column(name = "profissional", nullable = false)
    private String profissional;

    @ManyToOne
    @JoinColumn(name = "procedimento_id", nullable = false)
    private Procedimento procedimento;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConsulta status = StatusConsulta.PENDENTE;

    @Column(name = "observacoes", length = 500)
    private String observacoes; // Novo campo para observações
}
