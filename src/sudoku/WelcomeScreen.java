/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 - 5026231066 - Burju Ferdinand Harianja
 * 2 - 5026231132 - Clay Amsal Sebastian Hutabarat
 * 3 - 5026213181 - Sandythia Lova Ramadhani Krisnaprana
 */
package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField nameField;
    private JButton startButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private int difficulty = 0;

    public WelcomeScreen() {
        MusicPlayer.playMusic("src\\audio\\The Notebooks - Stray Game OST (Original Soundtrack) (1).wav");
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
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Center the input panel
        inputPanel.setOpaque(false); // Make the input panel transparent so the background shows

        // Add a fixed space between the title and the input field (around 1/4 from bottom + 50px)
        inputPanel.add(Box.createVerticalStrut(455)); // Adjust the space (290px for better positioning)

        // Label for "Enter your name:"
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(Color.WHITE); // White text to be visible over the background
        inputPanel.add(nameLabel);

        // Add horizontal strut to create space on the left
        inputPanel.add(Box.createHorizontalStrut(165)); // Adjust this value to move the field left or right

        // Create the text pane for name input with borderless style
        JTextPane nameField = new JTextPane();
        nameField.setPreferredSize(new Dimension (133, 15)); // Ukuran input field yang lebih pendek
        nameField.setBackground(new Color(0, 0, 0, 30)); // Latar belakang semi-transparan
        nameField.setForeground(Color.BLACK); // Warna teks hitam
        nameField.setBorder(null); // Hapus border
        nameField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); // Set orientation
        nameField.setCaretPosition(0); // Set caret position to the start

        // Center the text in the JTextPane
        StyledDocument doc = nameField.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        inputPanel.add(nameField); // Tambahkan field nama ke panel


        // Add the input panel to the main panel
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

        // Update the action listeners for the difficulty buttons
        easyButton.addActionListener((ActionEvent e) -> {
            updateDifficultyButtonStyles(easyButton); // Update styles for easy button
            difficulty = 1;
        });
        mediumButton.addActionListener((ActionEvent e) -> {
            updateDifficultyButtonStyles(mediumButton); // Update styles for medium button
            difficulty = 2;
        });
        hardButton.addActionListener((ActionEvent e) -> {
            updateDifficultyButtonStyles(hardButton); // Update styles for hard button
            difficulty = 3;
        });

        // Create and style the start button
        JPanel startPanel = new JPanel();
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
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
        startButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty() && difficulty!=0) {
                // Dispose the welcome screen and start the main game window
                dispose();
                new SudokuMain(playerName,difficulty); // Pass the player's name to the main game window
            } else {
                if(playerName.isEmpty()){
                    JOptionPane.showMessageDialog(
                    this,
                    "Please enter your name.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
                );
                }else if(difficulty==0){
                    JOptionPane.showMessageDialog(
                    this,
                    "Please select level.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
                );
                }
                
            }
        });
        

        // Make the welcome screen visible
        setVisible(true);
    }
    class ShadowTextField extends JTextField {
        private Color shadowColor = new Color(0, 0, 0, 30); // Warna bayangan dengan transparansi
    
        public ShadowTextField() {
            super();
            setOpaque(false); // Pastikan latar belakang transparan
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            // Gambar bayangan
            g.setColor(shadowColor);
            g.fillRoundRect(1, 5, getWidth() - 10, getHeight() - 10, 10, 10); // Gambar bayangan dengan sudut melengkung
    
            // Gambar teks field
            super.paintComponent(g);
        }
    }
    // Method to update button styles
    private void updateDifficultyButtonStyles(JButton selectedButton) {
        // Reset all buttons to default style
        easyButton.setBackground(null);
        mediumButton.setBackground(null);
        hardButton.setBackground(null);
        
        // Set the selected button to a darker color
        selectedButton.setBackground(Color.GRAY); // Change to a darker color
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
