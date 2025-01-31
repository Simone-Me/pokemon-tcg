package com.efrei.pokemon_tcg.services.implementations;

import com.efrei.pokemon_tcg.dto.CapturePokemon;
import com.efrei.pokemon_tcg.models.CardPokemon;
import com.efrei.pokemon_tcg.models.Master;
import com.efrei.pokemon_tcg.models.Pokemon;
import com.efrei.pokemon_tcg.repositories.MasterRepository;
import com.efrei.pokemon_tcg.services.IMasterService;
import com.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                    throw new IllegalStateException("Impossible d'avoir plus de 5 cartes dans le paquet principal. Veuillez échanger une carte principale avec une carte secondaire.");
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
    @Transactional
    public boolean challenge(String challengerUuid, String opponentUuid) {
        Master challenger = findById(challengerUuid);
        Master opponent = findById(opponentUuid);

        if (challenger == null || opponent == null) {
            throw new IllegalArgumentException("Un dresseur avec cet identifiant n'existe pas.");
        }

        if (challenger.getPackageCardsPrimary().size() <= 3) {
            throw new IllegalStateException("Le challenger n'a pas suffisamment de cartes pour combattre (minimum 3 cartes).");
        }
        if (opponent.getPackageCardsPrimary().size() <= 3) {
            throw new IllegalStateException("L'opposant n'a pas suffisamment de cartes pour combattre (minimum 3 cartes).");
        }


        boolean challengerWins = Math.random() > 0.5;

        List<CardPokemon> challengerDeck = challenger.getPackageCardsPrimary();
        List<CardPokemon> opponentDeck = opponent.getPackageCardsPrimary();

        if (challengerWins) {
            CardPokemon bestCard = findBestCard(opponentDeck);

            if (bestCard != null) {
                opponentDeck.remove(bestCard);

                if (!challengerDeck.contains(bestCard)) {
                    challengerDeck.add(bestCard);
                }
            }
        } else {
            CardPokemon bestCard = findBestCard(challengerDeck);

            if (bestCard != null) {
                challengerDeck.remove(bestCard);

                if (!opponentDeck.contains(bestCard)) {
                    opponentDeck.add(bestCard);
                }
            }
        }

        challenger.setPackageCardsPrimary(challengerDeck);
        opponent.setPackageCardsPrimary(opponentDeck);

        masterRepository.save(challenger);
        masterRepository.save(opponent);

        return challengerWins;
    }


    private CardPokemon findBestCard(List<CardPokemon> deck) {
        return deck.stream()
                .max((card1, card2) -> Integer.compare(card1.getStar(), card2.getStar()))
                .orElse(null);
    }


    @Override
    public void capturePokemon(String uuid, CapturePokemon capturePokemon) {
        Master master = findById(uuid);
        Pokemon pokemon = pokemonService.findById(capturePokemon.getUuid());
        master.getPokemonsTeam().add(pokemon);
        masterRepository.save(master);
    }
}
