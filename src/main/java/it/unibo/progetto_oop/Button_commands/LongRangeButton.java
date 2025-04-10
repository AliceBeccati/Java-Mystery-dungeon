package it.unibo.progetto_oop.Button_commands;


import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;


public class LongRangeButton implements GameButton {
    
    private Position flame;
    private int direction;

    /**
     * 
     * @param fiamma Posizione della fiamma o posizione del centro del giocatore o del nemico
     * @param direction 1 o -1 a seconda di se chiamata da giocatore (1) o nemico (-1) così da andare a destra o sinistra rispettivamentes
     */
    public void setAttributes(Position fiamma, int direction){
        this.flame = fiamma;
        this.direction = direction;
    }
    /** 
     * @return posizione della fiamma sotto forma di lista perché necessario per altri metodi che sia una lista
     * 
     * Scopo di questo metodo è di spostare la fiamma di un quadrato a destra o sinistra a seconda di dove deve andare (dettato da setAttributes)
    */
    @Override
    public List<Position> execute(){
        this.flame = new Position(this.flame.x() + direction, this.flame.y());
        return List.of(this.flame);
    }   
}