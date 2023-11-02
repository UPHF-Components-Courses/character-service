package com.polytech.courses.characterservice.entity;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "equipment_slots")
public class EquipmentSlot {

    @Id
    private ObjectId id;

    private ObjectId characterId;

    private String equipmentId;

    private EquipmentSlotType type;
}
