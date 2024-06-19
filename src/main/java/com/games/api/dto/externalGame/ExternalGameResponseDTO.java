package com.games.api.dto.externalGame;

import java.util.List;

public record ExternalGameResponseDTO(Integer count, String next, String previous, List<ExternalGameResultDTO> results) {
}
