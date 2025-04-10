package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public class LongRangeAttackStrategy implements EnemyAttackStrategy{

    /**
     * @param context Istanza di CombatController per chiamare i metodi necessari (handlePlayerLongRangeAttack)
     * chiama la funzione di attacco a lungo raggio con il parametro applyPoison messo a false perché senza veleno
     */
    @Override
    public void chosseAndPerformAttack(CombatController context) {
        context.handlePlayerLongRangeAttack(false);
    }
    
}
