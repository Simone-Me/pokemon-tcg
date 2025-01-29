package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.constants.TypePokemon;
import com.efrei.pokemon_tcg.dto.CreatePokemon;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.PokemonRepository;
import com.efrei.pokemon_tcg.services.IPokemonService;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements IPokemonService {

    private final PokemonRepository repository;
    Faker faker = new Faker();

    public PokemonServiceImpl(PokemonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pokemon> findAll(TypePokemon type) {
        if (type == null) {
            return repository.findAll();
        }
        return repository.findAllByType(type);
    }

    @Override
    public Pokemon findById(String uuid) {
        return repository.findById(uuid).orElse(null);
    }

    @Override
    public void create(CreatePokemon pokemon) {
        Pokemon pokemonToCreate = new Pokemon();
        pokemonToCreate.setHp(pokemon.getHp());
        pokemonToCreate.setName(pokemon.getName());
        pokemonToCreate.setLevel(pokemon.getLevel());
        pokemonToCreate.setType(pokemon.getType());
        repository.save(pokemonToCreate);
    }

    @Override
    public Pokemon createRandomPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(faker.pokemon().name());
        pokemon.setLevel(faker.number().numberBetween(10, 60));
        pokemon.setType(TypePokemon.values()[faker.number().numberBetween(0, TypePokemon.values().length - 1)]);
        pokemon.setHp(faker.number().numberBetween(50, 100));
        repository.save(pokemon);
        return pokemon;
    }

    @Override
    public boolean delete(String uuid) {
        Pokemon pokemonToDelete = findById(uuid);
        if (pokemonToDelete == null) {
            return false;
        }
        repository.deleteById(uuid);
        return true;
    }

    @Override
    public boolean update(String uuid, Pokemon pokemon) {
        Pokemon pokemonToModify = findById(uuid);
        if (pokemonToModify == null) {
            return false;
        }
        pokemonToModify.setName(pokemon.getName());
        pokemonToModify.setLevel(pokemon.getLevel());
        pokemonToModify.setType(pokemon.getType());
        repository.save(pokemonToModify);
        return true;
    }
}