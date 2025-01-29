package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.constants.TypePokemon;
import com.efrei.pokemon_tcg.models.Pokemon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/{name}")
    public String greetings(@PathVariable String name) {
        return "Welcome to Pokemon TCG " + name + " !";
    }

    @GetMapping("/pokemon")
    public Pokemon pokemon() {
        Pokemon Barbie = new Pokemon("Barbie", TypePokemon.ICE, 100, 1);
        return Barbie;
    }

}
