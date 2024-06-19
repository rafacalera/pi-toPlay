package com.games.api.service.spec;

import com.games.api.dto.game.CreateGameDTO;
import com.games.api.dto.game.GameDTO;
import com.games.api.dto.game.UpdateGameDTO;

import java.util.List;
import java.util.UUID;

public interface GameService {
    UUID create(CreateGameDTO gameDTO);
    GameDTO getById(UUID id);
    List<GameDTO> getAll();
    Boolean update(UUID id, UpdateGameDTO gameDTO);
    void delete(UUID id);
}
