package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.services.ICardPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/random")
    public ResponseEntity<?> createRandomCard() {
        CardPokemon createdCard = cardPokemonService.createRandomCard();
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteCard(@PathVariable String uuid) {
        cardPokemonService.deleteCardByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
