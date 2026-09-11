package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UIUtils {
    // UI/UX Pro Max: Softcloud Aesthetic Tokens
    public static final Color COLOR_PRIMARY = new Color(36, 119, 191); // #2477BF
    public static final Color COLOR_SECONDARY = new Color(95, 170, 217); // #5FAAD9
    public static final Color COLOR_BORDER = new Color(130, 192, 217); // #82C0D9
    public static final Color COLOR_BACKGROUND = new Color(242, 242, 242); // #F2F2F2
    public static final Color COLOR_CARD_BG = new Color(255, 255, 255); // #FFFFFF
    
    public static final Color COLOR_TEXT_PRIMARY = new Color(26, 26, 46); // #1A1A2E
    public static final Color COLOR_TEXT_SECONDARY = new Color(90, 90, 122); // #5A5A7A
    public static final Color COLOR_SUCCESS = new Color(40, 167, 69); // #28A745
    public static final Color COLOR_DANGER = new Color(220, 53, 69); // #DC3545

    // UI/UX Pro Max: Clear Typography
    public static final Font FONT_H1 = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_H2 = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);

    public static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(COLOR_PRIMARY.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(COLOR_SECONDARY);
                } else {
                    g2.setColor(getBackground());
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFont(FONT_BODY);
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        // UI/UX Pro Max: 8-point spacing system
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BODY);
        btn.setBackground(COLOR_CARD_BG);
        btn.setForeground(COLOR_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_PRIMARY, 1),
                new EmptyBorder(7, 15, 7, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(color);
        return lbl;
    }

    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_BODY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
        return tf;
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDER, 1, true),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }
}
