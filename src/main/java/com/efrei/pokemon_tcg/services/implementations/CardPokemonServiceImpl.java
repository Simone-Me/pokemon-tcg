package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.constants.TypeAttackCombo;
import com.efrei.pokemon_tcg.constants.TypeAttackSimple;
import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.CardPokemonRepository;
import com.efrei.pokemon_tcg.repositories.DailyDrawRepository;
import com.efrei.pokemon_tcg.repositories.MasterRepository;
import com.efrei.pokemon_tcg.repositories.PokemonRepository;
import com.efrei.pokemon_tcg.services.ICardPokemonService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CardPokemonServiceImpl implements ICardPokemonService {
    private final PokemonRepository pokemonRepository;
    private final CardPokemonRepository cardPokemonRepository;
    private final DailyDrawRepository dailyDrawRepository;
    private final MasterRepository masterPackageRepository;
    Random random = new Random();

    public CardPokemonServiceImpl(PokemonRepository pokemonRepository, CardPokemonRepository cardPokemonRepository, DailyDrawRepository dailyDrawRepository, MasterRepository masterPackageRepository) {
        this.pokemonRepository = pokemonRepository;
        this.cardPokemonRepository = cardPokemonRepository;
        this.dailyDrawRepository = dailyDrawRepository;
        this.masterPackageRepository = masterPackageRepository;
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

    @Transactional
    @Override
    public void deleteCardByUuid(String uuid) {
        if (!cardPokemonRepository.existsById(uuid)) {
            throw new IllegalStateException("CardPokemon pas trouvé avec l'UUID: " + uuid);
        }
        masterPackageRepository.removeCardAssociations(uuid);
        dailyDrawRepository.removeCardAssociation(uuid);

        cardPokemonRepository.deleteById(uuid);
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
