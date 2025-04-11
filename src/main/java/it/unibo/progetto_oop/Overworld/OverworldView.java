package it.unibo.progetto_oop.Overworld; // Adjust package as needed

import javax.swing.*;

import it.unibo.progetto_oop.combattimento.Position;

import java.awt.*;
import java.util.List;
import java.util.Objects;

// import it.unibo.progetto_oop.combattimento.Position;

public class OverworldView extends JPanel {

    private final OverworldModel model;

    // --- Fixed Viewport Dimensions ---
    private static final int VIEWPORT_WIDTH_CELLS = 13; // Always show 13 cells horizontally
    private static final int VIEWPORT_HEIGHT_CELLS = 13; // Always show 13 cells vertically

    // --- Colors (Keep these) ---
    private static final Color FLOOR_COLOR = Color.LIGHT_GRAY;
    private static final Color WALL_COLOR = Color.DARK_GRAY;
    private static final Color PLAYER_COLOR = Color.RED;
    private static final Color ENEMY_COLOR = Color.ORANGE;
    private static final Color GRID_COLOR = Color.GRAY;
    // No BACKGROUND_COLOR needed for letterboxing now

    /**
     * Constructor (remains the same as before)
     */
    public OverworldView(OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        int initialDefaultCellSize = 25;
        setPreferredSize(new Dimension(
                VIEWPORT_WIDTH_CELLS * initialDefaultCellSize,
                VIEWPORT_HEIGHT_CELLS * initialDefaultCellSize
        ));
        setMinimumSize(new Dimension(VIEWPORT_WIDTH_CELLS * 5, VIEWPORT_HEIGHT_CELLS * 5));
        setFocusable(true);
        // Optional: Set default background, but paintComponent will cover it
        setBackground(FLOOR_COLOR);
    }

    /**
     * The core drawing method, scales a fixed 13x13 grid to STRETCH and fill the panel.
     * @param g The Graphics object provided by Swing for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // 1. Call superclass method (important!)
        super.paintComponent(g);

        // 2. Get current panel dimensions
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Prevent errors if panel is invisible or too small
        if (panelWidth <= 0 || panelHeight <= 0) {
            return;
        }

        // 3. Calculate the dynamic cell width and height INDEPENDENTLY
        // This allows stretching to fill the panel exactly.
        float cellSizeX = (float) panelWidth / VIEWPORT_WIDTH_CELLS;
        float cellSizeY = (float) panelHeight / VIEWPORT_HEIGHT_CELLS;

        // 4. No need for background fill or offsets, the grid fills everything


        // --- Get current game state ---
        Position playerPos = model.getPlayer();
        List<Position> walls = model.getWalls();
        List<Position> enemies = model.getEnemies();


        // --- Viewport Calculation (Center on Player) ---
        // Determines which part of the *model* grid is the top-left corner
        int viewPortTopLeftX = playerPos.x() - VIEWPORT_WIDTH_CELLS / 2;
        int viewPortTopLeftY = playerPos.y() - VIEWPORT_HEIGHT_CELLS / 2;


        // --- Drawing Loop (Iterate over the FIXED 13x13 viewport cells) ---
        for (int viewY = 0; viewY < VIEWPORT_HEIGHT_CELLS; viewY++) {
            for (int viewX = 0; viewX < VIEWPORT_WIDTH_CELLS; viewX++) {

                // Corresponding model coordinates for this viewport cell
                int modelX = viewPortTopLeftX + viewX;
                int modelY = viewPortTopLeftY + viewY;

                // Calculate the screen coordinates using the dynamic cell widths/heights
                // No offsets needed as we fill the whole panel
                int screenX = Math.round(viewX * cellSizeX);
                int screenY = Math.round(viewY * cellSizeY);

                // Calculate the precise width/height for *this specific cell*
                // Handles potential rounding differences for the last cell in row/column
                int currentCellDrawWidth = Math.round((viewX + 1) * cellSizeX) - screenX;
                int currentCellDrawHeight = Math.round((viewY + 1) * cellSizeY) - screenY;
                // Ensure minimum size
                currentCellDrawWidth = Math.max(1, currentCellDrawWidth);
                currentCellDrawHeight = Math.max(1, currentCellDrawHeight);


                // --- Determine what to draw in this cell ---

                // 1. Draw Floor background for this cell
                g.setColor(FLOOR_COLOR);
                // Use calculated width/height for this specific cell
                g.fillRect(screenX, screenY, currentCellDrawWidth, currentCellDrawHeight);

                // 2. Draw Walls
                boolean isWall = false;
                for (Position wall : walls) {
                    if (wall.x() == modelX && wall.y() == modelY) {
                        g.setColor(WALL_COLOR);
                        g.fillRect(screenX, screenY, currentCellDrawWidth, currentCellDrawHeight);
                        isWall = true;
                        break;
                    }
                }
                // if (isWall) continue; // Optional

                // 3. Draw Enemies
                 for (Position enemy : enemies) {
                     if (enemy.x() == modelX && enemy.y() == modelY && !isWall) {
                         g.setColor(ENEMY_COLOR);
                         g.fillRect(screenX, screenY, currentCellDrawWidth, currentCellDrawHeight);
                     }
                 }

                // 4. Draw Player
                if (playerPos.x() == modelX && playerPos.y() == modelY && !isWall) {
                    g.setColor(PLAYER_COLOR);
                    g.fillRect(screenX, screenY, currentCellDrawWidth, currentCellDrawHeight);
                }

                // 5. Draw Grid Line for this cell
                g.setColor(GRID_COLOR);
                // Use calculated width/height for this specific cell
                g.drawRect(screenX, screenY, currentCellDrawWidth, currentCellDrawHeight);
            }
        }
    }
}