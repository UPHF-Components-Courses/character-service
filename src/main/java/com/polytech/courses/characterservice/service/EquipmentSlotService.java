package com.polytech.courses.characterservice.service;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import com.polytech.courses.characterservice.entity.Character;
import com.polytech.courses.characterservice.entity.EquipmentSlot;
import com.polytech.courses.characterservice.entity.external.Equipment;
import com.polytech.courses.characterservice.exception.api.InvalidDataException;
import com.polytech.courses.characterservice.exception.api.ResourceNotFoundException;
import com.polytech.courses.characterservice.repository.CharacterRepository;
import com.polytech.courses.characterservice.repository.EquipmentSlotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentSlotService {

    private final CharacterRepository characterRepository;

    private final EquipmentSlotRepository equipmentSlotRepository;

    private final MongoTemplate mongoTemplate;

    /**
     * Get every equipped {@link EquipmentSlot}s for the {@link Character} with the provided id
     *
     * @param characterId the id of the targeted {@link Character}
     * @return a list of {@link EquipmentSlot}
     */
    public List<EquipmentSlot> getEquipmentSlots(String characterId) {
        final ObjectId characterObjectId = new ObjectId(characterId);

        // Check if character exists
        if (!characterRepository.existsById(characterObjectId)) {
            throw new ResourceNotFoundException(Character.class, "id", characterId);
        }

        return equipmentSlotRepository.findByCharacterId(characterObjectId);
    }

    /**
     * Equip the provided {@link Equipment} in the {@link EquipmentSlot} with the provided {@link EquipmentSlotType} on
     * the {@link Character} with the provided id
     *
     * @param characterId the id of the targeted {@link Character}
     * @param equipment   the {@link Equipment} to equip
     * @param bodyPart    the type of the {@link EquipmentSlot} that should be used
     * @return the resulting {@link EquipmentSlot}
     */
    public EquipmentSlot equipToCharacter(ObjectId characterId, Equipment equipment, EquipmentSlotType bodyPart) {
        // Check if provided slot is valid
        if (!equipment.getSlotTypes().contains(bodyPart)) {
            throw new InvalidDataException(
                "Can't equip %s to a %s slot, it can only be equipped to %s slots".formatted(
                    equipment.getName(),
                    String.join(",", equipment.getSlotTypes().stream().map(Enum::name).toArray(String[]::new)),
                    bodyPart.name()
                )
            );
        }

        // Create query
        final Query query = new Query(
            Criteria
                .where("characterId").is(characterId)
                .and("type").is(bodyPart)
        );

        final Update update = new Update().set("equipmentId", equipment.getId());

        final FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);

        // Apply query
        return mongoTemplate.findAndModify(query, update, options, EquipmentSlot.class);
    }

    /**
     * Unequip the {@link Equipment} in the {@link EquipmentSlot} with the provided {@link EquipmentSlotType} of the
     * {@link Character} with the provided id
     *
     * @param characterId characterId the id of the targeted {@link Character}
     * @param slotType    the type of the {@link EquipmentSlot} that should be unequipped
     */
    public void unequipFromCharacter(ObjectId characterId, EquipmentSlotType slotType) {
        equipmentSlotRepository.deleteByCharacterIdAndType(characterId, slotType);
    }
}
