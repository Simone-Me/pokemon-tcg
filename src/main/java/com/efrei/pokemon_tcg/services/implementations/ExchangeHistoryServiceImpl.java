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

        // Vérification de l'échange quotidien
        LocalDateTime now = LocalDateTime.now();
        List<ExchangeHistory> recentExchanges = exchangeHistoryRepository.findByMasterFromUuidOrMasterToUuid(
                masterFromUuid, masterToUuid
        );

        boolean alreadyExchangedToday = recentExchanges.stream()
                .anyMatch(exchange -> exchange.getExchangeDate().toLocalDate().isEqual(now.toLocalDate()));

        if (alreadyExchangedToday) {
            throw new IllegalStateException("Un échange est déjà effectué entre ces dresseurs aujourd'hui.");
        }

        // Trouver les cartes et les déplacer
        CardPokemon cardFrom = masterFrom.getCards().stream()
                .filter(card -> card.getUuid().equals(cardFromUuid))
                .findFirst()
                .orElse(null);

        CardPokemon cardTo = masterTo.getCards().stream()
                .filter(card -> card.getUuid().equals(cardToUuid))
                .findFirst()
                .orElse(null);

        if (cardFrom == null || cardTo == null) {
            throw new IllegalArgumentException("Carte introuvable pour l'un ou l'autre dresseur.");
        }

        masterFrom.getCards().remove(cardFrom);
        masterTo.getCards().remove(cardTo);

        masterFrom.getCards().add(cardTo);
        masterTo.getCards().add(cardFrom);

        masterRepository.save(masterFrom);
        masterRepository.save(masterTo);

        // Enregistrer l'échange dans l'historique
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
}
