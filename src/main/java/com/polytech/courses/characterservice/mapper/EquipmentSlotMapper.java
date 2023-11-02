package com.polytech.courses.characterservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.courses.characterservice.api.dto.EquipmentSlotDto;
import com.polytech.courses.characterservice.entity.EquipmentSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentSlotMapper {

    private final ObjectMapper objectMapper;

    public EquipmentSlotDto toDto(EquipmentSlot entity) {
        return objectMapper.convertValue(entity, EquipmentSlotDto.class);
    }
}
