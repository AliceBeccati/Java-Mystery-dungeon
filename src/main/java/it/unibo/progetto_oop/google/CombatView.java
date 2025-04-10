package it.unibo.progetto_oop.google;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import it.unibo.progetto_oop.Button_commands.MeleeButton; // Keep for neighbour check in redraw
import it.unibo.progetto_oop.combattimento.Position;
import java.awt.image.BufferedImage;

public class CombatView extends JFrame {

    private final int size;
    private final Map<JLabel, Position> cells = new HashMap<>();
    private JProgressBar playerHealthBar;
    private JProgressBar enemyHealthBar;
    private JProgressBar playerStaminaBar;
    private JLabel infoLabel;
    private JPanel buttonPanel;
    private JPanel originalButtonPanel;
    private JPanel bagButtonPanel;
    private JPanel attackButtonPanel;
    private CardLayout cardLayout;

    // Buttons - declare them here
    private JButton attackButton;
    private JButton bagButton;
    private JButton runButton;
    private JButton infoButton;
    private JButton physicalAttackButton;
    private JButton longRangeButton;
    private JButton poisonButton;
    private JButton backButton;
    private JButton attackBuff;
    private JButton curePoison;
    private JButton healing;

    // Temporary reference for redraw logic - ideally remove MeleeButton dependency from View
    private final MeleeButton redrawHelper = new MeleeButton();


    public CombatView(int size) {
        this.size = size;
        this.setTitle("Combat Screen"); // Add a title
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * size, 75 * size); // Slightly taller for info label
        this.setLayout(new BorderLayout()); // Use BorderLayout for the main frame

