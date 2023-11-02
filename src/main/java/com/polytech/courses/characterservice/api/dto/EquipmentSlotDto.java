package com.polytech.courses.characterservice.api.dto;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EquipmentSlotDto {

    @Schema(
        description = "The type of slot. Only equipments that has type type of slot can be equipped in this slot.",
        example = "653280875702666aaa785071"
    )
    private EquipmentSlotType type;

    @Schema(
        description = "Unique identifier of the equipped equipment",
        example = "653280875702666aaa785071"
    )
    private String equipmentId;
}
