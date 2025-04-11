package it.unibo.progetto_oop.Overworld;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// Classe che rappresenta una stanza
class Room {
    int x, y, width, height;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Room other) {
        return (this.x < other.x + other.width && this.x + this.width > other.x &&
                this.y < other.y + other.height && this.y + this.height > other.y);
    }
}

// Classe principale per il dungeon con la pedina mobile
public class MysteryDungeonPlayer extends JPanel implements KeyListener {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int CELL_SIZE = 20;
    private static final int VIEWPORT_SIZE = 9; // 9x9 celle visibili intorno alla pedina

    private char[][] dungeon;
    private List<Room> rooms;
    private int playerX, playerY;

    public MysteryDungeonPlayer() {
        dungeon = generateDungeon();
        placePlayer();
        setFocusable(true);
        addKeyListener(this);
    }

    private char[][] generateDungeon() {
        Random rand = new Random();
        rooms = new ArrayList<>();
        char[][] grid = new char[HEIGHT][WIDTH];

        // Inizializza la griglia con muri
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++)
                grid[y][x] = '#';

        // Genera stanze casuali
        for (int i = 0; i < 8; i++) {
            int roomWidth = rand.nextInt(8) + 5;
            int roomHeight = rand.nextInt(6) + 5;
            int roomX = rand.nextInt(WIDTH - roomWidth - 1) + 1;
            int roomY = rand.nextInt(HEIGHT - roomHeight - 1) + 1;

            Room newRoom = new Room(roomX, roomY, roomWidth, roomHeight);

            boolean overlapping = false;
            for (Room room : rooms) {
                if (newRoom.intersects(room)) {
                    overlapping = true;
                    break;
                }
            }

            if (!overlapping) {
                rooms.add(newRoom);
                for (int y = roomY; y < roomY + roomHeight; y++)
                    for (int x = roomX; x < roomX + roomWidth; x++)
                        grid[y][x] = '.';
            }
        }

        // Collega le stanze con corridoi
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room r1 = rooms.get(i);
            Room r2 = rooms.get(i + 1);
            int x1 = r1.x + r1.width / 2;
            int y1 = r1.y + r1.height / 2;
            int x2 = r2.x + r2.width / 2;
            int y2 = r2.y + r2.height / 2;

            if (rand.nextBoolean()) {
                connectHorizontal(grid, x1, x2, y1);
                connectVertical(grid, y1, y2, x2);
            } else {
                connectVertical(grid, y1, y2, x1);
                connectHorizontal(grid, x1, x2, y2);
            }
        }

        return grid;
    }

    private void placePlayer() {
        for (Room room : rooms) {
            playerX = room.x + room.width / 2;
            playerY = room.y + room.height / 2;
            return;
        }
    }

    private void connectHorizontal(char[][] grid, int x1, int x2, int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++)
            grid[y][x] = '.';
    }

    private void connectVertical(char[][] grid, int y1, int y2, int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++)
            grid[y][x] = '.';
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int startX = Math.max(0, playerX - VIEWPORT_SIZE / 2);
        int startY = Math.max(0, playerY - VIEWPORT_SIZE / 2);
        int endX = Math.min(WIDTH, startX + VIEWPORT_SIZE);
        int endY = Math.min(HEIGHT, startY + VIEWPORT_SIZE);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (x == playerX && y == playerY) {
                    g.setColor(Color.RED); // Pedina del giocatore
                } else if (dungeon[y][x] == '#') {
                    g.setColor(Color.BLACK); // Muri
                } else {
                    g.setColor(Color.WHITE); // Stanze e corridoi
                }
                g.fillRect((x - startX) * CELL_SIZE, (y - startY) * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Disegna la griglia
                g.setColor(Color.GRAY);
                g.drawRect((x - startX) * CELL_SIZE, (y - startY) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newX = playerX;
        int newY = playerY;

        if (e.getKeyCode() == KeyEvent.VK_UP) newY--;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) newY++;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) newX--;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) newX++;

        // Controllo che il nuovo movimento sia valido
        if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && dungeon[newY][newX] == '.') {
            playerX = newX;
            playerY = newY;
        }
        
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mystery Dungeon con Pedina");
        MysteryDungeonPlayer panel = new MysteryDungeonPlayer();
        frame.add(panel);
        frame.setSize(VIEWPORT_SIZE * CELL_SIZE + 15, VIEWPORT_SIZE * CELL_SIZE + 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
