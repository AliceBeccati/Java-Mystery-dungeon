package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.Combat.CombatController;
import it.unibo.progetto_oop.Combat.CombatModel;
import it.unibo.progetto_oop.Combat.CombatView;
import it.unibo.progetto_oop.PotionStrategy.Potion;
import it.unibo.progetto_oop.combattimento.Position;

public class AnimatingState implements CombatState{
    Position centerOfDying;
    boolean fatto = false;

    @Override
    public void enterState(CombatController context) {
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
        
        CombatModel model = context.getModel();
        CombatView view = context.getView();
        // si da per scontato che il turno sia stato cambiato prima di chiamare questa funzione
        boolean isPlayerTurn = model.isPlayerTurn();
        boolean isEnemyPoisoned = model.isEnemyPoisoned((isPlayerTurn ? model.getPlayerPosition() : model.getEnemyPosition()));

        if (isEnemyPoisoned && model.getEnemyHealth() > 0){
            // questi controlli sono principalmente per animazione di veleno
            if (!isPlayerTurn){
                if (context.getPoisonAnimation()){
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
            view.updateEnemyHealth(model.getEnemyHealth());
            view.updatePlayerHealth(model.getPlayerHealth());
        }

        if (context.checkGameOver()) {
    
            // chi è morto e dai la posizione
            if (model.getPlayerHealth() <= 0) {
                centerOfDying = model.getPlayerPosition(); 
                System.out.println("Player died at " + centerOfDying);
            } else {
                centerOfDying = model.getEnemyPosition(); 
                System.out.println("Enemy died at " + centerOfDying);
            }
    
            context.performDeathAnimation(centerOfDying, () -> {
                context.setStates(new GameOverState());
            });
    
            return; // non si mette il prossimo turno
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
        throw new UnsupportedOperationException("Unimplemented method 'handleTimerExpired'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Potion selectedPotion) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
    
}
