package com.polytech.courses.characterservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.courses.characterservice.api.dto.EquipmentDto;
import com.polytech.courses.characterservice.api.response.CharacterEquipmentResponse;
import com.polytech.courses.characterservice.client.equipmentservice.response.EquipmentResponse;
import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import com.polytech.courses.characterservice.entity.external.Equipment;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentMapper {

    private final ObjectMapper objectMapper;

    public EquipmentDto toDto(Equipment equipment) {
        return objectMapper.convertValue(equipment, EquipmentDto.class);
    }

    public CharacterEquipmentResponse toResponse(Map<EquipmentSlotType, Equipment> equipments) {
        return CharacterEquipmentResponse.builder()
            .head(toDto(equipments.get(EquipmentSlotType.HEAD)))
            .body(toDto(equipments.get(EquipmentSlotType.BODY)))
            .weapon1(toDto(equipments.get(EquipmentSlotType.WEAPON_1)))
            .weapon2(toDto(equipments.get(EquipmentSlotType.WEAPON_2)))
            .build();
    }

    public Equipment toInternalModel(EquipmentResponse equipmentResponse) {
        // Map external type to internal type
        final List<EquipmentSlotType> slotTypes = switch (equipmentResponse.getType()) {
            case "HAT" -> List.of(EquipmentSlotType.HEAD);
            case "SUIT" -> List.of(EquipmentSlotType.BODY);
            case "WEAPON" -> List.of(EquipmentSlotType.WEAPON_1, EquipmentSlotType.WEAPON_2);
            default -> throw new IllegalStateException(
                "%s is not an handled equipment type".formatted(equipmentResponse.getType())
            );
        };

        // Build internal model
        return Equipment.builder()
            .id(equipmentResponse.getId())
            .name(equipmentResponse.getName())
            .description(equipmentResponse.getDescription())
            .slotTypes(slotTypes)
            .picture(equipmentResponse.getPicture())
            .build();
    }
}
