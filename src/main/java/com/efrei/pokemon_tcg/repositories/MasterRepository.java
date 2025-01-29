package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterRepository extends JpaRepository<Master, String> {
    List<Master> findAllByDeleteAtNull();
}
