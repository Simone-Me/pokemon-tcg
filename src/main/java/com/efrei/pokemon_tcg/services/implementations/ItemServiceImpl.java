package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.models.Item;
import com.efrei.pokemon_tcg.repositories.ItemRepository;
import com.efrei.pokemon_tcg.services.IItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements IItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item findById(String uuid) {
        return repository.findById(uuid).orElse(null);
    }

    @Override
    public void create(Item item) {
        Item itemToCreate = new Item();
        itemToCreate.setName(item.getName());
        repository.save(itemToCreate);
    }

    @Override
    public boolean delete(String uuid) {
        Item itemToDelete = findById(uuid);
        if (itemToDelete == null) {
            return false;
        }
        repository.deleteById(uuid);
        return true;
    }

    @Override
    public boolean update(String uuid, Item item) {
        Item itemToUpdate = findById(uuid);
        if(itemToUpdate == null) {
            return false;
        }
        itemToUpdate.setName(item.getName());
        repository.save(itemToUpdate);
        return true;
    }
}
