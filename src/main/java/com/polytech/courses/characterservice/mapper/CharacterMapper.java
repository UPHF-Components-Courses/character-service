package com.polytech.courses.characterservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.courses.characterservice.api.dto.CharacterDto;
import com.polytech.courses.characterservice.entity.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterMapper {

    private final ObjectMapper objectMapper;

    public CharacterDto toDto(Character entity) {
        return objectMapper.convertValue(entity, CharacterDto.class);
    }
}
