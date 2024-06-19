package com.games.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.api.dto.game.CreateGameDTO;
import com.games.api.dto.game.GameDTO;
import com.games.api.dto.game.UpdateGameDTO;
import com.games.api.service.spec.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GamesController.class)
public class GamesControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService service;

    @Test
    void gamesShouldReturnCreatedWithIdAndLocation() throws Exception {
        UUID id = UUID.randomUUID();

        CreateGameDTO gameDTO = new CreateGameDTO("string", "url.com");
        String requestBody = objectMapper.writeValueAsString(gameDTO);

        when(service.create(any(CreateGameDTO.class))).thenReturn(id);


        this.mvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(id.toString())))
                .andExpect(content().string(containsString(id.toString())));
    }

    @Test
    void gamesShouldReturnOkOfGameDTO() throws Exception {
        UUID id = UUID.randomUUID();
        GameDTO gameDTO = new GameDTO(id, "string", "url.com", 1);
        String response = objectMapper.writeValueAsString(gameDTO);

        when(service.getById(id)).thenReturn(gameDTO);

        this.mvc.perform(get("/api/games/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void gamesShouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.getById(id)).thenReturn(null);

        this.mvc.perform(get("/api/games/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void gamesShouldReturnOkWithListOfGameDTO() throws Exception {
        GameDTO gameDTO = new GameDTO(UUID.randomUUID(), "string", "url.com", 1);
        List<GameDTO> gamesDTO = new ArrayList<>();
        gamesDTO.add(gameDTO);

        when(service.getAll()).thenReturn(gamesDTO);

        this.mvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(gamesDTO)));
    }

    @Test
    void gamesShouldReturnNotContent() throws Exception {
        List<GameDTO> gamesDTO = new ArrayList<>();

        when(service.getAll()).thenReturn(gamesDTO);

        this.mvc.perform(get("/api/games"))
                .andExpect(status().isNoContent());
    }

    @Test
    void gamesShouldReturnOkWhenUpdated() throws Exception {
        var id = UUID.randomUUID();
        var gameDTO = new UpdateGameDTO("string", "url.com", 2);
        var requestBody = objectMapper.writeValueAsString(gameDTO);

        when(service.update(any(UUID.class), any(UpdateGameDTO.class))).thenReturn(true);

        this.mvc.perform(put("/api/games/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk());
    }

    @Test
    void gamesShouldReturnBadRequestWhenNotUpdated() throws Exception {
        var id = UUID.randomUUID();
        var gameDTO = new UpdateGameDTO("string", "url.com", 2);
        var requestBody = objectMapper.writeValueAsString(gameDTO);

        when(service.update(any(UUID.class), any(UpdateGameDTO.class))).thenReturn(false);

        this.mvc.perform(put("/api/games/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void gamesShouldReturnOkWhenDeleted() throws Exception {
        var id = UUID.randomUUID();

        this.mvc.perform(delete("/api/games/{id}", id)).andExpect(status().isOk());
    }

}
