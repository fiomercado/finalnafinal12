package component;

import swing.ButtonOutline;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class PanelCover extends JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private ActionListener event;
    private MigLayout layout;
    private JLabel title, description, description1, description2, description3;
    private ButtonOutline button;
    private boolean isLogin;

    public PanelCover() {
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]20[]10[]10[]10[]15[]push");
        setLayout(layout);
        initContent();
    }

    private void initContent() {
        title = createLabel("QUALIFICATIONS", new Font("Century Gothic", Font.BOLD, 30));
        description = createLabel("Must be an incoming first-year college technology student.", null);
        description1 = createLabel("Must have a general weighted average of 90% minimum.", null);
        description2 = createLabel("Must not have any failing grade in any subject.", null);
        description3 = createLabel("Must be a natural-born Filipino citizen by birth.", null);

        button = new ButtonOutline();
        button.setBackground(Color.WHITE);
        button.setForeground(Color.WHITE);
        button.setText("SIGN IN");
        button.addActionListener(e -> {
            if (event != null) {
                event.actionPerformed(e);
            }
        });

        add(title, "gapbottom 10");
        add(description);
        add(description1);
        add(description2);
        add(description3);
        add(button, "w 60%, h 40");
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(245, 245, 245));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    public void addEvent(ActionListener event) {
        this.event = event;
    }

    public void fireEvent() {
        if (event != null) {
            event.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }

    public void registerLeft(double v) {
        setLayoutConstraints(-v);
        login(false);
    }

    public void registerRight(double v) {
        setLayoutConstraints(-v);
        login(false);
    }

    public void loginLeft(double v) {
        setLayoutConstraints(v);
        login(true);
    }

    public void loginRight(double v) {
        setLayoutConstraints(v);
        login(true);
    }

    private void setLayoutConstraints(double v) {
        v = Double.valueOf(df.format(v));
        String pad = "pad 0 " + v + "% 0 " + v + "%";
        layout.setComponentConstraints(title, pad);
        layout.setComponentConstraints(description, pad);
        layout.setComponentConstraints(description1, pad);
        layout.setComponentConstraints(description2, pad);
        layout.setComponentConstraints(description3, pad);
    }

    public void login(boolean login) {
        if (this.isLogin != login) {
            if (login) {
                title.setText("Hi, Future Rise Scholar!");
                description.setText("Ready to rise with us?");
                description1.setText("Enter your personal details and join us!");
                description2.setText("Enjoy scholarship perks and academic opportunities.");
                description3.setText("Start your EduRise journey today!");
                button.setText("SIGN UP");
            } else {
                title.setText("QUALIFICATIONS");
                description.setText("Must be an incoming first-year college technology student.");
                description1.setText("Must have a general weighted average of 90% minimum.");
                description2.setText("Must not have any failing grade in any subject.");
                description3.setText("Must be a natural-born Filipino citizen by birth.");
                button.setText("SIGN IN");
            }
            this.isLogin = login;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(123, 8, 40), 0, getHeight(), new Color(82, 43, 71));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
