package com.maisapires.todosimple.models.dto;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcedimentoUpdateDTO {

    public Long id;

    @Size(min = 2, max = 100)
    public String nomeProcedimento;

    public Integer duracaoHoras;

    public Integer duracaoMinutos;

    public Double preco;

    public String materiaisNecessarios;
}
