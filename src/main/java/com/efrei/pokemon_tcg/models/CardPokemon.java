package com.efrei.pokemon_tcg.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CardPokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @OneToMany
    private List<Pokemon> pokemonsCards;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Pokemon> getPokemonsCards() {
        return pokemonsCards;
    }

    public void setPokemonsCards(List<Pokemon> pokemonsCards) {
        this.pokemonsCards = pokemonsCards;
    }
}
