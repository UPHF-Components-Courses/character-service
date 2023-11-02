package com.polytech.courses.characterservice.controller;

import com.polytech.courses.characterservice.api.dto.CharacterDto;
import com.polytech.courses.characterservice.api.request.CharacterCreationRequest;
import com.polytech.courses.characterservice.api.request.CharacterUpdateRequest;
import com.polytech.courses.characterservice.entity.Character;
import com.polytech.courses.characterservice.mapper.CharacterMapper;
import com.polytech.courses.characterservice.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
@Slf4j
@RequiredArgsConstructor
@Tag(
    name = "Character Controller",
    description = "APIs used to manage character"
)
public class CharacterController {

    private final CharacterService characterService;

    private final CharacterMapper characterMapper;

    @GetMapping
    @Operation(
        summary = "Get all characters",
        description = "Get every character that matches provided filters.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CharacterDto.class))
                )
            ),
        }
    )
    public ResponseEntity<List<CharacterDto>> getAllCharacters() {
        final List<CharacterDto> dtos = characterService.getAll()
            .stream()
            .map(characterMapper::toDto)
            .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{characterId}")
    @Operation(
        summary = "Get a character by id",
        description = "Get the character with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = CharacterDto.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character is matching the provided id"
            )
        }
    )
    public ResponseEntity<CharacterDto> getCharacter(
        @Parameter(description = "The id of the character")
        @PathVariable
        String characterId
    ) {
        final Character character = characterService.getById(characterId);

        return character != null
            ? ResponseEntity.ok(characterMapper.toDto(character))
            : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(
        summary = "Create a character",
        description = "Create a character from the provided data.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Response if the character has been successfully created",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = CharacterDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Response if the provided data is not valid",
                content = @Content(mediaType = "application/json")
            )
        }
    )
    public ResponseEntity<CharacterDto> createCharacter(@Valid @RequestBody CharacterCreationRequest request) {
        final Character character = characterService.create(request);
        final CharacterDto dto = characterMapper.toDto(character);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{characterId}")
    @GetMapping("/{characterId}")
    @Operation(
        summary = "Update a character by id",
        description = "Update the character with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Response in case of success",
                content = @Content(mediaType = "application/json", schema = @Schema(allOf = CharacterDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Response if the provided data is not valid",
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character is matching the provided id"
            )
        }
    )
    public ResponseEntity<CharacterDto> updateCharacter(
        @PathVariable
        @Parameter(description = "The id of the character")
        String characterId,

        @Valid
        @RequestBody
        CharacterUpdateRequest request
    ) {
        final Character character = characterService.update(characterId, request);

        return character != null
            ? ResponseEntity.ok(characterMapper.toDto(character))
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{characterId}")
    @GetMapping("/{characterId}")
    @Operation(
        summary = "Delete a character by id",
        description = "Delete the character with the provided id.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Response if the character has been successfully deleted"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No character is matching the provided id"
            )
        }
    )
    public ResponseEntity<Void> deleteCharacter(
        @Parameter(description = "The id of the character to delete")
        @PathVariable
        String characterId
    ) {
        characterService.delete(characterId);

        return ResponseEntity.accepted().build();
    }
}
