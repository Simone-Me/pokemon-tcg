package com.efrei.pokemon_tcg.dto;

import com.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class CreatePokemon {

    @Length(min = 3, max = 30)
    private String name;
    private TypePokemon type;
    private int hp;
    @Positive
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypePokemon getType() {
        return type;
    }

    public void setType(TypePokemon type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
