package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.services.IExchangeHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeHistoryController {
    private final IExchangeHistoryService exchangeHistoryService;
//TODO->test if this is good working or it bugs again :(
    public ExchangeHistoryController(IExchangeHistoryService exchangeHistoryService) {
        this.exchangeHistoryService = exchangeHistoryService;
    }

    public ResponseEntity<?> exangeCards(@RequestParam("masterFromUuid") String masterFromUuid, @RequestParam("cardFromUuid") String cardFromUuid, @RequestParam("masterToUuid") String masterToUuid, @RequestParam("cardToUuid") String cardToUuid) {
        try {
            boolean success = exchangeHistoryService.exchangeCards(masterFromUuid, cardFromUuid, masterToUuid, cardToUuid);
            if (success) {
                return ResponseEntity.ok("Exchange completed successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exchange failed.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred: " + e.getMessage());
        }
    }

}
