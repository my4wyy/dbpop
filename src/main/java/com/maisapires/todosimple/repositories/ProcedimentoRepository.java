package com.maisapires.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.maisapires.todosimple.models.Procedimento;

@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    @Transactional(readOnly = true)
    Procedimento findByNomeProcedimento(String nomeProcedimento);

}
