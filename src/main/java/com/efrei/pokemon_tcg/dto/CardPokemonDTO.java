package com.efrei.pokemon_tcg.dto;

import com.efrei.pokemon_tcg.constants.TypeAttackCombo;
import com.efrei.pokemon_tcg.constants.TypeAttackSimple;
import jakarta.validation.constraints.*;

public class CardPokemonDTO {
    private String uuid;
    @NotNull(message = "Le type d'attaque base ne peut pas etre vide.")
    private TypeAttackSimple attackSimple;
    private TypeAttackCombo attackCombo;
    @Positive
    @Min(value = 1, message = "Le nombre de stars doit etre superieur a 0.")
    @Max(value = 5, message = "Le nombre de stars doit etre inferieur a 6.")
    private Integer star;
    @NotNull
    private String pokemonUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TypeAttackSimple getAttackSimple() {
        return attackSimple;
    }

    public void setAttackSimple(TypeAttackSimple attackSimple) {
        this.attackSimple = attackSimple;
    }

    public TypeAttackCombo getAttackCombo() {
        return attackCombo;
    }

    public void setAttackCombo(TypeAttackCombo attackCombo) {
        this.attackCombo = attackCombo;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getPokemonUuid() {
        return pokemonUuid;
    }

    public void setPokemonUuid(String pokemonUuid) {
        this.pokemonUuid = pokemonUuid;
    }
}
