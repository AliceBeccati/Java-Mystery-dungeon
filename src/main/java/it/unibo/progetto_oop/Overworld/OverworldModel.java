package it.unibo.progetto_oop.Overworld;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unibo.progetto_oop.combattimento.Position;

public class OverworldModel {
    
    private Position player;
    private Position tempPlayer;
    private List<Position> enemies = new ArrayList<>();
    private List<Position> walls = new ArrayList<>(); 

    public OverworldModel(Position player, List<Position> enemies, List<Position> walls){
        this.player = player;
        this.enemies = enemies;
        this.walls = walls;
    }


    /**
     * 
     * @param directionx direzione da spostarsi su asse x
     * @param directiony direzione da spostarsi su asse y
     * @param lista usare getEnemies o getWalls a seconda di cosa si vuole controllare
     * @return vero se la prossima posizione è vicina ad qualcosa falso altrimenti
     */
    private boolean attemptMovement(int directionx, int directiony, List<Position> lista){
        this.tempPlayer = new Position(this.player.x() + directionx, this.player.y() + directiony);
        return lista.stream().anyMatch(pos -> pos.equals(tempPlayer)) ? true : false;
    }

    // --- getter methods ---

    /**
     * 
     * @return Posizione del giocatore
     */
    public Position getPlayer(){
        return this.player;
    }

    /**
     * 
     * @return Lista dei nemici sulla mappa
     */
    public List<Position> getEnemies(){
        return this.enemies;
    }

    /**
     * 
     * @return lista dei muri
     */
    public List<Position> getWalls(){
        return this.walls;
    }

    // --- setter methods ---

    /**
     * Usato per aggiornare la posizione del giocatore
     * @param directionx direzione nell'asse x in cui si sposta il giocatore
     * @param directiony direzione nell'asse y in cui si sposta il giocatore
     * @return la nouva o stessa posizione del giocatore
     */
    public Position setPlayer(int directionx, int directiony){
        if (!attemptMovement(directionx, directiony, this.walls)){
            this.player = this.tempPlayer;
        }
        if (attemptMovement(0, 0, this.enemies)){
            System.out.println("Da implementare Transizione a attacco");
            this.enemies = this.removeEnemy(this.player);
            System.out.println("Nemici nuovo => " + this.enemies);
        }
        return this.player;
    }

    /**
     * Rimuove il nemico sopra il quale si è passato
     * @param posToRemove la posizione del nemico da cavare
     * @return la lista di nemici aggiornata
     */
    public List<Position> removeEnemy(Position posToRemove){
        int originalSize = this.enemies.size();

        List<Position> newEnemies = this.enemies.stream().filter(pos -> !pos.equals(posToRemove)).collect(Collectors.toList());
        return newEnemies.size() < originalSize ? newEnemies : this.enemies;
    }

}
