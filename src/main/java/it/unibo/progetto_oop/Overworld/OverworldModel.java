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

    public Position getPlayer(){
        return this.player;
    }

    public List<Position> getEnemies(){
        return this.enemies;
    }

    public List<Position> getWalls(){
        return this.walls;
    }

    // --- setter methods ---

    public Position setPlayer(int directionx, int directiony){
        if (!attemptMovement(directionx, directiony, this.walls)){
            this.player = this.tempPlayer;
        }
        return this.player;
    }

    public List<Position> removeEnemy(Position posToRemove){
        int originalSize = this.enemies.size();

        List<Position> newEnemies = this.enemies.stream().filter(pos -> !pos.equals(posToRemove)).collect(Collectors.toList());
        return newEnemies.size() < originalSize ? newEnemies : this.enemies;
    }

}
