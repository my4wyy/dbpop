package com.maisapires.todosimple.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.maisapires.todosimple.models.Consulta;
import com.maisapires.todosimple.models.Procedimento;
import com.maisapires.todosimple.models.dto.ConsultaCreateDTO;
import com.maisapires.todosimple.models.dto.ConsultaUpdateDTO;
import com.maisapires.todosimple.repositories.ConsultaRepository;
import com.maisapires.todosimple.repositories.ProcedimentoRepository;
import com.maisapires.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    public Consulta findById(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        return consulta.orElseThrow(() -> new ObjectNotFoundException(
                "Consulta não encontrada! Id: " + id + ", Tipo: " + Consulta.class.getName()));
    }

    public List<Consulta> findAll() {
        return consultaRepository.findAll();
    }

    // Método para verificar conflito de horário com base na duração do procedimento
    private boolean hasConflict(String profissionalId, LocalDate localDate, LocalTime horario,
            Procedimento procedimento) {
        LocalTime startTime = horario;
        LocalTime endTime = horario.plusHours(procedimento.getDuracaoHoras())
                .plusMinutes(procedimento.getDuracaoMinutos());

        List<Consulta> consultas = consultaRepository.findByProfissionalAndData(profissionalId, localDate);
        for (Consulta c : consultas) {
            LocalTime existingStart = c.getHorario();
            LocalTime existingEnd = existingStart.plusHours(c.getProcedimento().getDuracaoHoras())
                    .plusMinutes(c.getProcedimento().getDuracaoMinutos());
            // Verifica se há conflito de horários
            if (startTime.isBefore(existingEnd) && endTime.isAfter(existingStart)) {
                return true; // Conflito detectado
            }
        }
        return false;
    }

    @Transactional
    public Consulta create(ConsultaCreateDTO obj) {
        Procedimento procedimento = procedimentoRepository.findById(obj.getProcedimentoId())
                .orElseThrow(() -> new ObjectNotFoundException("Procedimento não encontrado"));

        // Verifica conflito de horário
        if (hasConflict(obj.getProfissional(), obj.getData(), obj.getHorario(), procedimento)) {
            throw new IllegalArgumentException("Horário indisponível. Já existe uma consulta marcada nesse intervalo.");
        }

        Consulta consulta = fromDTO(obj, procedimento);
        return consultaRepository.save(consulta);
    }

    public Consulta update(ConsultaUpdateDTO obj, Long id) {
        Consulta consulta = findById(id);
        if (obj.getCliente() != null)
            consulta.setCliente(obj.getCliente());
        if (obj.getProfissional() != null)
            consulta.setProfissional(obj.getProfissional());
        if (obj.getProcedimentoId() != null) {
            Procedimento procedimento = procedimentoRepository.findById(obj.getProcedimentoId())
                    .orElseThrow(() -> new ObjectNotFoundException("Procedimento não encontrado"));
            consulta.setProcedimento(procedimento);
        }
        if (obj.getData() != null)
            consulta.setData(obj.getData());
        if (obj.getHorario() != null)
            consulta.setHorario(obj.getHorario());
        return consultaRepository.save(consulta);
    }

    public void delete(Long id) {
        findById(id);
        consultaRepository.deleteById(id);
    }

    public Consulta fromDTO(@Valid ConsultaCreateDTO obj, Procedimento procedimento) {
        Consulta consulta = new Consulta();
        consulta.setCliente(obj.getCliente());
        consulta.setProfissional(obj.getProfissional());
        consulta.setProcedimento(procedimento);
        consulta.setData(obj.getData());
        consulta.setHorario(obj.getHorario());

        // Define o status com o valor do DTO ou o padrão PENDENTE
        if (obj.getStatus() != null) {
            consulta.setStatus(obj.getStatus());
        }
        return consulta;
    }

    public List<Consulta> findByDate(LocalDate date) {
        return consultaRepository.findByData(date);
    }

    public List<Consulta> findAntesDeHoje() {
        LocalDate hoje = LocalDate.now();
        return consultaRepository.findByDataBefore(hoje);
    }

    // Método para adicionar observações a uma consulta específica
    public void addObservacoes(Long id, String observacoes) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada"));

        consulta.setObservacoes(observacoes); // Atualiza o campo observações
        consultaRepository.save(consulta); // Salva a alteração no banco de dados
    }

}
