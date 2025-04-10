package it.unibo.progetto_oop.Button_commands;

import java.util.LinkedList;
import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;

public class MeleeButton implements GameButton{
    private List<Position> giocatori = new LinkedList<>();
    Position player;
    Position enemy;
    int where;
    int distance;

    /**
     * 
     * @param player Posizione del giocatore
     * @param enemy Posizione del nemico
     * @param where direzione in cui deve andare andare l'attaccante giocatore o nemico
     * @param distance la distanza necessaria perché valga come vicino al nemico di solito 1 perché adiacente
     */

    public void setAttributes(Position player, Position enemy, int where, int distance){
        this.player = player;
        this.enemy = enemy;
        this.where = where;
        this.distance = distance;

    }

    /**
     * @return Lista "giocatori" con nuova posizione di nemico e giocatore usato per disegnare
     */

    @Override
    public List<Position> execute(){

        if (this.neighbours(new Position(this.player.x() + this.where, this.player.y()), this.enemy , 1)){

            this.giocatori = this.moveEnemy();
            return this.giocatori;
        }
        
        else{
            this.player = new Position(this.player.x() + this.where, this.player.y());
        }

        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        return this.giocatori;
    }

    /**
     * @return Lista "giocatori" con nuova posizione di nemico e giocatore usato per disegnare
     * Usato per spostare sia giocatore che nemico per esempio quando fanno contatto
     */

    public List<Position> moveEnemy(){
        
        this.enemy = new Position(this.enemy.x() + this.where, this.enemy.y());
        this.player = new Position(this.player.x() + this.where, this.player.y());
        
        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        
        return this.giocatori;
    }

    /**
     * 
     * @param player Posizione del giocatore
     * @param other Posizione del nemico
     * @param distance Distanza a cui viene considerato adiacente
     * @return true se adiacente false se non adiacente  
     */

    public boolean neighbours(Position player, Position other, int distance) {
        return Math.abs(player.x() - other.x()) <= distance && Math.abs(player.y() - other.y()) <= distance;
    }

    /**
     * 
     * @param player Posizione del giocatore
     * @param other Posizione del nemico
     * @param distance Distanza a cui viene considerato adiacente
     * @return true se i controlli valgono se no false
     * 
     * Usato per l'animaizone della morte, sono disegnati quadrati sono le adiacenti con un blocco vuoto tra il blocco colorato e quello centrale
     * es.
     * °°°
     * °°° nemico non morto
     * °°°
     * 
     * ° ° °
     * ° ° ° nemico morto (aumentata la distanza)
     * ° ° °
     * 
     */

    public boolean deathNeighbours(Position player, Position other, int distance){
        return 
        (Math.abs(player.x() - other.x()) == distance && Math.abs(player.y() - other.y()) == distance) || 
        (Math.abs(player.x() - other.x()) == distance && player.y() == other.y()) ||
        (player.x() == other.x() && Math.abs(player.y() - other.y()) == distance) ||
        (player.x() == other.x() && other.y() == player.y());
    }
}
