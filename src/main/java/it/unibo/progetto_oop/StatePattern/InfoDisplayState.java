package it.unibo.progetto_oop.StatePattern;

import java.awt.Component;

import it.unibo.progetto_oop.Combat.CombatController;
import it.unibo.progetto_oop.PotionStrategy.Potion;

public class InfoDisplayState implements CombatState{

    @Override
    public void enterState(CombatController context) {
        context.getView().showInfo("Name => " + context.getModel().getEnemyName());
        context.getView().showInfo("\n");
        context.getView().showInfo("Power => " + context.getModel().getEnemyPower());
        context.getView().showAttackOptions();
        context.getView().setButtonsEnabled(false);
        
        for (Component comp : context.getView().getAttackButtonPanel().getComponents()) {
            comp.setEnabled(false);
        }
        context.getView().getBackButton().setEnabled(true);
        context.getView().showAttackOptions();
    
    }

    @Override
    public void exitState(CombatController context) {
        context.getModel().resetPositions();
        context.redrawView();
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        context.setStates(new PlayerTurnState());
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
