package com.efrei.pokemon_tcg.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class MasterDTO {
    private String uuid;

    @NotBlank(message = "Le nom ne peut pas etre vide.")
    @Length(min = 3, max = 30, message = "Le nom doit etre entre 3 et 30 caractere.")
    private String name;

    @NotNull(message = "L'age ne peut pas etre vide.")
    @Min(value = 10, message = "Il fuat avoir plus de 10 ans")
    @Max(value = 150, message = "On pense que tu n'as pas plus de 190 ans, controle à nouveau.")
    private Integer age;

    @NotNull(message = "La ville 'city' doit etre selectionné entre celles disponible, comme SAFFRON_CITY.")
    private String city;

    public MasterDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
