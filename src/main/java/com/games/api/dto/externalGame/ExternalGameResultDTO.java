package com.games.api.dto.externalGame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ExternalGameResultDTO(Integer id,
                                    String slug,
                                    String name,
                                    LocalDate released,
                                    boolean tba,
                                    String background_image,
                                    Integer rating,
                                    Integer rating_top,
                                    Object ratings,
                                    Integer ratings_count,
                                    String reviews_text_count,
                                    Integer added,
                                    Object added_by_status,
                                    Integer metacritic,
                                    Integer playtime,
                                    Integer suggestions_count,
                                    LocalDateTime updated,
                                    Object esrb_rating,
                                    List<Object> platforms) {
}
