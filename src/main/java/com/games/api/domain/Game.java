package com.games.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "game")
@NoArgsConstructor
@Getter
public class Game {
    public Game(String title, String imageUrl){
        this.id = UUID.randomUUID();
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = 1;
    }

    @Id
    private UUID id;
    private String title;
    private String imageUrl;
    private Integer status;

    public void update(String title, String imageUrl, Integer status){
        this.title = title;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}

