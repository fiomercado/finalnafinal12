package panels;

import swing.UIUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpSupportPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public HelpSupportPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = UIUtils.createHeaderPanel("Help & Support Center");

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // FAQ Section
        contentPanel.add(createFAQSection());
        contentPanel.add(Box.createVerticalStrut(20));

        // Contact Information Section
        contentPanel.add(createContactSection());
        contentPanel.add(Box.createVerticalStrut(20));

        // Support Request Section
        contentPanel.add(createSupportRequestSection());

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Footer with back button
        JButton backButton = UIUtils.createBackButton();
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        JPanel footerPanel = UIUtils.createFooterPanel(backButton);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createFAQSection() {
        JPanel faqPanel = new JPanel();
        faqPanel.setLayout(new BoxLayout(faqPanel, BoxLayout.Y_AXIS));
        faqPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        faqPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Frequently Asked Questions",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                UIUtils.HEADER_FONT,
                UIUtils.PRIMARY_COLOR
        ));

        String[][] faqs = {
                {"What is the procedure for submitting a scholarship application?",
                        "To apply for a scholarship, please select the 'Fill-out Form' option on the homepage. Complete all mandatory fields within the application form and submit it upon completion."},
                {"What documentation is required to complete the scholarship application?",
                        "Applicants must provide personal information, details pertaining to their parent(s) or legal guardian(s), and the applicant's academic records."},
                {"How long does the application process take?",
                        "Applications are generally reviewed within a period of two to three weeks following their submission."},
                { "Is it possible to modify an application after it has been submitted?",
                        "No, applications cannot be altered after submission. Applicants are strongly advised to verify the accuracy of all information prior to submitting their application."},
                {"How and when will I be notified of the scholarship decision?",
                        "Scholarship decisions are announced on a monthly basis. Applicants will receive an email notification indicating the status of their application."}
        };

        for (String[] faq : faqs) {
            faqPanel.add(createFAQItem(faq[0], faq[1]));
            faqPanel.add(Box.createVerticalStrut(10));
        }

        return faqPanel;
    }

    private JPanel createFAQItem(String question, String answer) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(new Color(248, 249, 250));
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel questionLabel = new JLabel("Q: " + question);
        questionLabel.setFont(UIUtils.HEADER_FONT);
        questionLabel.setForeground(UIUtils.PRIMARY_COLOR);

        JTextArea answerArea = UIUtils.createTextArea(3, 40);
        answerArea.setText("A: " + answer);
        answerArea.setBackground(new Color(248, 249, 250));
        answerArea.setEditable(false);
        answerArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        itemPanel.add(questionLabel, BorderLayout.NORTH);
        itemPanel.add(answerArea, BorderLayout.CENTER);

        return itemPanel;
    }

    private JPanel createContactSection() {
        JPanel contactPanel = new JPanel(new GridBagLayout());
        contactPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        contactPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Contact Information",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                UIUtils.HEADER_FONT,
                UIUtils.PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Contact details
        String[][] contacts = {
                {"Phone:", "+63 123 456 7890"},
                {"Email:", "support@edurise.com"},
                {"Office Hours:", "Monday - Friday: 8:00 AM - 5:00 PM"},
                {"Address:", "123 Education Street, Quezon City, Metro Manila"}
        };

        for (int i = 0; i < contacts.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel labelIcon = new JLabel(contacts[i][0]);
            labelIcon.setFont(UIUtils.HEADER_FONT);
            contactPanel.add(labelIcon, gbc);

            gbc.gridx = 1;
            JLabel labelText = new JLabel(contacts[i][1]);
            labelText.setFont(UIUtils.BODY_FONT);
            contactPanel.add(labelText, gbc);
        }

        return contactPanel;
    }

    private JPanel createSupportRequestSection() {
        JPanel supportPanel = new JPanel(new BorderLayout());
        supportPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        supportPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Submit a Support Request",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                UIUtils.HEADER_FONT,
                UIUtils.PRIMARY_COLOR
        ));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Form fields
        JTextField nameField = UIUtils.createTextField(20);
        JTextField emailField = UIUtils.createTextField(20);
        JComboBox<String> categoryCombo = UIUtils.createComboBox(new String[]{
                "Application Issue", "Technical Problem", "General Inquiry", "Document Submission", "Other"
        });
        JTextArea messageArea = UIUtils.createTextArea(4, 30);
        messageArea.setBorder(BorderFactory.createLoweredBevelBorder());

        // Layout form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Your Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        formPanel.add(categoryCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Message:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(messageArea), gbc);

        // Submit button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton submitButton = new JButton("Submit Request");
        submitButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        submitButton.setBackground(new Color(123, 8, 40));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().trim().isEmpty() ||
                        emailField.getText().trim().isEmpty() ||
                        messageArea.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(supportPanel,
                            "Please fill in all required fields.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(supportPanel,
                            "Your support request has been submitted successfully!\n" +
                                    "We'll respond to your inquiry within 24 hours.",
                            "Request Submitted",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Clear form
                    nameField.setText("");
                    emailField.setText("");
                    categoryCombo.setSelectedIndex(0);
                    messageArea.setText("");
                }
            }
        });

        buttonPanel.add(submitButton);

        supportPanel.add(formPanel, BorderLayout.CENTER);
        supportPanel.add(buttonPanel, BorderLayout.SOUTH);

        return supportPanel;
    }
}