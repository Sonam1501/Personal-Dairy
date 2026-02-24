package Dairy;

import javax.swing.*;
import java.awt.*;

public class ModernMessagebox extends JDialog {

    public ModernMessagebox(JFrame parent, String title, String message, Color color, String iconText) {
        super(parent, true);
        setUndecorated(true);
        setSize(380,190);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

        // Header
        JPanel header = new JPanel();
        header.setBackground(color);
        header.setPreferredSize(new Dimension(380,45));

        JLabel titleLbl = new JLabel(" " + title);
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(titleLbl);

        // Message Area
        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);

        JLabel icon = new JLabel(iconText);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 40));
        icon.setForeground(color);

        JLabel msg = new JLabel(message);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        center.add(icon);
        center.add(msg);

        // Button
        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setBackground(color);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ok.setPreferredSize(new Dimension(90,35));
        ok.addActionListener(e -> dispose());

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(ok);

        card.add(header, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        add(card);
    }

    public static void success(JFrame parent, String msg) {
        new ModernMessagebox(parent,"SUCCESS",msg,
                new Color(46,204,113),"✔").setVisible(true);
    }

    public static void error(JFrame parent, String msg) {
        new ModernMessagebox(parent,"ERROR",msg,
                new Color(231,76,60),"✖").setVisible(true);
    }

    public static void info(JFrame parent, String msg) {
        new ModernMessagebox(parent,"INFO",msg,
                new Color(52,152,219),"ℹ").setVisible(true);
    }
}
