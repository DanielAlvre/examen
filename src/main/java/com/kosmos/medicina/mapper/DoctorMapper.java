package com.kosmos.medicina.mapper;

import com.kosmos.medicina.dto.DoctorDTO;
import com.kosmos.medicina.model.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    
    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) return null;
        
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setNombre(doctor.getNombre());
        dto.setApellidoPaterno(doctor.getApellidoPaterno());
        dto.setApellidoMaterno(doctor.getApellidoMaterno());
        dto.setEspecialidad(doctor.getEspecialidad());
        return dto;
    }

    public Doctor toEntity(DoctorDTO dto) {
        if (dto == null) return null;
        
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setNombre(dto.getNombre());
        doctor.setApellidoPaterno(dto.getApellidoPaterno());
        doctor.setApellidoMaterno(dto.getApellidoMaterno());
        doctor.setEspecialidad(dto.getEspecialidad());
        return doctor;
    }
} 