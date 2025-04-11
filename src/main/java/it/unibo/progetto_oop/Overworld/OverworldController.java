package it.unibo.progetto_oop.Overworld;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;


public class OverworldController {

    private final OverworldModel model;
    private final OverworldView view; 

    public OverworldController(OverworldModel model, OverworldView view) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");
    }

    /**
     * Inizializzazione funzioni necessarie per muovere il giocatore usando la tastiera
     */
    public void initializeInputBindings() {

// ************************************************************************************************************************************
// Per usare getInputMap che serve qui Serve che la view estenda JPanel in questo modo funzionerà correttamente il codice
// ************************************************************************************************************************************
        InputMap inputMap = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = view.getActionMap();

        final String MOVE_UP = "moveUp";
        final String MOVE_DOWN = "moveDown";
        final String MOVE_LEFT = "moveLeft";
        final String MOVE_RIGHT = "moveRight";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), MOVE_UP);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), MOVE_UP);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), MOVE_DOWN);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), MOVE_DOWN);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), MOVE_LEFT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MOVE_LEFT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), MOVE_RIGHT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MOVE_RIGHT);

        actionMap.put(MOVE_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: Action Move Up");
                model.setPlayer(0, -1); 
                view.repaint();
            }
        });

        actionMap.put(MOVE_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: Action Move Down");
                model.setPlayer(0, 1);
                view.repaint();
            }
        });

        actionMap.put(MOVE_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: Action Move Left");
                model.setPlayer(-1, 0);
                view.repaint();
            }
        });

        actionMap.put(MOVE_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: Action Move Right");
                model.setPlayer(1, 0);
                view.repaint();
            }
        });

        System.out.println("Input bindings initialized by Controller.");
    }
}