package com.efrei.pokemon_tcg.models;

import com.efrei.pokemon_tcg.constants.TypeAttackCombo;
import com.efrei.pokemon_tcg.constants.TypeAttackSimple;
import jakarta.persistence.*;

@Entity
public class CardPokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private TypeAttackSimple attackSimple;
    private TypeAttackCombo attackCombo;
    private Integer star;
    @ManyToOne
    @JoinColumn(name = "pokemon_uuid")
    private Pokemon pokemon;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
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
}
