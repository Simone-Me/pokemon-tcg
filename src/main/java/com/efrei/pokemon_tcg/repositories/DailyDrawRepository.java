package com.efrei.pokemon_tcg.repositories;


import com.efrei.pokemon_tcg.models.DailyDraw;
import com.efrei.pokemon_tcg.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyDrawRepository extends JpaRepository<DailyDraw, String> {
    Optional<DailyDraw> findFirstByMasterAndDrawDate(Master master, LocalDate drawDate);

}
