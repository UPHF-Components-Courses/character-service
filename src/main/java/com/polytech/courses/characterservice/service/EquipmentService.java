package com.polytech.courses.characterservice.service;

import com.polytech.courses.characterservice.client.equipmentservice.response.EquipmentResponse;
import com.polytech.courses.characterservice.entity.external.Equipment;
import com.polytech.courses.characterservice.exception.api.ResourceNotFoundException;
import com.polytech.courses.characterservice.mapper.EquipmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    @Value("${services.equipment.url}")
    private String serviceUrl;

    private final WebClient webClient;

    private final EquipmentMapper equipmentMapper;

    /**
     * Get an {@link Equipment} from the equipment service from its id
     *
     * @param equipmentId the id of the {@link Equipment} to retrieve
     * @return the {@link Equipment} with the provided id
     */
    public Equipment getEquipment(String equipmentId) {
        // Build request
        return webClient.get()
            .uri(
                serviceUrl,
                uriBuilder -> uriBuilder
                    .pathSegment("equipments", equipmentId)
                    .build()
            )
            .retrieve()

            // Handle service errors
            .onStatus(
                statusCode -> statusCode.value() == 404,
                e -> Mono.error(new ResourceNotFoundException(Equipment.class, "id", equipmentId))
            )
            .onStatus(
                statusCode -> !statusCode.is2xxSuccessful(),
                e -> Mono.error(new IllegalStateException("Failed to retrieve data from equipment service"))
            )

            // Map response
            .bodyToMono(EquipmentResponse.class)
            .map(equipmentMapper::toInternalModel)

            // Use block because we are not in a fully reactive context
            .block();
    }
}
