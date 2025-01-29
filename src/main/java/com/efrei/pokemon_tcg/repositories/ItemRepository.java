package com.efrei.pokemon_tcg.repositories;

import com.efrei.pokemon_tcg.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
}
