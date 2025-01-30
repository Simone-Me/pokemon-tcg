package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.repositories.CardPokemonRepository;
import com.efrei.pokemon_tcg.repositories.DailyDrawRepository;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DialyDrawServiceImpl implements IDailyDrawService {

    private final DailyDrawRepository dailyDrawRepository;
    private final CardPokemonRepository cardPokemonRepository;
    private final CardPokemonServiceImpl cardPokemonServiceImpl;


    public DialyDrawServiceImpl(DailyDrawRepository dailyDrawRepository, CardPokemonRepository cardPokemonRepository, CardPokemonServiceImpl cardPokemonServiceImpl) {
        this.dailyDrawRepository = dailyDrawRepository;
        this.cardPokemonRepository = cardPokemonRepository;
        this.cardPokemonServiceImpl = cardPokemonServiceImpl;
    }

    @Override
    public List<DailyDraw> getAll() {
        return dailyDrawRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> drawDailyCards(Master master) {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        List<CardPokemon> drawnCards = new ArrayList<>();
        List<Map<String, String>> responseMessages = new ArrayList<>();

//TODO->CREATE new objects with the DTO security to not manipulate the BDD
        Optional<DailyDraw> todayDraw = dailyDrawRepository.findFirstByMasterAndDrawDate(master, today);

        if (!todayDraw.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            throw new IllegalStateException("Vous avez déjà effectué votre tirage aujourd'hui !");
        } else {
            DailyDraw draw = new DailyDraw();
            draw.setDrawDate(today);
            draw.setMaster(master);
            List<CardPokemon> primaryCards = master.getPackageCardsPrimary();
            List<CardPokemon> secondaryCards = master.getPackageCardsSecondary();

            Map<String, String> logMessage = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                CardPokemon newCard = cardPokemonServiceImpl.createRandomCard();
                drawnCards.add(newCard);
                if (primaryCards.size() <= 5) {
                    primaryCards.add(newCard);
                    logMessage.put("card", newCard.getPokemon().getName());
                    logMessage.put("location", "1 carte ajouté au package primaire");
                    //responseMessages.add(logMessage);
                } else {
                    secondaryCards.add(newCard);
                    logMessage.put("card", newCard.getPokemon().getName());
                    logMessage.put("location", "1 carte ajouté au package secondaire");
                    //responseMessages.add(logMessage);
                }
                responseMessages.add(new HashMap<>(logMessage));
            }
            draw.setCardPokemon(drawnCards);
            dailyDrawRepository.save(draw);

            return new ResponseEntity<>(responseMessages, new HttpHeaders(), HttpStatusCode.valueOf(200));

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
