package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.dto.ExchangeRequestDTO;
import com.efrei.pokemon_tcg.services.IExchangeHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exchange")
public class ExchangeHistoryController {
    private final IExchangeHistoryService exchangeHistoryService;

    public ExchangeHistoryController(IExchangeHistoryService exchangeHistoryService) {
        this.exchangeHistoryService = exchangeHistoryService;
    }

    @PostMapping()
    public ResponseEntity<?> exchangeCards(@RequestBody ExchangeRequestDTO exchangeRequest
    ) {
        try {
            boolean success = exchangeHistoryService.exchangeCards(
                    exchangeRequest.getMasterFromUuid(),
                    exchangeRequest.getCardFromUuid(),
                    exchangeRequest.getMasterToUuid(),
                    exchangeRequest.getCardToUuid()
            );

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

    @GetMapping("/all")
    public ResponseEntity<?> getAllExchangesByDate(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        try {
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.MIN;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : LocalDate.MAX;

            return ResponseEntity.ok(exchangeHistoryService.getExchangesByDate(start, end));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format: " + e.getMessage());
        }
    }

    @GetMapping("/master/{masterUuid}")
    public ResponseEntity<?> getExchangesByMasterAndDate(
            @PathVariable String masterUuid,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        try {
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.MIN;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : LocalDate.MAX;

            return ResponseEntity.ok(exchangeHistoryService.getExchangesByMasterAndDate(masterUuid, start, end));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format: " + e.getMessage());
        }
    }

}
