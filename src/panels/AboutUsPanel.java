package panels;

import swing.UIUtils;
import javax.swing.*;
import java.awt.*;

public class AboutUsPanel extends JPanel {
    private final Image backgroundImage;

    public AboutUsPanel(CardLayout cardLayout, JPanel mainPanel) {

        backgroundImage = new ImageIcon("EDURISE.png").getImage();

        setLayout(new GridBagLayout());
        setOpaque(false);


        JLabel titleLabel = new JLabel("The EduRise Scholarship Program: Building Dreams Through Education", SwingConstants.CENTER);
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(UIUtils.PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        String htmlText = "<html><div style='text-align: justify; width: 600px;'>"
                + "<p style='font-family: Century Gothic; font-size: 14px; color: #522B47;'>"
                + "The EduRise Scholarship Program: Building Dreams Through Education is dedicated to supporting first-year college students pursuing degrees in technology and science. More than financial aid, it represents a commitment to academic excellence and innovation.<br><br>"
                + "By easing financial challenges, EduRise empowers students to focus on their studies, participate in research, and explore new ideas. Through our application process, we gather important details about each candidate to ensure fair, merit-based selection and maintain clear, open communication throughout their academic journey.<br><br>"
                + "At EduRise, we believe in building brighter futures through education."
                + "</p></div></html>";

        JLabel descriptionLabel = new JLabel(htmlText);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton backButton = UIUtils.createSecondaryButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(buttonPanel);

        add(textPanel, new GridBagConstraints());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}