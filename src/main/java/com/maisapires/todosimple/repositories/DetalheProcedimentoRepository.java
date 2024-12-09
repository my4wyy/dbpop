package com.maisapires.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maisapires.todosimple.models.DetalheProcedimento;

@Repository
public interface DetalheProcedimentoRepository extends JpaRepository<DetalheProcedimento, Long> {

}
