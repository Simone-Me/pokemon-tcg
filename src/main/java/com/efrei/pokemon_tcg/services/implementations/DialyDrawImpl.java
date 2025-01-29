package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.repositories.DailyDrawRepository;
import com.efrei.pokemon_tcg.services.IDailyDrawService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DialyDrawImpl implements IDailyDrawService {

    private final DailyDrawRepository repository;
    private final DailyDrawRepository dailyDrawRepository;

    public DialyDrawImpl(DailyDrawRepository repository, DailyDrawRepository dailyDrawRepository) {
        this.repository = repository;
        this.dailyDrawRepository = dailyDrawRepository;
    }

    @Override
    public List<CardPokemon> drawDailyCards(Master master) {

        LocalDate today = LocalDate.now();
        Optional<DailyDraw> todayDraw = dailyDrawRepository.findByMasterAndDrawDate(master, today);

        if (todayDraw.isPresent()) {
            throw new IllegalStateException("Vous avez déjà effectué votre tirage aujourd'hui !");
        }

        DailyDraw draw = new DailyDraw();
        draw.setDrawDate(today);
        draw.setMaster(master);
        draw.setCardPokemon(List.of());
        dailyDrawRepository.save(draw);

        return null;
    }
}
