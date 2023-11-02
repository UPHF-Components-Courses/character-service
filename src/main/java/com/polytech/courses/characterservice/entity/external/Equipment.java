package com.polytech.courses.characterservice.entity.external;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Equipment {

    private String id;

    private String name;

    private String description;

    private List<EquipmentSlotType> slotTypes;

    private String picture;
}
