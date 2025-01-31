package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.dto.CardPokemonDTO;
import com.efrei.pokemon_tcg.models.CardPokemon;

import java.util.List;

public interface ICardPokemonService {

    CardPokemon createRandomCard();

    List<CardPokemon> findAll(Integer star);

    void deleteCardByUuid(String uuid);

}
