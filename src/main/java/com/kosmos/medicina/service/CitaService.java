package com.kosmos.medicina.service;

import com.kosmos.medicina.model.Cita;
import com.kosmos.medicina.repository.CitaRepository;
import com.kosmos.medicina.repository.ConsultorioRepository;
import com.kosmos.medicina.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kosmos.medicina.model.Doctor;
import com.kosmos.medicina.model.Consultorio;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public Cita crearCita(Cita cita) {

        Doctor doctor = doctorRepository.findByNombreAndApellidoPaternoAndApellidoMaternoAndEspecialidad(
                cita.getDoctor().getNombre(),
                cita.getDoctor().getApellidoPaterno(),
                cita.getDoctor().getApellidoMaterno(),
                cita.getDoctor().getEspecialidad())
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));
        cita.setDoctor(doctor);


        Consultorio consultorio = consultorioRepository.findByNumeroConsultorioAndPiso(
                cita.getConsultorio().getNumeroConsultorio(),
                cita.getConsultorio().getPiso())
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        cita.setConsultorio(consultorio);

        validarReglasCita(cita);
        return citaRepository.save(cita);
    }

    public Cita actualizarCita(Long id, Cita citaActualizada) {
        Cita citaExistente = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        Doctor doctor = doctorRepository.findByNombreAndApellidoPaternoAndApellidoMaternoAndEspecialidad(
                citaActualizada.getDoctor().getNombre(),
                citaActualizada.getDoctor().getApellidoPaterno(),
                citaActualizada.getDoctor().getApellidoMaterno(),
                citaActualizada.getDoctor().getEspecialidad())
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));
        citaActualizada.setDoctor(doctor);


        Consultorio consultorio = consultorioRepository.findByNumeroConsultorioAndPiso(
                citaActualizada.getConsultorio().getNumeroConsultorio(),
                citaActualizada.getConsultorio().getPiso())
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        citaActualizada.setConsultorio(consultorio);
        
        citaActualizada.setId(id);
        validarReglasCita(citaActualizada);
        return citaRepository.save(citaActualizada);
    }

    public void cancelarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        
        if (cita.getHorarioConsulta().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede cancelar una cita pasada");
        }
        
        citaRepository.delete(cita);
    }

    private void validarReglasCita(Cita cita) {
        if (cita.getHorarioConsulta().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La cita debe ser programada en el futuro");
        }

        int hora = cita.getHorarioConsulta().getHour();
        if (hora < 8 || hora >= 20) {
            throw new IllegalArgumentException("Las citas solo pueden ser programadas entre las 8:00 y 20:00 horas");
        }

        if (!citaRepository.findByConsultorioAndHorarioConsultaBetween(
                cita.getConsultorio(),
                cita.getHorarioConsulta(),
                cita.getHorarioConsulta().plusHours(2)).isEmpty()) {
            throw new IllegalArgumentException("El consultorio ya está ocupado entre la hora seleccionada y las siguientes dos horas");
        }

        if (!citaRepository.findByDoctorAndHorarioConsultaBetween(
                cita.getDoctor(),
                cita.getHorarioConsulta(),
                cita.getHorarioConsulta().plusHours(1)).isEmpty()) {
            throw new IllegalArgumentException("El doctor ya tiene una cita entre la hora seleccionada y las  hora");
        }


        if (!citaRepository.findByNombrePacienteAndHorarioConsultaBetween(
                cita.getNombrePaciente(),
                cita.getHorarioConsulta().minusHours(2),
                cita.getHorarioConsulta().plusHours(2)).isEmpty()) {
            throw new IllegalArgumentException("El paciente ya tiene una cita cercana");
        }
        Long totalCitas = citaRepository.countByDoctorAndHorarioConsultaBetween(
                cita.getDoctor(),
                cita.getHorarioConsulta().toLocalDate().atStartOfDay(),
                cita.getHorarioConsulta().toLocalDate().atTime(23, 59));
        if (totalCitas >= 8) {
            throw new IllegalArgumentException("El doctor no puede tener más de 8 citas al día");
        }
    }

    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    public List<Cita> listarCitasPorDoctor(Long doctorId, LocalDateTime fecha) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));
        return citaRepository.findByDoctorAndHorarioConsultaBetween(
                doctor,
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59));
    }

    public List<Cita> listarCitasPorConsultorio(Long consultorioId, LocalDateTime fecha) {
        Consultorio consultorio = consultorioRepository.findById(consultorioId)
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        return citaRepository.findByConsultorioAndHorarioConsultaBetween(
                consultorio,
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59));
    }

    public List<Cita> listarCitasPorFecha(LocalDateTime fecha) {
        return citaRepository.findByHorarioConsultaBetween(
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59));
    }

    public List<Cita> listarCitasPorDoctorYConsultorioYFecha(Long doctorId, Long consultorioId, LocalDateTime fecha) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));
        Consultorio consultorio = consultorioRepository.findById(consultorioId)
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado"));
        
        return citaRepository.findByDoctorAndConsultorioAndHorarioConsultaBetween(
                doctor,
                consultorio,
                fecha.toLocalDate().atStartOfDay(),
                fecha.toLocalDate().atTime(23, 59));
    }

    public List<Cita> listarCitasPorDoctorYConsultorio(Long doctorId, Long consultorioId) {
        return citaRepository.findByDoctorIdAndConsultorioId(doctorId, consultorioId);
    }

}

