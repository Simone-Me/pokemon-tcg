package com.efrei.pokemon_tcg.services.implementations;


import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.ExchangeHistory;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.repositories.ExchangeHistoryRepository;
import com.efrei.pokemon_tcg.repositories.MasterRepository;
import com.efrei.pokemon_tcg.services.IExchangeHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeHistoryServiceImpl implements IExchangeHistoryService {

    private final ExchangeHistoryRepository exchangeHistoryRepository;
    private final MasterRepository masterRepository;

    public ExchangeHistoryServiceImpl(ExchangeHistoryRepository exchangeHistoryRepository,
                                      MasterRepository masterRepository) {
        this.exchangeHistoryRepository = exchangeHistoryRepository;
        this.masterRepository = masterRepository;
    }

    @Override
    public boolean exchangeCards(String masterFromUuid, String cardFromUuid, String masterToUuid, String cardToUuid) {
        Master masterFrom = masterRepository.findById(masterFromUuid).orElse(null);
        Master masterTo = masterRepository.findById(masterToUuid).orElse(null);

        if (masterFrom == null || masterTo == null) {
            throw new IllegalArgumentException("Un ou plusieurs dresseurs introuvables.");
        }

        LocalDateTime now = LocalDateTime.now();
        List<ExchangeHistory> recentExchanges = exchangeHistoryRepository.findByMasterFromUuidOrMasterToUuid(
                masterFromUuid, masterToUuid
        );

        boolean alreadyExchangedToday = recentExchanges.stream()
                .anyMatch(exchange -> exchange.getExchangeDate().toLocalDate().isEqual(now.toLocalDate()));

        if (alreadyExchangedToday) {
            throw new IllegalStateException("Un échange est déjà effectué entre ces dresseurs aujourd'hui.");
        }


        CardPokemon cardFrom = findCardInMasterPackages(masterFrom, cardFromUuid);
        CardPokemon cardTo = findCardInMasterPackages(masterTo, cardToUuid);

        if (cardFrom == null || cardTo == null) {
            throw new IllegalArgumentException("Carte introuvable pour l'un ou l'autre dresseur.");
        }

        removeCardFromMaster(masterFrom, cardFromUuid);
        removeCardFromMaster(masterTo, cardToUuid);

        addCardToMaster(masterFrom, cardTo);
        addCardToMaster(masterTo, cardFrom);

        masterRepository.save(masterFrom);
        masterRepository.save(masterTo);

        ExchangeHistory history = new ExchangeHistory();
        history.setMasterFromUuid(masterFromUuid);
        history.setMasterToUuid(masterToUuid);
        history.setCardFromUuid(cardFromUuid);
        history.setCardToUuid(cardToUuid);
        history.setExchangeDate(now);

        exchangeHistoryRepository.save(history);

        return true;
    }

    @Override
    public List<ExchangeHistory> getExchangesByMaster(String masterUuid) {
        return exchangeHistoryRepository.findByMasterFromUuidOrMasterToUuid(masterUuid, masterUuid);
    }

    @Override
    public List<ExchangeHistory> getExchangesByDate(LocalDate startDate, LocalDate endDate) {
        return exchangeHistoryRepository.findByExchangeDateBetween(startDate, endDate);
    }

    private CardPokemon findCardInMasterPackages(Master master, String cardUuid) {
        return master.getPackageCardsPrimary().stream()
                .filter(card -> card.getUuid().equals(cardUuid))
                .findFirst()
                .orElse(master.getPackageCardsSecondary().stream()
                        .filter(card -> card.getUuid().equals(cardUuid))
                        .findFirst()
                        .orElse(null));
    }

    private void removeCardFromMaster(Master master, String cardUuid) {
        List<CardPokemon> primaryCards = master.getPackageCardsPrimary();
        List<CardPokemon> secondaryCards = master.getPackageCardsSecondary();

        if (!primaryCards.removeIf(card -> card.getUuid().equals(cardUuid))) {
            secondaryCards.removeIf(card -> card.getUuid().equals(cardUuid));
        }
    }

    private void addCardToMaster(Master master, CardPokemon card) {
        List<CardPokemon> primaryCards = master.getPackageCardsPrimary();
        List<CardPokemon> secondaryCards = master.getPackageCardsSecondary();

        if (primaryCards.size() < 5) {
            primaryCards.add(card);
        } else {
            secondaryCards.add(card);
        }

        master.setPackageCardsPrimary(primaryCards);
        master.setPackageCardsSecondary(secondaryCards);
    }

}
