package com.maisapires.todosimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maisapires.todosimple.models.Pagamento;
import com.maisapires.todosimple.models.Procedimento;
import com.maisapires.todosimple.models.dto.PagamentoDTO;
import com.maisapires.todosimple.repositories.PagamentoRepository;
import com.maisapires.todosimple.repositories.ProcedimentoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    // Converte de Pagamento para PagamentoDTO
    private PagamentoDTO convertToDTO(Pagamento pagamento) {
        return new PagamentoDTO(
            pagamento.getId(),
            pagamento.getNomeCliente(),
            pagamento.getMetodoPagamento(),
            pagamento.getValor(),
            pagamento.getProcedimento().getId()
        );
    }

    // Converte de PagamentoDTO para Pagamento
    private Pagamento convertToEntity(PagamentoDTO pagamentoDTO, Procedimento procedimento) {
        return new Pagamento(
            pagamentoDTO.getNomeCliente(),
            pagamentoDTO.getMetodoPagamento(),
            pagamentoDTO.getValor(),
            procedimento
        );
    }

    // Criar pagamento
    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Optional<Procedimento> procedimentoOpt = procedimentoRepository.findById(pagamentoDTO.getProcedimentoId());
        if (!procedimentoOpt.isPresent()) {
            throw new RuntimeException("Procedimento não encontrado!");
        }

        Procedimento procedimento = procedimentoOpt.get();

        // Converte DTO para entidade
        Pagamento pagamento = convertToEntity(pagamentoDTO, procedimento);
        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        // Retorna o DTO atualizado com o ID
        return convertToDTO(pagamentoSalvo);
    }

    // Listar todos os pagamentos
public List<PagamentoDTO> listarPagamentos() {
    List<Pagamento> pagamentos = pagamentoRepository.findAll();
    // Converte a lista de Pagamento para PagamentoDTO
    return pagamentos.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

// Buscar pagamento por ID
public Optional<PagamentoDTO> buscarPagamentoPorId(Long id) {
    Optional<Pagamento> pagamentoOpt = pagamentoRepository.findById(id);
    // Converte Optional<Pagamento> para Optional<PagamentoDTO>
    return pagamentoOpt.map(this::convertToDTO);
}


    // Deletar pagamento
    public void deletarPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
