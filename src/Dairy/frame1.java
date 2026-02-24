package Dairy;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class frame1 extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JCheckBox showPass, remember;
    Connection con;

    public frame1() {

        setIconImage(Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\sonam\\Downloads\\icons8-login-90.png"));

        connectDB();

        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(100,120,135));

        JPanel card = new JPanel(null);
        card.setBounds(80,40,440,220);
        card.setBackground(Color.white);
        getContentPane().add(card);

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(0,0,290,220);
        leftPanel.setBackground(Color.white);
        card.add(leftPanel);

        usernameField = new JTextField();
        usernameField.setBounds(40,30,210,40);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        leftPanel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(40,80,210,35);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        leftPanel.add(passwordField);

        // 👁 show password
        showPass = new JCheckBox("Show Password");
        showPass.setBounds(40,120,150,20);
        showPass.setBackground(Color.white);
        leftPanel.add(showPass);

        showPass.addActionListener(e -> {
            if(showPass.isSelected())
                passwordField.setEchoChar((char)0);
            else
                passwordField.setEchoChar('•');
        });

        // remember me
        remember = new JCheckBox("Remember me");
        remember.setBounds(40,145,150,20);
        remember.setBackground(Color.white);
        leftPanel.add(remember);

        // forgot password
        JButton forgotBtn = new JButton("Forgot Password?");
        forgotBtn.setBounds(40,170,160,20);
        forgotBtn.setBorder(null);
        forgotBtn.setForeground(Color.BLUE);
        forgotBtn.setBackground(Color.white);
        forgotBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(forgotBtn);

        forgotBtn.addActionListener(e -> forgotPassword());

        // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(290,0,150,220);
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setLayout(new GridLayout(2,1,10,10));
        card.add(rightPanel);

        JButton loginBtn = new JButton("LOGIN");
        JButton registerBtn = new JButton("REGISTER");

        styleButton(loginBtn);
        styleButton(registerBtn);

        rightPanel.add(loginBtn);
        rightPanel.add(registerBtn);

        loginBtn.addActionListener(e -> login());

        // press ENTER to login
        getRootPane().setDefaultButton(loginBtn);

        registerBtn.addActionListener(e -> new Register());

        setVisible(true);
    }

    void styleButton(JButton btn){
        btn.setFocusPainted(false);
        btn.setForeground(Color.white);
        btn.setBackground(new Color(35,45,52));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(Color.white));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    void connectDB() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/diarydb",
                    "root",
                    "root");
        } catch (Exception e) {
            ModernMessagebox.error(this,"Database Connection Failed");
        }
    }

    void login() {

        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if(user.isEmpty() || pass.isEmpty()){
            ModernMessagebox.error(this,"Please enter username and password");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1,user);
            ps.setString(2,pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                ModernMessagebox.success(this,"Login Successful!");

                // open user diary
                new DiaryDBGU(user);

                dispose();

            } else {
                ModernMessagebox.error(this,"Invalid Username or Password");
            }

        } catch (Exception e) {
            ModernMessagebox.error(this,"Login Error");
        }
    }

    // forgot password feature
    void forgotPassword() {

        String user = JOptionPane.showInputDialog(this,"Enter Username");

        if(user == null || user.isEmpty()) return;

        try{
            PreparedStatement ps = con.prepareStatement(
                    "SELECT password FROM users WHERE username=?");
            ps.setString(1,user);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                ModernMessagebox.info(this,
                        "Your password: " + rs.getString(1));
            else
                ModernMessagebox.error(this,"User not found");

        }catch(Exception e){
            ModernMessagebox.error(this,"Error retrieving password");
        }
    }

    public static void main(String[] args) {
        new frame1();
    }
}
