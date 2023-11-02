package com.polytech.courses.characterservice.service;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import com.polytech.courses.characterservice.entity.Character;
import com.polytech.courses.characterservice.entity.EquipmentSlot;
import com.polytech.courses.characterservice.entity.external.Equipment;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterEquipmentService {

    private final CharacterService characterService;

    private final EquipmentSlotService equipmentSlotService;

    private final EquipmentService equipmentService;

    /**
     * Get every {@link Equipment} equipped to {@link Character} with provided id by {@link EquipmentSlotType}
     *
     * @param characterId the id of the {@link Character}
     * @return a map of {@link Equipment} by {@link EquipmentSlotType}
     */
    public Map<EquipmentSlotType, Equipment> getCharacterEquipments(String characterId) {
        return equipmentSlotService.getEquipmentSlots(characterId)
            .stream()
            .collect(Collectors.toMap(
                EquipmentSlot::getType,
                slot -> equipmentService.getEquipment(slot.getEquipmentId())
            ));
    }

    /**
     * Equip the {@link Equipment} with the provided id in the {@link EquipmentSlot} with the provided
     * {@link EquipmentSlotType} on the {@link Character} with the provided id
     *
     * @param characterId the id of the targeted {@link Character}
     * @param equipmentId the id of the {@link Equipment} to equip
     * @param bodyPart    the type of the {@link EquipmentSlot} that should be used
     * @return the resulting {@link EquipmentSlot}
     */
    public EquipmentSlot equipToCharacter(String characterId, String equipmentId, EquipmentSlotType bodyPart) {
        // Retrieve character
        final Character character = characterService.getById(characterId);

        // Retrieve equipment
        final Equipment equipment = equipmentService.getEquipment(equipmentId);

        // Equip item
        final EquipmentSlot slot = equipmentSlotService.equipToCharacter(character.getId(), equipment, bodyPart);

        log.info("{} has been equipped to {}", equipment.getId(), character.getId());

        return slot;
    }

    /**
     * Unequip the {@link Equipment} equipped in the {@link EquipmentSlot} with the provided {@link EquipmentSlotType}
     * from the {@link Character} with the provided id
     *
     * @param characterId the id of the targeted {@link Character}
     * @param bodyPart    the targeted body part
     */
    public void unequipFromCharacter(String characterId, EquipmentSlotType bodyPart) {
        // Retrieve character
        final Character character = characterService.getById(characterId);

        // Unequip item
        equipmentSlotService.unequipFromCharacter(character.getId(), bodyPart);

        log.info("The item equipped to {} in its {} slot has been unequipped", character.getName(), bodyPart);
    }
}
