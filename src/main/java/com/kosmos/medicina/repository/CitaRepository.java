package com.kosmos.medicina.repository;

import com.kosmos.medicina.model.Cita;
import com.kosmos.medicina.model.Consultorio;
import com.kosmos.medicina.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByConsultorioAndHorarioConsultaBetween(
            Consultorio consultorio,
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByDoctorAndHorarioConsultaBetween(
            Doctor doctor,
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByNombrePacienteAndHorarioConsultaBetween(
            String nombrePaciente,
            LocalDateTime inicio,
            LocalDateTime fin);

    Long countByDoctorAndHorarioConsultaBetween(
            Doctor doctor,
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByHorarioConsultaBetween(
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByDoctorAndConsultorioAndHorarioConsultaBetween(
            Doctor doctor,
            Consultorio consultorio,
            LocalDateTime inicio,
            LocalDateTime fin);

    List<Cita> findByDoctorIdAndConsultorioId(Long doctorId, Long consultorioId);
}
