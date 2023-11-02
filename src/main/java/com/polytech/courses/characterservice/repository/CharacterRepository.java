package com.polytech.courses.characterservice.repository;

import com.polytech.courses.characterservice.entity.Character;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface CharacterRepository extends MongoRepository<Character, ObjectId> {

    boolean existsByName(String name);
}
