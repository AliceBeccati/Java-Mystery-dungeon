package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public interface EnemyAttackStrategy {
    /**
     * @param context Istanza di CombatController per chiamare i metodi necessari
    */
     public void chosseAndPerformAttack(CombatController context);
}
