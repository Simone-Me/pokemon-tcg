package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.models.CardPokemon;

import java.util.List;

public interface ICardPokemonService {

    void createRandomCard();

    List<CardPokemon> findAll(Integer star);
}
