package it.unibo.progetto_oop.Overworld; // Adjust package as needed

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

// Assuming Position exists in the correct package
import it.unibo.progetto_oop.combattimento.Position;

public class OverworldView extends JPanel {

    private final OverworldModel model;

    // --- Constants for Drawing ---
    private static final int CELL_SIZE = 20; // Size of each tile in pixels
    // Define how many cells are visible around the player (e.g., 9x9 viewport)
    private static final int VIEWPORT_WIDTH_CELLS = 15; // Example: 15 cells wide
    private static final int VIEWPORT_HEIGHT_CELLS = 11; // Example: 11 cells high

    // --- Colors (or use ImageIcons later) ---
    private static final Color FLOOR_COLOR = Color.LIGHT_GRAY;
    private static final Color WALL_COLOR = Color.DARK_GRAY;
    private static final Color PLAYER_COLOR = Color.RED;
    private static final Color ENEMY_COLOR = Color.ORANGE;
    /**
     * Constructor for the OverworldView.
     * @param model The game model containing the state to display.
     */
    public OverworldView(OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");

        // Set the preferred size of this panel based on the viewport dimensions
        int preferredWidth = VIEWPORT_WIDTH_CELLS * CELL_SIZE;
        int preferredHeight = VIEWPORT_HEIGHT_CELLS * CELL_SIZE;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        // Set a default background (optional, often overridden by floor tiles)
        setBackground(FLOOR_COLOR);

        // Important for receiving keyboard events if using KeyListener
        // (Less critical but still good practice when using Key Bindings)
        setFocusable(true);
    }

    /**
     * The core drawing method, called by Swing when the component needs painting.
     * This method is invoked automatically when repaint() is called.
     *
     * @param g The Graphics object provided by Swing for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Always call the superclass method first for proper component painting
        super.paintComponent(g);

        // Get current game state from the model
        Position playerPos = model.getPlayer();
        List<Position> walls = model.getWalls();
        List<Position> enemies = model.getEnemies(); // Assuming getEnemies exists

        // --- Viewport Calculation (Center on Player) ---
        // Determine the top-left corner (in model coordinates) of the area to draw
        int viewPortTopLeftX = playerPos.x() - VIEWPORT_WIDTH_CELLS / 2;
        int viewPortTopLeftY = playerPos.y() - VIEWPORT_HEIGHT_CELLS / 2;

        // --- Drawing Loop ---
        // Iterate through the cells that should be visible in the viewport
        for (int viewY = 0; viewY < VIEWPORT_HEIGHT_CELLS; viewY++) {
            for (int viewX = 0; viewX < VIEWPORT_WIDTH_CELLS; viewX++) {

                // Calculate the corresponding model coordinates for this viewport cell
                int modelX = viewPortTopLeftX + viewX;
                int modelY = viewPortTopLeftY + viewY;

                // Calculate the screen coordinates where this cell should be drawn
                int screenX = viewX * CELL_SIZE;
                int screenY = viewY * CELL_SIZE;

                // --- Determine what to draw in this cell ---

                // 1. Draw Floor (Default)
                g.setColor(FLOOR_COLOR);
                g.fillRect(screenX, screenY, CELL_SIZE, CELL_SIZE);

                // 2. Check for Walls (using simple iteration - could optimize with Set/Map later)
                //    (Alternatively, if model has a getTile(x,y) method, use that)
                boolean isWall = false;
                for (Position wall : walls) {
                    if (wall.x() == modelX && wall.y() == modelY) {
                        g.setColor(WALL_COLOR);
                        g.fillRect(screenX, screenY, CELL_SIZE, CELL_SIZE);
                        isWall = true;
                        break; // Found wall, no need to check further for this cell
                    }
                }
                if (isWall) continue; // Skip drawing other things if it's a wall

                for (Position enemy : enemies) {
                     if (enemy.x() == modelX && enemy.y() == modelY) {
                         g.setColor(ENEMY_COLOR);
                         g.fillRect(screenX, screenY, CELL_SIZE, CELL_SIZE);
                     }
                 }
                 // If drawing enemies on top of floor, don't continue here

                // 4. Check for Player (Draw last to be on top)
                if (playerPos.x() == modelX && playerPos.y() == modelY) {
                    g.setColor(PLAYER_COLOR);
                    g.fillRect(screenX, screenY, CELL_SIZE, CELL_SIZE);
                }

                // 5. Optional: Draw Grid Lines
                // g.setColor(GRID_COLOR);
                // g.drawRect(screenX, screenY, CELL_SIZE, CELL_SIZE);
            }
        }
        // Optional: Draw UI elements, player stats, etc. over the game world
    }

    // NOTE: Methods like getInputMap(), getActionMap(), and repaint() are
    // inherited from JComponent/Component and are called by the Controller.
    // You DO NOT typically need to write them here.
}