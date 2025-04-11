package it.unibo.progetto_oop.PotionStrategy;

import it.unibo.progetto_oop.Combat.CombatModel;

public class CurePoison implements PotionEffectStrategy{

    /**
     * @param user Istanza di CombatModel per dire se giocatore è avvelenato o no
     * 
     */
    @Override
    public void apllyEffect(CombatModel user) {
        user.setPlayerPoisoned(false);
    }
    
}
