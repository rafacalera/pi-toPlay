package com.games.api.dto.game;

import java.util.UUID;

public record GameDTO(UUID id, String title, String imageUrl, Integer status) {
}
