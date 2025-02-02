package com.efrei.pokemon_tcg.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DailyDraw {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "master_uuid")
    private Master master;

    @ManyToMany
    @JoinTable(
            name = "daily_draw_cards",
            joinColumns = @JoinColumn(name = "daily_draw_uuid"),
            inverseJoinColumns = @JoinColumn(name = "card_uuid")
    )
    private List<CardPokemon> cardPokemon = new ArrayList<>();

    private LocalDate drawDate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public List<CardPokemon> getCardPokemon() {
        return cardPokemon;
    }

    public void setCardPokemon(List<CardPokemon> cardPokemon) {
        this.cardPokemon = cardPokemon;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }
}