package com.polytech.courses.characterservice.client.equipmentservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentResponse {

    private String id;

    private String name;

    private String description;

    private String type;

    private String picture;
}
