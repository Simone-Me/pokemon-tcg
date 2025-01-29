package com.efrei.pokemon_tcg.services;

import com.efrei.pokemon_tcg.models.Item;

import java.util.List;

public interface IItemService {
    List<Item> findAll();

    Item findById(String uuid);

    void create(Item item);

    boolean delete(String id);

    boolean update(String uuid, Item item);
}
