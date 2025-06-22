package panels;

import swing.UIUtils;
import javax.swing.*;
import java.awt.*;

public class SignOutPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public SignOutPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        ImageIcon icon = new ImageIcon("");
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Sign Out");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(UIUtils.PRIMARY_COLOR);

        JLabel messageLabel = new JLabel("Are you sure you want to sign out?");
        messageLabel.setFont(UIUtils.BODY_FONT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signOutButton = UIUtils.createDangerButton("Yes, Sign Out");
        JButton cancelButton = UIUtils.createSecondaryButton("Cancel");

        signOutButton.setPreferredSize(new Dimension(160, 35));
        cancelButton.setPreferredSize(new Dimension(120, 35));

        signOutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "You will be logged out of the application.\nAny unsaved changes will be lost.",
                    "Confirm Sign Out",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "You have been successfully signed out.\nThank you for using EduRise Scholarship Program!",
                        "Signed Out",
                        JOptionPane.INFORMATION_MESSAGE
                );

                System.exit(0);
            }
        });

        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        buttonPanel.add(signOutButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(cancelButton);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        centerPanel.add(contentPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Footer with back button
        JButton backButton = UIUtils.createBackButton();
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        JPanel footerPanel = UIUtils.createFooterPanel(backButton);
        add(footerPanel, BorderLayout.SOUTH);
    }
}