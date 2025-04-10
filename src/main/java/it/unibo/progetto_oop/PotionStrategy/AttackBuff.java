package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public class AttackBuff implements PotionEffectStrategy {

    private final int power = 5;
    /**
     * @param user Istanza di CombatModel per aumentare la potenza del giocatore per ora di 5 ma poi se necessario si può aggiungere un metodo per personalizzare la quantità
     */
    @Override
    public void apllyEffect(CombatModel user) {
        user.increasePlayerPower(this.power);
    }
    
}
