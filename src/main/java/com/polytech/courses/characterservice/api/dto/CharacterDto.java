package com.polytech.courses.characterservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterDto {

    @Schema(
        description = "Unique identifier of the character",
        example = "653280875702666aaa785071"
    )
    private String id;

    @Schema(
        description = "The name of the character",
        example = "Balthier"
    )
    private String name;

    @Schema(
        description = "An url of an image representing the character",
        example = "https://pbs.twimg.com/media/F0MgQ9GWcAI84be.jpg"
    )
    private String picture;
}
