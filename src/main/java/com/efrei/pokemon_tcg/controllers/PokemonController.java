package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.constants.TypePokemon;
import com.efrei.pokemon_tcg.dto.CreatePokemon;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.services.IPokemonService;
import com.efrei.pokemon_tcg.services.implementations.PokemonServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    private final IPokemonService pokemonService;

    public PokemonController(PokemonServiceImpl pokemonService) {

        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<List<Pokemon>> getAll(@RequestParam(required = false) TypePokemon type) {
        return new ResponseEntity<>(pokemonService.findAll(type), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Pokemon> getPokemon(@PathVariable String uuid) {
        Pokemon pokemon = pokemonService.findById(uuid);
        if (pokemon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pokemonService.findById(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPokemon(@Valid @RequestBody CreatePokemon pokemon) {
        // INSERT INTO pokemon(nom, niveau, type) VALUES (pokemon.nom, pokemon.niveau, pokemon.type);
        pokemonService.create(pokemon);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/random")
    public ResponseEntity<?> createRandomPokemon() {
        pokemonService.createRandomPokemon();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePokemon(@PathVariable String uuid) {
        boolean isDeleted = pokemonService.delete(uuid);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> modifyPokemon(@PathVariable String uuid, @RequestBody Pokemon updatedPokemon) {
        boolean isModified = pokemonService.update(uuid, updatedPokemon);
        if (!isModified) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
