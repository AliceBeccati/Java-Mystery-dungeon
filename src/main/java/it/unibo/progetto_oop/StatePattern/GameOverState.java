package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.Combat.CombatController;
import it.unibo.progetto_oop.PotionStrategy.Potion;

public class GameOverState implements CombatState{

    public GameOverState(){}

    @Override
    public void enterState(CombatController context) {
        System.out.println("\n\nFINISHED\n\n");
    }

    @Override
    public void exitState(CombatController context) {}

    @Override
    public void handlePhysicalAttackInput(CombatController context) {}

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {}

    @Override
    public void handleInfoInput(CombatController context) {}

    @Override
    public void handleBackInput(CombatController context) {}

    @Override
    public void handleBagInput(CombatController context) {}

    @Override
    public void handleRunInput(CombatController context) {}

    @Override
    public void handleAnimationComplete(CombatController context) {}

    @Override
    public void handleTimerExpired(CombatController context) {}

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
}
