package com.efrei.pokemon_tcg.constants;

import java.util.Random;

public enum TypeAttackSimple {
    TACKLE, SCRATCH, BITE, PUNCH, KICK, SLAM, CHOP, HEADBUTT, SWIPE, STING;

    public static TypeAttackSimple getRandomAttack() {
        Random random = new Random();
        TypeAttackSimple[] values = TypeAttackSimple.values();
        return values[random.nextInt(values.length)];
    }
}
