package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy{
    /**
     * @param context Istanza di CombatController per chiamare i metodi necessari (performEnemyTurn)
     */
    @Override
    public void chosseAndPerformAttack(CombatController context) {
        context.performEnemyTurn();
    }
    
}
