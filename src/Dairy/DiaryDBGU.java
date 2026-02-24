package Dairy;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;

public class DiaryDBGU extends JFrame {

    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> titleList = new JList<>(listModel);
    JTextArea noteArea = new JTextArea();
    JTextField titleField = new JTextField();
    JTextField searchField = new JTextField();

    Connection con;
    int selectedId = -1;

    String username;
    int userId;

    public DiaryDBGU(String username) {

        this.username = username;

        setIconImage(Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\sonam\\Downloads\\icons8-diary-94.png"));

        setSize(850,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(245,247,250));

        connectDB();
        fetchUserId();
        loadTitles();

        // ===== HEADER PANEL =====
        JLabel header = new JLabel("Personal Diary", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(new Color(44,62,80));

        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(231,76,60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.addActionListener(e -> {
            dispose();
            new frame1();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(5,10,5,10));
        topPanel.add(welcome, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ===== SEARCH BAR =====
        searchField.setBorder(BorderFactory.createTitledBorder("Search Notes"));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNotes(searchField.getText());
            }
        });

        // ===== LEFT TITLE LIST =====
        titleList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleList.setSelectionBackground(new Color(70,130,180));
        titleList.setFixedCellHeight(30);

        JScrollPane listScroll = new JScrollPane(titleList);
        listScroll.setPreferredSize(new Dimension(220,0));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(searchField, BorderLayout.NORTH);
        leftPanel.add(listScroll, BorderLayout.CENTER);

        // ===== NOTE AREA =====
        noteArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);

        JScrollPane textScroll = new JScrollPane(noteArea);

        titleField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleField.setBorder(BorderFactory.createTitledBorder("Note Title"));

        JPanel editorPanel = new JPanel(new BorderLayout(5,5));
        editorPanel.setBackground(Color.WHITE);
        editorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(10,10,10,10)));

        editorPanel.add(titleField, BorderLayout.NORTH);
        editorPanel.add(textScroll, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JButton addBtn = createButton("Add");
        JButton updateBtn = createButton("Update");
        JButton deleteBtn = createButton("Delete");
//        JButton changePassBtn = createButton("Change Password");

        JToggleButton darkMode = new JToggleButton("Dark Mode");

        darkMode.addActionListener(e -> toggleDarkMode(darkMode.isSelected()));

//        changePassBtn.addActionListener(e ->
//                new ChangePassword(username));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245,247,250));
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
//        buttonPanel.add(changePassBtn);
        buttonPanel.add(darkMode);

        add(leftPanel, BorderLayout.WEST);
        add(editorPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        titleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                loadSelectedNote();
        });

        addBtn.addActionListener(e -> addNote());
        updateBtn.addActionListener(e -> updateNote());
        deleteBtn.addActionListener(e -> deleteNote());

        setVisible(true);
    }

    JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70,130,180));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    void toggleDarkMode(boolean dark){
        Color bg = dark ? new Color(45,45,45) : Color.WHITE;
        Color fg = dark ? Color.WHITE : Color.BLACK;

        noteArea.setBackground(bg);
        noteArea.setForeground(fg);
        titleList.setBackground(bg);
        titleList.setForeground(fg);
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

    void fetchUserId(){
        try{
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                userId = rs.getInt("id");
            }
        }catch(Exception e){
            ModernMessagebox.error(this,"User load error");
        }
    }

    void loadTitles() {
        listModel.clear();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT title FROM notes WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                listModel.addElement(rs.getString("title"));
            }

        } catch (Exception e) {
            ModernMessagebox.error(this,"Error loading titles");
        }
    }

    void searchNotes(String text){
        listModel.clear();
        try{
            PreparedStatement ps = con.prepareStatement(
                    "SELECT title FROM notes WHERE user_id=? AND title LIKE ?");
            ps.setInt(1,userId);
            ps.setString(2,"%"+text+"%");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
                listModel.addElement(rs.getString(1));

        }catch(Exception e){}
    }

    void loadSelectedNote() {
        String title = titleList.getSelectedValue();
        if(title == null) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM notes WHERE title=? AND user_id=?");
            ps.setString(1,title);
            ps.setInt(2,userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                selectedId = rs.getInt("id");
                titleField.setText(rs.getString("title"));
                noteArea.setText(rs.getString("content"));
            }

        } catch (Exception e) {
            ModernMessagebox.error(this,"Error loading note");
        }
    }

    void addNote() {
        String title = titleField.getText();
        String content = noteArea.getText();

        if(title.isEmpty() || content.isEmpty()) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO notes(title,content,user_id) VALUES(?,?,?)");
            ps.setString(1,title);
            ps.setString(2,content);
            ps.setInt(3,userId);
            ps.executeUpdate();

            clearFields();
            loadTitles();

            ModernMessagebox.success(this,"Note Added");

        } catch (Exception e) {
            ModernMessagebox.error(this,"Error adding note");
        }
    }

    void updateNote() {
        if(selectedId == -1) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE notes SET title=?, content=? WHERE id=? AND user_id=?");
            ps.setString(1,titleField.getText());
            ps.setString(2,noteArea.getText());
            ps.setInt(3,selectedId);
            ps.setInt(4,userId);
            ps.executeUpdate();

            loadTitles();
            clearFields();

            ModernMessagebox.info(this,"Note Updated");

        } catch (Exception e) {
            ModernMessagebox.error(this,"Error updating note");
        }
    }

    void deleteNote() {
        if(selectedId == -1) return;

        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM notes WHERE id=? AND user_id=?");
            ps.setInt(1,selectedId);
            ps.setInt(2,userId);
            ps.executeUpdate();

            clearFields();
            loadTitles();

            ModernMessagebox.success(this,"Note Deleted");

        } catch (Exception e) {
            ModernMessagebox.error(this,"Error deleting note");
        }
    }

    void clearFields(){
        titleField.setText("");
        noteArea.setText("");
        selectedId = -1;
    }
}
