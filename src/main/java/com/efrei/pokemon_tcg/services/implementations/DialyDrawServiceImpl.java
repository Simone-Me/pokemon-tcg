package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.repositories.CardPokemonRepository;
import com.efrei.pokemon_tcg.repositories.DailyDrawRepository;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DialyDrawServiceImpl implements IDailyDrawService {

    private final DailyDrawRepository dailyDrawRepository;
    private final CardPokemonRepository cardPokemonRepository;

    public DialyDrawServiceImpl(DailyDrawRepository dailyDrawRepository, CardPokemonRepository cardPokemonRepository) {
        this.dailyDrawRepository = dailyDrawRepository;
        this.cardPokemonRepository = cardPokemonRepository;
    }

    @Override
    public List<CardPokemon> drawDailyCards(Master master) {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        List<CardPokemon> drawnCards = new ArrayList<>();
//TODO->CREATE new objects with the DTO ecurity to not manipulate the BDD
        Optional<DailyDraw> todayDraw = dailyDrawRepository.findByMasterAndDrawDate(master, today);

        if (todayDraw.isPresent()) {
            throw new IllegalStateException("Vous avez déjà effectué votre tirage aujourd'hui !");
        } else {
            DailyDraw draw = new DailyDraw();
            draw.setDrawDate(today);
            draw.setMaster(master);
            List<CardPokemon> allCards = cardPokemonRepository.findAll();

            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(allCards.size());
                CardPokemon randomCardPokemon = allCards.get(randomIndex);
                drawnCards.add(randomCardPokemon);
            }
            draw.setCardPokemon(drawnCards);
            dailyDrawRepository.save(draw);
            return null;
        }
    }

    @Override
    public void addCardToMaster(Master master, CardPokemon card) {
        // TODO-> Associazione di una carta al "packageCardsPrimary" del Master e controllare se si può mettere nel pacco uno fino a 5 se no andare al secondo
        List<CardPokemon> primaryCards = master.getPackageCardsPrimary();
        if (primaryCards == null) {
            primaryCards = new ArrayList<>();
        }
        primaryCards.add(card);
        master.setPackageCardsPrimary(primaryCards);
    }
}
