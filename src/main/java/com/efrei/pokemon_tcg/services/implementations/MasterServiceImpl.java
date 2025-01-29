package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.dto.CapturePokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.MasterRepository;
import com.efrei.pokemon_tcg.services.IMasterService;
import com.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MasterServiceImpl implements IMasterService {

    private final MasterRepository masterRepository;
    private final IPokemonService pokemonService;

    public MasterServiceImpl(MasterRepository masterRepository, PokemonServiceImpl pokemonService) {
        this.masterRepository = masterRepository;
        this.pokemonService = pokemonService;
    }

    @Override
    public List<Master> findAll() {
        return masterRepository.findAllByDeleteAtNull();
    }

    @Override
    public Master findById(String uuid) {
        return masterRepository.findById(uuid).orElse(null);
    }

    @Override
    public void create(Master master) {
        Master masterToCreate = new Master();
        masterToCreate.setName(master.getName());
        masterToCreate.setAge(master.getAge());
        masterToCreate.setCity(master.getCity());
        masterRepository.save(masterToCreate);
    }

    @Override
    public boolean update(String uuid, Master master) {
        Master masterToUpdate = findById(uuid);
        if (masterToUpdate == null) {
            return false;
        }
        masterToUpdate.setName(master.getName());
        masterToUpdate.setAge(master.getAge());
        masterToUpdate.setCity(master.getCity());
        masterRepository.save(masterToUpdate);
        return true;
    }

    @Override
    public boolean delete(String uuid) {
        Master masterToDelete = findById(uuid);
        if (masterToDelete == null) {
            return false;
        }
        masterToDelete.setDeleteAt(LocalDateTime.now());
        masterRepository.save(masterToDelete);
        return true;
    }

    @Override
    public void capturePokemon(String uuid, CapturePokemon capturePokemon) {
        Master master = findById(uuid);
        Pokemon pokemon = pokemonService.findById(capturePokemon.getUuid());
        master.getPokemonsTeam().add(pokemon);
        masterRepository.save(master);
    }

}
