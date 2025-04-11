package it.unibo.progetto_oop.Combat;


import javax.swing.Timer; // Use Swing Timer for UI updates
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.Button_commands.LongRangeButton;
import it.unibo.progetto_oop.Button_commands.MeleeButton;
import it.unibo.progetto_oop.StatePattern.AnimatingState;
import it.unibo.progetto_oop.StatePattern.CombatState;
import it.unibo.progetto_oop.StatePattern.InfoDisplayState;
import it.unibo.progetto_oop.StatePattern.ItemSelectionState;
import it.unibo.progetto_oop.StatePattern.PlayerTurnState;
import it.unibo.progetto_oop.combattimento.Position;
import it.unibo.progetto_oop.PotionStrategy.AttackBuff;
import it.unibo.progetto_oop.PotionStrategy.CurePoison;
import it.unibo.progetto_oop.PotionStrategy.Healing;
import it.unibo.progetto_oop.PotionStrategy.Potion;

public class CombatController {

    private final CombatModel model;
    private final CombatView view;
    private final MeleeButton meleeCommand;
    private final LongRangeButton longRangeCommand;
    private CombatState currentState;

    private final Potion healthPotion;
    private final Potion antidote;
    private final Potion attackBoost;

    private boolean poisonAnimation;

    final int STAMINA_COST = 10;

    private Timer animationTimer; 
    
    private static final int ANIMATION_DELAY = 100; // millisecondi
    private static final int INFO_ZOOM_DELAY = 200; // millisecondi
    
    public CombatController(CombatModel model, CombatView view) {
        this.model = model;
        this.view = view;
        this.meleeCommand = new MeleeButton();
        this.longRangeCommand = new LongRangeButton();

        // creazione di Barra di vita nella view con informazioni prese da model
        this.view.setHealthBarMax(model.getMaxHealth());
        this.view.updatePlayerHealth(model.getPlayerHealth());
        this.view.updateEnemyHealth(model.getEnemyHealth());
        
        // prima redraw
        this.redrawView();

        // applico ActionListener a tutti i bottoni
        attachListeners();

        // primo Stato del gioco
        this.currentState = new PlayerTurnState();

        // creazione delle Pozioni usate dopo
        this.healthPotion = new Potion("Health Potion", "Resores Hleath", 3, new Healing(25));
        this.antidote = new Potion("Antidote", "Cures from poison", 1, new CurePoison());
        this.attackBoost = new Potion("Attack Boost", "Boosts Attack for rest of encounter", 1, new AttackBuff());
    }


    /**
     * Applicazione di ActionListener a tutti i bottoni
     */
    private void attachListeners() {
        view.addAttackButtonListener(e -> handleAttackMenu());                                  // ActionListener per bottone "Attack"
        view.addPhysicalAttackButtonListener(e -> handlePlayerPhysicalAttack());                // ActionListener per attacco fisico
        view.addLongRangeButtonListener(e -> handlePlayerLongRangeAttack(false));   // false = no veleno
        view.addPoisonButtonListener(e -> handlePlayerLongRangeAttack(true));       // true = sì veleno
        view.addBackButtonListener(e -> handleBackToMainMenu());                                // ActionListener per bottone Indietro
        view.addInfoButtonListener(e -> handleInfo());                                          // ActionListener per bottone Info
        view.addBagButtonListener(e -> handleBagPressed());                                     // bottone borsa
        view.addRunButtonListener(e -> System.out.println("Run clicked - Not implemented"));  // Placeholder 
        view.addAttackBuffButtonListener(e -> handleAttackBuffPressed(this.attackBoost));       // bottone Attack Buff (cambai il argomento che sono tutte istanze di pozioni)
        view.addCurePoisonButtonListener(e -> handleAttackBuffPressed(this.antidote));          // bottone antidoto
        view.addHealingButtonListener(e -> handleAttackBuffPressed(healthPotion));              // bottone più vita
    }

    /**
     * Iniziare tutto
     */
    public void startCombat() {
        view.display();
        view.updatePlayerStamina(model.getPlayerStamina(), model.getMaxStamina());
    }

