package it.unibo.progetto_oop.Overworld; // Make sure this matches your package structure

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Import your other necessary classes (assuming they are in correct packages)
import it.unibo.progetto_oop.combattimento.Position; // Assuming Position class location

public class GameApplication {

    public static void main(String[] args) {
        // Run the GUI setup code on the Event Dispatch Thread (EDT)
        // This is the standard and safe way to initialize Swing GUIs.
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // --- 1. Create the Model ---
        // Initialize with some starting data (adjust as needed)
        Position startPlayerPos = new Position(5, 5); // Example starting position

        List<Position> walls = List.of( // Example walls (immutable list)
                new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0), new Position(9, 0),
                new Position(0, 9), new Position(1, 9), new Position(2, 9), new Position(3, 9), new Position(4, 9),
                new Position(5, 9), new Position(6, 9), new Position(7, 9), new Position(8, 9), new Position(9, 9),
                new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5),
                new Position(0, 6), new Position(0, 7), new Position(0, 8),
                new Position(9, 1), new Position(9, 2), new Position(9, 3), new Position(9, 4), new Position(9, 5),
                new Position(9, 6), new Position(9, 7), new Position(9, 8),
                // Some internal walls
                new Position(3, 3), new Position(3, 4), new Position(3, 5),
                new Position(6, 6), new Position(7, 6)
        );

        List<Position> enemies = new ArrayList<>(); // Start with an empty list (mutable)
        enemies.add(new Position(8, 2));
        enemies.add(new Position(2, 7));

        // Instantiate the model
        OverworldModel model = new OverworldModel(startPlayerPos, enemies, walls);

        // --- 2. Create the View ---
        // Pass the model to the view so it knows what to draw
        OverworldView view = new OverworldView(model);

        // --- 3. Create the Controller ---
        // Pass both the model and the view to the controller
        OverworldController controller = new OverworldController(model, view);

        // --- 4. Initialize Input Handling ---
        // Tell the controller to set up the keyboard bindings on the view
        controller.initializeInputBindings();

        // --- 5. Setup the Main Window (JFrame) ---
        JFrame frame = new JFrame("Overworld Adventure"); // Set window title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when window closes

        // Add the view component (the JPanel) to the frame's content pane
        frame.add(view);

        // Size the frame based on the preferred size of its contents (the view panel)
        frame.pack();

        // Optional: Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the window visible
        frame.setVisible(true);

        // --- 6. Request Initial Focus ---
        // Attempt to give focus to the view component so it receives key events
        view.requestFocusInWindow();

        System.out.println("Game GUI Initialized.");
    }
}