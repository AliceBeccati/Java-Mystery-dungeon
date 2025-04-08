package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class Healing implements PotionEffectStrategy{

    private final int healingAmount;

    public Healing(int healingAmount){
        this.healingAmount = healingAmount;
    }

    @Override
    public void apllyEffect(CombatModel user) {
        
        user.increasePlayerHealth(healingAmount);
    }
    
}
