package com.maisapires.todosimple.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maisapires.todosimple.models.Procedimento;
import com.maisapires.todosimple.models.dto.ProcedimentoCreateDTO;
import com.maisapires.todosimple.models.dto.ProcedimentoUpdateDTO;
import com.maisapires.todosimple.services.ProcedimentoService;

@RestController
@RequestMapping("/procedimentos")
@Validated
public class ProcedimentoController {

    @Autowired
    private ProcedimentoService procedimentoService;

    @GetMapping("/{id}")
    public ResponseEntity<Procedimento> findById(@PathVariable Long id) {
        Procedimento obj = procedimentoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    // Agora o método que busca todos os procedimentos está mapeado diretamente para /procedimentos
    @GetMapping
    public ResponseEntity<List<Procedimento>> findAll() {
        List<Procedimento> procedimentos = procedimentoService.findAll();
        return ResponseEntity.ok().body(procedimentos);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ProcedimentoCreateDTO obj) {
        Procedimento newProcedimento = procedimentoService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProcedimento.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Procedimento> update(@Valid @RequestBody ProcedimentoUpdateDTO obj) {
        procedimentoService.update(obj, obj.id);
        Procedimento objResponse = procedimentoService.findById(obj.id);
        return ResponseEntity.ok().body(objResponse);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestBody ProcedimentoUpdateDTO obj) {
        procedimentoService.delete(obj.id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/observacoes")
public ResponseEntity<Void> addObservacao(@PathVariable Long id, @RequestBody String observacoes) {
    procedimentoService.addObservacao(id, observacoes);
    return ResponseEntity.ok().build();
}

}
