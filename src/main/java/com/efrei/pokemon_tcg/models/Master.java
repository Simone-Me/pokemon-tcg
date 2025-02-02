package com.efrei.pokemon_tcg.models;

import com.efrei.pokemon_tcg.constants.City;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String name;
    private Integer age;
    private City city;
    private LocalDateTime deleteAt;
    @OneToMany
    private List<Pokemon> pokemonsTeam;
    @OneToMany
    private List<CardPokemon> packageCardsPrimary;
    @OneToMany
    private List<CardPokemon> packageCardsSecondary;

    public Master(String name, Integer age, City city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public Master() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Pokemon> getPokemonsTeam() {
        return pokemonsTeam;
    }

    public void setPokemonsTeam(List<Pokemon> pokemonsTeam) {
        this.pokemonsTeam = pokemonsTeam;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<CardPokemon> getPackageCardsSecondary() {
        return packageCardsSecondary;
    }

    public void setPackageCardsSecondary(List<CardPokemon> packageCardsSecondary) {
        this.packageCardsSecondary = packageCardsSecondary;
    }

    public List<CardPokemon> getPackageCardsPrimary() {
        return packageCardsPrimary;
    }

    public void setPackageCardsPrimary(List<CardPokemon> packageCardsPrimary) {
        this.packageCardsPrimary = packageCardsPrimary;
    }
}