    /**
     * Versione base di disegno solo giocatore e nemico
     */
    public void redrawView() {
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(),
            true, true, false, false, false, new Position(0, 0), 0, 1, 1, false, new Position(0, 0));
        view.updatePlayerHealth(model.getPlayerHealth());
        view.updateEnemyHealth(model.getEnemyHealth());
    }
    /**
     * 
     * @param drawFlame         Fiamma (attacco lungo raggio)
     * @param drawPoison        Veleno
     * @param drawPlayer        Giocatore
     * @param playerRange       grandezza giocatore
     * @param enemyRange        grandezza nemico
     * @param drawPoisonDamage  Animazione che fa vedere se giocatore o nemico sono avvelenati
     * @param poisonedPlayer    posizione del personaggio avvelenato
     * @param heightPois        posizione (int) del animazione del veleno
     * 
     * mettere true o false a seconda di quale impostazione si vuole
     * Aggiorna anche vita presa informazione da model
     * 
     */
    public void redrawView(boolean drawFlame, boolean drawPoison, boolean drawPlayer, int playerRange, int enemyRange, boolean drawPoisonDamage, Position poisonedPlayer, int heightPois) {
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(),
            drawPlayer, true, drawFlame, drawPoison, drawPoisonDamage, poisonedPlayer, heightPois, playerRange, enemyRange, false, new Position(0, 0));
        view.updatePlayerHealth(model.getPlayerHealth());
        view.updateEnemyHealth(model.getEnemyHealth());
    }

    /**
     * animazione che fa vedere se giocatore/nemico è avvelenato
     */
    public void drawPoison(){
        stopAnimationTimer();
        final int conto[] = {4};                                     // array perché così posso dichiararlo final usarlo nel Timer se no sarebbe stato più scomodo
        this.poisonAnimation = true;
        animationTimer = new Timer(300, e -> {               // Timer con delay di 300 ms perché così potevo vedere da tablet che laggava ahahahahaha
            if (conto[0] == 1){                                     // fine del timer resetto tutto
                conto[0] = 0;
                stopAnimationTimer();
                redrawView();
                this.setPoisonAnimation(false);
                this.currentState.handleAnimationComplete(this);    // chiamo la funzione che tratta la fine delle animazioni 
            }
            else{                                                   // ridisegno tutto con il veleno che sale 
                redrawView(false, false, true, 1, 1, true, (model.isPlayerTurn() ? model.getPlayerPosition() : model.getEnemyPosition()), conto[0]);
                conto[0]--;                                         // faccio salire il veleno
            }
        });
        animationTimer.start();                                     // faccio partire il timer (finisce tutte le prossime chiamate poi fa partire il timer non è coe un for (lo so è strano))
    }

    // --- Handler per ActionListener ---

    /**
     * Opzioni di attacco
     */
    private void handleAttackMenu() {
        if (!model.isPlayerTurn()) {
            return;                                                 // vogliamo che funzioni solo quando è il turno del giocatore
        }
        view.showAttackOptions();
        view.clearInfo();
    }

    /**
     * Opzioni di menu principale
     */
    private void handleBackToMainMenu() {
        this.currentState.handleBackInput(this);
        view.showOriginalButtons();
        view.clearInfo();
        stopAnimationTimer();                                       // fermare qualunque animazione in caso stia ancora andando
    }

    /**
     * Maneggiare info
     */
    private void handleInfo() {
        
        this.currentState.handleInfoInput(this);                    // Chiamo info attraverso la State che fa partire l'animazione di info
    }

    /**
     * Maneggiare attacco fisico
     */
    public void handlePlayerPhysicalAttack() {

        currentState.handlePhysicalAttackInput(this);               // Chiamo attacco fisico attravero State
    }

    /**
     * Fa attacco fisico
     */
    public void performPlayerPhysicalAttack(){
        Runnable onPlayerAttackComplete = () -> {                   // Cosa succede una volta finita l'animaizone
            
            this.currentState.handleAnimationComplete(this);
            startDelayedEnemyTurn();                                // se sopravvive il nemico tocca a lui

        };

        animatePhysicalMove(
            model.getPlayerPosition(),
            model.getEnemyPosition(),
            true,
            model.getPlayerPower(),
            onPlayerAttackComplete
        );
    }
    /**
     * Maneggiare attacco lungo raggio
    */
    public void handlePlayerLongRangeAttack(boolean applyPoison) {
        
        this.currentState.handleLongRangeAttackInput(this, applyPoison);                // dai hai capito cosa succede ahahahahaha
    }
    
    /**
     * fa attacco fisico
    */
    public void applyPlayerLongRangeAttack(boolean applyPoison){
        
        if (model.isPlayerTurn()){
            model.decreasePlayerStamina(STAMINA_COST);                                  // Aggiorna il model
            view.updatePlayerStamina(model.getPlayerStamina(), model.getMaxStamina());  // Aggiorna la vita
        }

        longRangeAttackAnimation(applyPoison, () -> {                                   // Inizia l'animazione
            if (currentState != null) {                                                 // runnable una volta finito il turno AnimationState capisce cosa fare
                currentState.handleAnimationComplete(this);
            }
        }, model.isPlayerTurn());
}


    // --- Logica per il Nemico ---

    /**
     * Inizia il turno del nemico dopo 150 millisecondi così che non parte subito ed è più bello
     */

    public void startDelayedEnemyTurn() {
        Timer enemyTurnDelayTimer = new Timer(150, e -> {
            this.handleEnemyTurn();
        });
        
        enemyTurnDelayTimer.setRepeats(false);
        enemyTurnDelayTimer.start();
    }

    /**
     * Sceglie a caso una mossa da fare
     */
    private void handleEnemyTurn() {
        Random rand = new Random();
        int num = rand.nextInt(3);
        if (num == 0){
            this.currentState.handlePhysicalAttackInput(this);
        }
        else if (num == 1){
            this.currentState.handleLongRangeAttackInput(this, true);
        }
        else {
            this.currentState.handleLongRangeAttackInput(this, false);
        }
    }

    /**
     * Animazione del turno del nemico
     */
    public void performEnemyTurn(){
        view.showInfo("Enemy attacks!");

        // Runnable per cosa succede una volta finita l'animazione
        Runnable onEnemyAttackComplete = () -> {

            this.currentState.handleAnimationComplete(this);
        };

        animatePhysicalMove(
            model.getEnemyPosition(),
            model.getPlayerPosition(),
            false,
            model.getEnemyPower(),
            onEnemyAttackComplete
        );
    }


    // --- Logica Animazione ---

    /**
     * Fermare tutte l'animazione in caso stia ancora andando
     * il Timer è sempre lo stesso così da poter usare questa funzione
     */
    private void stopAnimationTimer() {
        System.out.println("stoppato\n");
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            animationTimer = null;
        }
    }
    
    /**
     * 
     * @param attackerStartPos Poszione iniziale dell'attaccante
     * @param targetStartPos Posizione inizlare del nemico
     * @param isPlayerAttacker veros se attacca il giocatore false se attacca il nemico
     * @param attackPower potenza attaco
     * @param onComplete Funzione che descrive cosa succede una votla finita animazione
     */
    private void animatePhysicalMove( final Position attackerStartPos, final Position targetStartPos, final boolean isPlayerAttacker, final int attackPower, final Runnable onComplete){

        stopAnimationTimer();

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1; // distanza per quando viene considerato vicino

        // Uso array così le posso definire final e usare nel Timer
        final Position[] currentAttackerPos = { new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = { new Position(targetStartPos.x(), targetStartPos.y()) };

        // "Stati" in cui ci troviamo all'interno dell'animazione
        final int[] state = {0}; // 0: andare avanti, 1: Attacco e inizia a tornare indietro, 2: si va indietro
        final boolean[] damageApplied = {false};

        // Chi è chi
        if (isPlayerAttacker) {
            model.setPlayerPosition(currentAttackerPos[0]);
            model.setEnemyPosition(currentTargetPos[0]);
        } else {
            model.setPlayerPosition(currentTargetPos[0]);
            model.setEnemyPosition(currentAttackerPos[0]);
        }

        // creo il timer
        animationTimer = new Timer(ANIMATION_DELAY, null);
        
        animationTimer.addActionListener(event -> {

            Position nextAttackerPos = currentAttackerPos[0];
            Position nextTargetPos = currentTargetPos[0];

            if (!(currentState instanceof AnimatingState)) {
                // In caso ci sia un porblema
                stopAnimationTimer();
                System.out.println("Animation timer stopped: State is no longer AnimatingState.");
                return;
            }

            // --- Logica degli Stati ---
            if (state[0] == 0) {  // si va avanti
                meleeCommand.setAttributes(currentAttackerPos[0], currentTargetPos[0], moveDirection, meleeCheckDistance);
                List<Position> result = meleeCommand.execute(); // Prossima posizione
                nextAttackerPos = result.get(0);
                nextTargetPos = result.get(1); // In caso il difensore è stato spostato
                
                // Controllare se è stato fatto contato o se il difensore si è spostato
                
                if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance) || !nextTargetPos.equals(currentTargetPos[0])) {
                    state[0] = 1; // contato o si è spostato si cambia "stato"
                } 
                
                else if (!nextAttackerPos.equals(currentAttackerPos[0])) {
                    // L'attaccante si è spostato si continua con lo stato 0
                } 
                
                else {
                    // nessun movimento, si da per scontato che sia contato se abbastanza vicino (failsafe)
                    if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance + 1)) {
                       state[0] = 1;
                    } else {
                        // In caso sia bloccato
                        System.err.println("Animation stuck in state 0? Forcing state 1.");
                        state[0] = 1;
                    }
                }
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;

            } else if (state[0] == 1) { // Danno e inizia a tornare indietro
                if (!damageApplied[0]) {
                    if (isPlayerAttacker) {
                        model.decreaseEnemyHealth(attackPower);
                        view.updateEnemyHealth(model.getEnemyHealth());
                    } 
                    else {
                        model.decreasePlayerHealth(attackPower);
                        view.updatePlayerHealth(model.getPlayerHealth());
                    }
                    damageApplied[0] = true;
                }
                // Sposto entrambi per poi iniziare a tornare indietro
                nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                nextTargetPos = new Position(currentTargetPos[0].x() + returnDirection, currentTargetPos[0].y()); // Target also moves back
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;
                state[0] = 2; // Cambio "stato" per tornare indietro

            } else { // Si torna alla posizione iniziale
                if (currentAttackerPos[0].x() == attackerStartPos.x()) { // Attaccante ha raggiunto l'inizio
                    stopAnimationTimer();

                    // In caso non sia preciso lo metto alla posizione iniziale
                    currentAttackerPos[0] = attackerStartPos;
                    
                    if (isPlayerAttacker) {
                        model.setPlayerPosition(currentAttackerPos[0]);
                        model.setEnemyPosition(currentTargetPos[0]);
                        this.getModel().setPlayerTurn(!isPlayerAttacker);    
                    } else {
                        model.setPlayerPosition(currentTargetPos[0]); 
                        model.setEnemyPosition(currentAttackerPos[0]);
                        this.getModel().setPlayerTurn(!isPlayerAttacker);
                    }
                    redrawView();

                    // Fine animazione chiamo AnimatingState
                    if (onComplete != null) {
                        onComplete.run();
                    }
                    return; // Fine timer

                } else { // Continua ad andare indietro
                    nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                    // difensore non si muove quando attaccante si muove
                    currentAttackerPos[0] = nextAttackerPos;
                    System.out.println("TARGET POS => " + currentTargetPos[0]);
                }
            }

            // --- Aggiornamento di view e model ---
            if (isPlayerAttacker) {
                model.setPlayerPosition(currentAttackerPos[0]);
                model.setEnemyPosition(currentTargetPos[0]);
            } else {
                model.setPlayerPosition(currentTargetPos[0]); 
                model.setEnemyPosition(currentAttackerPos[0]); 
            }
            redrawView(); // redraw base perché non c'è niente di strano

        });
        animationTimer.setInitialDelay(150);
        animationTimer.start();
        System.out.println("iniziato\n");
    }

    /**
     * 
     * @param isPoison vero o falso se è attacco con veleno o meno
     * @param onHit Runnable per cosa succede dopo aver finito animazione
     * @param isPlayerAttacker vero o falso se giocatore è attaccante
     */
    private void longRangeAttackAnimation(boolean isPoison, Runnable onHit, boolean isPlayerAttacker) {
        stopAnimationTimer();

        Position reciever = (isPlayerAttacker ? model.getEnemyPosition() : model.getPlayerPosition());
        Position attacker = (isPlayerAttacker ? model.getPlayerPosition() : model.getEnemyPosition());
        model.setFlamePosition(attacker); // Poszione iniziale della fiamma (attaccante)
        int direction = (isPlayerAttacker ? -1 : 1);

        animationTimer = new Timer(ANIMATION_DELAY, e -> {
            // Controllo se fiamma è alla posizione del difensore
            if (model.getFlamePosition().x() == reciever.x() + direction) { // se è adiacente
                stopAnimationTimer();
                // Rimetto la fiamma alla posizione dell'attaccante
                model.setFlamePosition(attacker);  //la fiamma viene disegnata sotto l'attaccante se per sbaglio disegnata assieme agli altri
                
                if (attacker.equals(model.getPlayerPosition())){
                    this.model.decreaseEnemyHealth(model.getPlayerPower());
                }
                else{
                    this.model.decreasePlayerHealth(model.getEnemyPower());
                }
                model.setEnemyPoisoned((this.model.isEnemyPoisoned(reciever) || isPoison), reciever);
                this.view.updateEnemyHealth(model.getEnemyHealth());

                redrawView(false, false, true, 1, 1, false, new Position(0, 0), 0); // Redraw senza fiamma/veleno 
                model.setPlayerTurn(!model.isPlayerTurn());
                if (onHit != null) {
                   onHit.run();
                }
                
                return;
            }

            // sposta fiamma
            longRangeCommand.setAttributes(model.getFlamePosition(), (isPlayerAttacker ? 1 : -1));
            Position nextFlamePos = longRangeCommand.execute().get(0);
            model.setFlamePosition(nextFlamePos);

            // Redraw con fiamma/veleno
            redrawView( !isPoison, isPoison, true, 1, 1, false, new Position(0, 0), 0);

        });
        animationTimer.start();
    }


    /**
     * 
     * @param onZoomComplete Runnable ormai sai cosa fa
     * 
     * porta nemico al centro e dopo lo ingrandisce
     */
    public void performInfoZoomInAnimation(Runnable onZoomComplete) {
        System.out.println("Performing Info Zoom-In Animation...");
        stopAnimationTimer();
        view.setButtonsEnabled(false); // disabilita tutti i bottoni

        final int targetX = model.getSize() / 2;

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            Position currentEnemyPos = model.getEnemyPosition();

            if (currentEnemyPos.x() <= targetX) {
                // --- Zoom completato ---
                stopAnimationTimer();
                // Assicurazione che sia al centro
                model.setEnemyPosition(new Position(targetX, currentEnemyPos.y()));

                this.model.setEnemyPosition(new Position(targetX, currentEnemyPos.y()));
                
                this.makeBigger(5, onZoomComplete);

            } else {
                // --- non ancora al centro ---
                model.setEnemyPosition(new Position(currentEnemyPos.x() - 1, currentEnemyPos.y()));
                redrawView(false, false, false, 1, 1, false, new Position(0, 0), 0);
            }
        });
        animationTimer.start();
    }


    /**
     * 
     * @param size grandezza necessaria
     * @param onZoomComplete Runnable per fine animazione
     */
    private void makeBigger(int size, Runnable onZoomComplete){
        final int conto[] = {1};
        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            if (conto[0] > size){
                stopAnimationTimer();
                conto[0] = 0;
                if (onZoomComplete != null) {
                    System.out.println("Zoom-in complete, executing callback.");
                    onZoomComplete.run(); // farà partire nuovo State
                }
            }
            else{
                redrawView(false, false, false, 1, conto[0], false, new Position(0, 0), 0);
                conto[0]++;
            }
        });
        animationTimer.start();
    }


    // Chiamato da PlayerTurnState
    public void performInfoAnimation() {
        // fa partire zoom in
        performInfoZoomInAnimation(() -> {
            // Runnabel
            this.setStates(new InfoDisplayState());
        });
        
        view.showInfo("Enemy Info:\nName: " + model.getEnemyName() + "\nPower: " + model.getEnemyPower());
    }

    /**
     * 
     * @return true se qualcuno è morto false se no
     */
    public boolean checkGameOver() {
        if (model.isGameOver()) {
            stopAnimationTimer(); 
            view.setButtonsEnabled(false);
            String winner = model.getPlayerHealth() <= 0 ? "Enemy" : "Player";
            view.showInfo("Game Over! " + winner + " wins!");
            return true;
        }
        return false;
    }

    /**
     * 
     * @param death posizione di chi è morto
     * @param onComplete funzione da fare andare dopo
     */
    public void performDeathAnimation(Position death, Runnable onComplete){
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), death, 
        true, true, false, false, false, new Position(0, 0), 0, 1, 2, true, death);
        if (onComplete != null){
            onComplete.run();
        }
    }

    /**
     * cosa succede e premi borsa
     */
    public void handleBagPressed(){
        this.setStates(new ItemSelectionState());
        this.view.showBagButtons();
        view.clearInfo();
        stopAnimationTimer();
    }

    /**
     * 
     * @param potion Pozione
     */
    public void handleAttackBuffPressed(Potion potion){
        this.currentState.handlePotionUsed(this, potion);
    }

    // GETTER

    public CombatModel getModel(){
        return this.model;
    }

    public CombatView getView(){
        return this.view;
    }

    public Timer getAnimationTimer(){
        return animationTimer;
    }

    public boolean getPoisonAnimation(){
        return this.poisonAnimation;
    }

    public int getStaminaCost(){
        return this.STAMINA_COST;
    }

    // SETTER 

    public void setStates(CombatState newState){
        this.currentState.exitState(this);
        this.currentState = newState;
        this.currentState.enterState(this);
    }

    public void setPoisonAnimation(boolean value){
        this.poisonAnimation = value;
    }

}