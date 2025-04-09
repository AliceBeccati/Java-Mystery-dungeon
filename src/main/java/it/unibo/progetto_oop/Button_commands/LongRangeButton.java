package it.unibo.progetto_oop.Button_commands;


import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;


public class LongRangeButton implements GameButton {
    
    private Position flame;
    private int direction;

    public void setAttributes(Position fiamma, int direction){
        this.flame = fiamma;
        this.direction = direction;
    }

    @Override
    public List<Position> execute(){
        this.flame = new Position(this.flame.x() + direction, this.flame.y());
        return List.of(this.flame);
    }   
}