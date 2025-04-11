package it.unibo.progetto_oop.Overworld;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;


public class OverworldController {

    private final OverworldModel model;
    private final OverworldView view; // The controller needs the view to attach listeners/bindings

    public OverworldController(OverworldModel model, OverworldView view) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");
    }

    /**
     * Configures Key Bindings for player movement on the associated View component.
     */
    public void initializeInputBindings() {
        // Get the InputMap and ActionMap FROM THE VIEW component
        InputMap inputMap = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = view.getActionMap();

        // Define Action Keys (using constants might be even better)
        final String MOVE_UP = "moveUp";
        final String MOVE_DOWN = "moveDown";
        final String MOVE_LEFT = "moveLeft";
        final String MOVE_RIGHT = "moveRight";

        // --- Map KeyStrokes to Action Keys ---
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), MOVE_UP);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), MOVE_UP);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), MOVE_DOWN);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), MOVE_DOWN);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), MOVE_LEFT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MOVE_LEFT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), MOVE_RIGHT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MOVE_RIGHT);

        // --- Map Action Keys to Actions (AbstractAction) ---
        // The Actions now live inside the Controller (or are defined here)
        // They access the model and view via the Controller's fields

        actionMap.put(MOVE_UP, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Controller: Action Move Up"); // Debug
                model.setPlayer(0, -1); // Use model reference from Controller
                view.repaint();                 // Use view reference from Controller
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

        System.out.println("Input bindings initialized by Controller."); // Debug
    }

    // You could add other controller methods here if needed,
    // e.g., methods to handle button clicks if you add GUI buttons.
}