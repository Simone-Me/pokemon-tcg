
# Projet API Pokémon - Gestion des Cartes et Combats

Ceci est un projet realisé au sein de l'école EFREI pour le cours de JEE + JAKARTA.
Durée de réalisation : 29.01 - 31.01

## Description du projet

Ce projet consiste en la création d'une API qui simule un univers de cartes Pokémon avec les fonctionnalités suivantes :

- **Tirage quotidien de cartes :**  
  Chaque utilisateur peut tirer un lot de **5 cartes générées aléatoirement** par jour.  
  Une carte représente un Pokémon, dispose de **2 attaques** et d'une **rareté** allant de 1 à 5 étoiles. (la seconde attaque sera attribué lors que la carte est niveau rareté 3 ou plus)
    - La rareté de la carte est **générée aléatoirement** avec une probabilité plus faible pour les cartes 5 étoiles.
    - Une carte ayant la même espèce peut différer par sa rareté (par exemple, une carte Pikachu 1 étoile est différente d'une carte Pikachu 5 étoiles).

- **Échanges entre dresseurs :**
    - Deux dresseurs peuvent **échanger leurs cartes** entre eux, mais limité à **une seule fois par jour**.

- **Gestion des paquets de cartes :**
    - Chaque dresseur possède deux paquets de cartes :
        1. **Paquet principal** : Contient 5 cartes principales utilisées pour les combats. (le combat nécessitant d'au moins 3 cartes pour être effectué)
        2. **Paquet secondaire** : Contient toutes les autres cartes.
    - Les cartes peuvent être **échangées entre le paquet principal et secondaire** à tout moment.

- **Système de combats :**
    - Un dresseur peut **défier un autre dresseur** en combat.
    - Le dresseur qui gagne le combat remporte **la meilleure carte** du perdant (provenant de son paquet principal).

- **Historique des échanges :**
    - Il est possible d'accéder à **l'historique complet des échanges de cartes.**
    - Chaque dresseur peut aussi consulter **son propre historique.**
    - Cet historique peut être **filtré** par jour, mois ou année.

- **Authentification :**
    - Toutes les routes de l'API nécessitent une **authentification.**

## Technologies utilisées

- **Langage :** Java (JDK 17)
- **Framework backend :** Jakarta EE & Spring (Spring Boot, Spring MVC, Spring Data JPA)
- **Base de données :** H2
- **Authentification :** API-AUTH
- **Autres :** Maven