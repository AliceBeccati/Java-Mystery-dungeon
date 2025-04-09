package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy{

    @Override
    public void chosseAndPerformAttack(CombatController context) {
        context.performEnemyTurn();
    }
    
}
