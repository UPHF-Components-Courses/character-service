package com.polytech.courses.characterservice.api.response;

import com.polytech.courses.characterservice.api.dto.EquipmentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterEquipmentResponse {

    @Schema(
        description = "The equipment equipped on character's head"
    )
    private EquipmentDto head;

    @Schema(
        description = "The equipment equipped on character's body"
    )
    private EquipmentDto body;

    @Schema(
        description = "The equipment equipped on character's first weapon slot"
    )
    private EquipmentDto weapon1;

    @Schema(
        description = "The equipment equipped on character's second weapon slot"
    )
    private EquipmentDto weapon2;
}
