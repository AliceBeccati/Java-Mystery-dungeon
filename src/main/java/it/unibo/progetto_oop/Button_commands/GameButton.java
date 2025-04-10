package it.unibo.progetto_oop.Button_commands;

import java.util.List;

import it.unibo.progetto_oop.combattimento.Position;

/**
 * Fa partire un metodo maneggiato da classe specifica dopo aver cliccato un bottone
 */
public interface GameButton {
    List<Position> execute();
} 
