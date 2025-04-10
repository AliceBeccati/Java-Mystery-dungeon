package it.unibo.progetto_oop.Button_commands;

import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;

public class HealingButton implements GameButton {

    /**
     * @return lista vuota perché non necessaria per questo metodo
     */
    @Override
    public List<Position> execute() {
        return List.of();
    }
}