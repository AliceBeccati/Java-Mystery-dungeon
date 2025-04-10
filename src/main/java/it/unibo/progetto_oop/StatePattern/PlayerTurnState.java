package it.unibo.progetto_oop.StatePattern; // Or your appropriate package

import it.unibo.progetto_oop.PotionStrategy.Potion;
import it.unibo.progetto_oop.google.CombatController;

public class PlayerTurnState implements CombatState { // Must implement the interface

    @Override
    public void enterState(CombatController context) {

        context.getView().showOriginalButtons();       
        context.getView().setButtonsEnabled(true);
        context.redrawView();
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("Exiting Player Turn State");
        
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        
        // 1. cambio stato
        context.setStates(new AnimatingState());
        
        // 2. inizia l'attacco
        context.performPlayerPhysicalAttack();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean applyPoisonIntent) {
            
        // controlllo stamina
        if (context.getModel().getPlayerStamina() < context.getStaminaCost()) {
            context.getView().showInfo("Not enough Stamina!");
            return; // rimane in playerState
        }
    
        context.setStates(new AnimatingState());
        context.applyPlayerLongRangeAttack(applyPoisonIntent);
    }

    @Override
    public void handleInfoInput(CombatController context) {
        context.setStates(new AnimatingState());
        context.performInfoAnimation();
    }

    @Override
    public void handleBagInput(CombatController context) {
        context.setStates(new ItemSelectionState());
    }

    @Override
    public void handleRunInput(CombatController context) {
        context.getView().showInfo("Run not implemented yet!");
    }


    @Override
    public void handleBackInput(CombatController context) {
        System.out.println("PlayerTurnState: Back input ignored in main turn view.");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        System.err.println("LOGIC ERROR: handleAnimationComplete called during PlayerTurnState!");
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        System.out.println("PlayerTurnState: Timer expired (no action configured).");
    }

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
    
}