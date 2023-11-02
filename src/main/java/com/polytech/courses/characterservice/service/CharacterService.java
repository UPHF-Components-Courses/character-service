package com.polytech.courses.characterservice.service;

import com.polytech.courses.characterservice.api.request.CharacterCreationRequest;
import com.polytech.courses.characterservice.api.request.CharacterUpdateRequest;
import com.polytech.courses.characterservice.entity.Character;
import com.polytech.courses.characterservice.exception.api.InvalidDataException;
import com.polytech.courses.characterservice.exception.api.ResourceNotFoundException;
import com.polytech.courses.characterservice.repository.CharacterRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    /**
     * Gets every known {@link Character}
     *
     * @return A list of known {@link Character}s
     */
    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    /**
     * Gets a {@link Character} from its id
     *
     * @param characterId The id of the desired {@link Character}
     * @return The matching {@link Character} or null if it doesn't exist
     */
    public Character getById(String characterId) {
        return characterRepository.findById(new ObjectId(characterId))
            .orElseThrow(() -> new ResourceNotFoundException(Character.class, "id", characterId));
    }

    /**
     * Creates a {@link Character} from a request
     *
     * @param request The request containing {@link Character}'s data
     * @return The created {@link Character}
     */
    public Character create(CharacterCreationRequest request) {
        if (characterRepository.existsByName(request.getName())) {
            throw new InvalidDataException("a character named %s already exists".formatted(request.getName()));
        }

        final Character character = Character.builder()
            .name(request.getName())
            .picture(request.getPicture())
            .build();

        return characterRepository.insert(character);
    }

    /**
     * Updates a {@link Character} from a request
     *
     * @param characterId The id of the {@link Character} to update
     * @param request     The request containing {@link Character}'s data to update
     * @return The updated {@link Character}
     */
    public Character update(String characterId, CharacterUpdateRequest request) {
        final Character character = getById(characterId);

        if (!Objects.equals(character.getName(), request.getName())
            && characterRepository.existsByName(request.getName())
        ) {
            throw new InvalidDataException("a character named %s already exists".formatted(request.getName()));
        }

        character.setName(request.getName());
        character.setPicture(request.getPicture());

        return characterRepository.save(character);
    }

    /**
     * Deletes the {@link Character} with the provided id
     *
     * @param characterId The id of the {@link Character} to delete
     */
    public void delete(String characterId) {
        characterRepository.delete(getById(characterId));
    }
}
