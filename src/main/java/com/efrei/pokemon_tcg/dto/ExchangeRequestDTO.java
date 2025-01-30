package com.efrei.pokemon_tcg.dto;

public class ExchangeRequestDTO {
    private String masterFromUuid;
    private String cardFromUuid;
    private String masterToUuid;
    private String cardToUuid;

    // Getters e Setters
    public String getMasterFromUuid() {
        return masterFromUuid;
    }

    public void setMasterFromUuid(String masterFromUuid) {
        this.masterFromUuid = masterFromUuid;
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

    public String getCardToUuid() {
        return cardToUuid;
    }

    public void setCardToUuid(String cardToUuid) {
        this.cardToUuid = cardToUuid;
    }

}
