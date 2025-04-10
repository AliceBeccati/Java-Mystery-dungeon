package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class Healing implements PotionEffectStrategy{

    private final int healingAmount;
    /**
     * 
     * @param healingAmount quantità di vita data quando chiamato
     * 
     */
    public Healing(int healingAmount){
        this.healingAmount = healingAmount;
    }
    /**
     * @param user Istanza di CombatModel per chiamare il metodo per aumentare la vita
     */
    @Override
    public void apllyEffect(CombatModel user) {
        
        user.increasePlayerHealth(healingAmount);
    }
    
}
