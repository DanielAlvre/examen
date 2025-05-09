package com.kosmos.medicina.mapper;

import com.kosmos.medicina.dto.ConsultorioDTO;
import com.kosmos.medicina.model.Consultorio;
import org.springframework.stereotype.Component;

@Component
public class ConsultorioMapper {
    
    public ConsultorioDTO toDTO(Consultorio consultorio) {
        if (consultorio == null) return null;
        
        ConsultorioDTO dto = new ConsultorioDTO();
        dto.setId(consultorio.getId());
        dto.setNumeroConsultorio(consultorio.getNumeroConsultorio());
        dto.setPiso(consultorio.getPiso());
        return dto;
    }

    public Consultorio toEntity(ConsultorioDTO dto) {
        if (dto == null) return null;
        
        Consultorio consultorio = new Consultorio();
        consultorio.setId(dto.getId());
        consultorio.setNumeroConsultorio(dto.getNumeroConsultorio());
        consultorio.setPiso(dto.getPiso());
        return consultorio;
    }
} 