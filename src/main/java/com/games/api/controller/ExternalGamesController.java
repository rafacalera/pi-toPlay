package com.games.api.controller;

import com.games.api.dto.externalGame.ExternalGameDTO;
import com.games.api.service.spec.ExternalGameService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/externalGames")
@Tag(name = "ExternalGames")
public class ExternalGamesController {
    @Autowired
    private ExternalGameService externalGameService;

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ExternalGameDTO.class))) }),
            @ApiResponse(responseCode = "204", content = @Content) })
    public ResponseEntity<List<ExternalGameDTO>> getAll(@RequestParam String search) {
        List<ExternalGameDTO> games =  externalGameService.getAll(search);

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(games);
    }
}
