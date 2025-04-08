package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.PotionStrategy.Potion;
import it.unibo.progetto_oop.google.CombatController;

public class ItemSelectionState implements CombatState{

    @Override
    public void enterState(CombatController context) {
        System.out.println("\nEntrato sezione selezione\n");
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("\nESCO\n");

    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        context.setStates(new PlayerTurnState());
    }

    @Override
    public void handleBagInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        selectedPotion.use(context.getModel());
        context.setStates(new PlayerTurnState());
    }
    
}
