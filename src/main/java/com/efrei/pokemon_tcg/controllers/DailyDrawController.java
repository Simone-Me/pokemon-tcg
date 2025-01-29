package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dailydraw")
public class DailyDrawController {
    private final IDailyDrawService dailyDrawService;

    public DailyDrawController(IDailyDrawService dailyDrawService) {
        this.dailyDrawService = dailyDrawService;
    }

    @PostMapping("/draw")
    public ResponseEntity<List<CardPokemon>> drawDailyCards(@Valid @RequestBody Master master) {
        try {
            List<CardPokemon> dailyDraw = dailyDrawService.drawDailyCards(master);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
