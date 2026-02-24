package Dairy;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ChangePassword extends JFrame {

    JPasswordField oldPass, newPass;
    Connection con;
    String username;

    public ChangePassword(String username) {
        this.username = username;

        setTitle("Change Password");
        setSize(350,230);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Old Password"));
        oldPass = new JPasswordField();
        add(oldPass);

        add(new JLabel("New Password"));
        newPass = new JPasswordField();
        add(newPass);

        JButton changeBtn = new JButton("Change");
        add(changeBtn);

        changeBtn.addActionListener(e -> change());

        connectDB();
        setVisible(true);
    }

    void connectDB(){
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/diarydb","root","root");
        }catch(Exception e){}
    }

    void change(){
        try{
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE users SET password=? WHERE username=? AND password=?");
            ps.setString(1,new String(newPass.getPassword()));
            ps.setString(2,username);
            ps.setString(3,new String(oldPass.getPassword()));

            if(ps.executeUpdate()>0)
                ModernMessagebox.success(this,"Password Changed");
            else
                ModernMessagebox.error(this,"Wrong Old Password");

        }catch(Exception e){}
    }
}
