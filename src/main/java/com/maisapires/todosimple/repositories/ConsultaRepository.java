package com.maisapires.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;

import com.maisapires.todosimple.models.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByProfissionalAndData(String string, LocalDate data);
    List<Consulta> findByData(LocalDate data);
    List<Consulta> findByDataBefore(LocalDate data);
}

