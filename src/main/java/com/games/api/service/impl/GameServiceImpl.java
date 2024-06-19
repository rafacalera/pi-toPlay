package com.games.api.service.impl;

import com.games.api.domain.Game;
import com.games.api.dto.game.CreateGameDTO;
import com.games.api.dto.game.GameDTO;
import com.games.api.dto.game.UpdateGameDTO;
import com.games.api.repository.GameRepository;
import com.games.api.service.spec.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public UUID create(CreateGameDTO gameDTO) {
        Game game = new Game(gameDTO.title(), gameDTO.imageUrl());
        gameRepository.save(game);

        return game.getId();
    }

    @Override
    public GameDTO getById(UUID id) {
        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {
            return null;
        }

        return new GameDTO(game.getId(), game.getTitle(), game.getImageUrl(), game.getStatus());
    }

    @Override
    public List<GameDTO> getAll() {
        List<Game> games = gameRepository.findAll();

        return games.stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Boolean update(UUID id,UpdateGameDTO gameDTO) {
        Game game = gameRepository.findById(id).orElse(null);

        if (game == null || gameDTO.status() < 1 || gameDTO.status() > 3){
            return false;
        }

        game.update(gameDTO.title(), gameDTO.imageUrl(), gameDTO.status());
        gameRepository.save(game);
        return true;
    }

    @Override
    public void delete(UUID id) {
        gameRepository.deleteById(id);
    }

    private GameDTO toDTO(Game game) {
        return new GameDTO(game.getId(), game.getTitle(), game.getImageUrl(), game.getStatus());
    }
}
