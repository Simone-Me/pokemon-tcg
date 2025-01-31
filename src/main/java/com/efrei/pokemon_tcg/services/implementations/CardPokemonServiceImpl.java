package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.constants.TypeAttackCombo;
import com.efrei.pokemon_tcg.constants.TypeAttackSimple;
import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.CardPokemonRepository;
import com.efrei.pokemon_tcg.repositories.PokemonRepository;
import com.efrei.pokemon_tcg.services.ICardPokemonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CardPokemonServiceImpl implements ICardPokemonService {
    private final PokemonRepository pokemonRepository;
    private final CardPokemonRepository cardPokemonRepository;
    Random random = new Random();

    public CardPokemonServiceImpl(PokemonRepository pokemonRepository, CardPokemonRepository cardPokemonRepository) {
        this.pokemonRepository = pokemonRepository;
        this.cardPokemonRepository = cardPokemonRepository;
    }

    @Override
    public List<CardPokemon> findAll(Integer star) {
        if (star == null) {
            return cardPokemonRepository.findAll();
        }
        return cardPokemonRepository.findAllByStar(star);
    }

    @Override
    public CardPokemon createRandomCard() {
        CardPokemon card = new CardPokemon();

        List<Pokemon> allPokemons = pokemonRepository.findAll();
        if (allPokemons.isEmpty()) {
            throw new IllegalStateException("Aucun Pokemon est disponible.");
        }

        card.setStar(generateRandomStar());
        card.setAttackSimple(TypeAttackSimple.getRandomAttack());

        if (card.getStar() >= 3) {
            TypeAttackCombo randomAttackCombo = TypeAttackCombo.getRandomAttackCombo();
            card.setAttackCombo(randomAttackCombo);
        } else {
            card.setAttackCombo(null);
        }

        int randomIndex = random.nextInt(allPokemons.size());
        Pokemon randomPokemon = allPokemons.get(randomIndex);

        card.setPokemon(pokemonRepository.findById(randomPokemon.getUuid())
                .orElseThrow(() -> new IllegalStateException("Pokemon pas trouvé dans le BDD.")));

        cardPokemonRepository.save(card);
        return card;
    }


    private int generateRandomStar() {
        int chance = random.nextInt(100);

        if (chance < 50) return 1;
        if (chance < 75) return 2;
        if (chance < 90) return 3;
        if (chance < 98) return 4;
        return 5;
    }
}
