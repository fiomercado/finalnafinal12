package model;

import com.formdev.flatlaf.FlatLightLaf;
import panels.FillOutFormPanel;
import panels.FinalizeApplicationPanel;
import panels.HelpSupportPanel;
import panels.SignOutPanel;
import panels.ProfileInfoPanel;
import panels.AboutUsPanel;
import panels.DatabaseManagerPanel;
import swing.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private FillOutFormPanel formPanel;
    private FinalizeApplicationPanel finalizePanel;

    private String userName;
    private String userGmail;
    private boolean isAdmin;
    private String appId;

    public MainApp(String userName, String userGmail, String appId, boolean isAdmin) {
        this.userName = userName;
        this.userGmail = userGmail;
        this.appId = appId;
        this.isAdmin = isAdmin;
        
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("defaultFont", new Font("Century Gothic", Font.PLAIN, 14));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Initialize components first
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Then set up the UI
        initializeUI();
    }

    private void initializeUI() {
        setTitle("EDURISE - Scholarship Application System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Create the main layout
        setLayout(new BorderLayout());
        
        // Create white bar at top with logo
        JPanel whiteBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        whiteBar.setBackground(Color.WHITE);
        whiteBar.setPreferredSize(new Dimension(900, 45));

        ImageIcon logoIcon = null;
        try {
            logoIcon = new ImageIcon("src/icon/logoremoveprev.png");
            Image scaledImage = logoIcon.getImage().getScaledInstance(55, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            whiteBar.add(logoLabel);
        } catch (Exception e) {
            System.err.println("Logo image not found");
        }

        // Create gradient status row
        JPanel statusRow = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 8, 40), getWidth(), 0, new Color(82, 43, 71));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        statusRow.setPreferredSize(new Dimension(900, 60));

        // Add welcome title
        JLabel title = new JLabel("Welcome, " + userName + "!");
        title.setFont(new Font("Century Gothic", Font.BOLD, 40));
        title.setForeground(new Color(232, 178, 59));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);

        JPanel leftStatus = new JPanel();
        leftStatus.setLayout(new BoxLayout(leftStatus, BoxLayout.Y_AXIS));
        leftStatus.setOpaque(false);
        leftStatus.add(titlePanel);

        // Add About Us and Profile buttons
        JButton aboutUsBtn = makeFlatLink("About Us");
        aboutUsBtn.addActionListener(e -> cardLayout.show(mainPanel, "AboutUs"));

        ImageIcon profileIcon = null;
        try {
            profileIcon = new ImageIcon("src/icon/profile.png");
            Image resizedImg = profileIcon.getImage().getScaledInstance(53, 53, Image.SCALE_SMOOTH);
            JLabel profileImageLabel = new JLabel(new ImageIcon(resizedImg));
            profileImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            profileImageLabel.setToolTipText("View Profile");
            profileImageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Re-fetch appId to ensure it's up-to-date
                    appId = DatabaseManager.getInstance().getAppIDByEmail(userGmail);

                    // Find and remove the old profile panel to ensure it's always fresh
                    for (Component comp : mainPanel.getComponents()) {
                        if (comp instanceof ProfileInfoPanel) {
                            mainPanel.remove(comp);
                            break;
                        }
                    }

                    ProfileInfoPanel profilePanel = new ProfileInfoPanel(cardLayout, mainPanel, userName, userGmail, appId);
                    mainPanel.add(profilePanel, "Profile");
                    cardLayout.show(mainPanel, "Profile");
                }
            });

            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightPanel.setOpaque(false);
            rightPanel.add(aboutUsBtn);
            rightPanel.add(profileImageLabel);
            statusRow.add(rightPanel, BorderLayout.EAST);
        } catch (Exception e) {
            System.err.println("Profile icon image not found");
        }

        statusRow.add(leftStatus, BorderLayout.WEST);

        // Create info bar
        JPanel infoBar = new JPanel(new BorderLayout());
        infoBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        infoBar.setBackground(new Color(131, 95, 121));

        JLabel nameLabel = new JLabel("Name: " + userName);
        JLabel emailLabel = new JLabel("Gmail: " + userGmail);
        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        infoBar.add(nameLabel, BorderLayout.WEST);
        infoBar.add(emailLabel, BorderLayout.EAST);

        // Create dashboard panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.add(Box.createVerticalStrut(30));

        // Create buttons
        Font largeBoldFont = new Font("Century Gothic", Font.BOLD, 20);
        Font normalFont = new Font("Century Gothic", Font.BOLD, 20);
        JButton btnForm = makeModernButton("Fill-out Form", "src/icon/filloutbg.png", largeBoldFont, Color.BLACK);
        JButton btnUpdateMyApp = makeModernButton("Update Application", "src/icon/finalizebg.png", normalFont, Color.BLACK);

        btnForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUpdateMyApp.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashboardPanel.add(btnForm);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(btnUpdateMyApp);
        dashboardPanel.add(Box.createVerticalStrut(20));

        // Create gradient footer
        JPanel footer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 8, 40), getWidth(), 0, new Color(82, 43, 71));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        footer.setPreferredSize(new Dimension(900, 30));

        JButton help = makeFlatLink("Help & Support");
        JButton signOut = makeFlatLink("Sign out");
        help.addActionListener(e -> cardLayout.show(mainPanel, "Help"));
        setupSignOutButton(signOut);

        footer.add(help, BorderLayout.WEST);
        footer.add(signOut, BorderLayout.EAST);

        // Create other panels
        finalizePanel = new FinalizeApplicationPanel(cardLayout, mainPanel, userGmail);
        formPanel = new FillOutFormPanel(cardLayout, mainPanel, userGmail, data -> {
            // When a new form is submitted, save the data and return to the home screen
            this.appId = data.getAppID();
            formPanel.saveFormDataToDatabase(data);
            JOptionPane.showMessageDialog(this, "Application submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Home");
        });
        HelpSupportPanel helpPanel = new HelpSupportPanel(cardLayout, mainPanel);
        SignOutPanel signOutPanel = new SignOutPanel(cardLayout, mainPanel);
        AboutUsPanel aboutUsPanel = new AboutUsPanel(cardLayout, mainPanel);
        // Retrieve the user's StudID for filtering
        String userStudID = DatabaseManager.getInstance().getStudIDByEmail(userGmail);
        DatabaseManagerPanel dbManagerPanel = new DatabaseManagerPanel(cardLayout, mainPanel, userGmail, userStudID);

        // Create home panel with dashboard
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(dashboardPanel, BorderLayout.CENTER);

        // Add panels to card layout
        mainPanel.add(homePanel, "Home");
        mainPanel.add(formPanel, "Form");
        mainPanel.add(finalizePanel, "Finalize");
        mainPanel.add(helpPanel, "Help");
        mainPanel.add(signOutPanel, "SignOut");
        mainPanel.add(aboutUsPanel, "AboutUs");
        mainPanel.add(dbManagerPanel, "DatabaseManager");

        // Add button listeners
        btnForm.addActionListener(e -> {
            String studId = DatabaseManager.getInstance().getStudIDByEmail(userGmail);
            if (studId != null && DatabaseManager.getInstance().applicationExistsForStudID(studId)) {
                JOptionPane.showMessageDialog(this,
                    "You already have an application. Please use the 'View/Update My Application' button to make changes.",
                    "Application Exists",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                cardLayout.show(mainPanel, "Form");
            }
        });

        btnUpdateMyApp.addActionListener(e -> {
            String studId = DatabaseManager.getInstance().getStudIDByEmail(userGmail);
            if (studId != null && DatabaseManager.getInstance().applicationExistsForStudID(studId)) {
                FormData existingData = DatabaseManager.getInstance().getFormDataByEmail(userGmail);
                if (existingData != null) {
                    finalizePanel.setFormData(existingData);
                    cardLayout.show(mainPanel, "Finalize");
                } else {
                    JOptionPane.showMessageDialog(this, "Could not load your application data. Please contact support.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "You do not have an application to update. Please fill out the form first.", "No Application Found", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add components to frame
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(whiteBar, BorderLayout.NORTH);
        topContainer.add(statusRow, BorderLayout.CENTER);
        topContainer.add(infoBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "Home");
    }

    private JButton makeFlatLink(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Century Gothic", Font.BOLD, 12));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton makeModernButton(String text, String iconPath, Font font, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(textColor);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setIconTextGap(15);
        } catch (Exception e) {
            System.err.println("Button icon not found: " + iconPath);
        }

        return button;
    }

    private void setupSignOutButton(JButton signOut) {
        signOut.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to sign out?",
                "Confirm Sign Out",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new window.LoginWindow().setVisible(true);
            }
        });
    }

    public void showApp() {
        setVisible(true);
    }

    private JLabel makeStatusLabel(String text, Color color, boolean bold) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Century Gothic", bold ? Font.BOLD : Font.PLAIN, 12));
        return label;
    }
}