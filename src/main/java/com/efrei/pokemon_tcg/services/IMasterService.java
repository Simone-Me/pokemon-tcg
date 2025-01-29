package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.constants.City;
import com.efrei.pokemon_tcg.dto.CapturePokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.models.Pokemon;

import java.util.List;

public interface IMasterService {

    List<Master> findAll();

    Master findById(String uuid);

    void create(Master master);

    boolean delete(String uuid);

    boolean update(String uuid, Master master);

    void capturePokemon(String uuid, CapturePokemon capturePokemon);
}
