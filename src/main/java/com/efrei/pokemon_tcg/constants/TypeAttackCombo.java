package com.efrei.pokemon_tcg.constants;

import java.util.Random;

public enum TypeAttackCombo {
    BLAST_STRIKE, SHADOW_FRENZY, THUNDER_TORNADO, VOLCANIC_ERUPTION, PSYCHO_BARRAGE, FROSTBITE_FURY, DRAGON_COMET, METEOR_CRUSH, PHANTOM_RAMPAGE, CHAOS_QUAKE;

    public static TypeAttackCombo getRandomAttackCombo() {
        Random random = new Random();
        TypeAttackCombo[] values = TypeAttackCombo.values();
        return values[random.nextInt(values.length)];
    }
}
