package com.efrei.pokemon_tcg;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokemonTcgApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonTcgApplication.class, args);

        Faker faker = new Faker();

        String name = faker.pokemon().location();
        System.out.println(name);

    }

}
