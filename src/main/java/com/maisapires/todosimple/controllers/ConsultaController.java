package com.maisapires.todosimple.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maisapires.todosimple.models.Consulta;
import com.maisapires.todosimple.models.dto.ConsultaCreateDTO;
import com.maisapires.todosimple.models.dto.ConsultaUpdateDTO;
import com.maisapires.todosimple.models.enums.StatusConsulta;
import com.maisapires.todosimple.repositories.ConsultaRepository;
import com.maisapires.todosimple.services.ConsultaService;
import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> findById(@PathVariable Long id) {
        Consulta obj = consultaService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Consulta>> findAll() {
        List<Consulta> consultas = consultaService.findAll();
        return ResponseEntity.ok().body(consultas);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ConsultaCreateDTO obj) {
        Consulta newConsulta = consultaService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newConsulta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Consulta> update(@Valid @RequestBody ConsultaUpdateDTO obj) {
        consultaService.update(obj, obj.getId());
        Consulta objResponse = consultaService.findById(obj.getId());
        return ResponseEntity.ok().body(objResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Consulta>> findByDate(@PathVariable String data) {
        LocalDate date = LocalDate.parse(data); // Convertendo a String para LocalDate
        List<Consulta> consultas = consultaService.findByDate(date);
        return ResponseEntity.ok().body(consultas);
    }

    @GetMapping("/consultas/{data}")
    public ResponseEntity<List<Consulta>> getConsultasPorData(@PathVariable("data") LocalDate data) {
        List<Consulta> consultas = consultaService.findByDate(data);
        return ResponseEntity.ok().body(consultas);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Consulta> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String novoStatusStr = request.get("status");

        if (novoStatusStr == null) {
            return ResponseEntity.badRequest().body(null); // Retorna erro caso o status seja nulo
        }

        StatusConsulta novoStatus;
        try {
            novoStatus = StatusConsulta.valueOf(novoStatusStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Retorna erro caso o status não seja válido
        }

        Optional<Consulta> consultaOpt = consultaRepository.findById(id);
        if (consultaOpt.isPresent()) {
            Consulta consulta = consultaOpt.get();
            consulta.setStatus(novoStatus);
            consultaRepository.save(consulta); // Salva o novo status no banco
            return ResponseEntity.ok(consulta); // Retorna o objeto atualizado
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 se a consulta não for encontrada
        }

    }

    @PostMapping("/{id}/observacoes")
    public ResponseEntity<Void> addObservacoes(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String observacoes = request.get("observacoes");
        consultaService.addObservacoes(id, observacoes);
        return ResponseEntity.ok().build();
    }

}
