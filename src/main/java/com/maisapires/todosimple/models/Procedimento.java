package com.maisapires.todosimple.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "procedimento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_procedimento", nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String nomeProcedimento;

    @Column(name = "duracao_horas", nullable = false)
    @NotNull
    private Integer duracaoHoras;

    @Column(name = "duracao_minutos", nullable = false)
    @NotNull
    private Integer duracaoMinutos;

    @Column(name = "preco", nullable = false)
    @NotNull
    private Double preco;

    @Column(name = "materiais_necessarios", length = 255)
    @Size(max = 255)
    private String materiaisNecessarios;

    @Column(name = "observacoes", length = 500)
    private String observacoes; // Novo campo para observações
}
