package com.games.api.service.impl;

import com.games.api.dto.externalGame.ExternalGameDTO;
import com.games.api.dto.externalGame.ExternalGameResponseDTO;
import com.games.api.dto.externalGame.ExternalGameResultDTO;
import com.games.api.service.spec.ExternalGameService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalGameServiceImpl implements ExternalGameService {
    @Value("${rawg.api.key}")
    private String apiKey;
    @Value("${rawg.api.baseUrl}")
    private String baseUrl;

    public List<ExternalGameDTO> getAll(String search) {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "games")
                    .queryParam("key", apiKey)
                    .queryParam("page", 1)
                    .queryParam("page_size", 5)
                    .queryParam("search", search)
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ExternalGameResponseDTO responseDTO = restTemplate.getForObject(url, ExternalGameResponseDTO.class);

        if (responseDTO == null || responseDTO.results() == null){
            return List.of();
        }

        return responseDTO.results()
                    .stream()
                    .map(this::toExternalGameDTO)
                    .toList();
    }

    private ExternalGameDTO toExternalGameDTO(ExternalGameResultDTO externalDTO) {
        return new ExternalGameDTO(externalDTO.name(), externalDTO.background_image());
    }
}
