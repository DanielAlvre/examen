package com.kosmos.medicina.mapper;

import com.kosmos.medicina.dto.CitaDTO;
import com.kosmos.medicina.model.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private ConsultorioMapper consultorioMapper;
    
    public CitaDTO toDTO(Cita cita) {
        if (cita == null) return null;
        
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setDoctor(doctorMapper.toDTO(cita.getDoctor()));
        dto.setConsultorio(consultorioMapper.toDTO(cita.getConsultorio()));
        dto.setHorarioConsulta(cita.getHorarioConsulta());
        dto.setNombrePaciente(cita.getNombrePaciente());
        return dto;
    }

    public Cita toEntity(CitaDTO dto) {
        if (dto == null) return null;
        
        Cita cita = new Cita();
        cita.setId(dto.getId());
        cita.setDoctor(doctorMapper.toEntity(dto.getDoctor()));
        cita.setConsultorio(consultorioMapper.toEntity(dto.getConsultorio()));
        cita.setHorarioConsulta(dto.getHorarioConsulta());
        cita.setNombrePaciente(dto.getNombrePaciente());
        return cita;
    }
} 