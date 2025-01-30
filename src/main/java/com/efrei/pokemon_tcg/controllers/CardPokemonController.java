package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.services.ICardPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardPokemonController {

    private final ICardPokemonService cardPokemonService;

    public CardPokemonController(ICardPokemonService cardPokemonService) {
        this.cardPokemonService = cardPokemonService;
    }

    @GetMapping()
    public ResponseEntity<List<CardPokemon>> getAll(@RequestParam(required = false) Integer star) {
        return new ResponseEntity<>(cardPokemonService.findAll(star), HttpStatus.OK);
    }

    @GetMapping("/random")
    public ResponseEntity<?> createRandomCard() {
        cardPokemonService.createRandomCard();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
