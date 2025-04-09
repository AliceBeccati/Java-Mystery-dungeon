package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public class LongRangeAttackStrategy implements EnemyAttackStrategy{

    @Override
    public void chosseAndPerformAttack(CombatController context) {
        context.handlePlayerLongRangeAttack(false);
    }
    
}
