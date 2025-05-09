package com.kosmos.medicina.repository;

import com.kosmos.medicina.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {


    List<Doctor> findByNombre(String nombre);


    List<Doctor> findByEspecialidad(String especialidad);


    List<Doctor> findByNombreAndApellidoPaterno(String nombre, String apellidoPaterno);

    Optional<Doctor> findByNombreAndApellidoPaternoAndApellidoMaternoAndEspecialidad(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String especialidad);
}
