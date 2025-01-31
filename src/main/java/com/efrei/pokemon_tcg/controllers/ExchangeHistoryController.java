package com.efrei.pokemon_tcg.controllers;

import com.efrei.pokemon_tcg.dto.ExchangeRequestDTO;
import com.efrei.pokemon_tcg.models.ExchangeHistory;
import com.efrei.pokemon_tcg.services.IExchangeHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/exchanges")
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
                return ResponseEntity.ok("Exchange reussi !");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exchange échoué.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erreur: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Un erreur est apparu : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllExchanges(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day) {
        try {
            LocalDate startDate = buildStartDate(year, month, day);
            LocalDate endDate = buildEndDate(year, month, day);

            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<ExchangeHistory> exchanges = exchangeHistoryService.getExchangesByDate(startDateTime, endDateTime);

            return ResponseEntity.ok(exchanges);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/{masterUuid}")
    public ResponseEntity<?> getExchangesByMaster(
            @PathVariable String masterUuid,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day) {
        try {

            LocalDate startDate = buildStartDate(year, month, day);
            LocalDate endDate = buildEndDate(year, month, day);

            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            List<ExchangeHistory> exchanges = exchangeHistoryService.getExchangesByMasterAndDate(masterUuid, startDateTime, endDateTime);

            return ResponseEntity.ok(exchanges);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    private LocalDate buildStartDate(Integer year, Integer month, Integer day) {
        if (year == null) return LocalDate.of(1970, 1, 1);
        if (month == null) return LocalDate.of(year, 1, 1);
        if (day == null) return LocalDate.of(year, month, 1);
        return LocalDate.of(year, month, day);
    }

    private LocalDate buildEndDate(Integer year, Integer month, Integer day) {
        if (year == null) return LocalDate.now();
        if (month == null) return LocalDate.of(year, 12, 31);
        if (day == null) return LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth());
        return LocalDate.of(year, month, day);
    }


}
