package com.efrei.pokemon_tcg.repositories;


import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyDrawRepository extends JpaRepository<DailyDraw, String> {
    Optional<DailyDraw> findFirstByMasterAndDrawDate(Master master, LocalDate drawDate);

    @Modifying
    @Query(value = "DELETE FROM daily_draw_cards WHERE card_uuid = :uuid", nativeQuery = true)
    void removeCardAssociation(@Param("uuid") String uuid);
}
