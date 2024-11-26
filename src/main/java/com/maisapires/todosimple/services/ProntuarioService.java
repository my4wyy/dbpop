package com.maisapires.todosimple.services;

import com.maisapires.todosimple.models.Prontuario;
import com.maisapires.todosimple.models.Procedimento;
import com.maisapires.todosimple.models.DetalheProcedimento;
import com.maisapires.todosimple.models.dto.ProntuarioCreateDTO;
import com.maisapires.todosimple.models.dto.ProntuarioUpdateDTO;
import com.maisapires.todosimple.models.dto.DetalheProcedimentoDTO;
import com.maisapires.todosimple.repositories.ProntuarioRepository;
import com.maisapires.todosimple.repositories.ProcedimentoRepository;
import com.maisapires.todosimple.repositories.DetalheProcedimentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private DetalheProcedimentoRepository detalheProcedimentoRepository;

    public Prontuario findById(Long id) {
        return prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));
    }

    public List<Prontuario> findAll() {
        return prontuarioRepository.findAll();
    }

    @Transactional
    public Prontuario create(ProntuarioCreateDTO dto) {
        Prontuario prontuario = convertToEntity(dto);
        Prontuario savedProntuario = prontuarioRepository.save(prontuario);
        detalheProcedimentoRepository.saveAll(prontuario.getDetalhesProcedimentos());
        return savedProntuario;
    }

    @Transactional
    public Prontuario update(ProntuarioUpdateDTO dto, Long id) {
        Prontuario prontuario = findById(id);
        prontuario.setNomeCliente(dto.getNomeCliente());

        detalheProcedimentoRepository.deleteAll(prontuario.getDetalhesProcedimentos());

        List<DetalheProcedimento> detalhesProcedimentos = dto.getDetalhesProcedimentos().stream()
                .map(this::convertToEntity)
                .peek(dp -> dp.setProntuario(prontuario))
                .collect(Collectors.toList());

        prontuario.setDetalhesProcedimentos(detalhesProcedimentos);
        Prontuario savedProntuario = prontuarioRepository.save(prontuario);
        detalheProcedimentoRepository.saveAll(detalhesProcedimentos);

        return savedProntuario;
    }

    @Transactional
    public void delete(Long id) {
        Prontuario prontuario = findById(id);
        prontuarioRepository.delete(prontuario);
    }

    public List<Prontuario> searchByNomeCliente(String nomeCliente) {
        return prontuarioRepository.findByNomeClienteContainingIgnoreCase(nomeCliente);
    }

    private Prontuario convertToEntity(ProntuarioCreateDTO dto) {
        Prontuario prontuario = new Prontuario();
        prontuario.setNomeCliente(dto.getNomeCliente());

        List<DetalheProcedimento> detalhesProcedimentos = dto.getDetalhesProcedimentos().stream()
                .map(this::convertToEntity)
                .peek(dp -> dp.setProntuario(prontuario))
                .collect(Collectors.toList());

        prontuario.setDetalhesProcedimentos(detalhesProcedimentos);
        return prontuario;
    }

    private DetalheProcedimento convertToEntity(DetalheProcedimentoDTO dto) {
        DetalheProcedimento detalheProcedimento = new DetalheProcedimento();

        Procedimento procedimento = procedimentoRepository.findById(dto.getProcedimentoId())
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));
        detalheProcedimento.setProcedimento(procedimento);
        detalheProcedimento.setDetalhes(dto.getDetalhes());

        return detalheProcedimento;
    }
}
