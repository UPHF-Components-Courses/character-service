package com.polytech.courses.characterservice.controller;

import com.polytech.courses.characterservice.api.dto.EquipmentSlotDto;
import com.polytech.courses.characterservice.api.response.CharacterEquipmentResponse;
import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import com.polytech.courses.characterservice.entity.EquipmentSlot;
import com.polytech.courses.characterservice.entity.external.Equipment;
import com.polytech.courses.characterservice.mapper.EquipmentMapper;
import com.polytech.courses.characterservice.mapper.EquipmentSlotMapper;
import com.polytech.courses.characterservice.service.CharacterEquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters/{characterId}/equipments")
@Slf4j
@RequiredArgsConstructor
@Tag(
    name = "Character Equipment Controller",
    description = "APIs used to manage characters equipment"
)
public class CharacterEquipmentController {

    private final EquipmentMapper equipmentMapper;

    private final EquipmentSlotMapper equipmentSlotMapper;

    private final CharacterEquipmentService characterEquipmentService;

    @GetMapping
    @Operation(
        summary = "Get character's equipment",
        description = "Get every equipped items by body parts",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = CharacterEquipmentResponse.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character is matching the provided id"
            )
        }
    )
    public ResponseEntity<CharacterEquipmentResponse> getCharacterEquipment(
        @Parameter(description = "The id of the character")
        @PathVariable
        String characterId
    ) {
        final Map<EquipmentSlotType, Equipment> equipments = characterEquipmentService.getCharacterEquipments(
            characterId);
        final CharacterEquipmentResponse response = equipmentMapper.toResponse(equipments);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{equipmentId}/equip/{bodyPart}")
    @Operation(
        summary = "Equip equipment to character",
        description = "Equip the equipment with the provided id to the provided body part of the character with the provided id",
        responses = {
            @ApiResponse(
                responseCode = "202",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = EquipmentSlotDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character or equipment is matching the provided id"
            )
        }
    )
    public ResponseEntity<EquipmentSlotDto> equipToCharacter(
        @Parameter(description = "The id of the character to equip")
        @PathVariable
        String characterId,

        @Parameter(description = "The id of the equipment to equip")
        @PathVariable
        String equipmentId,

        @Parameter(description = "The body part to equip")
        @PathVariable
        EquipmentSlotType bodyPart
    ) {
        final EquipmentSlot slot = characterEquipmentService.equipToCharacter(characterId, equipmentId, bodyPart);
        final EquipmentSlotDto dto = equipmentSlotMapper.toDto(slot);

        return ResponseEntity.accepted().body(dto);
    }

    @DeleteMapping("/unequip/{bodyPart}")
    @Operation(
        summary = "Unequip equipment from character",
        description = "Unequip the equipment with the provided id to the provided body part of the character with the provided id",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Response in case of success"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character or equipment is matching the provided id"
            )
        }
    )
    public ResponseEntity<CharacterEquipmentResponse> unequipFromCharacter(
        @Parameter(description = "The id of the character to unequip")
        @PathVariable
        String characterId,

        @Parameter(description = "The body part to unequip")
        @PathVariable
        EquipmentSlotType bodyPart
    ) {
        characterEquipmentService.unequipFromCharacter(characterId, bodyPart);

        return ResponseEntity.noContent().build();
    }
}
