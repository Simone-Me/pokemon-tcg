package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {

    List<ExchangeHistory> findByMasterFromUuidOrMasterToUuid(String masterFromUuid, String masterToUuid);

    List<ExchangeHistory> findByExchangeDateBetween(LocalDate startDate, LocalDate endDate);

    List<ExchangeHistory> findByMasterUuidAndExchangeDateBetween(
            @Param("masterUuid") String masterUuid,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
