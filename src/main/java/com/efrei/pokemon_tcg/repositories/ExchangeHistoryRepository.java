package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.ExchangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExchangeHistoryRepository extends JpaRepository<ExchangeHistory, Long> {

    List<ExchangeHistory> findByMasterFromUuidOrMasterToUuid(String masterFromUuid, String masterToUuid);

    List<ExchangeHistory> findByExchangeDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM ExchangeHistory e " +
            "WHERE (e.masterFromUuid = :masterUuid OR e.masterToUuid = :masterUuid) " +
            "AND e.exchangeDate >= :startDate AND e.exchangeDate <= :endDate")
    List<ExchangeHistory> findExchangesByMasterAndDateBetween(
            @Param("masterUuid") String masterUuid,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
