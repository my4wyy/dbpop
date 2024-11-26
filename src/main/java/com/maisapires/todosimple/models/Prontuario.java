package com.maisapires.todosimple.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "prontuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cliente", nullable = false, length = 100)
    @NotBlank
    private String nomeCliente;

    @OneToMany(mappedBy = "prontuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // Corrigido para gerenciar o relacionamento
    private List<DetalheProcedimento> detalhesProcedimentos;
}
