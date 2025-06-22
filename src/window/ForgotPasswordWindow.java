package window;

import javax.swing.*;
import java.awt.*;

public class ForgotPasswordWindow extends JFrame {
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JButton updateBtn;
    private ImageIcon logoIcon;

    public ForgotPasswordWindow() {
        // Load logo icon
        logoIcon = new ImageIcon("src/icon/logo1.png");

        setTitle("Forgot Password - EduRise");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setIconImage(logoIcon.getImage());

        // Email Label
        JLabel lblEmail = createModernLabel("Email:");
        lblEmail.setBounds(30, 30, 100, 25);
        add(lblEmail);

        // Email Text Field
        emailField = createModernTextField();
        emailField.setBounds(140, 30, 200, 30);
        add(emailField);

        // New Password Label
        JLabel lblNewPass = createModernLabel("New Password:");
        lblNewPass.setBounds(30, 80, 100, 25);
        add(lblNewPass);

        // New Password Field
        newPasswordField = createModernPasswordField();
        newPasswordField.setBounds(140, 80, 200, 30);
        add(newPasswordField);

        // Update Button
        updateBtn = createModernButton("Update Password");
        updateBtn.setBounds(140, 140, 180, 40);
        updateBtn.addActionListener(e -> updatePassword());
        add(updateBtn);

        setVisible(true);
    }

    private void updatePassword() {
        String email = emailField.getText();
        String newPass = new String(newPasswordField.getPassword());

        if (email.isEmpty() || newPass.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE,
                    logoIcon
            );
        } else {
            // TODO: Connect this logic to your database backend for updating the password.

            JOptionPane.showMessageDialog(
                    this,
                    "Password updated successfully for " + email + "!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE,
                    logoIcon
            );
            dispose();
        }
    }

    // --- Modern Components ---

    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    private JTextField createModernTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(123, 8, 40), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(123, 8, 40), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(123, 8, 40));
        button.setFont(new Font("Century Gothic", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(82, 43, 71));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(123, 8, 40));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ForgotPasswordWindow::new);
    }
}
