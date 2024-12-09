package com.maisapires.todosimple.models.dto;

public class PagamentoDTO {

    private Long id;
    private String nomeCliente;
    private String metodoPagamento;
    private Double valor;
    private Long procedimentoId;

    // Construtores
    public PagamentoDTO() {}

    public PagamentoDTO(Long id, String nomeCliente, String metodoPagamento, Double valor, Long procedimentoId) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.metodoPagamento = metodoPagamento;
        this.valor = valor;
        this.procedimentoId = procedimentoId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getProcedimentoId() {
        return procedimentoId;
    }

    public void setProcedimentoId(Long procedimentoId) {
        this.procedimentoId = procedimentoId;
    }
}
