package com.games.api.controller;

import com.games.api.dto.game.CreateGameDTO;
import com.games.api.dto.game.GameDTO;
import com.games.api.dto.game.UpdateGameDTO;
import com.games.api.service.spec.GameService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/games")
@Tag(name = "Games")
public class GamesController {
    @Autowired
    private GameService gameService;

    @PostMapping
    @ApiResponse(responseCode = "201", content = @Content)
    public ResponseEntity<UUID> create(@RequestBody final CreateGameDTO game, UriComponentsBuilder uriBuilder) {
        UUID id = gameService.create(game);
            URI uri = uriBuilder.path("/games/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(id);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameDTO.class)) }),
            @ApiResponse(responseCode = "404", content = @Content) })
    public ResponseEntity<GameDTO> getById(@PathVariable UUID id) {
        GameDTO gameDTO = gameService.getById(id);

        if (gameDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(gameDTO);
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class))) }),
            @ApiResponse(responseCode = "204", content = @Content) })
    public ResponseEntity<List<GameDTO>> getAll() {
        List<GameDTO> gamesDTO = gameService.getAll();

        if (gamesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gamesDTO);
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content) })
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody final UpdateGameDTO game) {
        Boolean result = gameService.update(id, game);

        if (!result) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200", content = @Content)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        gameService.delete(id);
        return ResponseEntity.ok().build();
    }
}
