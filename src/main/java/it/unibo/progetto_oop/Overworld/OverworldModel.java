package it.unibo.progetto_oop.Overworld;

import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;

public class OverworldModel {
    
    private Position player;
    private List<Position> enemies;
    private List<Position> walls; 

    public OverworldModel(Position player, List<Position> enemies, List<Position> walls){
        this.player = player;
        this.enemies = enemies;
        this.walls = walls;
    }

    private Position attemptMovement(int directionx, int directiony){

        final Position tempPlayer = new Position(this.player.x() + directionx, this.player.y() + directiony);
        return this.walls.stream().anyMatch(pos -> pos.equals(tempPlayer)) ? this.player : tempPlayer;
    }


    // --- getter methods ---

    public Position getPlayer(){
        return this.player;
    }

    public List<Position> getEnemies(){
        return this.enemies;
    }

    // --- setter methods ---

    public Position setPlayer(int directionx, int directiony){
        this.player = attemptMovement(directionx, directiony);
        return this.player;
    }

}
