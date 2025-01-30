package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDailyDrawService {
    ResponseEntity<Object> drawDailyCards(Master master);

    void addCardToMaster(Master master, CardPokemon card);

    List<DailyDraw> getAll();

}
