package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.models.Item;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.services.IItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final IItemService itemService;

    public ItemController(IItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Item> getItem(@PathVariable String uuid) {
        Item item = itemService.findById(uuid);
        if(item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemService.findById(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createItem(@Valid @RequestBody Item item) {
        itemService.create(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteItem(@PathVariable String uuid) {
        boolean isDeleted = itemService.delete(uuid);
        if(!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> modifyItem(@PathVariable String uuid, @RequestBody Item updatedItem) {
        boolean isModified = itemService.update(uuid, updatedItem);
        if(!isModified) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
