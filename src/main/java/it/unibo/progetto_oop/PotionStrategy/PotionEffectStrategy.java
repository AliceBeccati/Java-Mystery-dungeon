package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.google.CombatModel;

public interface PotionEffectStrategy {
    /**
     * @param user Istanza di CombatModel per chiamare le funzioni necessarie per cambiare dettagli di giocatore
     */
    void apllyEffect(CombatModel user);
}
