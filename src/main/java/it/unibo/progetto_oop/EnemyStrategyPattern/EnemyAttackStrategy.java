package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.google.CombatController;

public interface EnemyAttackStrategy {
    public void chosseAndPerformAttack(CombatController context);
}
