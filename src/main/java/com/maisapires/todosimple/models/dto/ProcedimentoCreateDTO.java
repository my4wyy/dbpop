package com.maisapires.todosimple.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcedimentoCreateDTO {

    public Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nomeProcedimento;

    @NotNull
    private Integer duracaoHoras;

    @NotNull
    private Integer duracaoMinutos;

    @NotNull
    private Double preco;

    private String materiaisNecessarios;
}
