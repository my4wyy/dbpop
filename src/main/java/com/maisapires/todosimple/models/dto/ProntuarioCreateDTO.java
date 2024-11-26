package com.maisapires.todosimple.models.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

import lombok.Data;

@Data
public class ProntuarioCreateDTO {

     @NotBlank(message = "O nome do cliente não pode estar em branco")
    private String nomeCliente;
    

    private List<DetalheProcedimentoDTO> detalhesProcedimentos;

}
