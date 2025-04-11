package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.Combat.CombatController;
import it.unibo.progetto_oop.PotionStrategy.Potion;

public class EnemyTurnState implements CombatState{

    @Override
    public void enterState(CombatController context) {
        
        context.getView().setButtonsEnabled(false); // in caso non fosse successo con animatingState
        context.startDelayedEnemyTurn();
    }

    @Override
    public void exitState(CombatController context) {
        
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        context.setStates(new AnimatingState());

        context.performEnemyTurn();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        context.applyPlayerLongRangeAttack(isPoison);
        context.setStates(new AnimatingState());
    }

    @Override
    public void handleInfoInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBackInput'");
    }

    @Override
    public void handleBagInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
}
