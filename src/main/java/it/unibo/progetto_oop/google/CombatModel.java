package it.unibo.progetto_oop.google;

import java.util.Objects;
import it.unibo.progetto_oop.combattimento.Position;

// Simple Position record (can be a class too)

public class CombatModel {

    private int size;
    private Position playerPosition;
    private Position enemyPosition;
    private Position flamePosition; // For long-range attack animation
    private Position deadPlayer;

    private int playerHealth;
    private int enemyHealth;
    private int playerStamina;
    private final int maxStamina = 100; // Or your desired max
    private final int maxHealth = 100; // Assuming max health is 100

    private int playerPower;
    private int playerPoisonPower;
    private int enemyPower;
    private int enemySpeed; // If needed for future logic
    private String enemyName; // If needed

    private boolean enemyPoisoned;
    private boolean playerPoisoned = false;
    private boolean isPlayerTurn = true; // Added to manage turns

    public CombatModel(int size, int playerPower, int playerPoisonPower, int enemyPower, int enemySpeed, String enemyName) {
        this.size = size;
        this.playerPower = playerPower;
        this.playerPoisonPower = playerPoisonPower;
        this.enemyPower = enemyPower;
        this.enemySpeed = enemySpeed;
        this.enemyName = enemyName;

        this.playerStamina = maxStamina;

        // Initialize health
        this.playerHealth = 100;
        this.enemyHealth = 100;

        // Initialize positions
        resetPositions();
        this.flamePosition = this.playerPosition; // Initial flame position
        this.enemyPoisoned = false;
    }

    public void resetPositions() {
        // Same logic as original Player() method
        this.playerPosition = new Position((this.size / 3) - 2, (this.size / 2));
        this.enemyPosition = new Position(this.size - ((this.size / 3) - 1), (this.size / 2));
    }

    // --- Getters ---
    public int getSize() { 
        return size; 
    }
    public Position getPlayerPosition() { 
        return playerPosition; 
    }
    public Position getEnemyPosition() { 
        return enemyPosition; 
    }
    public Position getFlamePosition() { 
        return flamePosition; 
    }
    public int getPlayerHealth() { 
        return playerHealth; 
    }
    public int getEnemyHealth() { 
        return enemyHealth; 
    }
    public int getMaxHealth() { 
        return maxHealth; 
    }
    public int getPlayerPower() { 
        return playerPower; 
    }
    public int getEnemyPower() { 
        return enemyPower; 
    }
    public int getPlayerPoisonPower() { 
        return playerPoisonPower; 
    }
    public boolean isEnemyPoisoned(Position poisoned) { 
        if (poisoned.equals(playerPosition )){
            return this.playerPoisoned;
        }
        else if(poisoned.equals(enemyPosition)){
            return this.enemyPoisoned;
        }
        else {
            throw new IllegalArgumentException("Argument no Player nor Enemy");
        }
    }
    public boolean isPlayerPoisoned(){
        return playerPoisoned;
    }
    public String getEnemyName() { 
        return enemyName; 
    }
    public int getEnemySpeed() { 
        return enemySpeed; 
    }
    public boolean isPlayerTurn() { 
        return isPlayerTurn; 
    }

    public Position whoDied(){
        return this.deadPlayer;
    }

    public int getPlayerStamina() { 
        return playerStamina; 
    }
    
    public int getMaxStamina() { 
        return maxStamina; 
    }

    // --- Setters / Mutators ---
    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = Objects.requireNonNull(playerPosition);
    }

    public void setEnemyPosition(Position enemyPosition) {
        this.enemyPosition = Objects.requireNonNull(enemyPosition);
        System.out.println(this.getEnemyPosition());
    }

    public void setFlamePosition(Position flamePosition) {
        this.flamePosition = Objects.requireNonNull(flamePosition);
    }

    public void decreasePlayerHealth(int amount) {
        this.playerHealth = Math.max(0, this.playerHealth - amount);
    }

    public void decreaseEnemyHealth(int amount) {
        this.enemyHealth = Math.max(0, this.enemyHealth - amount);
    }

    public void increasePlayerHealth(int amount){
        this.playerHealth = Math.min(maxHealth, this.playerHealth + amount);
    }

    public void increaseEnemyHealth(int amount){
        this.enemyHealth = Math.min(maxHealth, this.enemyHealth + amount);
    }

    public void setEnemyPoisoned(boolean enemyPoisoned, Position reciever) {
        if (reciever.equals(this.enemyPosition)){
            this.enemyPoisoned = enemyPoisoned;
        }
        else if (reciever.equals(this.playerPosition)){
            this.playerPoisoned = enemyPoisoned;
        }
    }

    public void setPlayerPoisoned(boolean playerPoisoned){
        this.playerPoisoned = playerPoisoned;
    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public boolean isGameOver() {
        return playerHealth <= 0 || enemyHealth <= 0;
    }

    public void deadPlayer(Position deadPlayer){
        this.deadPlayer = deadPlayer;
    }

    public void increasePlayerPower(int power){
        this.playerPower += power;
    }

    public void decreasePlayerStamina(int amount) {
        this.playerStamina = Math.max(0, this.playerStamina - amount); // Prevent going below 0
    }
}