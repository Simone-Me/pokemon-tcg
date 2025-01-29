package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.constants.City;
import com.efrei.pokemon_tcg.dto.CapturePokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.services.IMasterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final IMasterService masterService;

    public MasterController(IMasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping
    public ResponseEntity<List<Master>> getAll() {
        return new ResponseEntity<>(masterService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Master> getMater(@PathVariable String uuid) {
        Master master = masterService.findById(uuid);
        if (master == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(masterService.findById(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createMaster(@Valid @RequestBody Master master) {
        masterService.create(master);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteMaster(@PathVariable String uuid) {
        boolean isDeleted = masterService.delete(uuid);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> modifyMaster(@PathVariable String uuid, @RequestBody Master updatedMaster) {
        boolean isModified = masterService.update(uuid, updatedMaster);
        if (!isModified) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/capture")
    public ResponseEntity<?> capturePokemnon(@PathVariable String uuid, @RequestBody CapturePokemon capturePokemon) {
        masterService.capturePokemon(uuid, capturePokemon);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{uuid}/buy")
    public ResponseEntity<?> buyItem() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
