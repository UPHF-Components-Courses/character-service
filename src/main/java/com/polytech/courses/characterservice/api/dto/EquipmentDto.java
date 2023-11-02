package com.polytech.courses.characterservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EquipmentDto {

    @Schema(
        description = "Unique identifier for the equipment",
        example = "653280875702666aaa785071"
    )
    private String id;

    @Schema(
        description = "The name of the equipment",
        example = "King Sword"
    )
    private String name;

    @Schema(
        description = "A text that describes the equipment",
        example = "A wonderful sword used by many kings"
    )
    private String description;

    @Schema(
        description = "The url of the picture that represents the equipment",
        example = "https://netrinoimages.s3.eu-west-2.amazonaws.com/2016/09/01/420421/275647/fantasy_sword_18_3d_model_c4d_max_obj_fbx_ma_lwo_3ds_3dm_stl_2883487_o.jpg"
    )
    private String picture;
}
