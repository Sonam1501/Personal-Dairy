package Dairy;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Register extends JFrame {

    JTextField userField;
    JPasswordField passField;
    JPasswordField confirmField;
    Connection con;

    public Register() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sonam\\Downloads\\icons8-registration-96.png"));

        connectDB();
        setSize(420,360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245,247,250));
        getContentPane().setLayout(null);

        // ===== CARD PANEL =====
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(30,20,350,300);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        getContentPane().add(card);

        // ===== TITLE =====
        JLabel title = new JLabel("Create Account");
        title.setBounds(70,15,220,30);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));
        card.add(title);

        // ===== USERNAME =====
        userField = new JTextField();
        userField.setBounds(40,70,260,40);
        styleField(userField, "Username");
        card.add(userField);

        // ===== PASSWORD =====
        passField = new JPasswordField();
        passField.setBounds(40,120,260,40);
        styleField(passField, "Password");
        card.add(passField);

        // ===== CONFIRM PASSWORD =====
        confirmField = new JPasswordField();
        confirmField.setBounds(40,170,260,45);
        styleField(confirmField, "Confirm Password");
        card.add(confirmField);

        // ===== REGISTER BUTTON =====
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(90,225,170,40);
        registerBtn.setFont(new Font("Segoe UI",Font.BOLD,15));
        registerBtn.setFocusPainted(false);
        registerBtn.setBackground(new Color(70,130,180));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBorder(BorderFactory.createEmptyBorder());
        card.add(registerBtn);

        registerBtn.addActionListener(e -> registerUser());

        setVisible(true);
    }

    // ===== FIELD STYLE =====
    void styleField(JTextField field, String title){
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180,180,180)),
                title
        ));
    }

    // ===== DATABASE CONNECTION =====
    void connectDB() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/diarydb",
                    "root",
                    "root");   // change password if needed
        } catch (Exception e) {
            ModernMessagebox.error(this,"Database Connection Failed");
        }
    }

    // ===== REGISTER USER =====
    void registerUser() {

        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());

        if(username.isEmpty() || password.isEmpty()){
            ModernMessagebox.error(this,"All fields required");
            return;
        }

        if(!password.equals(confirm)){
            ModernMessagebox.error(this,"Passwords do not match");
            return;
        }

        try {
            // check duplicate username
            PreparedStatement check = con.prepareStatement(
                    "SELECT * FROM users WHERE username=?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();

            if(rs.next()){
                ModernMessagebox.error(this,"Username already exists");
                return;
            }

            // insert user
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(username,password) VALUES(?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            ModernMessagebox.success(this,"Registration Successful!");
            dispose();

        } catch (Exception e) {
            ModernMessagebox.error(this,"Registration Failed");
        }
    }
}
