package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import com.efrei.pokemon_tcg.services.IMasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily-draws")
public class DailyDrawController {
    private final IDailyDrawService dailyDrawService;
    private final IMasterService masterService;

    public DailyDrawController(IDailyDrawService dailyDrawService, IMasterService masterService) {
        this.dailyDrawService = dailyDrawService;
        this.masterService = masterService;
    }

    @GetMapping
    public ResponseEntity<List<DailyDraw>> getAll() {
        return new ResponseEntity<>(dailyDrawService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<?> getDrawDailyCards(@PathVariable String uuid) {
        Master master = masterService.findById(uuid);
        try {
            ResponseEntity<Object> dailyDraw = dailyDrawService.drawDailyCards(master);
            return new ResponseEntity<>(dailyDraw, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}
