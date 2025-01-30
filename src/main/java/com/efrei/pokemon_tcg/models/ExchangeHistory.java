package com.efrei.pokemon_tcg.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ExchangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String masterFromUuid;
    private String masterToUuid;

    private String cardFromUuid;
    private String cardToUuid;

    private LocalDateTime exchangeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(LocalDateTime exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getCardToUuid() {
        return cardToUuid;
    }

    public void setCardToUuid(String cardToUuid) {
        this.cardToUuid = cardToUuid;
    }

    public String getCardFromUuid() {
        return cardFromUuid;
    }

    public void setCardFromUuid(String cardFromUuid) {
        this.cardFromUuid = cardFromUuid;
    }

    public String getMasterToUuid() {
        return masterToUuid;
    }

    public void setMasterToUuid(String masterToUuid) {
        this.masterToUuid = masterToUuid;
    }

    public String getMasterFromUuid() {
        return masterFromUuid;
    }

    public void setMasterFromUuid(String masterFromUuid) {
        this.masterFromUuid = masterFromUuid;
    }
}