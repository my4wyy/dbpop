package com.maisapires.todosimple.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maisapires.todosimple.models.Prontuario;
import com.maisapires.todosimple.models.dto.ProntuarioCreateDTO;
import com.maisapires.todosimple.models.dto.ProntuarioUpdateDTO;
import com.maisapires.todosimple.services.ProntuarioService;

@RestController
@RequestMapping("/prontuarios")
@Validated
public class ProntuarioController {

    @Autowired
    private ProntuarioService prontuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Prontuario> findById(@PathVariable Long id) {
        Prontuario obj = prontuarioService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Prontuario>> findAll() {
        List<Prontuario> prontuarios = prontuarioService.findAll();
        return ResponseEntity.ok().body(prontuarios);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ProntuarioCreateDTO obj) {
        Prontuario newProntuario = prontuarioService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProntuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prontuario> update(@Valid @RequestBody ProntuarioUpdateDTO obj, @PathVariable Long id) {
        Prontuario prontuario = prontuarioService.update(obj, id);
        return ResponseEntity.ok().body(prontuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prontuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
public ResponseEntity<List<Prontuario>> searchByNomeCliente(@RequestParam String nomeCliente) {
    List<Prontuario> prontuarios = prontuarioService.searchByNomeCliente(nomeCliente);
    return ResponseEntity.ok().body(prontuarios);
}

}
