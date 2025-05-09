package com.kosmos.medicina.controller;

import com.kosmos.medicina.dto.CitaDTO;
import com.kosmos.medicina.dto.FiltroCitaDTO;
import com.kosmos.medicina.mapper.CitaMapper;
import com.kosmos.medicina.model.Cita;
import com.kosmos.medicina.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaMapper citaMapper;

    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody CitaDTO citaDTO) {
        try {
            Cita cita = citaMapper.toEntity(citaDTO);
            citaService.crearCita(cita);
            return ResponseEntity.ok("Cita creada con éxito");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/consulta")
    public ResponseEntity<?> listarCitas(@RequestBody FiltroCitaDTO filtro) {

        Long doctorId = filtro.getDoctorId();
        Long consultorioId = filtro.getConsultorioId();
        LocalDateTime fecha = filtro.getFecha();

        System.out.println("Valores recibidos:");
        System.out.println("DoctorId: " + doctorId);
        System.out.println("ConsultorioId: " + consultorioId);
        System.out.println("Fecha: " + fecha);

        List<CitaDTO> citas;

        if (doctorId != null && consultorioId != null && fecha != null) {
            System.out.println("Entró en el if: doctorId != null && consultorioId != null && fecha != null");
            citas = citaService.listarCitasPorDoctorYConsultorioYFecha(doctorId, consultorioId, fecha).stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (doctorId != null && consultorioId != null) {
            System.out.println("Entró en el if: doctorId != null && consultorioId != null");
            citas = citaService.listarCitasPorDoctorYConsultorio(doctorId, consultorioId).stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (doctorId != null && fecha != null) {
            System.out.println("Entró en el if: doctorId != null && fecha != null");
            citas = citaService.listarCitasPorDoctor(doctorId, fecha).stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (consultorioId != null && fecha != null) {
            System.out.println("Entró en el if: consultorioId != null && fecha != null");
            citas = citaService.listarCitasPorConsultorio(consultorioId, fecha).stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (fecha != null) {
            System.out.println("Entró en el if: fecha != null");
            citas = citaService.listarCitasPorFecha(fecha).stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            System.out.println("Entró en el else: ningún parámetro fue proporcionado");
            citas = citaService.listarCitas().stream()
                    .map(citaMapper::toDTO)
                    .collect(Collectors.toList());
        }


        if (citas.isEmpty()) {
            System.out.println("No se encontraron citas para los filtros especificados.");
            return ResponseEntity.status(404).body("No se encontraron citas para los filtros especificados.");
        }

        return ResponseEntity.ok(citas);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        try {
            Cita cita = citaMapper.toEntity(citaDTO);
            citaService.actualizarCita(id, cita);
            return ResponseEntity.ok("Cita actualizada con éxito");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        try {
            citaService.cancelarCita(id);
            return ResponseEntity.ok("Cita cancelada con éxito");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
