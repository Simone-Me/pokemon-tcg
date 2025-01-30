package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.CardPokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardPokemonRepository extends JpaRepository<CardPokemon, String> {

    List<CardPokemon> findAllByStar(Integer star);
}
