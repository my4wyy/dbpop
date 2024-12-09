package com.maisapires.todosimple.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maisapires.todosimple.models.dto.PagamentoDTO;
import com.maisapires.todosimple.services.PagamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO novoPagamento = pagamentoService.criarPagamento(pagamentoDTO);
        return ResponseEntity.ok(novoPagamento);
    }

    @GetMapping
    public List<PagamentoDTO> listarPagamentos() {
        return pagamentoService.listarPagamentos();
    }
    
    @GetMapping("/{id}")
public ResponseEntity<PagamentoDTO> buscarPagamentoPorId(@PathVariable Long id) {
    Optional<PagamentoDTO> pagamentoOpt = pagamentoService.buscarPagamentoPorId(id);
    if (pagamentoOpt.isPresent()) {
        return ResponseEntity.ok(pagamentoOpt.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
