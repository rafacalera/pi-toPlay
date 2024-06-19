package com.games.api.service.spec;

import com.games.api.dto.externalGame.ExternalGameDTO;

import java.util.List;

public interface ExternalGameService {
    List<ExternalGameDTO> getAll(String search);
}
