package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import com.efrei.pokemon_tcg.services.IMasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dailydraw")
public class DailyDrawController {
    private final IDailyDrawService dailyDrawService;
    private final IMasterService masterService;

    public DailyDrawController(IDailyDrawService dailyDrawService, IMasterService masterService) {
        this.dailyDrawService = dailyDrawService;
        this.masterService = masterService;
    }

    //TODO->getAll DailyDraw

    @GetMapping("/{uuid}")
    public ResponseEntity<List<CardPokemon>> getDrawDailyCards(@PathVariable String uuid) {
        Master master = masterService.findById(uuid);
        try {
            List<CardPokemon> dailyDraw = dailyDrawService.drawDailyCards(master);
            return new ResponseEntity<>(dailyDraw, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