        initializeUI();
    }

    private void initializeUI() {
        // --- Grid Panel ---
        JPanel gridPanel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JLabel cellLabel = new JLabel();
                cellLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                cellLabel.setOpaque(true); // Needed for background colors
                cellLabel.setBackground(Color.WHITE); // Default background
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center icons
                cellLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/white.jpg"))); // Initial background
                this.cells.put(cellLabel, new Position(j, i));
                gridPanel.add(cellLabel);
            }
        }
        this.add(gridPanel, BorderLayout.CENTER);

        // --- Health Panel ---
        JPanel healthPanel = new JPanel();
        healthPanel.setLayout(new BoxLayout(healthPanel, BoxLayout.Y_AXIS));

        playerHealthBar = new JProgressBar(0, 100); // Set max later if needed
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(Color.GREEN);
        playerHealthBar.setPreferredSize(new Dimension(35 * size, 20));

        enemyHealthBar = new JProgressBar(0, 100); // Set max later if needed
        enemyHealthBar.setStringPainted(true);
        enemyHealthBar.setForeground(Color.RED);
        enemyHealthBar.setPreferredSize(new Dimension(35 * size, 20));

        playerStaminaBar = new JProgressBar(0, 100); // Set max from model later
        playerStaminaBar.setValue(100);            // Set value from model later
        playerStaminaBar.setStringPainted(true);
        playerStaminaBar.setForeground(new Color(173, 216, 230)); // Light Blue
        // Or: playerStaminaBar.setForeground(Color.CYAN); // Brighter Blue
        playerStaminaBar.setPreferredSize(new Dimension(35 * size, 20)); // Match others

        healthPanel.add(new JLabel("Player Health:")); // Add labels
        healthPanel.add(playerHealthBar);
        healthPanel.add(Box.createVerticalStrut(5));
        healthPanel.add(new JLabel("Enemy Health:")); // Add labels
        healthPanel.add(enemyHealthBar);
        this.add(healthPanel, BorderLayout.NORTH);

        // ... after adding enemyHealthBar ...
        healthPanel.add(Box.createVerticalStrut(5)); // Optional gap
        healthPanel.add(new JLabel("Player Stamina:")); // Add a label
        healthPanel.add(playerStaminaBar);

        // --- Button and Info Panel ---
        cardLayout = new CardLayout();
        buttonPanel = new JPanel(cardLayout);

        // Original Buttons
        originalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        attackButton = createButton("Attack", (50 * size) / 3, (20 * size) / 3);
        bagButton = createButton("Bag", (50 * size) / 3, (20 * size) / 3);
        runButton = createButton("Run", (50 * size) / 3, (20 * size) / 3);
        infoButton = createButton("Info", (50 * size) / 3, (20 * size) / 3);
        originalButtonPanel.add(attackButton);
        originalButtonPanel.add(bagButton);
        originalButtonPanel.add(runButton);
        originalButtonPanel.add(infoButton);

        // Attack Options Buttons
        attackButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        physicalAttackButton = createButton("Physical Attack", (50 * size) / 3, (20 * size) / 3);
        longRangeButton = createButton("Long Range", (50 * size) / 3, (20 * size) / 3);
        poisonButton = createButton("Poison", (50 * size) / 3, (20 * size) / 3);
        backButton = createButton("Back", (50 * size) / 3, (20 * size) / 3);
        attackButtonPanel.add(physicalAttackButton);
        attackButtonPanel.add(longRangeButton);
        attackButtonPanel.add(poisonButton);
        attackButtonPanel.add(backButton);

        //Bag Options Buttons
        bagButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.attackBuff = createButton("Attack Buff", (50 * size) / 3, (20 * size) / 3);
        this.curePoison = createButton("Cure poison", (50 * size) / 3, (20 * size) / 3);
        this.healing = createButton("Heal", (50 * size) / 3, (20 * size) / 3);
        bagButtonPanel.add(this.attackBuff);
        bagButtonPanel.add(this.curePoison);
        bagButtonPanel.add(this.healing);
        bagButtonPanel.add(backButton);

        buttonPanel.add(originalButtonPanel, "originalButtons");
        buttonPanel.add(attackButtonPanel, "attackOptions");
        buttonPanel.add(bagButtonPanel, "bagOptions");

        // Info Label
        infoLabel = new JLabel("Combat Started!", SwingConstants.CENTER); // Initial text
        infoLabel.setPreferredSize(new Dimension(70 * size, 30)); // Give it some height

        // South Panel combining buttons and info
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(buttonPanel);
        southPanel.add(infoLabel);
        this.add(southPanel, BorderLayout.SOUTH);

        // Set initial state
        showOriginalButtons();
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    // --- Public Methods for Controller Interaction ---

    public void display() {
        this.setVisible(true);
    }

    public void close() {
        this.dispose();
    }

    public void setHealthBarMax(int max) {
        playerHealthBar.setMaximum(max);
        enemyHealthBar.setMaximum(max);
    }

    public void updatePlayerHealth(int value) {
        playerHealthBar.setValue(value);
        playerHealthBar.setString("Player: " + value + "/" + playerHealthBar.getMaximum()); // More informative string
    }

    public void updateEnemyHealth(int value) {
        enemyHealthBar.setValue(value);
        enemyHealthBar.setString("Enemy: " + value + "/" + enemyHealthBar.getMaximum()); // More informative string
    }

    public void showInfo(String text) {
        infoLabel.setText("<html>" + text.replace("\n", "<br>") + "</html>"); // Use HTML for newlines
    }

    public void clearInfo() {
        infoLabel.setText("");
    }

    public void showAttackOptions() {
        attackButtonPanel.add(backButton);
        cardLayout.show(buttonPanel, "attackOptions");
    }

    public void showOriginalButtons() {
        cardLayout.show(buttonPanel, "originalButtons");
    }

    public void showBagButtons(){
        bagButtonPanel.add(backButton);
        cardLayout.show(buttonPanel, "bagOptions");
    }

    public void setButtonsEnabled(boolean enabled) {
        attackButtonPanel.add(backButton);
        setPanelEnabled(originalButtonPanel, enabled);
        setPanelEnabled(attackButtonPanel, enabled);
    }

    public void setCustomButtonEnable(JButton enabled){
        enabled.setEnabled(true);
    }

    private void setPanelEnabled(JPanel panel, boolean isEnabled) {
       panel.setEnabled(isEnabled);
       for (Component comp : panel.getComponents()) {
           if (comp instanceof JButton) {
               comp.setEnabled(isEnabled);
           }
       }
   }


   /*
    * DA RICONTROLLARE MORTE PERCHÈ NON FUNZIONA 
    */



    // --- Redraw Logica ---
    /**
     * 
     * @param player Posizione giocatore
     * @param enemy Poszione nemico
     * @param flame Posizione di fiamma
     * @param drawPlayer vero se vogliamo disegnare giocatore falso se no
     * @param drawEnemy vero se vogliamo disegnare nemico false se no
     * @param drawFlame vero se vogliamo fiamma false se no
     * @param drawPoison Vero se vogliamo veleno e non raggio normale false se no
     * @param drawPoisDamage Vero se vogliamo animazione di veleno
     * @param poisonedPlayer Posizione vgiocatore avvelenato
     * @param heightPois altezza (int) di posizione dia nimazione veleno
     * @param playerRange qaunto vogliamo che sia vicino per essere considerato vicino
     * @param enemyRange  qaunto vogliamo che sia vicino per essere considerato vicino
     * @param isGameOver vero se è qualcuno è morto false se no
     * @param whoDied posizione di chi è morto
     */
    public void redrawGrid(Position player, Position enemy, Position flame,
    boolean drawPlayer, boolean drawEnemy,
    boolean drawFlame, boolean drawPoison, boolean drawPoisDamage, Position poisonedPlayer, int heightPois, int playerRange, int enemyRange,
    boolean isGameOver, Position whoDied)
    {
    
    for (var entry : cells.entrySet()) {
            JLabel cellLabel = entry.getKey();
            Position cellPos = entry.getValue();
            Icon icon = null;

            if (isGameOver){
                if (redrawHelper.deathNeighbours(whoDied, cellPos, enemyRange)){
                    icon = getIconResource(whoDied.equals(player) ? "/Screenshot 2025-03-25 164621.png" : "/red.jpg");
                }
                else if(drawPlayer && redrawHelper.deathNeighbours(whoDied, cellPos, enemyRange)) {
                    icon = getIconResource(whoDied.equals(player) ? "/Screenshot 2025-03-25 164621.png" : "/red.jpg");
                }
            }
            else
            {
                if (drawPoisDamage && cellPos.x() == poisonedPlayer.x() && cellPos.y() == heightPois){
                    icon = getIconResource("/green.jpg");
                }
                else if ((drawFlame || drawPoison) && redrawHelper.neighbours(flame, cellPos, 0)) {
                    icon = drawFlame ? getIconResource("/yellow.jpg") : getIconResource("/green.jpg");
                } else if (drawPlayer && redrawHelper.neighbours(player, cellPos, playerRange)) {
                    icon = getIconResource("/Screenshot 2025-03-25 164621.png");
                } else if (drawEnemy && redrawHelper.neighbours(enemy, cellPos, enemyRange)) {
                    icon = getIconResource("/red.jpg");
                } else {
                    icon = getIconResource("/white.jpg");
                }
            }
    
            cellLabel.setIcon(icon);
        }

        this.revalidate();
        this.repaint();
    }

    private ImageIcon getIconResource(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return createDefaultIcon();
        }
    }

    // Helper per creare un'icona base in caso ci siano errori
    private ImageIcon createDefaultIcon() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 20, 20);
        g.dispose();
        return new ImageIcon(image);
    }

    public void updatePlayerStamina(int value, int max) {
        playerStaminaBar.setMaximum(max); // Aggiorno massimo in caso
        playerStaminaBar.setValue(value);
        playerStaminaBar.setString("Stamina: " + value + "/" + max);
    }

    // --- Aggiungere gli ActionListener ---
    public void addAttackButtonListener(ActionListener listener) {
        attackButton.addActionListener(listener);
    }

    public JPanel getAttackButtonPanel(){
        attackButtonPanel.add(backButton);
        return this.originalButtonPanel;
    }

    public JButton getAttackButton(){
        return this.attackButton;
    }

    public void addBagButtonListener(ActionListener listener) {
        bagButton.addActionListener(listener); // No action defined yet
    }

    public JButton getBagButton(){
        return this.bagButton;
    }
    public void addRunButtonListener(ActionListener listener) {
        runButton.addActionListener(listener); // No action defined yet
    }

    public JButton getRunButton(){
        return this.bagButton;
    }

    public void addInfoButtonListener(ActionListener listener) {
        infoButton.addActionListener(listener);
    }

    public JButton getInfoButton(){
        return this.infoButton;
    }

    public void addPhysicalAttackButtonListener(ActionListener listener) {
        physicalAttackButton.addActionListener(listener);
    }

    public JButton getPhysicalAttackButton(){
        return this.physicalAttackButton;
    }
    public void addLongRangeButtonListener(ActionListener listener) {
        longRangeButton.addActionListener(listener);
    }

    public JButton getLongRangeButton(){
        return this.longRangeButton;
    }

    public void addPoisonButtonListener(ActionListener listener) {
        poisonButton.addActionListener(listener);
    }

    public JButton getPoisonButton(){
        return this.poisonButton;
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public JButton getBackButton(){
        return this.backButton;
    }

    public void addAttackBuffButtonListener(ActionListener listener){
        attackBuff.addActionListener(listener);
    }

    public JButton getAttackBuff(){
        return this.attackBuff;
    }

    public void addCurePoisonButtonListener(ActionListener listener){
        curePoison.addActionListener(listener);
    }

    public JButton getCurePoison(){
        return this.curePoison;
    }

    public void addHealingButtonListener(ActionListener listener){
        healing.addActionListener(listener);
    }

    public JButton getHealing(){
        return this.healing;
    }

}