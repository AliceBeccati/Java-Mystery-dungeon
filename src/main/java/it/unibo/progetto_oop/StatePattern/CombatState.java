package it.unibo.progetto_oop.StatePattern;

import it.unibo.progetto_oop.Combat.CombatController;
import it.unibo.progetto_oop.PotionStrategy.Potion;

public interface CombatState {
    
    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per fare i primi compiti necessari per le singole classi, per esempio attivare/disattivare bottoni, scrivere testo ecc...
     */
    void enterState(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per completare gli utlimi compiti prima di passare ad un altro stato, per esempio disattivare/riattivare bottoni, togliere testo, rimettere giocatore/nemico al posto originale ecc...
     */
    void exitState(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria per fare attacco fisico
     */
    void handlePhysicalAttackInput(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * @param isPoison true se attacco con veleno false se non lo è
     * 
     * Usato per chiamare la funzione necessaria per fare attacco lungo raggio (con o senza veleno a seconda di cosa è necesasrio)
     */
    void handleLongRangeAttackInput(CombatController context, boolean isPoison);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria per attivare la funzione di Info
     */
    void handleInfoInput(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria per tornare indietro
     */
    void handleBackInput(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria per aprire borsa
     */
    void handleBagInput(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria per fuggire (sarà scritto fuggisci :) )
     */
    void handleRunInput(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Usato per chiamare la funzione necessaria capire cosa fare una volta finita un'animazione (usato solo e soltanto da AnimatingState)
     */
    void handleAnimationComplete(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * 
     * Capire cosa fare quando finisce timer (non sono nemmeno sicuro se usato o no ahahahah)
     */
    void handleTimerExpired(CombatController context);

    /**
     * 
     * @param context Istanza di controller per chiamare i metodi necessari
     * @param selectedPotion Istanza di Potion che rappresenta la pozione usata
     * 
     * Usato per chiamare la pozione usata
     */
    void handlePotionUsed(CombatController context, Potion selectedPotion);

}
