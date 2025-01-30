package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.models.ExchangeHistory;

import java.time.LocalDate;
import java.util.List;

public interface IExchangeHistoryService {

    boolean exchangeCards(String masterFromUuid, String cardFromUuid, String masterToUuid, String cardToUuid);

    List<ExchangeHistory> getExchangesByMaster(String masterUuid);

    List<ExchangeHistory> getExchangesByDate(LocalDate startDate, LocalDate endDate);
}