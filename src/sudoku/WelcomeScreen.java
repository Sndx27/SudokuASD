package sudoku;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JButton startButton;

    public WelcomeScreen() {
        // Start playing music once when the welcome screen is shown
        MusicPlayer.playMusic("src\\audio\\The Notebooks - Stray Game OST (Original Soundtrack) (1).wav"); // Replace with your music file path

        // Set up the welcome screen JFrame
        setTitle("Welcome to Sudoku");
        setSize(800, 500); // Size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        // Create a panel with GIF as background
        JPanel panel = new ImagePanel("src\\images\\catBG.gif"); // Your GIF file path (1920x900)
        panel.setLayout(new BorderLayout(10, 10)); // Use BorderLayout for a clean layout

        // Create and style the main title "SudoPaw"
        BufferedImage titleImage = loadImage("src\\images\\TitleFPASD.png"); // Load the PNG image (350x97)

        // Slice out "SudoPaw" part of the image (adjust the coordinates and size)
        Image sudoPawImage = titleImage.getSubimage(0, 0, 350, 97); // Slicing title (adjust as per requirement)
        ImageIcon sudoPawIcon = new ImageIcon(sudoPawImage);

        // Create a JLabel to display the sliced "SudoPaw" part
        JLabel sudoPawLabel = new JLabel(sudoPawIcon);
        sudoPawLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the image

        // Create the panel to add the "SudoPaw" label and position it 1/4 from top
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); // Make title background transparent
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the title panel
        titlePanel.add(sudoPawLabel);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Add space between title and input form using a simple spacer
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false); // Transparent spacer
        panel.add(spacerPanel, BorderLayout.CENTER);

        // Create a panel for the name input and label, positioned below the "SudoPaw" title
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the input panel
        inputPanel.setOpaque(false); // Make the input panel transparent so the background shows

        // Add a fixed space between the title and the input field (around 1/4 from bottom + 50px)
        inputPanel.add(Box.createVerticalStrut(450)); // Adjust the space (290px for better positioning)

        // Label for "Enter your name:"
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(Color.WHITE); // White text to be visible over the background
        inputPanel.add(nameLabel);

        // Create the text field for name input with borderless style
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(250, 30)); // Adjust size of the input field
        nameField.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white background for input field
        nameField.setForeground(Color.BLACK); // Text color black
        nameField.setBorder(null); // Remove the border
        nameField.setOpaque(false); // Ensure the background is fully transparent
        inputPanel.add(nameField); // Add name field to the panel

        // Add the input panel to the main panel
        panel.add(inputPanel, BorderLayout.CENTER);

        // Create and style the start button
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        panel.add(startButton, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);

        // Button click action to start the game
        startButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                // Dispose the welcome screen and start the main game window
                dispose();
                new SudokuMain(playerName); // Pass the player's name to the main game window
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

    // Helper method to load the image
    private BufferedImage loadImage(String path) {
        try {
            return javax.imageio.ImageIO.read(new java.io.File(path)); // Load the image from the file
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
            return null;
        }
    }

    // Inner class for displaying the background GIF
    private class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("Background image not found: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Fill entire panel with GIF
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeScreen::new); // Use method reference for simplicity
    }
}
