package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.dto.CapturePokemon;
import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.MasterRepository;
import com.efrei.pokemon_tcg.services.IMasterService;
import com.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public boolean toggleCard(String masterUuid, String cardUuid) {
        Master master = masterRepository.findById(masterUuid).orElse(null);
        if (master == null) {
            return false;
        }
        List<CardPokemon> primaryCards = master.getPackageCardsPrimary();
        List<CardPokemon> secondaryCards = master.getPackageCardsSecondary();

        CardPokemon cardToToggle = primaryCards.stream()
                .filter(card -> card.getUuid().equals(cardUuid))
                .findFirst()
                .orElse(null);
        if (cardToToggle != null) {
            primaryCards.remove(cardToToggle);
            secondaryCards.add(cardToToggle);

        } else {
            cardToToggle = secondaryCards.stream()
                    .filter(card -> card.getUuid().equals(cardUuid))
                    .findFirst()
                    .orElse(null);
            if (cardToToggle != null) {
                if (primaryCards.size() >= 5) {
                    throw new IllegalStateException("N'est pas possible d'avoir plus de 5 cartes dans le paquet primaire! Échanges d'abord une carte principale vers le paquet secondaire.");
                }

                secondaryCards.remove(cardToToggle);
                primaryCards.add(cardToToggle);
            } else {
                throw new IllegalStateException("Vous avez déjà effectué votre tirage aujourd'hui !");
            }
        }
        master.setPackageCardsPrimary(primaryCards);
        master.setPackageCardsSecondary(secondaryCards);
        masterRepository.save(master);
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
