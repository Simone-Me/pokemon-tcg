package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.constants.TypePokemon;
import com.efrei.pokemon_tcg.dto.CreatePokemon;
import com.efrei.pokemon_tcg.models.Pokemon;

import java.util.List;

public interface IPokemonService {

    List<Pokemon> findAll(TypePokemon type);

    Pokemon findById(String uuid);

    void create(CreatePokemon pokemon);

    boolean delete(String uuid);

    boolean update(String uuid, Pokemon pokemon);

    Pokemon createRandomPokemon();


}