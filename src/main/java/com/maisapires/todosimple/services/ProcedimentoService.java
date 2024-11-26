package com.maisapires.todosimple.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maisapires.todosimple.models.Procedimento;
import com.maisapires.todosimple.models.dto.ProcedimentoCreateDTO;
import com.maisapires.todosimple.models.dto.ProcedimentoUpdateDTO;
import com.maisapires.todosimple.repositories.ProcedimentoRepository;
import com.maisapires.todosimple.services.exceptions.DataBindingViolationException;
import com.maisapires.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class ProcedimentoService {

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    public Procedimento findById(Long id) {
        Optional<Procedimento> procedimento = this.procedimentoRepository.findById(id);
        return procedimento.orElseThrow(() -> new ObjectNotFoundException(
                "Procedimento não encontrado! Id: " + id + ", Tipo: " + Procedimento.class.getName()));
    }

    public List<Procedimento> findAll() {
        return procedimentoRepository.findAll();
    }

    @Transactional
    public Procedimento create(ProcedimentoCreateDTO obj) {
        Procedimento procedimento = fromDTO(obj);
        procedimento.setId(null);
        return this.procedimentoRepository.save(procedimento);
    }

    public Procedimento update(ProcedimentoUpdateDTO obj, Long id) {
        Procedimento procedimento = findById(id);
        procedimento.setNomeProcedimento(obj.getNomeProcedimento());
        procedimento.setPreco(obj.getPreco());
        procedimento.setDuracaoHoras(obj.getDuracaoHoras());
        procedimento.setDuracaoMinutos(obj.getDuracaoMinutos());
        procedimento.setMateriaisNecessarios(obj.getMateriaisNecessarios());
        return procedimentoRepository.save(procedimento);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.procedimentoRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    public Procedimento fromDTO(@Valid ProcedimentoCreateDTO obj) {
        Procedimento procedimento = new Procedimento();
        procedimento.setNomeProcedimento(obj.getNomeProcedimento());
        procedimento.setPreco(obj.getPreco());
        procedimento.setDuracaoHoras(obj.getDuracaoHoras());
        procedimento.setDuracaoMinutos(obj.getDuracaoMinutos());
        procedimento.setMateriaisNecessarios(obj.getMateriaisNecessarios());
        return procedimento;
    }

    public Procedimento fromDTO(@Valid ProcedimentoUpdateDTO obj) {
        Procedimento procedimento = new Procedimento();
        procedimento.setId(obj.getId()); // Define o ID do procedimento a ser atualizado
        procedimento.setNomeProcedimento(obj.getNomeProcedimento());
        procedimento.setPreco(obj.getPreco());
        procedimento.setDuracaoHoras(obj.getDuracaoHoras());
        procedimento.setDuracaoMinutos(obj.getDuracaoMinutos());
        procedimento.setMateriaisNecessarios(obj.getMateriaisNecessarios());
        return procedimento;
    }

    public void addObservacao(Long procedimentoId, String observacoes) {
        Procedimento procedimento = findById(procedimentoId);
        procedimento.setObservacoes(observacoes);
        procedimentoRepository.save(procedimento); // Salva a atualização no banco de dados
    }

}
