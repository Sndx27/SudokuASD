package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    // Declare components
    private JTextField nameField;
    private JButton startButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private int difficulty;

    public WelcomeScreen() {
        //int difficulty;
        // Set up the welcome screen JFrame
        setTitle("Welcome to Sudoku");
        setSize(400, 300); // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a panel with padding and layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10)); // Add gaps between components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Create and style the welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Sudoku Game!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Create a panel for the name input and label
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        panel.add(inputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        // Create difficulty button
        JPanel levelPanel = new JPanel(new FlowLayout());
        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font.BOLD, 12));
        mediumButton = new JButton("Mid");
        mediumButton.setFont(new Font("Arial", Font.BOLD, 12));
        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font.BOLD, 12));
        levelPanel.add(easyButton);
        levelPanel.add(mediumButton);
        levelPanel.add(hardButton);
        bottomPanel.add(levelPanel, BorderLayout.NORTH);

        // Create and style the start button
        JPanel startPanel = new JPanel();
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setFocusPainted(false);
        bottomPanel.add(startButton, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);
        JLabel easy = new JLabel("Easy");
        JLabel medium = new JLabel("Medium");
        JLabel hard = new JLabel("Hard");
        easyButton.addActionListener((ActionEvent e) -> {
            bottomPanel.add(easy);
            difficulty = 1;
        });
        mediumButton.addActionListener((ActionEvent e) -> {
            bottomPanel.add(medium);
            difficulty = 2;
        });
        hardButton.addActionListener((ActionEvent e) -> {
            bottomPanel.add(hard);
            difficulty = 3;
        });

        // Button click action to start the game
        startButton.addActionListener((ActionEvent e) -> {
            
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                // Dispose the welcome screen and start the main game window
                dispose();
                new SudokuMain(playerName,difficulty); // Pass the player's name to the main game window
                
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        

        // Make the welcome screen visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the constructor of the WelcomeScreen
        SwingUtilities.invokeLater(WelcomeScreen::new); // Use method reference for simplicity
    }
}
