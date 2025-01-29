package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Master;

import java.util.List;

public interface IDailyDrawService {
    List<CardPokemon> drawDailyCards(Master master);
}
