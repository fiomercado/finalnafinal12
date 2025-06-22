package component;

import swing.Button;
import swing.MyPasswordField;
import swing.MyTextField;
import model.UserManager;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class PanelLoginAndRegister extends JLayeredPane {

    public interface LoginSuccessListener {
        void onLoginSuccess(String email, String userName, String appId);
    }

    private LoginSuccessListener loginSuccessListener;
    private UserManager userManager;

    private final JPanel loginPanel;
    private final JPanel registerPanel;
    private final ImageIcon logoIcon;

    private static final Color PRIMARY_COLOR = new Color(0, 50, 93);
    private static final Color TEXT_COLOR = new Color(250, 250, 250);
    private static final Font HEADER_FONT = new Font("Century Gothic", Font.BOLD, 30);
    private static final Font LINK_FONT = new Font("Century Gothic", Font.BOLD, 12);

    public PanelLoginAndRegister() {
        logoIcon = new ImageIcon("src/icon/logo1.png");
        userManager = UserManager.getInstance();

        loginPanel = new JPanel();
        registerPanel = new JPanel();

        initComponents();
        initRegisterPanel();
        initLoginPanel();

        showRegister(true);
    }

    public void setLoginSuccessCallback(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    private void initComponents() {
        setLayout(new CardLayout());
        loginPanel.setBackground(Color.WHITE);
        registerPanel.setBackground(Color.WHITE);
        add(loginPanel, "login");
        add(registerPanel, "register");
    }

    private void initRegisterPanel() {
        registerPanel.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));

        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        registerPanel.add(titleLabel);

        final MyTextField txtName = new MyTextField();
        txtName.setPrefixIcon(new ImageIcon("src/icon/user.png"));
        txtName.setHint("Name");
        registerPanel.add(txtName, "w 60%");

        final MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon("src/icon/mail.png"));
        txtEmail.setHint("Email");
        registerPanel.add(txtEmail, "w 60%");

        final MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon("src/icon/pass.png"));
        txtPass.setHint("Password");
        registerPanel.add(txtPass, "w 60%");

        Button btnSignUp = new Button();
        btnSignUp.setBackground(PRIMARY_COLOR);
        btnSignUp.setForeground(TEXT_COLOR);
        btnSignUp.setText("SIGN UP");
        registerPanel.add(btnSignUp, "w 40%, h 40");

        btnSignUp.addActionListener(e -> {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String password = new String(txtPass.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all fields.",
                        "Incomplete Information",
                        JOptionPane.WARNING_MESSAGE,
                        logoIcon);
                return;
            }

            // Check if user already exists
            if (userManager.userExists(email)) {
                JOptionPane.showMessageDialog(this,
                        "An account with this email already exists.\nPlease use a different email or sign in.",
                        "Account Already Exists",
                        JOptionPane.WARNING_MESSAGE,
                        logoIcon);
                return;
            }

            // Register the user
            boolean success = userManager.registerUser(name, email, password);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Account successfully created!\n\nWelcome, " + name + "!\nYour email: " + email + "\n\nYou may now sign in.",
                        "Account Created",
                        JOptionPane.INFORMATION_MESSAGE,
                        logoIcon);

                // Clear the form
                txtName.setText("");
                txtEmail.setText("");
                txtPass.setText("");

                showRegister(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to create account. Please try again.",
                        "Registration Failed",
                        JOptionPane.ERROR_MESSAGE,
                        logoIcon);
            }
        });
    }

    private void initLoginPanel() {
        loginPanel.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));

        JLabel titleLabel = new JLabel("SIGN IN");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);
        loginPanel.add(titleLabel);

        final MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon("src/icon/mail.png"));
        txtEmail.setHint("Email");
        loginPanel.add(txtEmail, "w 60%");

        final MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon("src/icon/pass.png"));
        txtPass.setHint("Password");
        loginPanel.add(txtPass, "w 60%");

        JButton btnForgot = new JButton("Forgot your password?");
        btnForgot.setForeground(new Color(100, 100, 100));
        btnForgot.setFont(LINK_FONT);
        btnForgot.setContentAreaFilled(false);
        btnForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginPanel.add(btnForgot);

        btnForgot.addActionListener(e -> {
            window.ForgotPasswordWindow forgotPassWindow = new window.ForgotPasswordWindow();
            forgotPassWindow.setVisible(true);
        });

        Button btnSignIn = new Button();
        btnSignIn.setBackground(PRIMARY_COLOR);
        btnSignIn.setForeground(TEXT_COLOR);
        btnSignIn.setText("SIGN IN");
        loginPanel.add(btnSignIn, "w 40%, h 40");

        btnSignIn.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPass.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter both email and password.",
                        "Incomplete Information",
                        JOptionPane.WARNING_MESSAGE,
                        logoIcon);
                return;
            }

            UserManager.UserData userData = userManager.authenticateUser(email, password);
            
            if (userData != null) {
                // ✅ Modern dialog replacement
                showModernSuccessDialog(SwingUtilities.getWindowAncestor(this), logoIcon, "Welcome back, " + userData.getName() + "!");

                if (loginSuccessListener != null) {
                    loginSuccessListener.onLoginSuccess(email, userData.getName(), userData.getAppId());
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid email or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE,
                        logoIcon);
            }
        });
    }

    public void showRegister(boolean show) {
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, show ? "register" : "login");
    }

    // ✅ Modern Success Dialog
    private void showModernSuccessDialog(Window parent, ImageIcon icon, String message) {
        JDialog dialog = new JDialog(parent, "Login Successful", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setLayout(null);
        panel.setBounds(0, 0, 350, 200);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(20, 30, 50, 50);
        panel.add(iconLabel);

        JLabel messageLabel = new JLabel("<html><b>" + message + "</b></html>");
        messageLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        messageLabel.setBounds(80, 40, 240, 30);
        panel.add(messageLabel);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        okButton.setBackground(new Color(123, 8, 40));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        okButton.setBounds(110, 120, 120, 35);
        okButton.addActionListener(e -> dialog.dispose());
        panel.add(okButton);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
