package com.maisapires.todosimple.repositories;

import com.maisapires.todosimple.models.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    
    List<Prontuario> findByNomeClienteContainingIgnoreCase(String nomeCliente);

    Prontuario findByNomeCliente(String nomeCliente);

}
