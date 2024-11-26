package com.maisapires.todosimple.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.maisapires.todosimple.models.enums.StatusConsulta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsultaUpdateDTO {

    private Long id;

    private String cliente;

    private String profissional;

    private Long procedimentoId;  // ID do Procedimento já existente

    private LocalDate data;

    private LocalTime horario;

    public StatusConsulta getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }
}
