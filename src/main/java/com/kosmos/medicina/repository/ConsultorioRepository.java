package com.kosmos.medicina.repository;

import com.kosmos.medicina.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Long> {
    List<Consultorio> findByPiso(int piso);
    Optional<Consultorio> findByNumeroConsultorioAndPiso(int numeroConsultorio, int piso);
} 