package com.polytech.courses.characterservice.repository;

import com.polytech.courses.characterservice.constant.EquipmentSlotType;
import com.polytech.courses.characterservice.entity.EquipmentSlot;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface EquipmentSlotRepository extends MongoRepository<EquipmentSlot, ObjectId> {

    List<EquipmentSlot> findByCharacterId(ObjectId characterId);

    void deleteByCharacterIdAndType(ObjectId characterId, EquipmentSlotType type);
}
