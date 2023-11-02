package com.polytech.courses.characterservice.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterUpdateRequest {

    @Schema(
        description = "The name of the character",
        example = "Balthier"
    )
    @NotBlank
    private String name;

    @Schema(
        description = "An url of an image representing the character",
        example = "https://pbs.twimg.com/media/F0MgQ9GWcAI84be.jpg"
    )
    @NotBlank
    private String picture;
}
