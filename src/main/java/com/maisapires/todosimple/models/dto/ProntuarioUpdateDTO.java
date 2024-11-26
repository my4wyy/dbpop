package com.maisapires.todosimple.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import lombok.Data;

@Data
public class ProntuarioUpdateDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String nomeCliente;

    private List<DetalheProcedimentoDTO> detalhesProcedimentos;

}
