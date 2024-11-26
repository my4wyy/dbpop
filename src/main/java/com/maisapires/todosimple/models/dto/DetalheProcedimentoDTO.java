package com.maisapires.todosimple.models.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DetalheProcedimentoDTO {

    @NotNull
    private Long procedimentoId;

    private String detalhes;
}
