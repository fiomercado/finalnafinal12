package swing;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    
    // Standard colors used throughout the application
    public static final Color PRIMARY_COLOR = new Color(123, 8, 40);
    public static final Color SECONDARY_COLOR = new Color(82, 43, 71);
    public static final Color ACCENT_COLOR = new Color(232, 178, 59);
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    public static final Color LIGHT_GRAY = new Color(240, 240, 240);
    
    // Standard fonts
    public static final Font TITLE_FONT = new Font("Century Gothic", Font.BOLD, 24);
    public static final Font HEADER_FONT = new Font("Century Gothic", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("Century Gothic", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Century Gothic", Font.PLAIN, 12);
    
    /**
     * Creates a standardized section panel with title and content
     */
    public static JPanel createSectionPanel(String title, JPanel contentPanel) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(BACKGROUND_COLOR);
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                title,
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                HEADER_FONT,
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        sectionPanel.add(contentPanel, BorderLayout.CENTER);
        return sectionPanel;
    }
    
    /**
     * Creates a standardized button with consistent styling
     */
    public static JButton createStandardButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text);
        button.setFont(BODY_FONT);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return button;
    }
    
    /**
     * Creates a primary action button (submit, save, etc.)
     */
    public static JButton createPrimaryButton(String text) {
        return createStandardButton(text, PRIMARY_COLOR, Color.WHITE);
    }
    
    /**
     * Creates a secondary action button (cancel, back, etc.)
     */
    public static JButton createSecondaryButton(String text) {
        return createStandardButton(text, LIGHT_GRAY, Color.BLACK);
    }
    
    /**
     * Creates a danger button (delete, sign out, etc.)
     */
    public static JButton createDangerButton(String text) {
        return createStandardButton(text, new Color(220, 53, 69), Color.WHITE);
    }
    
    /**
     * Creates a standardized header panel with gradient background
     */
    public static JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    /**
     * Creates a standardized footer panel with back button
     */
    public static JPanel createFooterPanel(JButton... buttons) {
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(LIGHT_GRAY);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        for (JButton button : buttons) {
            footerPanel.add(button);
        }
        
        return footerPanel;
    }
    
    /**
     * Creates a standardized back button
     */
    public static JButton createBackButton() {
        JButton backButton = new JButton("← Back to Home");
        backButton.setFont(SMALL_FONT);
        backButton.setFocusPainted(false);
        return backButton;
    }
    
    /**
     * Creates a standardized text field with consistent styling
     */
    public static JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    /**
     * Creates a standardized combo box with consistent styling
     */
    public static JComboBox<String> createComboBox(String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(BODY_FONT);
        return comboBox;
    }
    
    /**
     * Creates a standardized text area with consistent styling
     */
    public static JTextArea createTextArea(int rows, int columns) {
        JTextArea textArea = new JTextArea(rows, columns);
        textArea.setFont(BODY_FONT);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return textArea;
    }
} 