package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.PotionStrategy.Potion;
import it.unibo.progetto_oop.combattimento.Position;
import it.unibo.progetto_oop.google.CombatController;
import it.unibo.progetto_oop.google.CombatModel;
import it.unibo.progetto_oop.google.CombatView;

public class AnimatingState implements CombatState{
    Position centerOfDying;
    boolean fatto = false;

    @Override
    public void enterState(CombatController context) {
        System.out.println("\n\nEntering Animating State\n\n");
        context.getView().setButtonsEnabled(false);
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("Exit\n");
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // Non deve fare assolutamente niente DA IGNORARE
        return;
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        return;
    }

    @Override
    public void handleInfoInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBackInput'");
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

        CombatModel model = context.getModel();
        CombatView view = context.getView();

        boolean isPlayerTurn = model.isPlayerTurn();
        boolean isEnemyPoisoned = model.isEnemyPoisoned((isPlayerTurn ? model.getPlayerPosition() : model.getEnemyPosition()));

        System.out.println("Is player turn => " + isPlayerTurn + "\n is enemy poisoned => " + isEnemyPoisoned);

        if (isEnemyPoisoned && model.getEnemyHealth() > 0){
            System.out.println("FUNZIONA\n");
            System.out.println("Health => " + model.getEnemyHealth());
            if (!isPlayerTurn){
                if (context.getPoisonAnimation()){
                    System.out.println("Timer Running");
                    context.setPoisonAnimation(false);
                }
                else{
                    if (!fatto){
                        context.drawPoison();
                        fatto = true;
                    }
                    else{
                        model.decreaseEnemyHealth(model.getPlayerPoisonPower());
                        fatto = false;
                    }
                }
            }
            else{
                if (context.getPoisonAnimation()){
                    System.out.println("Timer Running");
                    context.setPoisonAnimation(false);
                }
                else{
                    if (!fatto){
                        context.drawPoison();
                        fatto = true;
                    }
                    else{
                        model.decreasePlayerHealth(model.getPlayerPoisonPower());
                        fatto = false;
                    }
                }
            }
            System.out.println("Health => " + model.getEnemyHealth());
            view.updateEnemyHealth(model.getEnemyHealth());
            view.updatePlayerHealth(model.getPlayerHealth());
        }

        if (context.checkGameOver()) {
            System.out.println("Game Over detected. Starting death animation.");
    
            // Determine who died and get their position
            if (model.getPlayerHealth() <= 0) {
                centerOfDying = model.getPlayerPosition(); // Use player's last known position
                System.out.println("Player died at " + centerOfDying);
            } else {
                centerOfDying = model.getEnemyPosition(); // Use enemy's last known position
                System.out.println("Enemy died at " + centerOfDying);
            }
    
            // *** Instead of setState(GameOver), start the death animation ***
            // The callback will set the GameOverState when the animation finishes
            context.performDeathAnimation(centerOfDying, () -> {
                System.out.println("Death animation onComplete: Setting GameOverState.");
                context.setStates(new GameOverState());
            });
    
            return; // Stop further processing (don't transition to next turn)
        }
        else{
            if (context.getPoisonAnimation()){
                System.out.println("Timer Running");
            }
            else{
                context.setStates(isPlayerTurn ? new PlayerTurnState() : new EnemyTurnState());            
            }
        }

        view.updateEnemyHealth(model.getEnemyHealth());
        view.updatePlayerHealth(model.getPlayerHealth());
    }

    @Override
    public void handleTimerExpired(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
    
}
